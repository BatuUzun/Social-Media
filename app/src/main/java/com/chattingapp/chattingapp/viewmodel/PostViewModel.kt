package com.chattingapp.chattingapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class PostViewModel : ViewModel() {
    private val _postList = mutableStateListOf<Post>()
    private var errorMessage: String by mutableStateOf("")
    var postList: List<Post> ?= null
        get() = _postList


    private val _postListOfFollowings = mutableStateListOf<Post>()
    var postListOfFollowings: List<Post> ?= null
        get() = _postListOfFollowings

    fun deletePost(post:Post) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.deletePost(post)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("SAVE_POST:",errorMessage)
            }
        }
    }

    fun savePost(post:Post) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.savePost(post)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("SAVE_POST:",errorMessage)
            }
        }
    }

    fun getPostList(username:String, page: Int) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                _postList.clear()
                val input:Map<String, Int> = mapOf(username to page)
                _postList.addAll(apiService.findPostsByUsername(input))

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("POSTS_FOLLOWINGS:",errorMessage)


            }
        }
    }

    fun getPostsOfFollowings(username:String, page: Int) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                _postListOfFollowings.clear()
                val input:Map<String, Int> = mapOf(username to page)
                _postListOfFollowings.addAll(apiService.getPostsOfFollowings(input))
                Log.d("USER_PROFILE:",postListOfFollowings.toString())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("USER_PROFILE_ERROR:",errorMessage)

            }
        }
    }
}