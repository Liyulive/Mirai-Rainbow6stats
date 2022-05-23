package cf.liyu.util

import cf.liyu.config.Config
import cf.liyu.config.ProxyConfig
import okhttp3.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RequestUtil {
    suspend fun request(id: String): String {
        return suspendCoroutine { continuation ->
            val proxyConfig = ProxyConfig
            val clientBuilder = OkHttpClient().newBuilder()
            when (proxyConfig.type) {
                "HTTP" -> clientBuilder.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyConfig.ip, proxyConfig.port.toInt())))
                "SOCKS" -> clientBuilder.proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxyConfig.ip, proxyConfig.port.toInt())))
                else -> println("DIRECT")
            }
            clientBuilder.connectTimeout(20, TimeUnit.SECONDS)
            clientBuilder.readTimeout(20, TimeUnit.SECONDS)
            clientBuilder.retryOnConnectionFailure(true)
            val client = clientBuilder.build()
            val req =
                Request.Builder().url("https://api.statsdb.net/r6/pc/player/${id}")
                    .method("GET", null)
                    .header("X-Authorization", Config.apiAuth)
                    .build()
            val call = client.newCall(req)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response.body!!.string())
                }
            })
        }
    }
}