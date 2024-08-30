package com.example.qutes.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class QuoteModel(
    @SerializedName("author")
    val author: String?,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("quote")
    val quote: String?
)
