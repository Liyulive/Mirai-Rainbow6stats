package cf.liyu.util

import cf.liyu.bean.R6Bean
import cf.liyu.config.PreviewDescConfig

class JsonUtil {
    fun fuckDataFromId(data: R6Bean): String {
        val struilder = StringBuilder()
        struilder.appendLine("${PreviewDescConfig.nickname}：" + data.payload.user.nickname)
        struilder.append("${PreviewDescConfig.used_name}：")
        data.payload.user.aliases.forEach {
            struilder.append(it.nickname + " ")
        }
        struilder.appendLine("\n${PreviewDescConfig.rankKD}：${data.payload.preview[0].value}")
        struilder.appendLine("${PreviewDescConfig.playtime}：${data.payload.preview[1].value}")
        struilder.appendLine("${PreviewDescConfig.level}：${data.payload.preview[2].value}")
        struilder.appendLine("${PreviewDescConfig.casual_mmr}：${data.payload.stats.seasonal.casual.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.casual.mmr.toInt())}")
        struilder.appendLine("${PreviewDescConfig.rank_mmr}：${data.payload.stats.seasonal.ranked.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.mmr.toInt())}")
        struilder.appendLine("${PreviewDescConfig.max_rank_mmr}：${data.payload.stats.seasonal.ranked.max_mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.max_mmr.toInt())}")
        val opList = data.payload.stats.operators.sortedByDescending { it.roundsplayed }
        struilder.appendLine("${PreviewDescConfig.operator}：${opList[0].id}、${opList[1].id}、${opList[2].id}")
        val weaponList = data.payload.stats.weaponDetails.sortedByDescending { it.kills }
        struilder.append("${PreviewDescConfig.weapon}：${weaponList[0].name}、${weaponList[1].name}、${weaponList[2].name}")
        return struilder.toString()
    }

    fun getRank(mmr: Int) = when (mmr) {
        0 -> "未排位"
        in 1..1199 -> "紫铜5"
        in 1200..1299 -> "紫铜4"
        in 1300..1399 -> "紫铜3"
        in 1400..1499 -> "紫铜2"
        in 1500..1599 -> "紫铜1"
        in 1600..1699 -> "青铜5"
        in 1700..1799 -> "青铜4"
        in 1800..1899 -> "青铜3"
        in 1900..1999 -> "青铜2"
        in 2000..2099 -> "青铜1"
        in 2100..2199 -> "白银5"
        in 2200..2299 -> "白银4"
        in 2300..2399 -> "白银3"
        in 2400..2499 -> "白银2"
        in 2500..2599 -> "白银1"
        in 2600..2799 -> "黄金3"
        in 2800..2999 -> "黄金2"
        in 3000..3199 -> "黄金1"
        in 3200..3499 -> "白金3"
        in 3500..3799 -> "白金2"
        in 3800..4099 -> "白金1"
        in 4100..4399 -> "钻石3"
        in 4400..4699 -> "钻石2"
        in 4700..4999 -> "钻石1"
        else -> "冠军"
    }
}