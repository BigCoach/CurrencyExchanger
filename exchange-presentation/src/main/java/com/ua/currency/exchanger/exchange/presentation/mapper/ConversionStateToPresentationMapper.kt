package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState

class ConversionStateToPresentationMapper(
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper,
    private val conversionMapper: ConversionMapper,
) {
    fun toPresentation(
        conversionDomainModel: ConversionDomainModel,
        currentLoadedState: ExchangeViewState.Loaded
    ) = when (conversionDomainModel) {
        is ConversionDomainModel.Conversion -> currentLoadedState.copy(
            isRefreshing = false,
            amountToReceive = conversionDomainModel.toAmount,
            calculatedConversion = conversionMapper.toPresentation(conversionDomainModel)
        )

        is ConversionDomainModel.Error -> ExchangeViewState.Error(
            exceptionToPresentationMapper.toPresentation(conversionDomainModel.exception)
        )
    }
}
