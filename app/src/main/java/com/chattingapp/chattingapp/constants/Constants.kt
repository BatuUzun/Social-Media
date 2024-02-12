package com.chattingapp.chattingapp.constants

import com.chattingapp.chattingapp.entity.User

object Constants {
    private var user = User("", "")
    private var clickedUser = User("", "")

    fun resetClickedUser(){
        clickedUser = User("","")
    }
    fun resetUser(){
        user = User("","")
    }

    private const val appName:String = "Chit-Chatters"
    private const val PAGE_SIZE:Int = 10


    const val TABLE_NAME="keep_me_signed_in"
    const val DATABASE_NAME="LoginDB"

    fun setClickedUser(clickedUser:User){
        clickedUser.setPassword("")
        this.clickedUser = clickedUser
    }

    fun getClickedUser() : User{
        return clickedUser
    }


    fun setUser(user:User){
        user.setPassword("")
        this.user = user
    }

    fun getUser() : User{
        return user
    }

    fun getPageSize() : Int{
        return PAGE_SIZE
    }

    fun getAppName():String{
        return appName
    }

}