package com.example.gotravel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gotravel.common.model.User
import com.example.gotravel.domain.UserDao

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class GoTravelDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: GoTravelDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GoTravelDatabase::class.java,
            "gotravel.db"
        ).fallbackToDestructiveMigration().build()
    }

}