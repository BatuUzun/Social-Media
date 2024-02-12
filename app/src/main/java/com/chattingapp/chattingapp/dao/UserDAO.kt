package com.chattingapp.chattingapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.entity.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun deleteAllUsers()

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE email =:email")
    fun getUserByEmail(email:String):User

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY email ASC")
    fun getAllUsers(): LiveData<List<User>>


}