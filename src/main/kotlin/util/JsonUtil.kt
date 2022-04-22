package cf.liyu.util

import cf.liyu.bean.EmeaBean
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.config.HistoryDesc
import cf.liyu.config.PreviewDescConfig

class JsonUtil {
    fun fuckDataFromId(data: R6Bean): String {
        val struilder = StringBuilder()
        struilder.appendLine("${PreviewDescConfig.nickname}：" + data.payload.user.nickname)
        struilder.append("${PreviewDescConfig.used_name}：")
        data.payload.user.aliases.forEach {
            struilder.append(it.nickname + " ")
        }
        struilder.appendLine("\n${PreviewDescConfig.level}：${data.payload.preview[2].value}")
        struilder.appendLine("${PreviewDescConfig.playtime}：${data.payload.preview[1].value}")
        val kd = data.payload.stats.general.kills?.toDouble()?.div(data.payload.stats.general.deaths!!.toDouble())
        struilder.appendLine("${PreviewDescConfig.KD}：${"%.2f".format(kd)}")
        val win = data.payload.stats.general.losses?.plus(data.payload.stats.general.wins!!)?.let {
            data.payload.stats.general.wins?.toDouble()
                ?.div(it.toDouble())
        }?.times(100)
        struilder.appendLine("${PreviewDescConfig.win}：${"%.2f".format(win)}%")
        struilder.appendLine("${PreviewDescConfig.rankKD}：${data.payload.preview[0].value}")
        val rankWin = data.payload.stats.ranked.wins.toDouble()
            .div((data.payload.stats.ranked.wins + data.payload.stats.ranked.losses).toDouble()).times(100)
        struilder.appendLine("${PreviewDescConfig.rankWin}：${"%.2f".format(rankWin)}%")
        struilder.appendLine("${PreviewDescConfig.casual_mmr}：${data.payload.stats.seasonal.casual.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.casual.mmr.toInt())}")
        struilder.appendLine("${PreviewDescConfig.rank_mmr}：${data.payload.stats.seasonal.ranked.mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.mmr.toInt())}")
        struilder.appendLine("${PreviewDescConfig.max_rank_mmr}：${data.payload.stats.seasonal.ranked.max_mmr.toInt()} - ${getRank(data.payload.stats.seasonal.ranked.max_mmr.toInt())}")
        val opList = data.payload.stats.operators.sortedByDescending { it.roundsplayed }
        struilder.appendLine("${PreviewDescConfig.operator}：${opList[0].id}、${opList[1].id}、${opList[2].id}")
        val weaponList = data.payload.stats.weaponDetails.sortedByDescending { it.kills }
        struilder.append("${PreviewDescConfig.weapon}：${weaponList[0].name}、${weaponList[1].name}、${weaponList[2].name}")
        return struilder.toString()
    }

    /*查询历史rank 优先显示亚太战绩*/
    fun fuckDataFromHis(data: List<HistorySeasonBean>): String {
        val strBuilder = StringBuilder()
        data.forEach {
            if (it.apac == null) {
                if (!it.emea.noMatchesPlayed) {
                    strBuilder.appendLine(littleFuckRankData(it.emea))
                }
            } else {
                if (!it.apac.noMatchesPlayed) {
                    strBuilder.appendLine(littleFuckRankData(it.apac))
                } else {
                    if (!it.ncsa.noMatchesPlayed) {
                        strBuilder.appendLine(littleFuckRankData(it.ncsa))
                    } else if (!it.emea.noMatchesPlayed) {
                        strBuilder.appendLine(littleFuckRankData(it.emea))
                    }
                }
            }
        }
        strBuilder.append("=========")
        return strBuilder.toString()
    }

    /*处理各区数据*/
    fun littleFuckRankData(emea: EmeaBean): String {
        val builder = StringBuilder()
        builder.appendLine("===${getSeason(emea.season)}===")
        builder.appendLine(HistoryDesc.ranked + (emea.wins + emea.losses).toString())
        builder.appendLine(HistoryDesc.mmr + getRank(emea.mmr) + " - ${emea.mmr}")
        builder.appendLine(HistoryDesc.max_mmr + getRank(emea.max_mmr) + " - ${emea.max_mmr}")
        val kd = emea.kills.toDouble().div(emea.deaths.toDouble())
        builder.appendLine(HistoryDesc.kd + "%.2f".format(kd))
        val win = emea.wins.toDouble().div((emea.wins + emea.losses).toDouble()) * 100
        builder.append(HistoryDesc.win + "%.2f".format(win) + "%")
        return builder.toString()
    }

    private fun getRank(mmr: Int) = when (mmr) {
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

    fun getSeason(season: Int): String {
        var Yea = season / 4
        var Sea = season % 4
        if (Sea == 0) {
            Sea = 4
        } else {
            Yea++
        }
        return "Y${Yea}S${Sea}"
    }
}