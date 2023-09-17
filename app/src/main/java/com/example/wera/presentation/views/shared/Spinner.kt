package com.example.wera.presentation.views.shared

import android.widget.Spinner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),

            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}