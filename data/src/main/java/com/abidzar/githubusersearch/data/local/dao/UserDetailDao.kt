package com.abidzar.githubusersearch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abidzar.githubusersearch.data.local.entity.UserDetailEntity

@Dao
interface UserDetailDao {
    @Query("SELECT * FROM user_details WHERE username = :username LIMIT 1")
    suspend fun get(username: String): UserDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserDetailEntity)

    @Query("DELETE FROM user_details WHERE cachedAt < :threshold")
    suspend fun deleteOlderThan(threshold: Long)
}
