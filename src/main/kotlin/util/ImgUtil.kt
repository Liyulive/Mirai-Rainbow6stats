package cf.liyu.util

import cf.liyu.Rainbow6stats
import cf.liyu.Rainbow6stats.logger
import cf.liyu.bean.R6Bean
import cf.liyu.bean.RankMMR
import cf.liyu.config.PreviewDescConfig
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.*
import java.net.URL
import javax.imageio.ImageIO

class ImgUtil {

    val imgWidth = 800
    val radius = 20
    private val renderingHints = RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    init {
        renderingHints[RenderingHints.KEY_RENDERING] = RenderingHints.VALUE_RENDER_QUALITY
    }

    fun drawViewById(data: R6Bean): BufferedImage {

        val imgHeight = 690
        val contentMargin = 20
        val fontSize = 30

        val image = BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB)
        image.createGraphics().apply {
            setRenderingHints(ImgUtil().renderingHints)
            background(this, imgHeight, Color(234, 237, 244))

            /*header*/
            color = Color.WHITE
            fillRoundRect(contentMargin, contentMargin, imgWidth - contentMargin * 2, 190, radius, radius)
            val user = data.payload!!.user
            val avatar =
                ImageIO.read(URL(user.avatar))
            drawImage(avatar, contentMargin * 2, contentMargin * 2, 100, 100, null)
            font = Font("微软雅黑", Font.PLAIN, fontSize)
            color = Color.BLACK
            val fixedOffsetY = this.fontMetrics.ascent - (this.fontMetrics.height / 2 - fontSize / 2)
            drawString("玩家名：" + user.nickname, 160, 50 + fixedOffsetY)
            drawString("等级：" + data.payload.preview[2].value, 160, 100 + fixedOffsetY)
            drawString("游戏时间：" + data.payload.preview[1].value, 400, 100 + fixedOffsetY)
            val strBuilder = StringBuilder()
            data.payload.user.aliases.forEach {
                strBuilder.append(it.nickname + " ")
            }
            drawString("曾用名：$strBuilder", 40, 160 + fixedOffsetY)
            strBuilder.clear()

            /*rank*/
            color = Color.WHITE
            val rankRecX = 20
            val rankRecY = 230
            val rankWidth = 370
            val rankHeight = 290
            val rankMMR = RankMMR(data.payload.stats.seasonal.ranked.mmr.toInt())
            fillRoundRect(rankRecX, rankRecY, rankWidth, rankHeight, radius, radius)
            color = Color.BLACK
            drawString("排位数据", rankRecX + 20, rankRecY + 50)
            val rankImg = ImageIO.read(Rainbow6stats.dataFolder.resolve(rankMMR.url))
            drawImage(rankImg, rankRecX + 20, rankRecY + 70, 100, 100, null)
            drawString("段位：" + rankMMR.rank, 160, rankRecY + 80 + fixedOffsetY)
            drawString("MMR：" + rankMMR.mmr, 160, rankRecY + 130 + fixedOffsetY)
            val rankWin = data.payload.stats.ranked.wins.toDouble()
                .div((data.payload.stats.ranked.wins + data.payload.stats.ranked.losses).toDouble()).times(100)
            drawString("生涯KD：" + data.payload.preview[0].value, rankRecX + 20, rankRecY + 190 + fixedOffsetY)
            drawString("生涯胜率：" + "%.2f".format(rankWin) + "%", rankRecX + 20, rankRecY + 240 + fixedOffsetY)

            /*casual*/
            val casualRecX = rankRecX + rankWidth + contentMargin
            val casualRecY = rankRecY
            val casualWidth = rankWidth
            val casualMMR = RankMMR(data.payload.stats.seasonal.casual.mmr.toInt())
            color = Color.WHITE
            fillRoundRect(casualRecX, casualRecY, casualWidth, rankHeight, radius, radius)
            color = Color.BLACK
            drawString("休闲数据", casualRecX + 20, casualRecY + 50)
            val causalImg = ImageIO.read(Rainbow6stats.dataFolder.resolve(casualMMR.url))
            drawImage(causalImg, casualRecX + 20, casualRecY + 70, 100, 100, null)
            drawString("分段：" + casualMMR.rank, casualRecX + 140, casualRecY + 80 + fixedOffsetY)
            drawString("MMR：" + casualMMR.mmr, casualRecX + 140, casualRecY + 130 + fixedOffsetY)
            val kd = data.payload.stats.general.kills?.toDouble()?.div(data.payload.stats.general.deaths!!.toDouble())
            drawString("生涯KD：" + "%.2f".format(kd), casualRecX + 20, casualRecY + 190 + fixedOffsetY)
            val win = data.payload.stats.general.losses?.plus(data.payload.stats.general.wins!!)?.let {
                data.payload.stats.general.wins?.toDouble()
                    ?.div(it.toDouble())
            }?.times(100)
            drawString("生涯胜率：" + "%.2f".format(win) + "%", casualRecX + 20, casualRecY + 240 + fixedOffsetY)

            /*weapon and operator*/
            val weaponRecX = rankRecX
            val weaponRecY = rankRecY + rankHeight + contentMargin
            val weaponWidth = imgWidth - 2 * contentMargin
            val weaponHeight = 130
            color = Color.WHITE
            fillRoundRect(weaponRecX, weaponRecY, weaponWidth, weaponHeight, radius, radius)
            color = Color.BLACK
            val opList = data.payload.stats.operators.sortedByDescending { it.roundsplayed }
            strBuilder.appendLine("${opList[0].id}、${opList[1].id}、${opList[2].id}")
            drawString("常用干员：" + strBuilder.toString(), weaponRecX + 20, weaponRecY + 50)
            strBuilder.clear()
            val weaponList = data.payload.stats.weaponDetails.sortedByDescending { it.kills }
            strBuilder.append("${weaponList[0].name}、${weaponList[1].name}、${weaponList[2].name}")
            drawString("击杀最多武器：" + strBuilder.toString(), weaponRecX + 20, weaponRecY + 100)
            strBuilder.clear()
        }
        return image
    }

    private fun background(graphics2D: Graphics2D, imgHeight: Int, color: Color) {
        graphics2D.color = color
        graphics2D.fillRect(0, 0, imgWidth, imgHeight)
    }

    fun bufferedImageToInputStream(image: BufferedImage?): InputStream? {
        val os = ByteArrayOutputStream()
        try {
            ImageIO.write(image, "png", os)
            return ByteArrayInputStream(os.toByteArray())
        } catch (e: IOException) {
            logger.error("提示:", e)
        }
        return null
    }


}