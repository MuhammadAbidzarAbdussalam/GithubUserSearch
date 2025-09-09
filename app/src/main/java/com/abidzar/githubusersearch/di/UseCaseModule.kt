package com.abidzar.githubusersearch.di

import com.abidzar.githubusersearch.domain.repository.GitHubRepository
import com.abidzar.githubusersearch.domain.usecase.GetUserDetailUseCase
import com.abidzar.githubusersearch.domain.usecase.SearchUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSearchUsersUseCase(repository: GitHubRepository): SearchUsersUseCase =
        SearchUsersUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserDetailUseCase(repository: GitHubRepository): GetUserDetailUseCase =
        GetUserDetailUseCase(repository)
}
