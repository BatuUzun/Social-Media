package com.chattingapp.chattingapp.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chattingapp.chattingapp.R
import com.chattingapp.chattingapp.entity.User
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

private fun isEmailValid(email:String): Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun SignupForm(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Re-enter Password") },
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

        Button(
            onClick = {
                if (!email.trim().equals("") &&  !password.trim().equals("") && !confirmPassword.trim().equals("")){
                    if (password.contains(" ")){
                        displayToast("Password cannot contain a space \" \"!", context)
                    }
                    else if(!password.equals(confirmPassword)){
                        displayToast("Passwords must match!", context)
                    }
                    else if(!isEmailValid(email)){
                        displayToast("Invalid email address!", context)
                    }

                    else{
                        val user = User(email,password, false)
                        val apiService = RetrofitHelper.apiService

                        apiService.saveUser(user).enqueue(object : Callback<User> {

                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                displayToast("Your account is successfully created!", context)

                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.d("ERROR", t.toString())
                                //displayToast("Username is already exists!", context)
                                if(t.toString().contains("failed to connect to")) {

                                    displayToast("Servers are disabled!", context)
                                }

                                else if (t.toString().contains("EOFException")){

                                    displayToast("Email is already exists!", context)
                                }
                                else{
                                    displayToast("Something went wrong!", context)

                                }
                            }
                        })
                    }

                }
                else{
                    displayToast("Fill empty fields!", context)

                }

            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                Color.Gray
            )
        ) {
            Text("Sign Up")
        }
    }
}