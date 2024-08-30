package com.example.qutes.data.api

import com.example.qutes.data.model.QuoteModel
import retrofit2.http.GET

interface ApiService {
    @GET("random")
    suspend fun getRandomQuote(): QuoteModel
}