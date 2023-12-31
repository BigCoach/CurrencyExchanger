package com.ua.currency.exchanger.exchange.ui.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LabelText(text: String, modifier: Modifier){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
    )
}
