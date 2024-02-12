package com.chattingapp.chattingapp.entity

import android.util.TypedValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserProfile(
    @SerializedName("username")
    private var username: String? = null,

    @SerializedName("numberOfFollowers")
    private var numberOfFollowers: Int,

    @SerializedName("numberOfFollowings")
    private var numberOfFollowings: Int,

    @SerializedName("numberOfPosts")
    private var numberOfPosts: Int,

    private var user: User? = null,

    @Expose(serialize = false)
    private var imageData: String

) {

    fun getUsername(): String? {
        return username
    }

    fun getImageData(): String {
        return imageData
    }

    fun getNumberOfPosts(): Int {
        return numberOfPosts
    }

    fun getNumberOfFollowers(): Int {
        return numberOfFollowers
    }

    fun getNumberOfFollowings(): Int {
        return numberOfFollowings
    }

    fun setImageData(imageData: String){
        this.imageData = imageData
    }

    fun setUser(user: User){
        this.user = user
    }
    /*fun setFollower(follower: UserRelationship){
        this.followers = follower
    }

    fun setFollowing(following: UserRelationship){
        this.followings = following
    }

    fun getFollower(): UserRelationship? {
        return followers
    }

    fun getFollowing(): UserRelationship? {
        return followings
    }*/

    /*override fun toString(): String {
        return "UserProfile(username=$username, numberOfFollowers=$numberOfFollowers, numberOfFollowings=$numberOfFollowings, numberOfPosts=$numberOfPosts, user=$user, follower=$followers, following=$followings)"
    }
*/

}