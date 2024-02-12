package com.chattingapp.chattingapp.entity


class Post(
    private var id: Int,
    private var ownerUsername:UserProfile,
    private var numberOfLikes: Int,
    private var numberOfComments: Int,
    private var image: String)

{

    fun getImage():String{
        return image
    }

    fun getId():Int{
        return id
    }

    fun getOwnerUsername():UserProfile{
        return ownerUsername
    }

    fun getNumberOfLikes():Int{
        return numberOfLikes
    }
    fun getNumberOfComments():Int{
        return numberOfComments
    }
    fun setNumberOfComments(numberOfComments:Int){
        this.numberOfComments = numberOfComments
    }
    override fun toString(): String {
        return "Post(id=$id, numberOfLikes=$numberOfLikes, numberOfComments=$numberOfComments)"
    }

}