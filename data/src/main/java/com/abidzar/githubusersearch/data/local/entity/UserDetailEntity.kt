package com.abidzar.githubusersearch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetailEntity(
    @PrimaryKey val username: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val twitterUsername: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val bio: String?,
    val followers: Int?,
    val following: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val cachedAt: Long
)
