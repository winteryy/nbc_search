package com.winteryy.nbcsearch.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK 8a81e9d14560f5b7e696711bf1c8f62b"
            )
            .build()
        return chain.proceed(newRequest)
    }
}