package cf.liyu.command

import cf.liyu.Rainbow6stats
import cf.liyu.bean.HistorySeasonBean
import cf.liyu.bean.R6Bean
import cf.liyu.config.CommandConfig
import cf.liyu.config.Config
import cf.liyu.config.ViewMode
import cf.liyu.util.ImgUtil
import cf.liyu.util.JsonUtil
import cf.liyu.util.RequestUtil
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import java.net.SocketTimeoutException

object R6stats : CompositeCommand(
    Rainbow6stats,
    primaryName = "r6stats",
    secondaryNames = CommandConfig.r6stats
) {

    @SubCommand("id", "preview")
    @Description("战绩速览")
    suspend fun CommandSender.id(id: String) {
        var result = R6Bean(0, "0", "0", null)
        var flg = 0
        sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "正在查询，请稍等")
        do {
            flg++
            try {
                val res = RequestUtil().request(id)
                result = Gson().fromJson(res, R6Bean::class.java)
            } catch (e: SocketTimeoutException) {
                println("连接超时，重试次数：$flg")
                e.printStackTrace()
            } catch (e: Exception) {
                bot?.getFriend(Config.master)?.sendMessage(e.toString())
                e.printStackTrace()
            }
        } while ((result.code == 0) or (flg == 5))

        if (flg == 5) {
            sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "寄，请重试。")
        } else {
            when (result.message) {
                "OK" -> {
                    if (ViewMode.preview) {
                        val img = ImgUtil().drawViewById(result)
                        val imgStream = ImgUtil().bufferedImageToInputStream(img)
                        if (imgStream != null) {
                            sendMessage(At(user!!))
                            subject?.sendImage(imgStream, "png")
                        }
                    } else {
                        val msg = JsonUtil().fuckDataFromId(result)
                        sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + msg)
                    }
                }
                "Not Found" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "查无此人")
                "Unauthorized" -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "API错误")
                else -> sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote() + "未知错误")
            }
        }
    }

    /*查询排位历史*/
    @SubCommand("his", "history")
    @Description("查询历史数据")
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


    @SubCommand("sea", "seasonal")
    @Description("查询本赛季数据")
    suspend fun CommandSender.rank(id: String) {
        try {
            val res = RequestUtil().request(id)
            val result = Gson().fromJson(res, R6Bean::class.java)
            when (result.message) {
                "OK" -> {
                    val msg = JsonUtil().fuckDataFromSeason(result.payload!!.stats.seasonal)
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

    @SubCommand("help")
    suspend fun CommandSender.help() {
        sendMessage(
            "/<r6s|r6stats> <id|preview> [昵称] - 玩家数据速览\n" +
                    "/<r6s|r6stats> <his|history> [昵称] - 历史排位数据\n" +
                    "/<r6s|r6stats> <sea|season> [昵称] - 本赛季数据\n" +
                    "/<r6s|r6stats> help - 帮助"
        )

    }




}