package com.chattingapp.chattingapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chattingapp.chattingapp.views.SignupForm

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
             Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    SignupForm()
                }

        }
    }

}
