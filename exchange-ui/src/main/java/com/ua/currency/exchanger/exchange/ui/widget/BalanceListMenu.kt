package com.ua.currency.exchanger.exchange.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ua.currency.exchanger.exchange.presentation.model.BalancePresentationModel

@Composable
fun BalanceListMenu(
    onBalanceSelected: (String) -> Unit,
    balances: List<BalancePresentationModel>,
    expanded: Boolean,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            balances.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(text = s.symbol)
                    },
                    onClick = {
                        onBalanceSelected(balances[index].symbol)
                    })
            }
        }
    }
}

