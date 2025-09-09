package com.abidzar.githubusersearch.data.remote

import com.abidzar.githubusersearch.data.remote.dto.SearchUsersResponseDto
import com.abidzar.githubusersearch.data.remote.dto.UserDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): SearchUsersResponseDto

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailDto
}
