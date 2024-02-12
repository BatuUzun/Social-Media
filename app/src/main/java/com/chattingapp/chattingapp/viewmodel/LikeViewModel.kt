package com.chattingapp.chattingapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.entity.Like
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class LikeViewModel:ViewModel() {
    private var errorMessage: String by mutableStateOf("")

    fun saveLike(like: Like) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.savePostLike(like)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("POSTS:",errorMessage)
            }
        }
    }

    fun deleteLike(like: Like) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.deletePostLike(like)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("LIKE:",errorMessage)
            }
        }
    }


}