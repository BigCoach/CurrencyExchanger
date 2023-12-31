package com.ua.currency.exchanger.exchange.domain.usecase

import com.ua.currency.exchanger.architecture.domain.usecase.BackgroundExecutingUseCase
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.domain.exception.NoBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.model.BalanceDomainModel
import com.ua.currency.exchanger.exchange.domain.model.CurrencyBalanceListDomainModel
import com.ua.currency.exchanger.exchange.domain.model.CurrencyRateDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyBalanceRepository
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyRateRepository

class GetCurrencyBalanceListUseCase(
    private val currencyRateRepository: CurrencyRateRepository,
    private val currencyBalanceRepository: CurrencyBalanceRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, CurrencyBalanceListDomainModel>(coroutineContextProvider) {

    override fun executeInBackground(request: Unit): CurrencyBalanceListDomainModel {
        val currencyBalanceList = currencyBalanceRepository.getCurrenciesBalanceList()
        if (currencyBalanceList.isNotEmpty()) return CurrencyBalanceListDomainModel.BalanceList(
            currencyBalanceList.sortedByDescending { it.balance }
        )
        val currencyRateList: List<CurrencyRateDomainModel> =
            currencyRateRepository.fetchCurrencyRatesFromApi().getOrNull()
                ?: return CurrencyBalanceListDomainModel.Error(NoBalanceDomainException())
        val currencyBalanceListToSave = currencyRateList.map {
            BalanceDomainModel(
                it.symbol,
                if(it.symbol == "EUR") 1000.00 else 0.0
            )
        }
        currencyBalanceRepository.saveCurrencyBalanceList(currencyBalanceListToSave)
        return CurrencyBalanceListDomainModel.BalanceList(currencyBalanceListToSave.sortedByDescending { it.balance })
    }

}
