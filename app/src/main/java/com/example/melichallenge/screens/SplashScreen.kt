package com.example.melichallenge.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToSearch: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isExpanded = true
        delay(1500)
        onNavigateToSearch()
    }

    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://blog.saleslayer.com/hubfs/mercado-libre-logo.jpg")
                .transformations(CircleCropTransformation())
                .crossfade(true)
                .build(),
            contentDescription = "Logo",
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )
    }
}