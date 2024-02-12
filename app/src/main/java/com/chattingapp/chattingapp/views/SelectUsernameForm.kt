package com.chattingapp.chattingapp.views

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chattingapp.chattingapp.activities.HomePageActivity
import com.chattingapp.chattingapp.activities.user
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.encoded_image.Images
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@Composable
fun SelectUsernameForm(){
    var username by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isNullUserProfile:Boolean = false
    user.getUserProfile()?.let {
        // userProfile is not null, do something with it
        isNullUserProfile = false
    } ?: run {
        // userProfile is null
        isNullUserProfile = true


    }
    var displayUsernameBox by remember { mutableStateOf(isNullUserProfile) }


    if(displayUsernameBox){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Button(
                onClick = {
                    if(username.trim().isEmpty()){
                        displayToast("Enter a username", context)
                    }
                    else{


                        val user = Constants.getUser()
                        val imageData: String = Images.questionMark
                        val userProfile = UserProfile(username,0,0,0, user, imageData)



                        val apiService = RetrofitHelper.apiService

                        apiService.saveUsername(userProfile).enqueue(object : Callback<UserProfile> {

                            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                                if (response.body() == null){
                                    displayToast("Username is taken!", context)
                                }
                                else{
                                    displayToast("Hello ${response.body()!!.getUsername()}!", context)

                                    Constants.setUser(user)
                                    Log.d("IS_VERIFIED", user.toString())

                                    Constants.getUser().setUserProfile(response.body()!!)
                                    Log.d("IS_VERIFIED", user.toString())

                                    displayUsernameBox = false
                                    Log.d("IS_VERIFIED", user.getIsVerifiedEmail().toString())

                                    if(user.getIsVerifiedEmail()){
                                        val intent = Intent(context, HomePageActivity::class.java)
                                        context.startActivity(intent)
                                    }

                                }
                            }

                            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                                displayToast("Username is taken!", context)

                                Log.d("ERROR", user.getEmail())
                                Log.d("ERROR", t.toString())
                            }
                        })



                    }

                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .padding(top = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    Color.Gray
                )
            ) {
                Text("Select", fontSize = 20.sp)
            }
        }
    }




}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

        SelectUsernameForm()

}