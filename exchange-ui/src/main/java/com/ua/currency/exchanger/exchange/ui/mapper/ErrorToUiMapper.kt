package com.ua.currency.exchanger.exchange.ui.mapper

import android.content.res.Resources
import androidx.annotation.StringRes
import com.ua.currency.exchanger.exchange.presentation.model.ErrorPresentationModel
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState
import com.ua.currency.exchanger.exchange.ui.R

class ErrorToUiMapper(
    private val resources: Resources
) {

    fun toUi(presentationError: ExchangeViewState.Error) = when (presentationError.error) {
        ErrorPresentationModel.NoBalance -> string(R.string.failed_to_retrieve_balance)
        ErrorPresentationModel.InSufficientBalance -> string(R.string.insufficient_balance)
        ErrorPresentationModel.NoCurrencyRates -> string(R.string.failed_to_retrieve_currency_rates)
        ErrorPresentationModel.Unknown -> string(R.string.something_went_wrong)
    }

    private fun string(@StringRes stringResourceId: Int) = resources.getString(stringResourceId)
}
