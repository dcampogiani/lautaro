package com.danielecampogiani.lautaro.internal

import org.junit.Before
import org.junit.Test

class EventMappingKtTest {

    @Before
    fun setUp() {
    }

    @Test
    fun eventTitle() {
        val title = "[10:30 GMT] Bucheon FC 1995 vs Daejeon Citizen FC"
        val post = RootResponse.Data.Child(RootResponse.Data.Child.Data(title, "url"))

        val result = mapPost(post)
    }
}