package com.gigl.androidtask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gigl.androidtask.models.StoredDetails

@Database(
    entities = [StoredDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDetailsDao(): ImageDetailsDao
}