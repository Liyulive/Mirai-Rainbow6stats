package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object PreviewDescConfig : AutoSavePluginConfig("PreviewDescConfig") {

    @ValueDescription("玩家昵称：")
    val nickname: String by value<String>("玩家昵称")

    @ValueDescription("曾用名：")
    val used_name: String by value<String>("曾用名")

    @ValueDescription("生涯KD：")
    val KD: String by value<String>("生涯K/D")

    @ValueDescription("生涯胜率：")
    val win: String by value<String>("生涯胜率")

    @ValueDescription("生涯排位KD：")
    val rankKD: String by value<String>("排位生涯K/D")

    @ValueDescription("生涯排位KD：")
    val rankWin: String by value<String>("排位生涯胜率")

    @ValueDescription("游戏时长：")
    val playtime: String by value<String>("游戏时间")

    @ValueDescription("等级：")
    val level: String by value<String>("等级")

    @ValueDescription("休闲分：")
    val casual_mmr: String by value<String>("休闲分")

    @ValueDescription("排位分：")
    val rank_mmr: String by value<String>("排位分")

    @ValueDescription("最高排位分：")
    val max_rank_mmr: String by value<String>("本赛季最高排位分")

    @ValueDescription("生涯常用干员：")
    val operator: String by value<String>("生涯常用干员")

    @ValueDescription("击杀最多武器：")
    val weapon: String by value<String>("击杀最多武器")
}