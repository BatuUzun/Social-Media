package com.chattingapp.chattingapp.repository

import androidx.lifecycle.LiveData
import com.chattingapp.chattingapp.dao.UserDAO
import com.chattingapp.chattingapp.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository(private val userDAO: UserDAO) {
    val readAlldata:LiveData<List<User>> = userDAO.getAllUsers()

    fun insertUser(user: User){
        userDAO.insertUser(user)
    }

    fun updateUser(user: User){
        userDAO.updateUser(user)
    }

    fun deleteAllUsers(){
        userDAO.deleteAllUsers()
    }

    fun getUserByEmail(email:String):User{
        return userDAO.getUserByEmail(email)
    }


}