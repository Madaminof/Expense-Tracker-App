package com.example.expensetracker.data.Repository

import com.example.expensetracker.data.local.Entity.ProfileEntity
import com.example.expensetracker.data.local.dao.ProfileDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val dao: ProfileDao
) {
    suspend fun insertProfile(profile: ProfileEntity) = dao.insert(profile)
    suspend fun updateProfile(profile: ProfileEntity) = dao.update(profile)
    suspend fun deleteProfile(profile: ProfileEntity) = dao.delete(profile)

    fun getProfileFlow() = dao.getProfileFlow()
    suspend fun getProfile() = dao.getProfile()
}
