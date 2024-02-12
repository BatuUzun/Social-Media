package com.chattingapp.chattingapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.chattingapp.entity.Comment
import com.chattingapp.chattingapp.entity.Like
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class CommentViewModel:ViewModel() {

    private var errorMessage: String by mutableStateOf("")
    private val _commentList = mutableStateListOf<Comment>()
    var commentList: List<Comment> ?= null
        get() = _commentList

    fun getCommentList(postId:Int, page: Int) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                _commentList.clear()
                val input:Map<Int, Int> = mapOf(postId to page)
                _commentList.addAll(apiService.getCommentsOfPost(input))

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("POSTS:",errorMessage)

            }
        }
    }


    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.deletePostComment(comment)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("COMMENT:",errorMessage)
            }
        }
    }


    fun saveComment(comment: Comment) {
        viewModelScope.launch {
            val apiService = RetrofitHelper.apiService
            try {
                apiService.savePostComment(comment)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("COMMENT:",errorMessage)
            }
        }
    }
}