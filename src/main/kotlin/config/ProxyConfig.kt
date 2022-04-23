package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object ProxyConfig : AutoSavePluginConfig("ProxyConfig") {

    @ValueDescription("代理模式：DIRECT/HTTP/SOCKS")
    val type: String by value<String>("DIRECT")

    @ValueDescription("IP:")
    val ip: String by value<String>("127.0.0.1")

    @ValueDescription("Port:")
    val port: String by value<String>("7890")

}