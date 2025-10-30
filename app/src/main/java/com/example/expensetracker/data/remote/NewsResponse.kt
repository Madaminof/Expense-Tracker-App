package com.example.expensetracker.data.remote


data class Article(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?
)

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
