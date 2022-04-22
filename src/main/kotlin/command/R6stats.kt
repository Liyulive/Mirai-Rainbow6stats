package cf.liyu.command

import cf.liyu.Rainbow6stats
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.config.CommandConfig
import cf.liyu.util.JsonUtil
import cf.liyu.util.RequestUtil
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.MessageSource.Key.quote

object R6stats : CompositeCommand(
    Rainbow6stats,
    primaryName = "r6stats",
    secondaryNames = CommandConfig.r6stats
) {

    @SubCommand("id")
    suspend fun CommandSender.id(id: String) {
        try {
            val res = RequestUtil().request(id)
            val result = Gson().fromJson(res, R6Bean::class.java)
            when (result.message) {
                "OK" -> {
                    val msg = JsonUtil().fuckDataFromId(result)
                    sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + msg)
                }
                "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")

                else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SubCommand("rank")
    suspend fun CommandSender.rank(id: String) {
        try {
            val res = RequestUtil().request(id)
            val result = JsonParser().parse(res).asJsonObject
            val mssg = result.get("message").asString
            when (mssg) {
                "OK" -> {
                    val payload = result.get("payload").asJsonObject
                    val stats = payload.get("stats").asJsonObject
                    val history = stats.get("history").asJsonObject
                    val rankList = ArrayList<HistorySeasonBean>()
                    for (i in 6..100) {
                        val rankStats = history.get(i.toString()) ?: break
                        rankList.add(Gson().fromJson(rankStats, HistorySeasonBean::class.java))
                    }
                    val relist = rankList.reversed()
                    sendMessage(relist[0].emea.noMatchesPlayed.toString())
                }
                "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")
                else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SubCommand("help")
    suspend fun CommandSender.help() {
        sendMessage(
            "/r6stats id [昵称] | 玩家数据速览\n" +
                    "/r6stats help | 帮助"
        )
    }

//    @SubCommand("reload")
//    suspend fun CommandSender.reload() {
//        CommandConfig.reload()
//        Config.reload()
//        PreviewDescConfig.reload()
//        sendMessage("已重新加载配置文件")
//    }
}