package cf.liyu.command

import cf.liyu.Rainbow6stats
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.config.CommandConfig
import cf.liyu.config.Config
import cf.liyu.util.JsonUtil
import cf.liyu.util.RequestUtil
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import java.net.SocketTimeoutException

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
                "Unauthorized" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "API错误")
                else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
            }

        } catch (e: SocketTimeoutException) {
            sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "连接超时，请重试")
            e.printStackTrace()
        } catch (e: Exception) {
            bot?.getFriend(Config.master)?.sendMessage(e.toString())
            e.printStackTrace()
        }
    }

    /*查询排位历史*/
    @SubCommand("his")
    suspend fun CommandSender.his(id: String) {
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
                    val msg = JsonUtil().fuckDataFromHis(relist)
                    sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "\n" + msg)
                }
                "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")
                "Unauthorized" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "API错误")
                else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
            }

        } catch (e: SocketTimeoutException) {
            sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "连接超时，请重试")
            e.printStackTrace()
        } catch (e: Exception) {
            bot?.getFriend(Config.master)?.sendMessage(e.toString())
            e.printStackTrace()
        }
    }

//    @SubCommand("rank")
//    suspend fun CommandSender.rank() {
//
//    }

    @SubCommand("help")
    suspend fun CommandSender.help() {
        sendMessage(
            "/r6stats id [昵称] | 玩家数据速览\n" +
                    "/r6stats his [昵称] | 历史排位数据" +
                    "/r6stats help | 帮助"
        )
    }

}