package com.jeezzzz.colabcraft.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary            = PrimaryCyan,
    onPrimary          = Color(0xFF000000),
    primaryContainer   = Color(0xFF003344),
    onPrimaryContainer = TextWhite,

    secondary          = PrimaryCyanDark,
    onSecondary        = Color(0xFF000000),
    secondaryContainer = SurfaceVariantDark,
    onSecondaryContainer = TextWhite,

    tertiary           = TagRed,
    onTertiary         = TextWhite,

    background         = BackgroundBlack,
    onBackground       = TextWhite,

    surface            = SurfaceCard,
    onSurface          = TextWhite,

    surfaceVariant     = SurfaceVariantDark,
    onSurfaceVariant   = TextGray,

    outline            = SurfaceVariantDark,
    outlineVariant     = Color(0xFF1F2D40),

    error              = ErrorRed,
    onError            = TextWhite,

    inverseSurface     = TextWhite,
    inverseOnSurface   = BackgroundBlack
)

@Composable
fun CoLabCraftTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BackgroundBlack.toArgb()
            window.navigationBarColor = BackgroundBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = Typography,
        content     = content
    )
}
