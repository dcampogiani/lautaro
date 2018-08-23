package com.danielecampogiani.lautaro.internal

import junit.framework.TestCase.assertEquals
import org.junit.Test

class SourceMappingTest {

    @Test
    fun addOneHttpLink() {
        val input = "**HD** Twitch Stream | [ Eleven Sports ] (http://www.blacktiestreams.xyz/stream3) | Clicks : 4 Pop Ads: 2 |  Mobile : ✅ Yes | ENG | MISR:1 MBPS |"

        val result = input.addHttpLinks()

        assertEquals("**HD** Twitch Stream | [ Eleven Sports ] (<a href=\"http://www.blacktiestreams.xyz/stream3\">http://www.blacktiestreams.xyz/stream3</a>) | Clicks : 4 Pop Ads: 2 |  Mobile : ✅ Yes | ENG | MISR:1 MBPS |", result)
    }

    @Test
    fun addTwoHttpLinks() {

        val input = """SD Streams: [Olympiakos Piraeus vs Burnley Eleven Sports](http://moviee.xyz/123app/eleven.html) | ENGLISH | MISR: 700 | [Olympiakos Piraeus vs Burnley](http://moviee.xyz/123app/esbur.html) | ES | MISR: 700

NSWF | Disable Ad-block | Clicks: 3 | Mobile Compatibility= Yes"""

        val output = input.addHttpLinks()
        assertEquals("""SD Streams: [Olympiakos Piraeus vs Burnley Eleven Sports](<a href="http://moviee.xyz/123app/eleven.html">http://moviee.xyz/123app/eleven.html</a>) | ENGLISH | MISR: 700 | [Olympiakos Piraeus vs Burnley](<a href="http://moviee.xyz/123app/esbur.html">http://moviee.xyz/123app/esbur.html</a>) | ES | MISR: 700

NSWF | Disable Ad-block | Clicks: 3 | Mobile Compatibility= Yes""", output)
    }
}