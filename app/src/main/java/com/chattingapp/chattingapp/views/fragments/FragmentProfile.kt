package com.chattingapp.chattingapp.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chattingapp.chattingapp.R
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.encoded_image.Images
import com.chattingapp.chattingapp.entity.Comment
import com.chattingapp.chattingapp.entity.Like
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.entity.User
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.entity.UserRelationship
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import com.chattingapp.chattingapp.viewmodel.CommentViewModel
import com.chattingapp.chattingapp.viewmodel.LikeViewModel
import com.chattingapp.chattingapp.viewmodel.PostViewModel
import com.chattingapp.chattingapp.viewmodel.UserProfileViewModel
import com.chattingapp.chattingapp.viewmodel.UserRelationshipViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

@Composable
fun FragmentProfile() {
    val vm = PostViewModel()
    var user: User = Constants.getUser()
    var userProfile:UserProfile = user.getUserProfile()!!
    val vmRelationship = UserRelationshipViewModel()
    var isFollowing by remember { mutableStateOf(false) }
    val userRelationship = UserRelationship(0, Constants.getUser().getUserProfile(), Constants.getClickedUser().getUserProfile())
    var displayAllInfo by remember { mutableStateOf(false) }
    var displayFollow by remember { mutableStateOf(false) }
    var displayImagePicker by remember { mutableStateOf(false) }


    val apiService = RetrofitHelper.apiService
    apiService.getUserProfile(user).enqueue(object : Callback<UserProfile> {
        override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
            Constants.getUser().setUserProfile(response.body()!!)
            displayAllInfo = true

        }
        override fun onFailure(call: Call<UserProfile>, t: Throwable) {

        }
    })

    if(Constants.getClickedUser().getUserProfile() != null){
        user = Constants.getClickedUser()
        userProfile = user.getUserProfile()!!

        val apiService = RetrofitHelper.apiService
        apiService.getUserRelationshipIsFollowing(userRelationship).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                isFollowing = response.body()!!
                displayFollow = true
                displayImagePicker = false
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {

            }
        })

    }
    else{
        displayImagePicker = true
    }
    if (displayAllInfo){


        var displayPreviousButton by remember { mutableStateOf(false) }
        var displayNextButton by remember { mutableStateOf(false) }

        val decodedBytes = Base64.decode(userProfile.getImageData(), Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        var page: Int = 1
        var maxPage = (Math.ceil ((userProfile.getNumberOfPosts()/Constants.getPageSize().toDouble()))).toInt()

        if(page < maxPage){
            displayNextButton = true
        }

        mapOf(userProfile.getUsername()!! to page)

        // Display username, imageData and numerical information

        Column {
            Text(user.getUserProfile()!!.getUsername()!!,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                painterResource(id = R.drawable.ic_launcher_background)

                // Use the Image composable to display the image

                // Use the Image composable to display the image
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(top = 16.dp)
                        .padding(start = 16.dp)
                )

                Column (modifier = Modifier.padding(start = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = userProfile.getNumberOfPosts().toString(),fontWeight = FontWeight.Bold)
                    Text(text = "post")

                }
                Column (modifier = Modifier.padding(start = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Text(text = userProfile.getNumberOfFollowers().toString(),fontWeight = FontWeight.Bold)
                    Text(text = "followers")

                }
                Column(modifier = Modifier.padding(start = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = userProfile.getNumberOfFollowings().toString(),fontWeight = FontWeight.Bold)
                    Text(text = "followings")

                }
            }

            if(displayFollow){

                Button(
                    onClick = {
                        if(isFollowing){
                            vmRelationship.deleteUserRelationshipList(userRelationship)

                        }
                        else{
                            vmRelationship.saveUserRelationshipList(userRelationship)

                        }

                        isFollowing = !isFollowing
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(10f)
                ) {
                    // Use different content based on the follow state
                    if (isFollowing) {

                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Unfollow")
                        Spacer(modifier = Modifier.width(8.dp))

                    } else {

                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Follow")
                        Spacer(modifier = Modifier.width(8.dp))

                    }
                }

            }
            else{
                if(displayImagePicker)
                    PickImageFromGalleryForProfilePicture()
            }

            // Display Posts and Reels
    //(displayPosts){
            var postList by remember { mutableStateOf(generatePost()) }

            LaunchedEffect(Unit, block = {
                vm.getPostList(userProfile.getUsername()!!, page)
                postList = vm.postList!!
            })
            LazyColumn {
            items(postList) { post ->
                Log.d("POSTS:",post.toString())

                PostItemView(post)

                if(postList.get(postList.size-1) == post){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Image Button on the left side
                        if(displayPreviousButton){
                            IconButton(
                                onClick = {
                                    vm.getPostList(userProfile.getUsername()!!, --page)
                                    postList = vm.postList!!
                                    if(page != 1){
                                        displayPreviousButton = true
                                    }
                                    else{
                                        displayPreviousButton = false
                                    }
                                    displayNextButton = true
                                },
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .padding(start = 100.dp)
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.previous),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                        val padding = if (displayPreviousButton) {
                            PaddingValues(start = 24.dp, top = 0.dp, end = 100.dp, bottom = 16.dp)
                        } else {
                            PaddingValues(start = 250.dp, top = 8.dp, end = 0.dp, bottom = 16.dp)
                        }

                        if(displayNextButton){

                        IconButton(
                            onClick = {

                                vm.getPostList(userProfile.getUsername()!!, ++page)
                                postList = vm.postList!!
                                displayPreviousButton = true
                                if(page < maxPage){
                                    displayNextButton = true
                                }
                                else{
                                    displayNextButton = false
                                }
                            },
                            modifier = Modifier
                                .padding(padding)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.next),
                                contentDescription = "",
                                tint = Color.White
                                )
                            }
                        }

                    }
                }
            }
        }
        }

}
    }

