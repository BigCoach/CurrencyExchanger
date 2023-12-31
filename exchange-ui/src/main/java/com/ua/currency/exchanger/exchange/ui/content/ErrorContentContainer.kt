package com.ua.currency.exchanger.exchange.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun ErrorContentContainer(visible: Boolean, errorText: String) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        )
    ) {
        ErrorContent(
            errorText = errorText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Stable
fun <T> enterSpring(visibilityThreshold: T) = spring(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = visibilityThreshold
)
