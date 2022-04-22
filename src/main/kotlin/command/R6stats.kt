package cf.liyu.command

import cf.liyu.Rainbow6stats
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.command.R6stats.id
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
            if (res == "error") {
                sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "请求错误，请重试！")
            } else {
                val result = Gson().fromJson(res, R6Bean::class.java)
                when (result.message) {
                    "OK" -> {
                        val msg = JsonUtil().fuckDataFromId(result)
                        sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + msg)
                    }
                    "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")

                    else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SubCommand("his")
    suspend fun CommandSender.his(id: String) {
        try {
            val res = RequestUtil().request(id)
            if (res == "error") {
                sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "请求错误，请重试！")
            } else {
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
                        sendMessage(msg)
                    }
                    "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")
                    else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SubCommand("help")
    suspend fun CommandSender.help() {
        sendMessage(
            "/r6stats id [昵称] | 玩家数据速览\n" +
                    "/r6stats his [昵称] | 历史排位数据" +
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