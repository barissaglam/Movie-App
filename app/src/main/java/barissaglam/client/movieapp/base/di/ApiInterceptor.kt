package barissaglam.client.movieapp.base.di

import barissaglam.client.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder().url(originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build())
        return chain.proceed(requestBuilder.build())
    }
}