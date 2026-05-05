package com.jeezzzz.colabcraft.ui.theme

import androidx.compose.ui.graphics.Color

// ── Primary Cyan (matching the design) ──────────────────────────────
val PrimaryCyan      = Color(0xFF00D4FF)   // vibrant teal-cyan
val PrimaryCyanDark  = Color(0xFF00A8CC)   // deeper cyan

// ── Background ──────────────────────────────────────────────────────
val BackgroundBlack      = Color(0xFF0A0E1A)   // deep navy-black
val SurfaceDark          = Color(0xFF111827)   // slightly lighter
val SurfaceCard          = Color(0xFF131E2F)   // card background
val SurfaceVariantDark   = Color(0xFF1A2336)   // elevated surface

// ── Text ────────────────────────────────────────────────────────────
val TextWhite  = Color(0xFFFFFFFF)
val TextGray   = Color(0xFF9CA3AF)
val TextMuted  = Color(0xFF6B7280)

// ── Status ──────────────────────────────────────────────────────────
val SuccessGreen = Color(0xFF00FF94)
val ErrorRed     = Color(0xFFFF6B6B)
val TertiaryPink = Color(0xFFFF6B6B)

// ── Tech-tag palette (colorful chips) ───────────────────────────────
val TagYellow  = Color(0xFFFFD700)
val TagRed     = Color(0xFFFF6B6B)
val TagPurple  = Color(0xFF9B59FF)
val TagGreen   = Color(0xFF00FF94)
val TagOrange  = Color(0xFFFF9F43)
val TagCyan    = Color(0xFF00D4FF)
val TagPink    = Color(0xFFFF6EB4)
val TagTeal    = Color(0xFF00E5CC)

// Provide the full tag list for cycling
val techTagColors = listOf(TagYellow, TagRed, TagPurple, TagGreen, TagOrange, TagCyan, TagPink, TagTeal)

// ── Legacy aliases (keep existing references compiling) ──────────────
val PrimaryIndigo       = PrimaryCyan
val PrimaryIndigoDark   = PrimaryCyanDark
val AccentCyan          = PrimaryCyan
val SecondaryCyan       = PrimaryCyanDark
val Purple80            = PrimaryCyan
val PurpleGrey80        = TextGray
val Pink80              = TertiaryPink
val Purple40            = PrimaryCyanDark
val PurpleGrey40        = SurfaceVariantDark
val Pink40              = TertiaryPink
val PrimaryViolet       = PrimaryCyan
val PrimaryVioletDark   = PrimaryCyanDark
