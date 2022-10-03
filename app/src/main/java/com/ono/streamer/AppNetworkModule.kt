package com.ono.streamer

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ono.streamerlibrary.ApplicationScope
import com.ono.streamerlibrary.RetrofitHelper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class AppNetworkModule {
    @Provides
    @ApplicationScope
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @ApplicationScope
    fun getClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.readTimeout(2, TimeUnit.MINUTES)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(2, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)

        }
        return builder.build()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitHelper.baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideWebservice(retrofit: Retrofit): AppWebService {
        return retrofit.create(AppWebService::class.java)
    }
}