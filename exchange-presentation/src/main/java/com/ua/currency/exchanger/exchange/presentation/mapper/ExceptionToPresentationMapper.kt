package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.architecture.domain.exception.DomainException
import com.ua.currency.exchanger.exchange.domain.exception.InSufficientBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.exception.NoBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.exception.NoCurrencyRatesDomainException
import com.ua.currency.exchanger.exchange.presentation.model.ErrorPresentationModel

class ExceptionToPresentationMapper {
    fun toPresentation(exception: DomainException) = when (exception) {
        is InSufficientBalanceDomainException -> ErrorPresentationModel.InSufficientBalance
        is NoBalanceDomainException -> ErrorPresentationModel.NoBalance
        is NoCurrencyRatesDomainException -> ErrorPresentationModel.NoCurrencyRates
        else -> ErrorPresentationModel.Unknown
    }
}
