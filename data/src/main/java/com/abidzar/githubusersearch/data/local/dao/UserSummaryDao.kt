package com.abidzar.githubusersearch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abidzar.githubusersearch.data.local.entity.UserSummaryEntity

@Dao
interface UserSummaryDao {
    @Query("SELECT * FROM user_summaries WHERE `query` = :query AND page = :page ORDER BY id ASC")
    suspend fun getPage(query: String, page: Int): List<UserSummaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UserSummaryEntity>)

    @Query("DELETE FROM user_summaries WHERE cachedAt < :threshold")
    suspend fun deleteOlderThan(threshold: Long)

    @Query("DELETE FROM user_summaries WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)
}
