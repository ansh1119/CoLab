package com.jeezzzz.colabcraft.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var username   by remember { mutableStateOf("") }
    var email      by remember { mutableStateOf("") }
    var password   by remember { mutableStateOf("") }
    var bio        by remember { mutableStateOf("") }
    var techStack  by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(56.dp))

        // ── Big cyan title ─────────────────────────────────────────
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Create your\naccount.")
                }
            },
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Black,
                fontSize   = 34.sp,
                lineHeight = 42.sp
            ),
            textAlign = TextAlign.Start,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )
        Text(
            text      = "Join the CoLabCraft community.",
            style     = MaterialTheme.typography.bodyLarge,
            color     = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
            modifier  = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(36.dp))

        // ── Fields ────────────────────────────────────────────────
        OutlinedTextField(
            value         = username,
            onValueChange = { username = it },
            label         = { Text("Full Name") },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(14.dp),
            singleLine    = true,
            colors        = fieldColors()
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value         = email,
            onValueChange = { email = it },
            label         = { Text("College Email ID") },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(14.dp),
            singleLine    = true,
            colors        = fieldColors()
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value                = password,
            onValueChange        = { password = it },
            label                = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier             = Modifier.fillMaxWidth(),
            shape                = RoundedCornerShape(14.dp),
            singleLine           = true,
            colors               = fieldColors()
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value         = bio,
            onValueChange = { bio = it },
            label         = { Text("Short Bio") },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(14.dp),
            maxLines      = 3,
            colors        = fieldColors()
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value         = techStack,
            onValueChange = { techStack = it },
            label         = { Text("Domain / Skills") },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(14.dp),
            singleLine    = true,
            supportingText = {
                Text("e.g. Kotlin, React, Figma — separate with commas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
            },
            colors = fieldColors()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── Create button ──────────────────────────────────────────
        Button(
            onClick = {
                val skills = techStack.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                viewModel.register(username, email, password, bio, skills)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape   = RoundedCornerShape(14.dp),
            enabled = authState !is AuthState.Loading
                    && username.isNotBlank()
                    && email.isNotBlank()
                    && password.isNotBlank(),
            colors  = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor   = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier    = Modifier.size(22.dp),
                    color       = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    "Create",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 16.sp,
                    color      = Color.Black
                )
            }
        }

        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)) {
                Text(
                    (authState as AuthState.Error).message,
                    color    = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    style    = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already a member?", color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Sign In", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
