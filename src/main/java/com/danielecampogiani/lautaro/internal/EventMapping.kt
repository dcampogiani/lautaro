package com.danielecampogiani.lautaro.internal

import com.danielecampogiani.lautaro.Event

private val TITLE_REGEX = """\[(\d{2}):(\d{2}) GMT] (.+) vs (.+)""".toRegex()

internal fun map(rootResponse: RootResponse?): List<Event> {
    val posts = rootResponse?.data?.children.orEmpty()
    return posts.mapNotNull(::mapPost)
}

internal fun mapPost(post: RootResponse.Data.Child): Event? {
    val title = post.data?.title ?: return null

    val matchResult = TITLE_REGEX.findAll(title)

    val values = matchResult.toList().firstOrNull()?.groupValues.orEmpty()

    return if (values.isEmpty()) {
        null
    } else {
        val hour = values[1]
        val minutes = values[2]
        val homeTeam = values[3]
        val awayTeam = values[4]

        Event("$hour : $minutes", homeTeam, awayTeam, post.data.url.orEmpty())
    }
}
