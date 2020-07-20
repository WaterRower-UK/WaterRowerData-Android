package uk.co.waterrower.waterrowerdata.sample.ui.theming

import androidx.compose.Composable
import androidx.ui.material.MaterialTheme
import androidx.ui.material.lightColorPalette

@Composable
fun AppTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = lightColorPalette(
            primary = AppColors.waterRowerBlue
        ),
        content = content
    )
}
