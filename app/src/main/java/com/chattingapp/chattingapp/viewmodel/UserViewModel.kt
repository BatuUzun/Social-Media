package com.chattingapp.chattingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.database.UserRoomDatabase
import com.chattingapp.chattingapp.entity.User
import com.chattingapp.chattingapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository:UserRepository
    val readAllData: LiveData<List<User>>


    init {
        val userDAO= UserRoomDatabase.getDatabase(application).userDAO()
        repository = UserRepository(userDAO)
        readAllData = repository.readAlldata
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertUser(user)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllUsers()
        }
    }

    fun updateUser(user:User){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(user)
        }
    }

    fun getUserByEmail(email:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getUserByEmail(email)
        }
    }





}