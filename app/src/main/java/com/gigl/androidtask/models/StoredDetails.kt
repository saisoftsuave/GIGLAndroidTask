package com.gigl.androidtask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stored_details")
data class StoredDetails(
    @PrimaryKey
    val id: String,
    val profileImage: String,
    val thumbnailUrl: String,
    val description: String,
    val shortDescription: String
)