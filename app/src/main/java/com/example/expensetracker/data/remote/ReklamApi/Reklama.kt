package com.example.expensetracker.data.remote.ReklamApi

import com.google.gson.annotations.SerializedName

data class Reklama(
    val id:Int,
    val title:String,
    val description:String,
    @SerializedName("reklamImage")
    val imageUrl:String, )
