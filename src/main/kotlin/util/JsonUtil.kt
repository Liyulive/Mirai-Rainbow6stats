package cf.liyu.util

import cf.liyu.bean.EmeaBean
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.bean.SeasonalBean
import cf.liyu.config.HistoryDesc
import cf.liyu.config.PreviewDescConfig
import cf.liyu.config.SeasonDesc
import java.text.SimpleDateFormat
import java.util.*


class JsonUtil {
    fun fuckDataFromId(data: R6Bean): String {
        val struilder = StringBuilder()
        struilder.appendLine(PreviewDescConfig.nickname + data.payload!!.user.nickname)
        struilder.append(PreviewDescConfig.used_name)
        data.payload.user.aliases.forEach {
            struilder.append(it.nickname + " ")
        }
        struilder.appendLine("\n${PreviewDescConfig.level + data.payload.preview[2].value}")
        struilder.appendLine(PreviewDescConfig.playtime+data.payload.preview[1].value)
        val kd = data.payload.stats.general.kills?.toDouble()?.div(data.payload.stats.general.deaths!!.toDouble())
        struilder.appendLine(PreviewDescConfig.KD + "%.2f".format(kd))
        val win = data.payload.stats.general.losses?.plus(data.payload.stats.general.wins!!)?.let {
            data.payload.stats.general.wins?.toDouble()
                ?.div(it.toDouble())
        }?.times(100)
        struilder.appendLine("${PreviewDescConfig.win + "%.2f".format(win)}%")
        struilder.appendLine(PreviewDescConfig.rankKD + data.payload.preview[0].value)
        val rankWin = data.payload.stats.ranked.wins.toDouble()
            .div((data.payload.stats.ranked.wins + data.payload.stats.ranked.losses).toDouble()).times(100)
        struilder.appendLine("${PreviewDescConfig.rankWin + "%.2f".format(rankWin)}%")
        struilder.appendLine(
            "${PreviewDescConfig.casual_mmr + data.payload.stats.seasonal.casual.mmr.toInt()} - ${
                getRankByMmr(
                    data.payload.stats.seasonal.casual.mmr.toInt()
                )
            }"
        )
        struilder.appendLine(
            "${PreviewDescConfig.rank_mmr + data.payload.stats.seasonal.ranked.mmr.toInt()} - ${
                getRankByMmr(
                    data.payload.stats.seasonal.ranked.mmr.toInt()
                )
            }"
        )
        struilder.appendLine(
            "${PreviewDescConfig.max_rank_mmr + data.payload.stats.seasonal.ranked.maxMmr.toInt()} - ${
                getRankByMmr(
                    data.payload.stats.seasonal.ranked.maxMmr.toInt()
                )
            }"
        )
        val opList = data.payload.stats.operators.sortedByDescending { it.roundsplayed }
        struilder.appendLine("${PreviewDescConfig.operator + opList[0].id}???${opList[1].id}???${opList[2].id}")
        val weaponList = data.payload.stats.weaponDetails.sortedByDescending { it.kills }
        struilder.append("${PreviewDescConfig.weapon + weaponList[0].name}???${weaponList[1].name}???${weaponList[2].name}")
        return struilder.toString()
    }

    /*?????????????????????*/
    fun fuckDataFromSeason(seasonal: SeasonalBean): String {
        val strBuilder = StringBuilder()
        strBuilder.appendLine("===??????===")
        if (seasonal.ranked.noMatchesPlayed) {
            strBuilder.appendLine("??????????????????")
        } else {
            val rankBean = seasonal.ranked
            strBuilder.appendLine(SeasonDesc.played + (rankBean.wins + rankBean.losses).toString())
            strBuilder.appendLine(SeasonDesc.abandon + rankBean.abandons)
            strBuilder.appendLine(SeasonDesc.rank + getRank(rankBean.rank) + " - ${rankBean.mmr.toInt()}")
            strBuilder.appendLine(SeasonDesc.maxRank + getRank(rankBean.maxRank) + " - ${rankBean.maxMmr.toInt()}")
            val kd = rankBean.kills.toDouble().div(rankBean.deaths.toDouble())
            strBuilder.appendLine(SeasonDesc.kd + "%.2f".format(kd))
            val win = rankBean.wins.toDouble().div((rankBean.wins + rankBean.losses).toDouble()) * 100
            strBuilder.appendLine(SeasonDesc.win + "%.2f".format(win) + "%")
            strBuilder.appendLine(SeasonDesc.mmrChanged + rankBean.lastMatchMmrChange)
            strBuilder.appendLine(SeasonDesc.banned + rankBean.banned)
        }
        strBuilder.appendLine("===??????===")
        if (seasonal.casual.noMatchesPlayed) {
            strBuilder.appendLine("??????????????????")
        } else {
            val casualBean = seasonal.casual
            strBuilder.appendLine(SeasonDesc.played + (casualBean.wins + casualBean.losses).toString())
            strBuilder.appendLine(SeasonDesc.abandon + casualBean.abandons)
            strBuilder.appendLine(SeasonDesc.casualMmr + getRankByMmr(casualBean.mmr.toInt()) + " - ${casualBean.mmr.toInt()}")
            val kd = casualBean.kills.toDouble().div(casualBean.deaths.toDouble())
            strBuilder.appendLine(SeasonDesc.kd + "%.2f".format(kd))
            val win = casualBean.wins.toDouble().div((casualBean.wins + casualBean.losses).toDouble()) * 100
            strBuilder.appendLine(SeasonDesc.win + "%.2f".format(win) + "%")
            strBuilder.appendLine(SeasonDesc.mmrChanged + casualBean.lastMatchMmrChange)
            strBuilder.appendLine(SeasonDesc.banned + casualBean.banned)
        }
        strBuilder.appendLine("=========")
        strBuilder.append(SeasonDesc.updateTime + getDate(seasonal.ranked.updateTime, SeasonDesc.timeFormat))
        return strBuilder.toString()
    }

    /*????????????rank ????????????????????????*/
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

    /*??????????????????*/
    fun littleFuckRankData(emea: EmeaBean): String {
        val builder = StringBuilder()
        builder.appendLine("===${getSeason(emea.season)}===")
        builder.appendLine(HistoryDesc.ranked + (emea.wins + emea.losses).toString())
        builder.appendLine(HistoryDesc.mmr + getRankByMmr(emea.mmr.toInt()) + " - ${emea.mmr.toInt()}")
        builder.appendLine(HistoryDesc.max_mmr + getRankByMmr(emea.max_mmr.toInt()) + " - ${emea.max_mmr.toInt()}")
        val kd = emea.kills.toDouble().div(emea.deaths.toDouble())
        builder.appendLine(HistoryDesc.kd + "%.2f".format(kd))
        val win = emea.wins.toDouble().div((emea.wins + emea.losses).toDouble()) * 100
        builder.append(HistoryDesc.win + "%.2f".format(win) + "%")
        return builder.toString()
    }

    private fun getRankByMmr(mmr: Int) = when (mmr) {
        0 -> "?????????"
        in 1..1199 -> "??????5"
        in 1200..1299 -> "??????4"
        in 1300..1399 -> "??????3"
        in 1400..1499 -> "??????2"
        in 1500..1599 -> "??????1"
        in 1600..1699 -> "??????5"
        in 1700..1799 -> "??????4"
        in 1800..1899 -> "??????3"
        in 1900..1999 -> "??????2"
        in 2000..2099 -> "??????1"
        in 2100..2199 -> "??????5"
        in 2200..2299 -> "??????4"
        in 2300..2399 -> "??????3"
        in 2400..2499 -> "??????2"
        in 2500..2599 -> "??????1"
        in 2600..2799 -> "??????3"
        in 2800..2999 -> "??????2"
        in 3000..3199 -> "??????1"
        in 3200..3499 -> "??????3"
        in 3500..3799 -> "??????2"
        in 3800..4099 -> "??????1"
        in 4100..4399 -> "??????3"
        in 4400..4699 -> "??????2"
        in 4700..4999 -> "??????1"
        else -> "??????"
    }

    private fun getRank(rank: Int) = when (rank) {
        0 -> "?????????"
        1 -> "??????5"
        2 -> "??????4"
        3 -> "??????3"
        4 -> "??????2"
        5 -> "??????1"
        6 -> "??????5"
        7 -> "??????4"
        8 -> "??????3"
        9 -> "??????2"
        10 -> "??????1"
        11 -> "??????5"
        12 -> "??????4"
        13 -> "??????3"
        14 -> "??????2"
        15 -> "??????1"
        16 -> "??????3"
        17 -> "??????2"
        18 -> "??????1"
        19 -> "??????3"
        20 -> "??????2"
        21 -> "??????1"
        22 -> "??????3"
        23 -> "??????2"
        24 -> "??????1"
        25 -> "??????"
        else -> "??????"
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

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds * 1000
        return formatter.format(calendar.time)
    }
}