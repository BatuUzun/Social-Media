package com.chattingapp.chattingapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.entity.Comment
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.entity.UserRelationship
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import kotlinx.coroutines.launch


class UserRelationshipViewModel : ViewModel() {
    private var errorMessage: String by mutableStateOf("")

    fun saveUserRelationshipList(userRelationship:UserRelationship) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.saveUserRelationship(userRelationship)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("USER_RELATIONSHIP_ERROR:",errorMessage)

            }
        }
    }
    fun deleteUserRelationshipList(userRelationship:UserRelationship) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.deleteUserRelationship(userRelationship)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("USER_RELATIONSHIP_ERROR:",errorMessage)

            }
        }
    }


}