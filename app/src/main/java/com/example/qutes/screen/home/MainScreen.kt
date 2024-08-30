package com.example.qutes.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.qutes.data.model.QuoteModel
import com.example.qutes.screen.home.composables.BottomNavigationBar
import com.example.qutes.screen.home.composables.QuoteCard
import com.example.qutes.screen.home.composables.TopBar
import com.example.qutes.utils.Result
import com.example.qutes.viewmodel.QuoteViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: QuoteViewModel = hiltViewModel()
) {
    val quoteState by viewModel.quoteState.collectAsState()
    val savedQuotesState by viewModel.savedQuotesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRandomQuote()
        viewModel.getSavedQuotes()
    }

    val isLoading = quoteState is Result.Loading || savedQuotesState is Result.Loading

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    when (quoteState) {
                        is Result.Success -> {
                            val quote = (quoteState as Result.Success<QuoteModel>).data
                            val isSaved =
                                (savedQuotesState as? Result.Success<List<QuoteModel>>)?.data
                                    ?.any { it.id == quote.id } == true

                            QuoteCard(
                                quote = quote.quote ?: "No quote available",
                                author = quote.author ?: "Unknown",
                                isSaved = isSaved,
                                onClickSave = { shouldSave ->
                                    if (shouldSave) {
                                        viewModel.saveQuote(quote)
                                    } else {
                                        viewModel.deleteQuote(quote.id)
                                    }
                                },
                                onClickRefresh = { viewModel.getRandomQuote() }
                            )
                        }

                        is Result.Error -> {
                            Text(
                                "Failed to load quote",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        else -> {
                            Text(
                                "Press refresh to get a quote",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    )
}
