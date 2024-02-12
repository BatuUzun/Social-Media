package com.chattingapp.chattingapp.retrofit

import com.chattingapp.chattingapp.entity.Comment
import com.chattingapp.chattingapp.entity.Like
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.entity.User
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.entity.UserRelationship
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitAPI {
    @POST("/api/user/save/")
    fun saveUser(@Body user: User?): Call<User>

    @POST("/api/user/check/")
    fun checkUser(@Body user: User?): Call<User>

    @POST("/api/user/generate-verification-code/")
    fun generateVerificationCode(): Call<Int>

    @POST("/api/user/send-verification-code/")
    fun sendEmail(@Body input: Map<String, Int>): Call<Int>

    @POST("/api/user/change-email-status/")
    fun changeEmailStatus(@Body user: User?): Call<Int>

    @POST("/api/user/save-username/")
    fun saveUsername(@Body userProfile: UserProfile): Call<UserProfile>

    @POST("/api/user/get-user-profile/")
    fun getUserProfile(@Body user: User): Call<UserProfile>

    @POST("/api/post/get-posts-by-username/")
    suspend fun findPostsByUsername(@Body input: Map<String, Int>): List<Post>

    @POST("/api/user/search-users-profile/")
    @Headers("Content-Type: text/plain")
    suspend fun searchUsersProfile(@Body body: RequestBody): List<UserProfile>

    @POST("/api/user/save-user-relationship/")
    suspend fun saveUserRelationship(@Body userRelationship: UserRelationship)

    @HTTP(method = "DELETE", path = "/api/user/delete-user-relationship/", hasBody = true)
    suspend fun deleteUserRelationship(@Body userRelationship: UserRelationship)

    @POST("/api/user/get-user-relationship-is-following/")
    fun getUserRelationshipIsFollowing(@Body userRelationship: UserRelationship): Call<Boolean>

    @POST("/api/post/find-post-by-id/")
    fun findPostById(@Body id: Int): Call<Post>

    @POST("/api/post/save-post-like/")
    suspend fun savePostLike(@Body like: Like)

    @POST("/api/post/find-like-by-like/")
    fun findLikeByLike(@Body like: Like): Call<Int>

    @POST("/api/post/delete-post-like/")
    suspend fun deletePostLike(@Body like: Like)

    @POST("/api/post/save-post-comment/")
    suspend fun savePostComment(@Body comment: Comment)

    @POST("/api/post/get-comments-of-post/")
    suspend fun getCommentsOfPost(@Body input: Map<Int, Int>): List<Comment>

    @POST("/api/post/delete-comment/")
    suspend fun deletePostComment(@Body comment: Comment)

    @POST("/api/user/update-user-profile/")
    suspend fun updateUserProfile(@Body userProfile: UserProfile)

    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part, @Part("username") username: RequestBody): Response<ResponseBody>

    @POST("/api/post/find-posts-of-followings/")
    suspend fun getPostsOfFollowings(@Body input: Map<String, Int>): List<Post>

    @POST("/api/post/find-number-of-posts-of-followings/")
    fun getNumberOfPostsOfFollowings(@Body userProfile: UserProfile): Call<Int>

    @POST("/api/post/save-post/")
    suspend fun savePost(@Body post: Post)

    @POST("/api/post/delete-post/")
    suspend fun deletePost(@Body post: Post)

}