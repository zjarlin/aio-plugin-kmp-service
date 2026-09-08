package site.addzero.aio.plugin.kmpservice

data class SceneDefinition(
    val id: String,
    val label: String,
)

data class TextBody(
    val title: String,
    val content: String,
) {
    fun encode(): String =
        """{"kind":"text","title":${title.jsonString()},"content":${content.jsonString()}}"""
}

data class PageDefinition(
    val id: String,
    val label: String,
    val icon: String?,
    val scene: SceneDefinition,
    val requiredPermission: String?,
    val body: TextBody,
) {
    fun encode(): String = buildString {
        append("{\"id\":")
        append(id.jsonString())
        append(",\"label\":")
        append(label.jsonString())
        append(",\"icon\":")
        append(icon?.jsonString() ?: "null")
        append(",\"scene\":{\"id\":")
        append(scene.id.jsonString())
        append(",\"label\":")
        append(scene.label.jsonString())
        append("},\"required_permission\":")
        append(requiredPermission?.jsonString() ?: "null")
        append(",\"body\":")
        append(body.encode())
        append('}')
    }
}

data class RequestContext(
    val tenantId: String,
    val userId: String,
)

data class EchoResponse(
    val method: String,
    val path: String,
    val query: String?,
    val body: String,
    val context: RequestContext,
) {
    fun encode(): String = buildString {
        append("{\"runtime\":\"kotlin-jvm\",\"version\":2,\"method\":")
        append(method.jsonString())
        append(",\"path\":")
        append(path.jsonString())
        append(",\"query\":")
        append(query?.jsonString() ?: "null")
        append(",\"body\":")
        append(body.jsonString())
        append(",\"tenant_id\":")
        append(context.tenantId.jsonString())
        append(",\"user_id\":")
        append(context.userId.jsonString())
        append('}')
    }
}

object KmpProcessPlugin {
    val pages: List<PageDefinition> = listOf(
        PageDefinition(
            id = "kmp-process",
            label = "KMP 服务",
            icon = "server",
            scene = SceneDefinition("community", "社区插件"),
            requiredPermission = null,
            body = TextBody(
                title = "Kotlin 进程插件 v2 已在线",
                content = "v2 页面与后端服务来自同一个独立 Git 仓库，并由 Kotlin Toolchain 构建。",
            ),
        ),
    )

    fun definitionJson(): String =
        pages.joinToString(prefix = "[", postfix = "]", separator = ",") { it.encode() }
}

fun String.jsonString(): String = buildString {
    append('"')
    for (character in this@jsonString) {
        when (character) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\b' -> append("\\b")
            '\u000C' -> append("\\f")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> if (character.code < 0x20) {
                append("\\u")
                append(character.code.toString(16).padStart(4, '0'))
            } else {
                append(character)
            }
        }
    }
    append('"')
}
