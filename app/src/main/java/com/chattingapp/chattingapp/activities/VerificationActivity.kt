package com.chattingapp.chattingapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import com.chattingapp.chattingapp.views.SelectUsernameForm
import com.chattingapp.chattingapp.views.VerifyEmailDialog

var user = Constants.getUser()
var targetCode: Int = 0

class VerificationActivity : ComponentActivity() {
    private lateinit var  userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                user = Constants.getUser()
                getUserViewModel()
                VerifyEmailDialog(userViewModel)
                SelectUsernameForm()


            }

        }
    }
    private fun getUserViewModel(){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }
}
