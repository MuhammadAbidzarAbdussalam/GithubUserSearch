package com.abidzar.githubusersearch.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abidzar.githubusersearch.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CacheCleanupWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val db = AppDatabaseHolder.get(applicationContext)
            val now = System.currentTimeMillis()
            val threshold = now - TTL
            db.userSummaryDao().deleteOlderThan(threshold)
            db.userDetailDao().deleteOlderThan(threshold)
            Result.success()
        } catch (t: Throwable) {
            Result.retry()
        }
    }

    companion object {
        const val TTL: Long = 24L * 60 * 60 * 1000
    }
}

object AppDatabaseHolder {
    @Volatile
    private var instance: AppDatabase? = null

    fun get(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: androidx.room.Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "github.db"
            ).build().also { instance = it }
        }
    }
}
