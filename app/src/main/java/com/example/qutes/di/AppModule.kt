package com.example.qutes.di

import com.example.qutes.data.api.ApiService
import com.example.qutes.data.repository.ImplQuoteRepository
import com.example.qutes.data.repository.IntQuoteRepository
import com.example.qutes.data.db.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/quotes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(apiService: ApiService, quoteDao: QuoteDao): IntQuoteRepository {
        return ImplQuoteRepository(apiService, quoteDao)
    }
}
