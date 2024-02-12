package com.chattingapp.chattingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.dao.UserDAO
import com.chattingapp.chattingapp.entity.User

@Database(
    entities = [User::class],
    version = 2,
    exportSchema = false
)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object{
        @Volatile
        private var INSTANCE:UserRoomDatabase?=null

        fun getDatabase(context: Context):UserRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }

            synchronized(this){
                val  instance = Room.databaseBuilder(context.applicationContext, UserRoomDatabase::class.java, Constants.DATABASE_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
