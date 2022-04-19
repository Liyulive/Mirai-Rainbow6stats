package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("Config") {

    @ValueDescription("API X-Authorization（应为Basic XXXXXX）")
    val apiAuth: String by value<String>()
}