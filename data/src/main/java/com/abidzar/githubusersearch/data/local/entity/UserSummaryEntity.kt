package com.abidzar.githubusersearch.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_summaries",
    indices = [Index(value = ["query", "page"]) , Index(value = ["username"], unique = false)]
)
data class UserSummaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val apiUserId: Long?,
    val nodeId: String?,
    val avatarUrl: String,
    val gravatarId: String?,
    val url: String?,
    val htmlUrl: String?,
    val followersUrl: String?,
    val subscriptionsUrl: String?,
    val organizationsUrl: String?,
    val reposUrl: String?,
    val receivedEventsUrl: String?,
    val type: String?,
    val score: Int?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val starredUrl: String?,
    val eventsUrl: String?,
    val siteAdmin: Boolean?,
    val query: String,
    val page: Int,
    val cachedAt: Long
)
