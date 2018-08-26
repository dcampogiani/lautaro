package com.danielecampogiani.lautaro

import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

class LautaroTest {

    @Test
    fun testEvents() {

        runBlocking {

            val eventResponse = Lautaro.getEvents()
            val success = eventResponse as Lautaro.Result.Success
            val events = success.value

            assertFalse(events.isEmpty())
            events.forEach {
                assertFalse(it.timeGMT.isBlank())
                assertFalse(it.homeTeam.isBlank())
                assertFalse(it.awayTeam.isBlank())
                assertFalse(it.url.isBlank())
            }
        }
    }

    @Test
    fun testGetFirstEvent() {

        runBlocking {
            val eventResponse = Lautaro.getEvents()
            val successEvent = eventResponse as Lautaro.Result.Success
            val events = successEvent.value
            val firstEvent = events[0]

            val detailResponse = Lautaro.getDetail(firstEvent.url)
            val successDetail = detailResponse as Lautaro.Result.Success
            val detail = successDetail.value


            assertFalse(detail.isEmpty())
            detail.forEach {
                assertFalse(it.text.isBlank())
            }
        }
    }
}