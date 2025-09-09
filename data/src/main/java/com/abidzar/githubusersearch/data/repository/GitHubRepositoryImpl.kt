package com.abidzar.githubusersearch.data.repository

import com.abidzar.githubusersearch.data.local.AppDatabase
import com.abidzar.githubusersearch.data.mapper.toDomain
import com.abidzar.githubusersearch.data.mapper.toEntity
import com.abidzar.githubusersearch.data.remote.GitHubApi
import com.abidzar.githubusersearch.domain.model.UserDetail
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.domain.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val api: GitHubApi,
    private val db: AppDatabase
) : GitHubRepository {

    private val cacheTtlMillis: Long = 24L * 60 * 60 * 1000

    override suspend fun searchUsers(query: String, page: Int): List<UserSummary> = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val threshold = now - cacheTtlMillis

        val cached = db.userSummaryDao().getPage(query, page)
            .filter { it.cachedAt >= threshold }
        if (cached.isNotEmpty()) {
            return@withContext cached.map { it.toDomain() }
        }

        val response = api.searchUsers(query, page)
        val entities = response.items.map { it.toEntity(query, page, now) }
        db.userSummaryDao().insertAll(entities)
        return@withContext entities.map { it.toDomain() }
    }

    override suspend fun getUserDetail(username: String): UserDetail = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val threshold = now - cacheTtlMillis

        db.userDetailDao().get(username)?.let { entity ->
            if (entity.cachedAt >= threshold) {
                return@withContext entity.toDomain()
            }
        }

        val dto = api.getUserDetail(username)
        val entity = dto.toEntity(now)
        db.userDetailDao().insert(entity)
        return@withContext entity.toDomain()
    }
}
