package com.chattingapp.chattingapp.views

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chattingapp.chattingapp.activities.HomePageActivity
import com.chattingapp.chattingapp.activities.SignUpActivity
import com.chattingapp.chattingapp.activities.VerificationActivity
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.entity.User
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}


@Composable
fun LoginPage(userViewModel: UserViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isKeepSignedIn by remember { mutableStateOf(false) }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isKeepSignedIn,
                    onCheckedChange = { isKeepSignedIn = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = if (isKeepSignedIn) Color.White else Color.Gray,
                        checkedColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
                Text("Keep me signed in", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(end = 0.dp, top = 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    text = "New to ${Constants.getAppName()}? ",
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable {
                            context.startActivity(Intent(context, SignUpActivity::class.java))

                        },
                    textAlign = TextAlign.Left,
                    color = Color.Cyan, text = "Create an account! ", fontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    val user = User(email, password)


                    val apiService = RetrofitHelper.apiService
                    apiService.checkUser(user).enqueue(object : Callback<User> {


                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Constants.setUser(response.body()!!)
                            var intent = Intent(context, HomePageActivity::class.java)
                            if(!Constants.getUser().getIsVerifiedEmail()||Constants.getUser().getUserProfile()?.getUsername().isNullOrEmpty()){
                                intent = Intent(context, VerificationActivity::class.java)
                            }

                            response.body()?.let { Constants.setUser(it) }

                            if (isKeepSignedIn) {

                                userViewModel.addUser(Constants.getUser())
                            }

                            else{
                                context.startActivity(intent)
                            }

                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            displayToast("Try again!", context)
                            Log.d("ERROR", t.toString())
                        }
                    })


                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    Color.Gray
                )
            ) {
                Text("Login", fontSize = 20.sp)
            }
        }
}