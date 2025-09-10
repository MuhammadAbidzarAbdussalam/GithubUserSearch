package com.abidzar.githubusersearch.data.mapper

import com.abidzar.githubusersearch.data.remote.dto.UserDetailDto
import com.abidzar.githubusersearch.data.remote.dto.UserSummaryDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `user summary dto to entity and to domain`() {
        val dto = UserSummaryDto(login = "octocat", avatarUrl = "url")
        val entity = dto.toEntity("q", 1, 100L)
        assertEquals("octocat", entity.username)
        assertEquals("url", entity.avatarUrl)
        val domain = entity.toDomain()
        assertEquals("octocat", domain.username)
        assertEquals("url", domain.avatarUrl)
    }

    @Test
    fun `user detail dto to entity and to domain`() {
        val dto = UserDetailDto(
            login = "octo",
            avatarUrl = "a",
            bio = "b",
            followers = 1,
            following = 2,
            id = null,
            nodeId = null,
            gravatarId = null,
            url = null,
            htmlUrl = null,
            followersUrl = null,
            followingUrl = null,
            gistsUrl = null,
            starredUrl = null,
            subscriptionsUrl = null,
            organizationsUrl = null,
            reposUrl = null,
            eventsUrl = null,
            receivedEventsUrl = null,
            type = null,
            siteAdmin = null,
            name = null,
            company = null,
            blog = null,
            location = null,
            email = null,
            hireable = null,
            twitterUsername = null,
            publicRepos = null,
            publicGists = null,
            createdAt = null,
            updatedAt = null
        )
        val entity = dto.toEntity(100L)
        assertEquals("octo", entity.username)
        val domain = entity.toDomain()
        assertEquals("octo", domain.username)
        assertEquals("b", domain.bio)
        assertEquals(1, domain.followers)
        assertEquals(2, domain.following)
    }
}
