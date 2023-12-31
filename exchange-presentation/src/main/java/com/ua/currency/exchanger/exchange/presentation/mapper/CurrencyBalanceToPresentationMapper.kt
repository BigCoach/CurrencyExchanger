package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.exchange.domain.model.CurrencyBalanceListDomainModel
import com.ua.currency.exchanger.exchange.presentation.model.ErrorPresentationModel
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState

class CurrencyBalanceToPresentationMapper(
    private val balanceToPresentationMapper: BalanceToPresentationMapper,
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper
) {

    fun toPresentation(
        currencyBalanceListDomainModel: CurrencyBalanceListDomainModel,
    ): ExchangeViewState {
        return when (currencyBalanceListDomainModel) {
            is CurrencyBalanceListDomainModel.BalanceList -> {
                if(currencyBalanceListDomainModel.balanceList.size < 2){
                    ExchangeViewState.Error(
                        ErrorPresentationModel.NoBalance
                    )
                } else {
                    ExchangeViewState.Loaded(
                        isRefreshing = false,
                        balances = balanceToPresentationMapper.toPresentation(currencyBalanceListDomainModel.balanceList),
                        firstSelectedCurrency = currencyBalanceListDomainModel.balanceList[0].symbol,
                        secondSelectedCurrency = currencyBalanceListDomainModel.balanceList[1].symbol,
                        amountToSellMaxValue = currencyBalanceListDomainModel.balanceList[0].balance,
                        amountToSell = 0.0,
                        amountToReceive = 0.0,
                        calculatedConversion = null
                    )
                }
            }
            is CurrencyBalanceListDomainModel.Error -> ExchangeViewState.Error(
                exceptionToPresentationMapper.toPresentation(currencyBalanceListDomainModel.exception)
            )
        }
    }

    fun toPresentationWithViewStateLoaded(
        currencyBalanceListDomainModel: CurrencyBalanceListDomainModel,
        viewState: ExchangeViewState.Loaded
    ) = when(currencyBalanceListDomainModel){
        is CurrencyBalanceListDomainModel.BalanceList -> viewState.copy(
            isRefreshing = false,
            balances = balanceToPresentationMapper.toPresentation(currencyBalanceListDomainModel.balanceList),
            amountToSellMaxValue = currencyBalanceListDomainModel.balanceList.firstOrNull { it.symbol == viewState.firstSelectedCurrency }?.balance ?: 0.0,
            amountToSell = 0.0,
            amountToReceive = 0.0,
        )
        is CurrencyBalanceListDomainModel.Error -> ExchangeViewState.Error(
            exceptionToPresentationMapper.toPresentation(currencyBalanceListDomainModel.exception)
        )
    }


}
