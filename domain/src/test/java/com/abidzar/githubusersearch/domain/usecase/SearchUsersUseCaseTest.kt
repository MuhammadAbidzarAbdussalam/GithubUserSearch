package com.abidzar.githubusersearch.domain.usecase

import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.domain.repository.GitHubRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchUsersUseCaseTest {

    @Test
    fun `invoke delegates to repository`() = runBlocking {
        val fakeRepo = object : GitHubRepository {
            override suspend fun searchUsers(query: String, page: Int): List<UserSummary> {
                return listOf(UserSummary("u1", "a1"))
            }
            override suspend fun getUserDetail(username: String) = throw UnsupportedOperationException()
        }
        val useCase = SearchUsersUseCase(fakeRepo)
        val result = useCase("q", 1)
        assertEquals(1, result.size)
        assertEquals("u1", result.first().username)
    }
}
