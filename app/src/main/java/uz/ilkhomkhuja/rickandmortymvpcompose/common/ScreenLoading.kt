package uz.ilkhomkhuja.rickandmortymvpcompose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun StateScreen(
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        ScreenLoading()
    } else {
        content()
    }
}

@Composable
fun ScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}