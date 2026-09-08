package site.addzero.aio.plugin.kmpservice

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RuntimeModelTest {
    @Test
    fun exposesDeclaredPageDefinition() {
        val pages = KmpProcessPlugin.pages

        assertEquals(listOf("kmp-process"), pages.map(PageDefinition::id))
        assertTrue(KmpProcessPlugin.definitionJson().contains("\"kind\":\"text\""))
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
