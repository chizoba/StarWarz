package com.github.chizoba.starwarz.di.modules

import com.github.chizoba.starwarz.BuildConfig
import com.github.chizoba.starwarz.data.MoviesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [AppModule::class])
class NetworkModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MoviesService.ENDPOINT)
            .client(okHttpClient)
            .build()
    }

//    @Provides
//    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
//        val client = OkHttpClient.Builder()
//        client.addInterceptor(httpLoggingInterceptor)
//        client.addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//            request.addHeader("Content-Type", "application/json")
//            chain.proceed(request.build())
//        }
//        client.connectTimeout(20, TimeUnit.SECONDS)
//        client.readTimeout(30, TimeUnit.SECONDS)
//        client.writeTimeout(30, TimeUnit.SECONDS)
//        client.cache(cache)
//        return client.build()
//    }

//    @Provides
//    fun provideCache(context: Context): Cache {
//        val cacheSize = 10 * 1024 * 1024
//        return Cache(context.cacheDir, cacheSize.toLong())
//    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
}
