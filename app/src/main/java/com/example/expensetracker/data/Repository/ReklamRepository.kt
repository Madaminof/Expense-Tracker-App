package com.example.expensetracker.data.Repository


import com.example.expensetracker.data.remote.ReklamApi.ReklamServiceApi
import com.example.expensetracker.data.remote.ReklamApi.Reklama
import javax.inject.Inject

class ReklamRepository @Inject constructor(
    private val api: ReklamServiceApi
) {

    suspend fun getReklamalar(): List<Reklama> {
        return api.getReklam()
    }
    suspend fun getReklamById(id: String): Reklama {
        return api.getReklamById(id)
    }

}
