package com.example.expensetracker.data.Repository

import com.example.expensetracker.data.remote.Article
import com.example.expensetracker.data.remote.NewsApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



sealed class Resource<out T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val message: String, val code: Int? = null): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}

class NewsRepository @Inject constructor(
    private val api: NewsApiService
) {
    suspend fun getFinanceNews(): Resource<List<Article>> {
        return try {
            val response = api.getFinanceNews()
            if (response.status == "ok") {
                Resource.Success(response.articles)
            } else {
                Resource.Error("API status: ${response.status}")
            }
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.code()} ${e.message()}", e.code())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}