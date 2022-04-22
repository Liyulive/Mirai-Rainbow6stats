# Mirai-彩六查询
基于Mirai的简易彩虹六号战绩查询，新手小白学习项目

## 使用方法

1. 将插件下载放到`plugin`目录下
2. 初次加载后去`config`下配置`Config.yml`（https://developers.statsdb.net/reference 下获取API）
3. 启动运行

## 命令

/r6stats id [昵称] 速览数据

/r6stats his [昵称] 历史排位数据

/r6stats help 没什么用的帮助

## 获取API
1. 在StatsDB创建好应用后进入 https://developers.statsdb.net/reference

2. 选择API Info

3. 点击Try然后execute

4. 复制解码好的字符串填配置文件（X-Authorization：后的内容）

   ![getapi](https://github.com/Liyulive/imagebed/blob/main/getapi.png)

## Todo

- [ ] 查询排位详情数据
- [ ] 代理
- [ ] ~
- [x] 速览数据
- [x] 历史排位数据

