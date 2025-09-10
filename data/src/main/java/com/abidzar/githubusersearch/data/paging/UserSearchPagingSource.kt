package com.abidzar.githubusersearch.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abidzar.githubusersearch.data.remote.GitHubApi
import com.abidzar.githubusersearch.data.mapper.toEntity
import com.abidzar.githubusersearch.data.local.AppDatabase
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.data.mapper.toDomain

class UserSearchPagingSource(
    private val api: GitHubApi,
    private val db: AppDatabase,
    private val query: String,
    private val cacheTtlMillis: Long = 24L * 60 * 60 * 1000
) : PagingSource<Int, UserSummary>() {

    override fun getRefreshKey(state: PagingState<Int, UserSummary>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserSummary> {
        val page = params.key ?: 1
        val now = System.currentTimeMillis()
        val threshold = now - cacheTtlMillis
        return try {
            val cached = db.userSummaryDao().getPage(query, page).filter { it.cachedAt >= threshold }
            val items = if (cached.isNotEmpty()) {
                cached.map { it.toDomain() }
            } else {
                val response = api.searchUsers(query, page)
                val entities = response.items.map { it.toEntity(query, page, now) }
                db.userSummaryDao().insertAll(entities)
                entities.map { it.toDomain() }
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}


