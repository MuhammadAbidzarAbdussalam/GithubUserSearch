package com.abidzar.githubusersearch.domain.usecase

import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.domain.repository.GitHubRepository

class SearchUsersUseCase(private val repository: GitHubRepository) {
    suspend operator fun invoke(query: String, page: Int): List<UserSummary> =
        repository.searchUsers(query.trim(), page)
}
