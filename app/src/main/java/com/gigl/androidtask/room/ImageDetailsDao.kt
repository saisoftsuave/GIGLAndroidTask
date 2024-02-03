package com.gigl.androidtask.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gigl.androidtask.models.StoredDetails

@Dao
interface ImageDetailsDao {
    @Query("SELECT * FROM stored_details")
    fun getAllImageDetails(): MutableList<StoredDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageDetailsList: MutableList<StoredDetails>)
}
