package com.example.qutes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qutes.data.model.QuoteModel
import com.example.qutes.data.repository.IntQuoteRepository
import com.example.qutes.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteRepository: IntQuoteRepository
) : ViewModel() {

    private val _quoteState = MutableStateFlow<Result<QuoteModel>?>(null)
    val quoteState: StateFlow<Result<QuoteModel>?> = _quoteState

    private val _savedQuotesState = MutableStateFlow<Result<List<QuoteModel>>?>(null)
    val savedQuotesState: StateFlow<Result<List<QuoteModel>>?> = _savedQuotesState

    init {
        getSavedQuotes()
    }

    fun getRandomQuote() {
        viewModelScope.launch {
            quoteRepository.getRandomQuote().collect { result ->
                _quoteState.value = result
            }
        }
    }

    fun saveQuote(quote: QuoteModel) {
        viewModelScope.launch {
            try {
                val existingQuote = quoteRepository.getQuoteById(quote.id)
                if (existingQuote == null) {
                    quoteRepository.saveQuoteLocally(quote)
                }
                getSavedQuotes()
            } catch (e: Exception) {
                _quoteState.value = Result.Error(e)
            }
        }
    }

    fun deleteQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
                quoteRepository.deleteQuote(quoteId)
                getSavedQuotes()
            } catch (e: Exception) {
                _quoteState.value = Result.Error(e)
            }
        }
    }

    fun getSavedQuotes() {
        viewModelScope.launch {
            quoteRepository.getSavedQuotes().collect { result ->
                _savedQuotesState.value = result
            }
        }
    }
}

