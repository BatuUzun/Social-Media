package com.chattingapp.chattingapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chattingapp.chattingapp.constants.Constants


@Composable
fun TextTitleMain() {
        Box (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp), textAlign = TextAlign.Center,
                color = Color.White,text = buildAnnotatedString {
                    append("Welcome to\n")
                    withStyle(style = SpanStyle( color = Color.Cyan)){
                        append("${Constants.getAppName()}!")
                    }
                }, fontSize = 40.sp)

        }


}