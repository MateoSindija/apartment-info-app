package com.example.apartmentinfoapp.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.di.AppModule
import com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme
import com.example.apartmentinfoapp.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModelLogin: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = this
        if (AppModule.provideAccessTokenProvider(this).getAccessToken().isNotEmpty()) {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainActivityIntent)
        }

        setContent {
            ApartmentInfoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF2F4F5),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LoginForm(viewModelLogin, activity)
                    }

                }
            }
        }
    }
}

@Composable
fun LoginForm(viewModelLogin: LoginViewModel, activity: Activity) {
    var apartmentIdentification by remember { mutableStateOf("") }
    var apartmentPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(viewModelLogin.loginState) {
        if (viewModelLogin.loginState.isSuccess) {
            val mainActivityIntent = Intent(context, MainActivity::class.java)
            context.startActivity(mainActivityIntent)
        }
    }

    Column(
        modifier = Modifier
            .width(450.dp)
    ) {
        Text(text = "Apartment identification")
        Spacer(modifier = Modifier.height(5.dp))
        LoginInput({ apartmentIdentification = it }, "Enter apartment id", apartmentIdentification)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Apartment password")
        Spacer(modifier = Modifier.height(5.dp))
        LoginInput({ apartmentPassword = it }, "Enter apartment password", apartmentPassword)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                viewModelLogin.login(
                    apartmentIdentification,
                    apartmentPassword,
                    activity
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Login", color = Color.White)
        }
        if (viewModelLogin.loginState.error?.isNotEmpty() == true) {
            return Text(text = viewModelLogin.loginState.error.toString())
        }
    }
}

@Composable
fun LoginInput(onTextChanged: (String) -> Unit, placeholderText: String, textState: String) {
    TextField(
        value = textState,
        onValueChange = onTextChanged,
        placeholder = { Text(placeholderText) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // Hide the underline indicator
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = colorResource(id = R.color.gains_boro)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}