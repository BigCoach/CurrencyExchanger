package com.ua.currency.exchanger.app.presentation.di

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ua.currency.exchanger.exchange.data.network.CurrencyRateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_BASE_URL = "https://developers.paysera.com/"

    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
    }

    @Provides
    fun provideCurrencyRateService(
        retrofit: Retrofit
    ) : CurrencyRateService {
        return retrofit.create(CurrencyRateService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor())
        return httpClientBuilder.build()
    }

    @Provides
    fun gson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("HttpLoggingInterceptor", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

}
