package com.jeezzzz.colabcraft.di

import com.jeezzzz.colabcraft.data.remote.CollabApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Use 10.83.22.218 for Physical Device (Local Wi-Fi IP)
    private const val BASE_URL = "http://10.83.22.218:8081/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCollabApi(retrofit: Retrofit): CollabApi {
        return retrofit.create(CollabApi::class.java)
    }
    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: android.content.Context): com.jeezzzz.colabcraft.data.local.SessionManager {
        return com.jeezzzz.colabcraft.data.local.SessionManager(context)
    }
}
