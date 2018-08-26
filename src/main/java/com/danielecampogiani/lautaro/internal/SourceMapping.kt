package com.danielecampogiani.lautaro.internal

import com.danielecampogiani.lautaro.Source

private val HTTP_REGEX = """.*\(http://(.*)\)""".toRegex()
private val ACE_REGEX = """.*acestream://(.*)""".toRegex()
private const val HTTP_PREFIX = "http://"
private const val ACE_PREFIX = "acestream://"

internal fun map(details: List<PostDetail>): List<Source> {
    return details.lastOrNull()?.data?.children?.mapNotNull {
        mapChild(it)
    }.orEmpty()
}

internal fun mapChild(child: PostDetail.Data.Child): Source? {
    val rawText = child.data?.body.orEmpty()
    return if (!rawText.containsAtLeastOneLink()) {
        null
    } else {
        Source(rawText.addHttpLinks().addAceLinks())
    }
}

internal fun String.containsAtLeastOneLink(): Boolean {
    return !this.isBlank() && (this.contains(HTTP_PREFIX, ignoreCase = true) || this.contains(ACE_PREFIX, ignoreCase = true))
}

internal fun String.addHttpLinks(): String {
    val delimiter = "|"
    val regex = HTTP_REGEX
    val protocolPrefix = HTTP_PREFIX

    return addLinks(delimiter, regex, protocolPrefix)
}

internal fun String.addAceLinks(): String {

    val delimiter = " "
    val regex = ACE_REGEX
    val protocolPrefix = ACE_PREFIX

    return addLinks(delimiter, regex, protocolPrefix)
}

fun String.addLinks(delimiter: String, regex: Regex, protocolPrefix: String): String {
    val subStrings = this.split(delimiter)

    val formattedSubstrings: List<String> = subStrings.map { subString ->
        val matchResult = regex.findAll(subString)
        val values = matchResult.toList().firstOrNull()?.groupValues.orEmpty()

        if (values.size > 1) {
            val links = values.toMutableList().apply { removeAt(0) }
            val formattedLinks = links.map {
                subString.replace(
                    protocolPrefix + it,
                    "<a href=\"$protocolPrefix$it\">$protocolPrefix$it</a>")
            }
            formattedLinks.joinToString()
        } else subString

    }

    return formattedSubstrings.joinToString(separator = delimiter)
}