package com.chattingapp.chattingapp.entity

import androidx.compose.runtime.MutableState

class Comment(

    private var id: Int? = null,

    private var commentOwner:UserProfile? = null,

    private var commentText: String,

    private var targetPost: Post?=null

) {

    fun getCommentOwner():UserProfile?{
        return commentOwner
    }

    fun getCommentText():String{
        return commentText
    }
    fun getTargetPost():Post?{
        return targetPost
    }

    override fun toString(): String {
        return "Comment(id=$id, commentText='$commentText')"
    }
}