package cf.liyu.command

import cf.liyu.Rainbow6stats
import cf.liyu.bean.R6Bean
import cf.liyu.config.CommandConfig
import cf.liyu.config.Config
import cf.liyu.config.PreviewDescConfig
import cf.liyu.util.JsonUtil
import cf.liyu.util.RequestUtil
import com.google.gson.Gson
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.plugins.chat.command.PluginMain.reload

object R6stats : CompositeCommand(
    Rainbow6stats,
    primaryName = "r6stats",
    secondaryNames = CommandConfig.r6stats
) {

    @SubCommand("id")
    suspend fun CommandSender.id(id: String) {
        try {
            val res = RequestUtil().request(id, Config.apiAuth)
            val result = Gson().fromJson(res, R6Bean::class.java)
            when (result.message) {
                "OK" -> {
                    val msg = JsonUtil().fuckDataFromId(result)
                    sendMessage(msg)
                }
                "Not Found" -> sendMessage("查无此人")
                else -> sendMessage("未知错误")
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