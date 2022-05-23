package cf.liyu.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object ViewMode : AutoSavePluginConfig("ViewMode") {

    @ValueDescription("速览数据使用图片模式：")
    val preview: Boolean by value<Boolean>(false)

    @ValueDescription("赛季数据使用图片模式：")
    val seasonal: Boolean by value<Boolean>(false)

    @ValueDescription("历史数据使用图片模式：")
    val history: Boolean by value<Boolean>(false)

}