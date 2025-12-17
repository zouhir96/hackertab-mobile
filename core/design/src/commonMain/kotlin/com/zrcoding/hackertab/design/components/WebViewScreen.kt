package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun WebViewRoute(
    url: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val state = rememberWebViewState(url = url)
        val jsBridge = rememberWebViewJsBridge()
        WebView(
            modifier = Modifier.fillMaxSize(),
            state = state,
            captureBackPresses = false,
            navigator = rememberWebViewNavigator(),
            webViewJsBridge = jsBridge
        )
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        LaunchedEffect(state.errorsForCurrentRequest) {
            state.errorsForCurrentRequest.forEach { error ->

            }
        }
        DisposableEffect(Unit) {
            state.webSettings.apply {
                isJavaScriptEnabled = true
                supportZoom = true
                androidWebSettings.apply {
                    useWideViewPort = true
                    domStorageEnabled = true
                    allowFileAccess = true
                    allowFileAccessFromFileURLs = true
                    allowUniversalAccessFromFileURLs = true
                    isAlgorithmicDarkeningAllowed = true
                }
            }
            onDispose {}
        }
    }
}