//}

fun generatePost(): List<Post>{
    return List(2) { Post(1, Constants.getUser().getUserProfile()!!,0,0,Images.white) }
}
var deletedComments: MutableList<Comment> = mutableListOf()
@Composable
fun PostItemView(post: Post) {
    val decodedBytes = Base64.decode(post.getImage(), Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    var postClicked: Post = post

    // Each item in the LazyColumn with an Image
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(start = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        var showDialog by remember { mutableStateOf(false) }

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null, // Provide a content description if needed
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(end = 16.dp)
                    .size(300.dp)
                    .clickable {
                        val apiService = RetrofitHelper.apiService
                        apiService
                            .findPostById(post.getId())
                            .enqueue(object : Callback<Post> {
                                override fun onResponse(
                                    call: Call<Post>,
                                    response: Response<Post>
                                ) {
                                    Log.d(
                                        "POST: ",
                                        response
                                            .body()
                                            .toString()
                                    )
                                    postClicked = response.body()!!
                                    showDialog = true

                                }

                                override fun onFailure(call: Call<Post>, t: Throwable) {

                                }
                            })

                    }

            )


        if (showDialog) {
            val vmLike = LikeViewModel()
            var commentText by remember { mutableStateOf("") }
            var showDeleteDialog by remember { mutableStateOf(false) }

            val context = LocalContext.current
            val vmComment = CommentViewModel()
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    color = Color.White, // Set background color of the dialog
                    modifier = Modifier
                        .padding(16.dp)
                        .size(700.dp)
                ) {
                    // Content of the dialog

                        Box (modifier = Modifier
                            .padding(horizontal = 8.dp)){
                            Column {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null, // Provide a content description if needed
                                modifier = Modifier
                                    //.fillMaxSize()
                                    .aspectRatio(1f) // Maintain aspect ratio
                                    .scale(1f)
                            )
                            Row {
                                var likeImage by remember { mutableIntStateOf(R.drawable.heart) }

                                IconButton(
                                    modifier = Modifier
                                        .size(48.dp),
                                    onClick = {
                                        var like: Like
                                        if (likeImage == R.drawable.heart) {
                                            likeImage = R.drawable.heart_liked
                                            like = Like(
                                                0,
                                                Constants.getUser().getUserProfile(),
                                                postClicked
                                            )
                                            vmLike.saveLike(like)
                                        } else {
                                            likeImage = R.drawable.heart
                                            like = Like(
                                                0,
                                                Constants.getUser().getUserProfile(),
                                                postClicked
                                            )

                                            val apiService = RetrofitHelper.apiService
                                            apiService.findLikeByLike(like)
                                                .enqueue(object : Callback<Int> {
                                                    override fun onResponse(
                                                        call: Call<Int>,
                                                        response: Response<Int>
                                                    ) {
                                                        like.setId(response.body()!!)
                                                        vmLike.deleteLike(like)

                                                    }

                                                    override fun onFailure(
                                                        call: Call<Int>,
                                                        t: Throwable
                                                    ) {

                                                    }
                                                })


                                        }
                                    },

                                    ) {
                                    Icon(
                                        painter = painterResource(id = likeImage),
                                        contentDescription = "",
                                        modifier = Modifier.size(35.dp),

                                        )
                                }

                                IconButton(
                                    modifier = Modifier
                                        .size(48.dp),
                                    onClick = {
                                        if (commentText.toString().trim().equals("")) {
                                            displayToast("Enter a comment!", context)
                                        } else {
                                            val comment = Comment(
                                                0, Constants.getUser().getUserProfile(),
                                                commentText.toString().trim(), postClicked
                                            )
                                            vmComment.saveComment(comment)
                                            displayToast("Your comment is added!", context)
                                            commentText = ""
                                            postClicked.setNumberOfComments(postClicked.getNumberOfComments() + 1)
                                        }

                                    },

                                    ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.comment),
                                        contentDescription = "",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                if(Constants.getUser().getUserProfile()!!.getUsername().equals(postClicked.getOwnerUsername().getUsername())){
                                    Text(text = "Delete this post",
                                        modifier = Modifier
                                            .clickable {
                                                showDeleteDialog = true
                                            }
                                            .padding(top = 10.dp).padding(start = 5.dp), color = Color.Red,
                                        style = TextStyle(fontWeight = FontWeight.Bold)
                                    )
                                }

                            }



                            Text(text = postClicked.getNumberOfLikes().toString()+" Likes",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .padding(top = 8.dp))
                            Text(text = postClicked.getNumberOfComments().toString()+" Comments",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .padding(top = 8.dp))

                                // display comments
                                TextField(
                                    value = commentText,
                                    onValueChange = { commentText = it },
                                    label = { Text("Enter your comment") },
                                    modifier = Modifier.padding(8.dp)
                                )
                                var commentList by remember { mutableStateOf(generateComment()) }
                                var page = 1
                                LaunchedEffect(Unit, block = {
                                    vmComment.getCommentList(postClicked.getId(), page)
                                    commentList = vmComment.commentList!!
                                })
                                var maxPageComment:Int = Math.ceil(postClicked.getNumberOfComments()/Constants.getPageSize().toDouble()).toInt()
                                var pageComment:Int = 1
                                var displayPreviousCommentButton by remember { mutableStateOf(false) }
                                var displayNextCommentButton by remember { mutableStateOf(false) }

                                LazyColumn {
                                    items(commentList) { comment ->
                                        val decodedBytesComment = Base64.decode(comment.getCommentOwner()?.getImageData(), Base64.DEFAULT)

                                        val bitmapComment = BitmapFactory.decodeByteArray(decodedBytesComment, 0, decodedBytesComment.size)

                                        Log.d("COMMENT_LIST:", comment.toString())
                                        val doubleClickTimeThreshold = 300 // milliseconds
                                        val configuration = LocalConfiguration.current
                                        val density = LocalDensity.current
                                        val doubleClickTimeThresholdPx = with(density) { doubleClickTimeThreshold * density.density }

                                        var lastClickTime by remember { mutableStateOf(0L) }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 16.dp)
                                                .clickable {
                                                    val currentTime = System.currentTimeMillis()
                                                    if (currentTime - lastClickTime < doubleClickTimeThresholdPx) {
                                                        if (deletedComments.indexOf(comment) == -1) {
                                                            if (comment
                                                                    .getCommentOwner()!!
                                                                    .getUsername()
                                                                    .equals(
                                                                        Constants
                                                                            .getUser()
                                                                            .getUserProfile()!!
                                                                            .getUsername()
                                                                    ) ||
                                                                Constants
                                                                    .getUser()
                                                                    .getUserProfile()!!
                                                                    .getUsername()!!
                                                                    .equals(
                                                                        postClicked
                                                                            .getOwnerUsername()
                                                                            .getUsername()
                                                                    )
                                                            ) {
                                                                vmComment.deleteComment(comment)
                                                                deletedComments.add(comment)
                                                                displayToast(
                                                                    "Comment is deleted. Refresh the page!",
                                                                    context
                                                                )
                                                                postClicked.setNumberOfComments(
                                                                    postClicked.getNumberOfComments() - 1
                                                                )
                                                            }


                                                        }

                                                    }
                                                    lastClickTime = currentTime
                                                },
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start// Align the Row to the top of the Column
                                        ) {
                                            Image(
                                                bitmap = bitmapComment.asImageBitmap(),
                                                contentDescription = null, // Provide a content description if needed
                                                modifier = Modifier
                                                    .size(30.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = comment.getCommentOwner()
                                                        ?.getUsername()!!,
                                                    modifier = Modifier.padding(start = 16.dp),
                                                    style = TextStyle(fontWeight = FontWeight.Bold),
                                                )
                                                Text(text = comment.getCommentText(), modifier = Modifier.padding(start = 16.dp))

                                            }
                                        }
                                        if(comment == commentList.get(commentList.size-1)){
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                if(pageComment < maxPageComment){
                                                    displayNextCommentButton = true
                                                }

                                                if(displayPreviousCommentButton){
                                                    IconButton(
                                                        onClick = {
                                                            vmComment.getCommentList(postClicked.getId(), --pageComment)

                                                            displayNextCommentButton = true
                                                            if(pageComment == 1){
                                                                displayPreviousCommentButton = false
                                                            }

                                                        },
                                                        modifier = Modifier
                                                            .padding(bottom = 16.dp)
                                                            .padding(start = 75.dp)
                                                            .size(24.dp)
                                                            .clip(CircleShape)
                                                            .background(MaterialTheme.colorScheme.secondary)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.previous),
                                                            contentDescription = "",
                                                            tint = Color.White
                                                        )
                                                    }
                                                }

                                                if(displayNextCommentButton){
                                                    val padding = if (displayPreviousCommentButton) {
                                                        PaddingValues(start = 24.dp, top = 0.dp, end = 75.dp, bottom = 16.dp)
                                                    } else {
                                                        PaddingValues(start = 150.dp, top = 8.dp, end = 0.dp, bottom = 16.dp)
                                                    }
                                                    IconButton(
                                                        onClick = {
                                                            vmComment.getCommentList(postClicked.getId(), ++pageComment)
                                                            displayPreviousCommentButton = true
                                                            if(pageComment == maxPageComment){
                                                                displayNextCommentButton = false
                                                            }

                                                        },
                                                        modifier = Modifier
                                                            .padding(padding)
                                                            .size(24.dp)
                                                            .clip(CircleShape)
                                                            .background(MaterialTheme.colorScheme.secondary),

                                                        ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.next),
                                                            contentDescription = "",
                                                            tint = Color.White
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        }

                                if (showDeleteDialog) {
                                    AlertDialog(
                                        onDismissRequest = {
                                            // Dismiss the dialog if the user clicks outside of it
                                            showDeleteDialog = false
                                        },
                                        title = {
                                            Text("Confirmation")
                                        },
                                        text = {
                                            Text("Are you sure you want to delete this post?")
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = {
                                                    // Perform the action and set isConfirmed to true
                                                    val vmPost = PostViewModel()
                                                    vmPost.deletePost(post)
                                                    showDialog = false
                                                    // Dismiss the dialog
                                                    showDeleteDialog = false
                                                }
                                            ) {
                                                Text("YES")
                                            }
                                        },
                                        dismissButton = {
                                            Button(
                                                onClick = {
                                                    // Dismiss the dialog and do not perform the action
                                                    showDeleteDialog = false
                                                }
                                            ) {
                                                Text("NO")
                                            }
                                        }
                                    )
                            }
                    }
                }
            }
        }
    }
}
fun generateComment(): List<Comment>{
    return List(2) { Comment(0, Constants.getUser().getUserProfile(), "", null) }
}
private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@Composable
fun PickImageFromGalleryForProfilePicture() {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(

    ) {

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)

            }



                val inputStream = context.contentResolver.openInputStream(it)
                val byteArrayOutputStream = ByteArrayOutputStream()
                inputStream?.use { input ->
                    input.copyTo(byteArrayOutputStream)
                }
                val byteArray = byteArrayOutputStream.toByteArray()
                val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)
                Images.imageTarget = encodedImage
                val vmUserProfile = UserProfileViewModel()

                val up = UserProfile(Constants.getUser().getUserProfile()!!.getUsername(),
                    Constants.getUser().getUserProfile()!!.getNumberOfFollowers(),
                    Constants.getUser().getUserProfile()!!.getNumberOfFollowings(),
                    Constants.getUser().getUserProfile()!!.getNumberOfPosts(), Constants.getUser(), encodedImage)

                Log.d("", encodedImage)

                vmUserProfile.updateUserProfile(up)
            displayToast("You have changed your profile picture successfully. Please refresh the page!", context)
                // Use encodedImage as needed, such as sending it to a server


        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .padding(0.dp) // Add some padding around the button
                .padding(horizontal = 16.dp, vertical = 8.dp) // Additional padding inside the button
        ) {
            Text(
                text = "Change profile photo",
                color = Color.White, // Text color
                fontSize = 16.sp // Font size
            )
        }

    }

}