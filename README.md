# AIO Kotlin Multiplatform 进程插件

这是由 JetBrains Kotlin Toolchain 构建的 AIO `process` 示例插件。语言无关的页面、请求上下文和响应模型位于 `model` 的 commonMain；`service` 使用 JDK 自带 HTTP 服务实现宿主约定。

```bash
./kotlin check
./kotlin package -m service -p jvm -f executable-jar
cp build/tasks/_service_executableJarJvm/service-jvm-executable.jar dist/plugin.jar
```

运行时提供 `GET /health`、`GET /aio/definition` 和 `/echo`。AIO 宿主通过受限内部网络转发 `/echo`，并注入当前用户与租户上下文；插件容器没有外网、宿主文件系统或数据库权限。
