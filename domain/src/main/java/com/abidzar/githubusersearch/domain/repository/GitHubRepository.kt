package com.abidzar.githubusersearch.domain.repository

import com.abidzar.githubusersearch.domain.model.UserDetail
import com.abidzar.githubusersearch.domain.model.UserSummary

interface GitHubRepository {
    suspend fun searchUsers(query: String, page: Int): List<UserSummary>
    suspend fun getUserDetail(username: String): UserDetail
}
