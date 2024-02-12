package com.chattingapp.chattingapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserProfileViewModel : ViewModel() {
    private val _userProfileList = mutableStateListOf<UserProfile>()
    private var errorMessage: String by mutableStateOf("")
    var userProfileList: List<UserProfile> ?= null
        get() = _userProfileList

    fun getUserProfileList(username:String) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                _userProfileList.clear()
                val body: RequestBody = RequestBody.create(MediaType.parse("text/plain"), username)
                _userProfileList.addAll(apiService.searchUsersProfile(body))
                Log.d("USER_PROFILE:",userProfileList.toString())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("USER_PROFILE_ERROR:",errorMessage)

            }
        }
    }

    fun updateUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.updateUserProfile(userProfile)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("UPDATE_USER_PROFILE:",errorMessage)
            }
        }
    }

    fun uploadImage(image: MultipartBody.Part, username: RequestBody) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.uploadImage(image, username)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("UPDATE_USER_PROFILE:",errorMessage)
            }
        }
    }
}