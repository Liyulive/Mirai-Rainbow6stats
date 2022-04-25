package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object SeasonDesc : AutoSavePluginConfig("SeasonDesc") {

    @ValueDescription("场数：")
    val played: String by value<String>("场数：")

    @ValueDescription("弃赛场数：")
    val abandon: String by value<String>("弃赛：")

    @ValueDescription("最终定级：")
    val rank: String by value<String>("定级：")

    @ValueDescription("最高定级：")
    val maxRank: String by value<String>("最高定级：")

    @ValueDescription("KD：")
    val kd: String by value<String>("K/D：")

    @ValueDescription("胜率：")
    val win: String by value<String>("胜率：")

    @ValueDescription("最近一场比赛mmr变化：")
    val mmrChanged: String by value<String>("最近一场比赛mmr变化：")

    @ValueDescription("休闲分：")
    val casualMmr: String by value<String>("休闲分：")

    @ValueDescription("是否禁赛：")
    val banned: String by value<String>("禁赛ing：")

    @ValueDescription("更新时间：")
    val updateTime: String by value<String>("更新时间：")

    @ValueDescription("时间格式（最多精确到秒）：")
    val timeFormat: String by value<String>("M月dd日 hh时mm分ss秒")

}