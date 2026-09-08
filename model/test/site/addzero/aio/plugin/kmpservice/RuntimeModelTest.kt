package site.addzero.aio.plugin.kmpservice

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RuntimeModelTest {
    @Test
    fun exposesDeclaredPageDefinition() {
        val pages = KmpProcessPlugin.pages()

        assertEquals(listOf("kmp-process"), pages.map(PageDefinition::id))
        assertTrue(KmpProcessPlugin.definitionJson().contains("\"kind\":\"actions\""))
        val result = KmpProcessPlugin.actionResultJson(
            """{"page_id":"kmp-process","action_id":"increment","body":{"state":{"count":2}}}""",
        )
        assertTrue(result.contains("\"content\":\"计数：3\""))
        assertTrue(result.contains("\"state\":{\"count\":3}"))
    }

    @Test
    fun encodesTrustedContextAndEscapesInput() {
        val response = EchoResponse(
            method = "POST",
            path = "/echo",
            query = null,
            body = "say \"hello\"\n",
            context = RequestContext("tenant-a", "user-a"),
        ).encode()

        assertTrue(response.contains("\"body\":\"say \\\"hello\\\"\\n\""))
        assertTrue(response.contains("\"version\":2"))
        assertTrue(response.contains("\"tenant_id\":\"tenant-a\""))
        assertTrue(response.contains("\"user_id\":\"user-a\""))
    }
}
