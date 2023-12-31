package com.longhrk.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.longhrk.data.Const.DATABASE_NAME_APP
import com.longhrk.data.Const.SHARE_PREFERENCE_APP
import com.longhrk.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesSharePreference(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(SHARE_PREFERENCE_APP, Context.MODE_PRIVATE)
    }

    @DatabaseName
    @Provides
    @Singleton
    fun provideDatabaseName() = DATABASE_NAME_APP

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context, @DatabaseName dbName: String
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}