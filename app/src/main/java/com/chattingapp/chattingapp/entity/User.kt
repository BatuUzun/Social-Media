package com.chattingapp.chattingapp.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.chattingapp.chattingapp.constants.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_NAME)
class User(
    @PrimaryKey
    @SerializedName("email")
    private var email:String,
    @Ignore
    @SerializedName("password")
    private var password:String,
    @SerializedName("is_verified_email")
    private var isVerifiedEmail:Boolean

) {

    @Ignore
    private var userProfile:UserProfile? = null

    constructor(email:String,password:String) : this(email, password, false)
    constructor(email:String,isVerifiedEmail: Boolean) : this(email, "", isVerifiedEmail)
    constructor(email:String,isVerifiedEmail: Boolean, userProfile:UserProfile) : this(email, "", isVerifiedEmail){
        this.userProfile = userProfile
    }



    fun getEmail(): String {
        return email
    }

    fun getIsVerifiedEmail(): Boolean {
        return isVerifiedEmail
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password:String) {
        this.password = password
    }

    fun setIsVerifiedEmail(isVerifiedEmail: Boolean) {
        this.isVerifiedEmail = isVerifiedEmail
    }

    fun setUserProfile(userProfile: UserProfile) {
        this.userProfile = userProfile
    }

    fun getUserProfile(): UserProfile? {
        return userProfile
    }

    override fun toString(): String {
        return "User(email='$email', password='$password', " +
                "isVerifiedEmail=$isVerifiedEmail, userProfile=$userProfile)"
    }


}