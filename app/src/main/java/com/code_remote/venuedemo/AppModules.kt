package com.code_remote.venuedemo

import android.content.Context
import androidx.room.Room
import com.code_remote.venuedemo.data.local.VenueDao
import com.code_remote.venuedemo.data.remote.VenueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVenueDatabase(@ApplicationContext context: Context): VenueDao =
        Room.databaseBuilder(context, AppDatabase::class.java, "Database").build().venueDao()
}

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteVenueService(okHttpClient: OkHttpClient): VenueApiService =
        Retrofit.Builder().baseUrl("https://api.foursquare.com/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VenueApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor { chain ->
            var req = chain.request()
            val url = req.url().newBuilder()
                .addQueryParameter("client_id", BuildConfig.FOUR_SQUARE_CLIENT_ID)
                .addQueryParameter("client_secret", BuildConfig.FOUR_SQUARE_CLIENT_SECRET)
                .addQueryParameter("v", "20200710")
                .build()
            req = req.newBuilder().url(url).build()
            chain.proceed(req)
        }.build()
}
