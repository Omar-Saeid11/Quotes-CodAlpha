package com.example.qutes.data.repository

import com.example.qutes.data.model.QuoteModel
import com.example.qutes.utils.Result
import kotlinx.coroutines.flow.Flow

interface IntQuoteRepository {
    suspend fun getRandomQuote(): Flow<Result<QuoteModel>>
    suspend fun saveQuoteLocally(quote: QuoteModel)
    suspend fun getSavedQuotes(): Flow<Result<List<QuoteModel>>>
    suspend fun deleteQuote(quoteId: Int)
    suspend fun getQuoteById(quoteId: Int): QuoteModel?
}