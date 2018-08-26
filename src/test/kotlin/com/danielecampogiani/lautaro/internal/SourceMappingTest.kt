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

    @Test
    fun addOneAceLink() {
        val input = """English [NBC] [1080p] acestream://f31ffcafb40097acee1a1193cf77b37b6185be96"""

        val output = input.addAceLinks()
        assertEquals("""English [NBC] [1080p] <a href="acestream://f31ffcafb40097acee1a1193cf77b37b6185be96">acestream://f31ffcafb40097acee1a1193cf77b37b6185be96</a>""", output)
    }

    @Test
    fun addTwoAceLinks() {
        val input = """English [NBC] [1080p] acestream://f31ffcafb40097acee1a1193cf77b37b6185be96 [NBC] [540p] acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8"""
        val output = input.addAceLinks()
        assertEquals("""English [NBC] [1080p] <a href="acestream://f31ffcafb40097acee1a1193cf77b37b6185be96">acestream://f31ffcafb40097acee1a1193cf77b37b6185be96</a> [NBC] [540p] <a href="acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8">acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8</a>""", output)
    }

    @Test
    fun testMapChild() {
        val child = PostDetail.Data.Child(PostDetail.Data.Child.Data(
            """**HD** | [Watford vs Crystal Palace **HD** Live](http://buffstreamz.com/watch/soccer-stream.php) | MISR:1mbps | NBC | Clicks:4 | Mobile: Yes | English **HD** | Acestream | English [NBC] [1080p] acestream://f31ffcafb40097acee1a1193cf77b37b6185be96 [NBC] [540p] acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8 [Sky] [1080p] acestream://ef48558600b89ddba78267d103f3016de7180a1d"""
        ))
        val result = mapChild(child)
        assertEquals("""**HD** | [Watford vs Crystal Palace **HD** Live](<a href="http://buffstreamz.com/watch/soccer-stream.php">http://buffstreamz.com/watch/soccer-stream.php</a>) | MISR:1mbps | NBC | Clicks:4 | Mobile: Yes | English **HD** | Acestream | English [NBC] [1080p] <a href="acestream://f31ffcafb40097acee1a1193cf77b37b6185be96">acestream://f31ffcafb40097acee1a1193cf77b37b6185be96</a> [NBC] [540p] <a href="acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8">acestream://03dd1fc19ef40fcee343155143d3212f0749f0e8</a> [Sky] [1080p] <a href="acestream://ef48558600b89ddba78267d103f3016de7180a1d">acestream://ef48558600b89ddba78267d103f3016de7180a1d</a>""", result!!.text)
    }
}