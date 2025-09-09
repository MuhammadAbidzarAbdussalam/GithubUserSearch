package com.abidzar.githubusersearch.domain.usecase

import com.abidzar.githubusersearch.domain.model.UserDetail
import com.abidzar.githubusersearch.domain.repository.GitHubRepository

class GetUserDetailUseCase(private val repository: GitHubRepository) {
    suspend operator fun invoke(username: String): UserDetail =
        repository.getUserDetail(username)
}
