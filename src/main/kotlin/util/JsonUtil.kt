package cf.liyu.util

import cf.liyu.bean.R6Bean

class JsonUtil {
    fun fuckDataFromId(data: R6Bean): String {
        val struilder = StringBuilder()
        struilder.appendLine("玩家昵称：" + data.payload.user.nickname)
        struilder.append("曾用名：")
        data.payload.user.aliases.forEach {
            struilder.append(it.nickname + " ")
        }
        struilder.appendLine("\n总排位K/D：${data.payload.preview[0].value}")
        struilder.appendLine("游戏时间：${data.payload.preview[1].value}")
        struilder.appendLine("等级：${data.payload.preview[2].value}")
        struilder.appendLine("隐藏分：${data.payload.stats.seasonal.casual.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.casual.mmr.toInt())}")
        struilder.appendLine("排位等级：${data.payload.stats.seasonal.ranked.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.mmr.toInt())}")
        struilder.append("最高排位等级：${data.payload.stats.seasonal.ranked.max_mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.max_mmr.toInt())}")
        return struilder.toString()
    }

    fun getRank(mmr: Int) = when (mmr) {
        in 0..1199 -> "紫铜5"
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