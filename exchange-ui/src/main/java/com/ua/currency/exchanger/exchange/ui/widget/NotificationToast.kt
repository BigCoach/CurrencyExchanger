package com.ua.currency.exchanger.exchange.ui.widget

import android.content.Context
import android.widget.Toast

fun notificationToast(context: Context, text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
