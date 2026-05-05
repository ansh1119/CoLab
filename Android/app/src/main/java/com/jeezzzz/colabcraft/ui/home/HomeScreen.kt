package com.jeezzzz.colabcraft.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeezzzz.colabcraft.data.model.Hackathon
import com.jeezzzz.colabcraft.data.model.Post
import com.jeezzzz.colabcraft.ui.theme.techTagColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Tab 0 = "For You", Tab 1 = "Trending"
    var selectedTab by remember { mutableIntStateOf(0) }

    val hackathons by viewModel.hackathons.collectAsState()
    val posts      by viewModel.posts.collectAsState()
    val recommended by viewModel.recommendedPosts.collectAsState()
    val isLoading  by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                // ── Top App Bar ──────────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo badge
                    Surface(
                        modifier = Modifier.size(38.dp),
                        shape    = CircleShape,
                        color    = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("CC", style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "CoLabCraft",
                        style      = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color      = MaterialTheme.colorScheme.onSurface,
                        modifier   = Modifier.weight(1f)
                    )

                    IconButton(onClick = { navController.navigate("notifications") }) {
                        Icon(Icons.Default.Notifications, null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { viewModel.loadData() }) {
                        Icon(Icons.Default.Refresh, null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                // ── Tabs ─────────────────────────────────────────────
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor   = Color.Transparent,
                    contentColor     = MaterialTheme.colorScheme.primary,
                    indicator = { positions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(positions[selectedTab]),
                            color  = MaterialTheme.colorScheme.primary,
                            height = 3.dp
                        )
                    }
                ) {
                    listOf("For You", "Trending").forEachIndexed { i, label ->
                        Tab(
                            selected = selectedTab == i,
                            onClick  = { selectedTab = i },
                            text = {
                                Text(label,
                                    style      = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (selectedTab == i) FontWeight.ExtraBold
                                                 else FontWeight.Medium)
                            },
                            selectedContentColor   = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, strokeWidth = 3.dp)
            }
            return@Scaffold
        }

        val displayPosts = if (selectedTab == 0) recommended else posts

        LazyColumn(
            modifier        = Modifier.padding(innerPadding),
            contentPadding  = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Hackathon carousel only on Trending tab
            if (selectedTab == 1) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        Text("🏆 Trending Events",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color      = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(12.dp))
                        if (hackathons.isEmpty()) {
                            Text("No events yet", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(hackathons) { h ->
                                    HackathonCard(h) { id -> navController.navigate("hackathon_detail/$id") }
                                }
                            }
                        }
                    }
                    Divider(color = Color.White.copy(alpha = 0.05f))
                }
            }

            if (selectedTab == 0) {
                item {
                    Text("✨ Recommended for You",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color      = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier   = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                }
            }

            if (displayPosts.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🚀", fontSize = 40.sp)
                            Spacer(Modifier.height(12.dp))
                            Text("No posts yet",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            } else {
                items(displayPosts, key = { it.id ?: it.description }) { post ->
                    PostCard(
                        post = post,
                        onLikeClick = { post.id?.let { viewModel.likePost(it) } },
                        onRepostClick = { post.id?.let { viewModel.repostPost(it) } },
                        onCommentClick = { post.id?.let { viewModel.commentPost(it) } },
                        onApplyClick = { clicked ->
                            clicked.user?.id?.let { uid ->
                                viewModel.startChat(uid) { teamId ->
                                    navController.navigate("chat/$teamId")
                                }
                            }
                        }
                    )
                    Divider(color = Color.White.copy(alpha = 0.05f))
                }
            }
        }
    }
}

// ── Hackathon Card (compact, for the horizontal row) ──────────────────
@Composable
fun HackathonCard(hackathon: Hackathon, onClick: (Long) -> Unit) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { hackathon.id?.let { onClick(it) } },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            ) {
                Text("EVENT",
                    modifier   = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style      = MaterialTheme.typography.labelSmall,
                    color      = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(10.dp))
            Text(hackathon.title,
                style      = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                maxLines   = 2,
                color      = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(4.dp))
            Text(hackathon.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary)
        }
    }
}

// ── Post Card (matching screenshot layout) ─────────────────────────────
@Composable
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onRepostClick: () -> Unit,
    onCommentClick: () -> Unit,
    onApplyClick: (Post) -> Unit
) {
    val likeCount    = post.likeCount
    val signalCount  = post.repostCount
    val commentCount = post.commentCount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // ── Header row ──────────────────────────────────────────────
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier.size(42.dp),
                shape    = CircleShape,
                color    = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        post.user?.username?.firstOrNull()?.uppercase() ?: "U",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color      = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    post.user?.username ?: "Unknown",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color      = MaterialTheme.colorScheme.onSurface
                )
                val timeAgo = listOf("Just now", "2m ago", "1h ago", "3h ago", "1d ago", "2d ago")[(post.id?.toInt() ?: 0) % 6]
                Text(
                    buildString {
                        append("@${(post.user?.username ?: "user").lowercase().replace(" ", "_")}")
                        append(" · $timeAgo")
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.MoreVert, null,
                    tint   = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp))
            }
        }

        Spacer(Modifier.height(10.dp))

        // ── Description ─────────────────────────────────────────────
        Text(
            text   = post.description,
            style  = MaterialTheme.typography.bodyMedium,
            color  = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
            lineHeight = 21.sp
        )

        // ── Colorful tech tags ──────────────────────────────────────
        post.requiredTechStack?.takeIf { it.isNotEmpty() }?.let { skills ->
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                skills.forEachIndexed { idx, skill ->
                    val tagColor = techTagColors[idx % techTagColors.size]
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = tagColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            skill,
                            modifier   = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style      = MaterialTheme.typography.labelSmall,
                            color      = tagColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // ── Action row ──────────────────────────────────────────────
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Heart
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onLikeClick() }.padding(4.dp)) {
                Icon(Icons.Default.Favorite, null,
                    tint     = if (likeCount > 0) Color(0xFFFF6B6B) else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    "$likeCount",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(18.dp))

            // Signal / Reach
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onRepostClick() }.padding(4.dp)) {
                Text("📶",
                    fontSize = 12.sp,
                    modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    "$signalCount",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(18.dp))

            // Comment
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onCommentClick() }.padding(4.dp)) {
                Text("💬",
                    fontSize = 12.sp,
                    modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    "$commentCount",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.weight(1f))

            // APPLY button
            Surface(
                modifier  = Modifier.clip(RoundedCornerShape(20.dp)).clickable { onApplyClick(post) },
                shape     = RoundedCornerShape(20.dp),
                color     = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    if (post.type == "LOOKING_FOR_TEAM") "CONNECT" else "APPLY",
                    modifier   = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                    style      = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color      = Color.Black
                )
            }
        }
    }
}
