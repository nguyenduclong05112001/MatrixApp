package com.longhrk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.longhrk.data.database.converters.Converters
import com.longhrk.data.database.dao.DaoDatabase
import com.longhrk.data.database.entity.DataLocal

@Database(
    entities = [DataLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDataBase(): DaoDatabase
}