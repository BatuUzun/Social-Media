package com.chattingapp.chattingapp.entity

import androidx.room.util.TableInfo.*




class Like (
    private var id: Int = 0,

    private var likeOwner: UserProfile? = null,

    private var targetPost: Post
){

fun setId(id:Int){
    this.id = id
}



}