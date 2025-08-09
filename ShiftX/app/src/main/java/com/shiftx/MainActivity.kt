package com.shiftx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.shiftx.navigation.ShiftXNavGraph
import com.shiftx.ui.theme.ShiftXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ShiftXTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ShiftXNavGraph()
                }
            }
        }
    }
}