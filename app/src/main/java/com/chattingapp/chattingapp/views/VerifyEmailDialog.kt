package com.chattingapp.chattingapp.views

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chattingapp.chattingapp.activities.HomePageActivity
import com.chattingapp.chattingapp.activities.targetCode
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private fun displayToast(msg:String, context: Context){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VerifyEmailDialog(userViewModel: UserViewModel) {
    val context = LocalContext.current
    var code by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var displayButton by remember { mutableStateOf(!Constants.getUser().getIsVerifiedEmail()) }


    val user = Constants.getUser()

    if(displayButton){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {
            // Button to show the dialog
            Button(
                onClick = { showDialog = true


                    val apiService = RetrofitHelper.apiService

                    apiService.generateVerificationCode().enqueue(object : Callback<Int> {

                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                            targetCode = response.body()!!
                            Log.d("ERROR", user.getEmail())
                            Log.d("ERROR", targetCode.toString())

                            val input:Map<String, Int> = mapOf(user.getEmail() to targetCode)
                            apiService.sendEmail(input).enqueue(object : Callback<Int> {

                                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                    targetCode = response.body()!!
                                    Log.d("ERROR", user.getEmail())
                                    Log.d("ERROR", targetCode.toString())


                                }

                                override fun onFailure(call: Call<Int>, t: Throwable) {
                                    displayToast("ERROR!", context)

                                    Log.d("ERROR_FAILURE", user.getEmail())
                                    Log.d("ERROR_FAILURE", t.toString())
                                }
                            })








                        }

                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            displayToast("ERROR!", context)

                            Log.d("ERROR_FAILURE", user.getEmail())
                            Log.d("ERROR_FAILURE", t.toString())
                        }
                    })

                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
                ,

                colors = ButtonDefaults.buttonColors(
                    Color.Gray
                )
            ) {
                Text("Verify your account!")
            }

            // Custom Dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Handle dismiss
                        showDialog = false
                    },
                    title = { Text("An email has been sent. Please check your email!") },
                    text = {
                        // Number input field
                        LocalSoftwareKeyboardController.current

                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it },
                            label = { Text("Verification Code: ") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {

                                Log.d("ERROR_CODE", code)
                                Log.d("ERROR_TARGET_CODE", targetCode.toString())
                                if(code.toInt() == targetCode){


                                    val apiService = RetrofitHelper.apiService

                                    apiService.changeEmailStatus(user).enqueue(object : Callback<Int> {

                                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                            showDialog = false
                                            displayToast("Your email is verified!", context)
                                            Constants.getUser().setIsVerifiedEmail(true)
                                            userViewModel.updateUser(Constants.getUser())
                                            displayButton = false

                                            if(!Constants.getUser().getUserProfile()?.getUsername().isNullOrEmpty()){
                                                val intent = Intent(context, HomePageActivity::class.java)
                                                context.startActivity(intent)
                                            }
                                        }

                                        override fun onFailure(call: Call<Int>, t: Throwable) {
                                            Log.d("ERROR_HERE", user.getEmail())
                                            Log.d("ERROR_HERE", t.toString())
                                        }
                                    })
                                }
                                else{
                                    displayToast("Verification code is wrong!", context)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                Color.Gray)
                        ) {
                            Text("Verify my account")
                        }
                    },

                    )
            }
        }
    }


}
