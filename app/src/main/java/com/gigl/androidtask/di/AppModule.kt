package com.gigl.androidtask.di

import android.content.Context
import androidx.room.Room
import com.gigl.androidtask.ApiService.ApiService
import com.gigl.androidtask.ApiService.AuthInterceptor
import com.gigl.androidtask.ApiService.NetworkManager
import com.gigl.androidtask.constants.Constants
import com.gigl.androidtask.constants.Constants.BASE_URL
import com.gigl.androidtask.room.AppDatabase
import com.gigl.androidtask.room.ImageDetailsDao
import com.gigl.androidtask.ui.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val client: OkHttpClient.Builder =
            OkHttpClient.Builder().connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
        return client.addInterceptor(NetworkManager.getLogInterceptor()).build()
    }


    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): ApiService {
        return retrofitBuilder.client(okHttpClient).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, Constants.DATABASE_NAME
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideChatMessageDao(dataBase: AppDatabase): ImageDetailsDao = dataBase.imageDetailsDao()

    @Singleton
    @Provides
    fun provideHomeRepository(apiService: ApiService, imageDetailsDao: ImageDetailsDao ) = HomeRepository(apiService, imageDetailsDao)


}