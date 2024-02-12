package com.chattingapp.chattingapp.views.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.chattingapp.chattingapp.MainActivity
import com.chattingapp.chattingapp.R
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.entity.Comment
import com.chattingapp.chattingapp.entity.Like
import com.chattingapp.chattingapp.entity.Post
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import com.chattingapp.chattingapp.viewmodel.CommentViewModel
import com.chattingapp.chattingapp.viewmodel.LikeViewModel
import com.chattingapp.chattingapp.viewmodel.PostViewModel
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

@Composable
fun FragmentHome(userViewModel: UserViewModel) {
    val context = LocalContext.current
    var displayPreviousButton by remember { mutableStateOf(false) }
    var displayNextButton by remember { mutableStateOf(false) }
    var vm = PostViewModel()
    var page: Int = 1
    var maxPage by remember { mutableIntStateOf(0) }

    val apiService = RetrofitHelper.apiService

    apiService.getNumberOfPostsOfFollowings(Constants.getUser().getUserProfile()!!).enqueue(object : Callback<Int> {

        override fun onResponse(call: Call<Int>, response: Response<Int>) {
            maxPage = (Math.ceil((response.body()!!/Constants.getPageSize().toDouble()))).toInt()
            if (page == maxPage){
                displayNextButton = false
                displayPreviousButton = false
            }
            else{
                displayNextButton = true
            }
        }

        override fun onFailure(call: Call<Int>, t: Throwable) {

        }
    })

    var postList by remember { mutableStateOf(generatePost()) }
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        PickImageFromGalleryForPost()
        Button(
            onClick = {
                userViewModel.deleteAllUsers()
                Constants.resetClickedUser()
                Constants.resetUser()
                context.startActivity(Intent(context, MainActivity::class.java))
                      },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(text = "Logout",
                color = Color.White, // Text color
                fontSize = 16.sp )
        }
    }

    LaunchedEffect(Unit, block = {
        vm.getPostsOfFollowings(Constants.getUser().getUserProfile()?.getUsername()!!, page)
        postList = vm.postListOfFollowings!!
    })
    LazyColumn(modifier = Modifier
        .padding(top = 50.dp)
        .padding(bottom = 32.dp)) {
        items(postList) { post ->
            Log.d("POSTS:", post.toString())
            //if(!Constants.getUser().getUserProfile()!!.getUsername().equals(post.getOwnerUsername().getUsername()))
                PostFollowingView(post)

            if(post == postList.get(postList.size-1)) {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){


                if (displayPreviousButton) {
                    IconButton(
                        onClick = {
                            vm.getPostsOfFollowings(
                                Constants.getUser().getUserProfile()?.getUsername()!!, --page
                            )
                            postList = vm.postListOfFollowings!!
                            displayNextButton = true

                            if (page != 1) {
                                displayPreviousButton = true
                            } else {
                                displayPreviousButton = false
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .padding(start = 100.dp)
                            .padding(top = 32.dp)

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
                if (displayNextButton) {
                    val padding = if (displayPreviousButton) {
                        PaddingValues(start = 16.dp, top = 32.dp, end = 75.dp, bottom = 16.dp)
                    } else {
                        PaddingValues(start = 240.dp, top = 32.dp, end = 0.dp, bottom = 16.dp)
                    }
                    IconButton(
                        onClick = {
                            vm.getPostsOfFollowings(
                                Constants.getUser().getUserProfile()?.getUsername()!!, ++page
                            )
                            postList = vm.postListOfFollowings!!
                            displayPreviousButton = true

                            if (page < maxPage) {
                                displayNextButton = true
                            } else {
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun PostFollowingView(post: Post) {
    var postClicked: Post = post
    var showDialog by remember { mutableStateOf(false) }

    var decodedBytes = Base64.decode(post.getOwnerUsername().getImageData(), Base64.DEFAULT)
    var bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        Row (verticalAlignment = Alignment.CenterVertically){
            painterResource(id = R.drawable.ic_launcher_background)

            // Use the Image composable to display the image

            // Use the Image composable to display the image
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 32.dp)
                    .padding(start = 16.dp)
            )

            Column (modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = post.getOwnerUsername().getUsername()!!,fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 32.dp))


            }
            }

    Log.d("NULL_POST:",post.getId().toString())
      decodedBytes = Base64.decode(post.getImage(), Base64.DEFAULT)
      bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null, // Provide a content description if needed
            modifier = Modifier
                //.weight(1f)
                .fillMaxSize()
                .padding(end = 16.dp, start = 16.dp)
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
                    }
                }
            }
        }




}
private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}
@Composable
fun PickImageFromGalleryForPost() {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

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

            val vmPost = PostViewModel()
            val post = Post(0, Constants.getUser().getUserProfile()!!, 0,0, encodedImage)
            vmPost.savePost(post)
            displayToast("Your post is shared successfully.", context)
        }

        //Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .padding(end = 32.dp) // Add some padding around the button
                .padding(horizontal = 16.dp, vertical = 0.dp) // Additional padding inside the button
        ) {
            Text(
                text = "New Post",
                color = Color.White, // Text color
                fontSize = 16.sp // Font size
            )
        }

    }

}