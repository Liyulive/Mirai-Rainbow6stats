package cf.liyu.util

import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RequestUtil {
    suspend fun request(id: String, apiAuth: String): String {
        return suspendCoroutine { continuation ->
            val client = OkHttpClient()
            val req =
                Request.Builder().url("https://api.statsdb.net/r6/pc/player/${id}")
                    .method("GET", null)
                    .header("X-Authorization", apiAuth)
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