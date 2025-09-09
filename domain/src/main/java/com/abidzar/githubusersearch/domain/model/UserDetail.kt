package com.abidzar.githubusersearch.domain.model

data class UserDetail(
    val username: String,
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
    val updatedAt: String?
)
