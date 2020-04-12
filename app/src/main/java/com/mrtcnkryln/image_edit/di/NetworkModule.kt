package com.mrtcnkryln.image_edit.di

import com.mrtcnkryln.image_edit.network.ApiInterface
import com.mrtcnkryln.image_edit.util.AppConstants.Companion.BASE_URL
import com.mrtcnkryln.image_edit.util.AppConstants.Companion.MAX_REQUESTS
import com.mrtcnkryln.image_edit.util.AppConstants.Companion.READ_TIMEOUT
import com.mrtcnkryln.image_edit.util.AppConstants.Companion.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    internal fun httpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = MAX_REQUESTS
        httpClient.dispatcher(dispatcher)
        httpClient.addInterceptor(logging)
        httpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        return httpClient.build()
    }
}