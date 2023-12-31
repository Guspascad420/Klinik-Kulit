package com.example.klinikkulit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.klinikkulit.R
import com.example.klinikkulit.ui.theme.Purple20
import com.example.klinikkulit.ui.theme.lato
import com.example.klinikkulit.utils.AppText
import com.example.klinikkulit.utils.NavRoute
import com.example.klinikkulit.viewmodels.AuthUiState
import com.example.klinikkulit.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    val allNotFilled = remember {
        derivedStateOf {
            viewModel.email.text.isEmpty() || viewModel.password.text.isEmpty()
        }
    }

    LaunchedEffect(key1 = viewModel.authUiState) {
        if (viewModel.authUiState == AuthUiState.Success) {
            navController.navigate(NavRoute.HOME.name)
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.padding(vertical = 37.dp), Alignment.Center) {
            Image(painterResource(R.drawable.logo_1), "Logo")
        }
        Text(
            "Sign In", fontFamily = lato,
            fontWeight = FontWeight.Bold, fontSize = 33.sp
        )
        Spacer(Modifier.height(45.dp))
        Column {
            Text("Email", fontFamily = lato, fontSize = 15.sp)
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Purple20
                ),
                leadingIcon = { Icon(painterResource(R.drawable.email), "Email", tint = Purple20) }
            )
        }
        Spacer(Modifier.height(20.dp))
        Column {
            Text("Password", fontFamily = lato, fontSize = 15.sp)
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Purple20
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.isPasswordVisible = !viewModel.isPasswordVisible
                    }) {
                        Icon(
                            if (viewModel.isPasswordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff, null
                        )
                    }
                },
                leadingIcon = { Icon(Icons.Default.Lock, "Lock", tint = Purple20) },
                visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            if (viewModel.error) {
                Spacer(Modifier.height(8.dp))
                AppText("Email / Password salah", FontWeight.Medium, 18.sp, Color.Red)
            }
        }
        Button(
            onClick = { viewModel.handleLogin() },
            Modifier
                .width(280.dp)
                .padding(vertical = 30.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(Purple20),
            enabled = !allNotFilled.value
        ) {
            Text("Sign In", fontFamily = lato,
                fontWeight = FontWeight.Medium, color = Color.White,
                fontSize = 22.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Belum punya akun?", fontFamily = lato, fontSize = 14.sp)
            TextButton(onClick = { navController.navigate(NavRoute.SIGNUP.name) }) {
                Text("Daftar", fontFamily = lato,
                    fontSize = 14.sp, color = Purple20,
                    fontWeight = FontWeight.Bold)
            }
        }
        Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
            Text("Copyright 2023 - JBK", Modifier.padding(bottom = 20.dp),
                fontFamily = lato, fontSize = 12.sp)
        }
    }
}