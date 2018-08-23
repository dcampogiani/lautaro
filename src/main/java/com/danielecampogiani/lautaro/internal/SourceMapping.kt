package com.danielecampogiani.lautaro.internal

import com.danielecampogiani.lautaro.Event

private val HTTP_REGEX = """.*\(http:\/\/(.*)\)""".toRegex()
private const val HTTP_PREFIX = "http://"
private const val ACE_PREFIX = "acestream://"

internal fun map(details: List<PostDetail>): List<Event> {
    val comments = details.lastOrNull()?.data?.children?.mapNotNull {
        it.data?.body
    }.orEmpty()

    TODO()
}

internal fun mapChild(child: PostDetail.Data.Child): Event? {
    val rawText = child.data?.body.orEmpty()
    if (!rawText.containsAtLeastOneLink()) {
        return null
    } else {
        TODO()
    }
}

internal fun String.containsAtLeastOneLink(): Boolean {
    return !this.isBlank() && (this.contains(HTTP_PREFIX, ignoreCase = true) || this.contains(ACE_PREFIX, ignoreCase = true))
}

internal fun String.addHttpLinks(): String {

    val subStrings = this.split("|")

    val formattedSubstrings: List<String> = subStrings.map { subString ->
        val matchResult = HTTP_REGEX.findAll(subString)
        val values = matchResult.toList().firstOrNull()?.groupValues.orEmpty()

        if (values.size > 1) {
            val links = values.toMutableList().apply { removeAt(0) }
            val formattedLinks = links.map {
                subString.replace(
                        HTTP_PREFIX + it,
                        "<a href=\"$HTTP_PREFIX$it\">$HTTP_PREFIX$it</a>")
            }
            formattedLinks.joinToString()

        } else subString

    }

    return formattedSubstrings.joinToString(separator = "|")
}