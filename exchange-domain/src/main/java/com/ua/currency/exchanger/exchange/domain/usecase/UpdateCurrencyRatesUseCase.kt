package com.ua.currency.exchanger.exchange.domain.usecase

import com.ua.currency.exchanger.architecture.domain.usecase.BackgroundExecutingUseCase
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.domain.exception.NoCurrencyRatesDomainException
import com.ua.currency.exchanger.exchange.domain.model.CurrencyRateDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyRateRepository

class UpdateCurrencyRatesUseCase(
    private val currencyRateRepository: CurrencyRateRepository,
    coroutineContextProvider: CoroutineContextProvider,
): BackgroundExecutingUseCase<Unit, Unit>(coroutineContextProvider) {

    override fun executeInBackground(request: Unit) {
        val rates : List<CurrencyRateDomainModel> = currencyRateRepository.fetchCurrencyRatesFromApi().getOrNull()
            ?: throw NoCurrencyRatesDomainException()
        currencyRateRepository.saveCurrencyRatesToDb(rates)
    }

}
