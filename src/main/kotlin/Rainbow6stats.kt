package cf.liyu

import cf.liyu.command.R6stats
import cf.liyu.config.CommandConfig
import cf.liyu.config.Config
import cf.liyu.config.PreviewDescConfig
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object Rainbow6stats : KotlinPlugin(
    JvmPluginDescription(
        id = "cf.liyu.rainbow6stats",
        version = "0.0.5",
    ) {
        author("立羽")
        info("菜鸟彩虹6号围攻战绩查询插件")
    }
) {
    override fun onEnable() {
        CommandConfig.reload()
        Config.reload()
        PreviewDescConfig.reload()

        R6stats.register()

        logger.info { "R6Stats Plugin loaded" }
    }
}