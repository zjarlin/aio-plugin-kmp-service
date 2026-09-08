# AIO Kotlin Multiplatform 进程插件

这是由 JetBrains Kotlin Toolchain 构建的 AIO `process` 示例插件。语言无关的页面、请求上下文和响应模型位于 `model` 的 commonMain；`service` 使用 JDK 自带 HTTP 服务实现宿主约定。

```bash
./kotlin check
./kotlin package -m service -p jvm -f executable-jar
cp build/tasks/_service_executableJarJvm/service-jvm-executable.jar dist/plugin.jar
```

运行时提供 `GET /health`、`GET /aio/definition`、`POST /aio/action` 和 `/echo`。页面模型、结构化 JSON 事件解析和动作 reducer 位于 commonMain，不在 JVM 进程保存租户状态；AIO 宿主负责持久化、动作白名单、RBAC 和用户/租户上下文注入。插件容器没有外网、宿主文件系统或数据库权限。
