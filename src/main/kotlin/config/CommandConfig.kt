package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object CommandConfig : AutoSavePluginConfig("CommandConfig") {

    @ValueDescription("命令别名")
    val r6stats: Array<String> by value(arrayOf("彩六战绩"))

}