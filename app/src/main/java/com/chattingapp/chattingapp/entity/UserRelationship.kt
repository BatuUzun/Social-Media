package com.chattingapp.chattingapp.entity

class UserRelationship(
    private val id: Long? = null,

    private var follower: UserProfile? = null,

    private var following: UserProfile? = null
) {

    fun getFollower(): UserProfile?{
        return follower
    }
    fun getFollowing(): UserProfile?{
        return following
    }

    fun setFollowing(following: UserProfile?){
        this.following = following
    }
    fun setFollower(follower: UserProfile?){
        this.follower = follower
    }

    override fun toString(): String {
        return "UserRelationship(id=$id, follower=$follower, following=$following)"
    }

}