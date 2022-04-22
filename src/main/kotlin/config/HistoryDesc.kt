package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object HistoryDesc : AutoSavePluginConfig("HistoryDesc") {

    @ValueDescription("场数")
    val ranked: String by value<String>("场数：")

    @ValueDescription("最终mmr")
    val mmr: String by value<String>("最终定级：")

    @ValueDescription("最高mmr")
    val max_mmr: String by value<String>("最高mmr：")

    @ValueDescription("K/D")
    val kd: String by value<String>("K/D：")

    @ValueDescription("胜率")
    val win: String by value<String>("胜率：")

}