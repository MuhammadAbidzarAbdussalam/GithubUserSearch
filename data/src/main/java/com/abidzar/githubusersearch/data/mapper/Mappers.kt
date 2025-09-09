package com.abidzar.githubusersearch.data.mapper

import com.abidzar.githubusersearch.data.local.entity.UserDetailEntity
import com.abidzar.githubusersearch.data.local.entity.UserSummaryEntity
import com.abidzar.githubusersearch.data.remote.dto.UserDetailDto
import com.abidzar.githubusersearch.data.remote.dto.UserSummaryDto
import com.abidzar.githubusersearch.domain.model.UserDetail
import com.abidzar.githubusersearch.domain.model.UserSummary

fun UserSummaryDto.toEntity(query: String, page: Int, now: Long): UserSummaryEntity =
    UserSummaryEntity(
        username = login,
        apiUserId = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        gravatarId = gravatarId,
        url = url,
        htmlUrl = htmlUrl,
        followersUrl = followersUrl,
        subscriptionsUrl = subscriptionsUrl,
        organizationsUrl = organizationsUrl,
        reposUrl = reposUrl,
        receivedEventsUrl = receivedEventsUrl,
        type = type,
        score = score,
        followingUrl = followingUrl,
        gistsUrl = gistsUrl,
        starredUrl = starredUrl,
        eventsUrl = eventsUrl,
        siteAdmin = siteAdmin,
        query = query,
        page = page,
        cachedAt = now
    )

fun UserSummaryEntity.toDomain(): UserSummary =
    UserSummary(
        username = username,
        avatarUrl = avatarUrl
    )

fun UserDetailDto.toEntity(now: Long): UserDetailEntity =
    UserDetailEntity(
        username = login,
        avatarUrl = avatarUrl,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        hireable = hireable,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        bio = bio,
        followers = followers,
        following = following,
        createdAt = createdAt,
        updatedAt = updatedAt,
        cachedAt = now
    )

fun UserDetailEntity.toDomain(): UserDetail =
    UserDetail(
        username = username,
        avatarUrl = avatarUrl,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        hireable = hireable,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        bio = bio,
        followers = followers,
        following = following,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
