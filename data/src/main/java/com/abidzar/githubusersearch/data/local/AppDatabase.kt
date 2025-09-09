package com.abidzar.githubusersearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abidzar.githubusersearch.data.local.dao.UserDetailDao
import com.abidzar.githubusersearch.data.local.dao.UserSummaryDao
import com.abidzar.githubusersearch.data.local.entity.UserDetailEntity
import com.abidzar.githubusersearch.data.local.entity.UserSummaryEntity

@Database(
    entities = [UserSummaryEntity::class, UserDetailEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSummaryDao(): UserSummaryDao
    abstract fun userDetailDao(): UserDetailDao
}
