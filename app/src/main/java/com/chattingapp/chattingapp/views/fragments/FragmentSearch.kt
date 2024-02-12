package com.chattingapp.chattingapp.views.fragments

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.encoded_image.Images
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.viewmodel.UserProfileViewModel

@Composable
fun FragmentSearch() {
    var query by remember { mutableStateOf("") }
    val vm = UserProfileViewModel()

    var userProfileList by remember { mutableStateOf(generateUserProfile()) }


    var displaySearchBar by remember { mutableStateOf(true) }


    if (displaySearchBar){
        LaunchedEffect(Unit, block = {
            vm.getUserProfileList(query)
            userProfileList = vm.userProfileList!!
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    vm.getUserProfileList(query)
                    userProfileList = vm.userProfileList!!
                },
                placeholder = { Text("Search for people") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    // You can add a clear icon or any other action icon here if needed
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Perform your search operation when the keyboard search action is triggered
                    }
                ),
            )

            // Display search results or other content based on the search query
            Text("Search results for: $query")

            LazyColumn {
                items(userProfileList) { userProfile ->
                    if(!Constants.getUser().getUserProfile()?.getUsername().equals(userProfile.getUsername())){
                        Log.d("USER_PROFILE:",userProfile.toString())
                        val decodedBytes = Base64.decode(userProfile.getImageData(), Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        // Each item in the LazyColumn with an Image

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clickable {
                                    Constants
                                        .getClickedUser()
                                        .setUserProfile(userProfile)
                                    displaySearchBar = false
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start// Align the Row to the top of the Column
                        ) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null, // Provide a content description if needed
                                modifier = Modifier
                                    .size(50.dp)
                            )
                            Text(text = userProfile.getUsername()!!, modifier = Modifier.padding(start = 16.dp))
                        }
                    }

                }
            }

        }
    }
    else{
        FragmentProfile()
    }


}

fun generateUserProfile(): List<UserProfile>{
    return List(1) { UserProfile("", 0, 0,0,null, Images.questionMark) }
}
