package com.ua.currency.exchanger.exchange.domain.usecase

import com.ua.currency.exchanger.architecture.domain.usecase.BackgroundExecutingUseCase
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.domain.exception.InSufficientBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.exception.NoBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.ConversionRepository
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyBalanceRepository

class ExecuteAndSaveConversionUseCase(
    private val conversionRepository: ConversionRepository,
    private val currencyBalanceRepository: CurrencyBalanceRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ConversionDomainModel.Conversion, Unit>(
    coroutineContextProvider
) {

    override fun executeInBackground(request: ConversionDomainModel.Conversion) {
        val currencyBalanceFrom = currencyBalanceRepository.getCurrencyBalance(request.fromCurrency).getOrNull()
            ?: throw NoBalanceDomainException()
        val currencyBalanceTo = currencyBalanceRepository.getCurrencyBalance(request.toCurrency).getOrNull()
            ?: throw NoBalanceDomainException()
        if(request.fromAmount > currencyBalanceFrom.balance) throw InSufficientBalanceDomainException()
        val newBalanceFrom = currencyBalanceFrom.copy(balance = currencyBalanceFrom.balance - request.fromAmount)
        val newBalanceTo = currencyBalanceTo.copy(balance = currencyBalanceTo.balance + request.toAmount)
        currencyBalanceRepository.updateCurrencyBalance(newBalanceFrom)
        currencyBalanceRepository.updateCurrencyBalance(newBalanceTo)
        conversionRepository.saveConversion(request)
    }
}
