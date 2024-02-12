package com.chattingapp.chattingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chattingapp.chattingapp.activities.HomePageActivity
import com.chattingapp.chattingapp.activities.VerificationActivity
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.entity.UserProfile
import com.chattingapp.chattingapp.retrofit.RetrofitHelper
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import com.chattingapp.chattingapp.views.LoginPage
import com.chattingapp.chattingapp.views.TextTitleMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var  userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    getUserViewModel()
                    getUsers()
                    TextTitleMain()
                    LoginPage(userViewModel)

                }
        }
    }
    
    private fun getUserViewModel(){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun getUsers() {
        userViewModel.readAllData.observe(this, Observer { users ->
            if(users.isNotEmpty()){

                Constants.setUser(users.get(0))



                val apiService = RetrofitHelper.apiService
                apiService.getUserProfile(Constants.getUser()).enqueue(object : Callback<UserProfile> {
                    override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                        Log.d("USER1:: ",response.body().toString())
                        Log.d("USER2:: ",Constants.getUser().toString())

                        response.body()?.let {

                            Constants.getUser().setUserProfile(response.body()!!)
                            Log.d("USER3:: ",Constants.getUser().toString())
                        }

                        startHomePageActivity()
                    }
                    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                        Log.d("USER:: ",Constants.getUser().toString())
                        Log.d("USER:: ",t.message!!)
                        startHomePageActivity()
                    }
                })

            }

        })
    }

    private fun startHomePageActivity(){

        if(Constants.getUser().getIsVerifiedEmail() && !Constants.getUser().getUserProfile()!!.getUsername().isNullOrEmpty()){
            val intent = Intent(this, HomePageActivity::class.java)

            this.startActivity(intent)
        }
        else{
            val intent = Intent(this, VerificationActivity::class.java)

            this.startActivity(intent)
        }
    }

}
