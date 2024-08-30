package com.example.qutes.data.repository

import com.example.qutes.data.api.ApiService
import com.example.qutes.data.db.QuoteDao
import com.example.qutes.data.model.QuoteModel
import com.example.qutes.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplQuoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val quoteDao: QuoteDao
) : IntQuoteRepository {

    override suspend fun getRandomQuote(): Flow<Result<QuoteModel>> = flow {
        try {
            val response = apiService.getRandomQuote()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveQuoteLocally(quote: QuoteModel) {
        val existingQuote = getQuoteById(quote.id)
        if (existingQuote == null) {
            val quoteEntity = QuoteModel(
                id = quote.id,
                quote = quote.quote ?: "",
                author = quote.author ?: "Unknown"
            )
            quoteDao.insertQuote(quoteEntity)
        }
    }

    override suspend fun getSavedQuotes(): Flow<Result<List<QuoteModel>>> = flow {
        try {
            val savedQuotes = quoteDao.getSavedQuotes().map { quoteEntity ->
                QuoteModel(
                    id = quoteEntity.id,
                    quote = quoteEntity.quote,
                    author = quoteEntity.author
                )
            }
            emit(Result.Success(savedQuotes))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteQuote(quoteId: Int) {
        quoteDao.deleteQuote(quoteId)
    }

    override suspend fun getQuoteById(quoteId: Int): QuoteModel? {
        return quoteDao.getQuoteById(quoteId)
    }
}