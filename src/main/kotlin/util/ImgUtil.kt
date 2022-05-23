package cf.liyu.util

import cf.liyu.Rainbow6stats
import cf.liyu.Rainbow6stats.logger
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

    fun drawViewById(): BufferedImage {

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
            val avator =
                ImageIO.read(URL("https://ubisoft-avatars.akamaized.net/b8c8bf52-d91f-4052-80c1-84a99491e05e/default_256_256.png"))
            drawImage(avator, contentMargin * 2, contentMargin * 2, 100, 100, null)
            font = Font("微软雅黑", Font.PLAIN, fontSize)
            color = Color.BLACK
            val fixedOffsetY = this.fontMetrics.ascent - (this.fontMetrics.height / 2 - fontSize / 2)
            drawString("ksen.Cyanide", 160, 50 + fixedOffsetY)
            drawString("等级：233", 160, 100 + fixedOffsetY)
            drawString("游戏时间：906H", 400, 100 + fixedOffsetY)
            drawString("曾用名：liyulive L1yu.Cyanide Stri9i.Cyanide Liyu.Cyanide", 40, 160 + fixedOffsetY)

            /*rank*/
            color = Color.WHITE
            val rankRecX = 20
            val rankRecY = 230
            val rankWidth = 370
            val rankHeight = 290
            fillRoundRect(rankRecX, rankRecY, rankWidth, rankHeight, radius, radius)
            color = Color.BLACK
            drawString("排位数据", rankRecX + 20, rankRecY + 50)
            val rankImg = ImageIO.read(Rainbow6stats.dataFolder.resolve("rank/gold-1.png"))
            drawImage(rankImg, rankRecX + 20, rankRecY + 70, 100, 100, null)
            drawString("段位：黄金2", 160, rankRecY + 80 + fixedOffsetY)
            drawString("MMR：2333", 160, rankRecY + 130 + fixedOffsetY)
            drawString("生涯KD：1.06", rankRecX + 20, rankRecY + 190 + fixedOffsetY)
            drawString("生涯胜率：53.91%", rankRecX + 20, rankRecY + 240 + fixedOffsetY)

            /*casual*/
            val casualRecX = rankRecX + rankWidth + contentMargin
            val casualRecY = rankRecY
            val casualWidth = rankWidth
            color = Color.WHITE
            fillRoundRect(casualRecX, casualRecY, casualWidth, rankHeight, radius, radius)
            color = Color.BLACK
            drawString("排位数据", casualRecX + 20, casualRecY + 50)
            val causalImg = ImageIO.read(Rainbow6stats.dataFolder.resolve("rank/gold-1.png"))
            drawImage(causalImg, casualRecX + 20, casualRecY + 70, 100, 100, null)
            drawString("段位：黄金2", casualRecX + 140, casualRecY + 80 + fixedOffsetY)
            drawString("MMR：2333", casualRecX + 140, casualRecY + 130 + fixedOffsetY)
            drawString("生涯KD：1.06", casualRecX + 20, casualRecY + 190 + fixedOffsetY)
            drawString("生涯胜率：53.91%", casualRecX + 20, casualRecY + 240 + fixedOffsetY)

            /*weapon operator*/
            val weaponRecX = rankRecX
            val weaponRecY = rankRecY + rankHeight + contentMargin
            val weaponWidth = imgWidth - 2 * contentMargin
            val weaponHeight = 130
            color = Color.WHITE
            fillRoundRect(weaponRecX, weaponRecY, weaponWidth, weaponHeight, radius, radius)
            color = Color.BLACK
            drawString("常用干员：thatcher、jager、thermite", weaponRecX + 20, weaponRecY + 50)
            drawString("击杀最多武器：416 CARRBIN、L85A2、AK-12", weaponRecX + 20, weaponRecY + 100)

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