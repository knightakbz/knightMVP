package com.example.knightmvp.server.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit

class TimeoutInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val timeoutAnnotation = getTimeOut(chain.request())

        timeoutAnnotation?.let {
            val connectTimeout = it.connect
            val readTimeout = it.read
            val writeTimeout = it.write

            return chain
                .withConnectTimeout(connectTimeout, TimeUnit.SECONDS)
                .withReadTimeout(readTimeout, TimeUnit.SECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.SECONDS)
                .proceed(chain.request())
        }

        return chain.proceed(chain.request())
    }


    private fun getTimeOut(request: okhttp3.Request) =
        request.tag(Invocation::class.java)
            ?.method()
            ?.takeIf { it.isAnnotationPresent(Timeout::class.java) }
            ?.getAnnotation(Timeout::class.java)

}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timeout(val connect: Int = 30, val read: Int = 30, val write: Int = 30)