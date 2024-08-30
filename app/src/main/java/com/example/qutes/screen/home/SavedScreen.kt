package com.example.qutes.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
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
fun SavedScreen(
    navController: NavController,
    viewModel: QuoteViewModel = hiltViewModel()
) {
    val savedQuotesState by viewModel.savedQuotesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSavedQuotes()
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            when (savedQuotesState) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    val quotes = (savedQuotesState as Result.Success<List<QuoteModel>>).data
                    if (quotes.isEmpty()) {
                        Text(
                            "You have no saved quotes.",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(quotes) { quote ->
                                QuoteCard(
                                    quote = quote.quote ?: "No quote available",
                                    author = quote.author ?: "Unknown",
                                    isSaved = true,
                                    onClickSave = { isSaved ->
                                        if (isSaved) {
                                            viewModel.saveQuote(quote)
                                        } else {
                                            viewModel.deleteQuote(quote.id)
                                        }
                                    },
                                    onClickRefresh = { viewModel.getSavedQuotes() }
                                )
                            }
                        }
                    }
                }

                is Result.Error -> {
                    Text(
                        "Oops! Something went wrong while loading your saved quotes.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }

                else -> {
                    Text(
                        "Press refresh to get saved quotes",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    )
}
