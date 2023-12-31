package com.ua.currency.exchanger.exchange.ui.mapper

import android.app.AlertDialog
import android.content.Context
import com.ua.currency.exchanger.architecture.presentation.notification.PresentationNotification
import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.architecture.ui.notification.model.UiNotification
import com.ua.currency.exchanger.exchange.presentation.model.ExchangePresentationNotification
import com.ua.currency.exchanger.exchange.ui.R
import com.ua.currency.exchanger.exchange.ui.widget.notificationToast
import java.util.*
import kotlin.math.floor

class ExchangeNotificationToUiMapper(
    private val context: Context
) : NotificationToUiMapper {

    override fun toUi(notification: PresentationNotification): UiNotification =
        when (notification) {
            is ExchangePresentationNotification.CurrencyConverted -> CurrencyConvertedUiNotification(context, notification)
            is ExchangePresentationNotification.AmountToSellExceedsBalance -> ToastNotification(context,
                context.getString(
                    R.string.amount_to_sell_exceeds_balance
                ))
            is ExchangePresentationNotification.CurrencyBalanceNotFound -> ToastNotification(context, notification.message)
            is ExchangePresentationNotification.FailedToExecuteConversion -> ToastNotification(context,
                context.getString(
                    R.string.failed_to_execute_conversion
                ))
            is ExchangePresentationNotification.ConversionNotCalculated -> ToastNotification(context,
                context.getString(
                    R.string.conversion_not_calculated
                ))
            is ExchangePresentationNotification.FailedToUpdateRates -> ToastNotification(context,
                context.getString(
                    R.string.failed_to_update_rates
                ))
            else -> error("Unhandled notification type: $notification")
        }

    private class CurrencyConvertedUiNotification(
        private val context: Context,
        private val currencyConvertedNotification: ExchangePresentationNotification.CurrencyConverted
    ) : UiNotification {
        override fun present() {
            val formattedConvertedToAmount = String.format(Locale.US, "%.2f", floor(currencyConvertedNotification.convertedToAmount * 100) / 100)
            val formattedCommissionAmount = String.format(Locale.US, "%.2f", floor(currencyConvertedNotification.commissionAmount * 100) / 100)

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder
                .setMessage("You have converted ${currencyConvertedNotification.convertedFromAmount} ${currencyConvertedNotification.convertedFromCurrency} to $formattedConvertedToAmount ${currencyConvertedNotification.convertedToCurrency}. Commission Fee $formattedCommissionAmount ${currencyConvertedNotification.commissionCurrency}.")
                .setTitle(context.getString(R.string.currency_converted))
                .setPositiveButton(context.getString(R.string.done)) { dialog, which ->
                    dialog.dismiss()
                }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private class ToastNotification(
        private val context: Context,
        private val text: String
    ) : UiNotification {
        override fun present() {
            notificationToast(context = context, text = text)
        }
    }
}
