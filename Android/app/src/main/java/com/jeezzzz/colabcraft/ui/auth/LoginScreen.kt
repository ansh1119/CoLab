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
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
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
        Spacer(modifier = Modifier.height(64.dp))

        // ── Big cyan title (matching screenshot) ──────────────────
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Welcome to\nCoLabCraft.")
                }
            },
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Black,
                fontSize   = 36.sp,
                lineHeight = 44.sp
            ),
            textAlign = TextAlign.Start,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        Text(
            text      = "Sign in to your account.",
            style     = MaterialTheme.typography.bodyLarge,
            color     = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
            modifier  = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ── Fields ────────────────────────────────────────────────
        OutlinedTextField(
            value         = email,
            onValueChange = { email = it },
            label         = { Text("Email") },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(14.dp),
            singleLine    = true,
            colors        = fieldColors()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value                  = password,
            onValueChange          = { password = it },
            label                  = { Text("Password") },
            visualTransformation   = PasswordVisualTransformation(),
            modifier               = Modifier.fillMaxWidth(),
            shape                  = RoundedCornerShape(14.dp),
            singleLine             = true,
            colors                 = fieldColors()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Sign In button ─────────────────────────────────────────
        Button(
            onClick  = { viewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape    = RoundedCornerShape(14.dp),
            enabled  = authState !is AuthState.Loading && email.isNotBlank() && password.isNotBlank(),
            colors   = ButtonDefaults.buttonColors(
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
                    "Sign in",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 16.sp,
                    color      = Color.Black
                )
            }
        }

        // ── Error ─────────────────────────────────────────────────
        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                shape  = RoundedCornerShape(10.dp),
                color  = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
            ) {
                Text(
                    (authState as AuthState.Error).message,
                    color    = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    style    = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Don't have an account?",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    "Register",
                    color      = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor        = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor      = Color.White.copy(alpha = 0.12f),
    focusedLabelColor         = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor       = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor               = MaterialTheme.colorScheme.primary,
    focusedContainerColor     = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor   = MaterialTheme.colorScheme.surface,
    focusedTextColor          = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor        = MaterialTheme.colorScheme.onSurface
)
