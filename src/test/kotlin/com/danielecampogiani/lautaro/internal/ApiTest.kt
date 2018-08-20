package com.danielecampogiani.lautaro.internal

import junit.framework.TestCase.assertFalse
import org.junit.Test

class ApiTest {

    val api = Api()

    @Test
    fun testGetInter() {

        val input = "busan"

        val call = api.getTop()
        val response = call.execute()
        val body = response.body()
        val posts = body?.data?.children.orEmpty()

        val post = posts.firstOrNull { it.data?.title?.contains(input, ignoreCase = true) ?: false }

        val postDetailUrl = post?.data?.url + ".json"

        val detailsCall = api.getDetails(postDetailUrl)
        val detailsResponse = detailsCall.execute()
        val details = detailsResponse.body().orEmpty()

        val comments = details.lastOrNull()?.data?.children?.mapNotNull {
            it.data?.body
        }.orEmpty()

        assertFalse(comments.isEmpty())
    }
}