package com.abidzar.githubusersearch.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abidzar.githubusersearch.data.local.entity.UserDetailEntity
import com.abidzar.githubusersearch.data.local.entity.UserSummaryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun userSummaryDao_insertAndQuery() = runBlocking {
        val now = System.currentTimeMillis()
        val e = UserSummaryEntity(
            username = "u",
            avatarUrl = "a",
            query = "q",
            page = 1,
            cachedAt = now,
            apiUserId = null,
            nodeId = null,
            gravatarId = null,
            url = null,
            htmlUrl = null,
            followersUrl = null,
            subscriptionsUrl = null,
            organizationsUrl = null,
            reposUrl = null,
            receivedEventsUrl = null,
            type = null,
            score = null,
            followingUrl = null,
            gistsUrl = null,
            starredUrl = null,
            eventsUrl = null,
            siteAdmin = null
        )
        db.userSummaryDao().insertAll(listOf(e))
        val list = db.userSummaryDao().getPage("q", 1)
        assertEquals(1, list.size)
        assertEquals("u", list.first().username)
    }

    @Test
    fun userDetailDao_insertAndGet() = runBlocking {
        val now = System.currentTimeMillis()
        val e = UserDetailEntity(
            username = "u2",
            avatarUrl = "a2",
            bio = null,
            followers = 1,
            following = 2,
            cachedAt = now,
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
        db.userDetailDao().insert(e)
        val got = db.userDetailDao().get("u2")
        assertEquals("u2", got?.username)
    }
}
