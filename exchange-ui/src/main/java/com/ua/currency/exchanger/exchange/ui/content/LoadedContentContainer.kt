package com.ua.currency.exchanger.exchange.ui.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState
import com.ua.currency.exchanger.exchange.ui.R
import com.ua.currency.exchanger.exchange.ui.widget.BalanceListMenu
import com.ua.currency.exchanger.exchange.ui.widget.LabelText
import java.util.*
import kotlin.math.floor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadedContentContainer(
    state: ExchangeViewState.Loaded,
    onFirstCurrencySelected: (String) -> Unit,
    onSecondCurrencySelected: (String) -> Unit,
    onAmountChanged: (Double) -> Unit,
    onExchangeClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var firstBalanceDropDownMenuExpanded by remember { mutableStateOf(false) }
    var secondBalanceDropDownMenuExpanded by remember { mutableStateOf(false) }


    LabelText(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        text = stringResource(R.string.my_balances),
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        items(
            count = state.balances.size,
            key = { index -> state.balances[index].symbol },
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                val formattedBalance =
                    String.format(Locale.US, "%.2f", floor(state.balances[it].balance * 100) / 100)
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "${state.balances[it].symbol} $formattedBalance",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }

    LabelText(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        text = stringResource(R.string.currency_exchange),
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                    text = "Sell",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.weight(1f))
                // Text field amount
                var textFieldValue by remember { mutableStateOf(TextFieldValue(state.amountToSell.toString())) }
                LaunchedEffect(key1 = state.balances, block = {
                    textFieldValue = TextFieldValue(state.amountToSell.toString())
                })
                TextField(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                        .weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        onAmountChanged(it.text.toDoubleOrNull() ?: 0.0)
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                )
                // Text selected currency
                Text(
                    modifier = Modifier
                        .padding(end = 0.dp, top = 16.dp, bottom = 16.dp)
                        .clickable { firstBalanceDropDownMenuExpanded = true },
                    text = state.firstSelectedCurrency,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                        .clickable { firstBalanceDropDownMenuExpanded = true },
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                    text = "Receive",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.weight(1f))

                val formattedAmountToReceive =
                    String.format(Locale.US, "%.2f", floor(state.amountToReceive * 100) / 100)

                Text(
                    modifier = Modifier
                        .padding(end = 28.dp, top = 16.dp, bottom = 16.dp),
                    text = "+$formattedAmountToReceive",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    modifier = Modifier
                        .padding(end = 0.dp, top = 16.dp, bottom = 16.dp)
                        .clickable { secondBalanceDropDownMenuExpanded = true },
                    text = state.secondSelectedCurrency,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                        .clickable { secondBalanceDropDownMenuExpanded = true },
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

    val isButtonEnabled =
        !state.isRefreshing && state.calculatedConversion != null && state.amountToSell != 0.0 && state.amountToSell <= state.amountToSellMaxValue

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .heightIn(min = 50.dp),
        onClick = {
            onExchangeClicked()
            keyboardController?.hide()
            focusManager.clearFocus()
        },
        shape = MaterialTheme.shapes.medium,
        enabled = isButtonEnabled,
    ) {
        Text(
            text = stringResource(R.string.submit),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }

    BalanceListMenu(
        onBalanceSelected = {
            secondBalanceDropDownMenuExpanded = false
            onSecondCurrencySelected(it)
        },
        balances = state.balances,
        expanded = secondBalanceDropDownMenuExpanded,
        onDismiss = { secondBalanceDropDownMenuExpanded = false }
    )

    BalanceListMenu(
        onBalanceSelected = {
            firstBalanceDropDownMenuExpanded = false
            onFirstCurrencySelected(it)
        },
        balances = state.balances,
        expanded = firstBalanceDropDownMenuExpanded,
        onDismiss = { firstBalanceDropDownMenuExpanded = false }
    )


}
