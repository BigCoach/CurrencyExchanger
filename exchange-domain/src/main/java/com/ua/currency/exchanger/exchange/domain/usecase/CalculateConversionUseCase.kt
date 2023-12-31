package com.ua.currency.exchanger.exchange.domain.usecase

import com.ua.currency.exchanger.architecture.domain.usecase.BackgroundExecutingUseCase
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.domain.exception.InSufficientBalanceDomainException
import com.ua.currency.exchanger.exchange.domain.exception.NoCurrencyRatesDomainException
import com.ua.currency.exchanger.exchange.domain.model.CommissionDomainModel
import com.ua.currency.exchanger.exchange.domain.model.CommissionStrategyDomainModel
import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel
import com.ua.currency.exchanger.exchange.domain.model.ConversionInputDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.ConversionRepository
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyBalanceRepository
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyRateRepository

class CalculateConversionUseCase(
    private val conversionRepository: ConversionRepository,
    private val currencyRateRepository: CurrencyRateRepository,
    private val currencyBalanceRepository: CurrencyBalanceRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ConversionInputDomainModel, ConversionDomainModel>(
    coroutineContextProvider
) {

    override fun executeInBackground(request: ConversionInputDomainModel): ConversionDomainModel {
        val fromCurrencyRate: Double =
            currencyRateRepository.getCurrencyRateFromDb(request.fromCurrency).getOrNull()
                ?: return ConversionDomainModel.Error(NoCurrencyRatesDomainException())
        val toCurrencyRate: Double =
            currencyRateRepository.getCurrencyRateFromDb(request.toCurrency).getOrNull()
                ?: return ConversionDomainModel.Error(NoCurrencyRatesDomainException())
        if (!isBalanceSufficient(
                request.fromCurrency,
                request.fromAmount
            )
        ) return ConversionDomainModel.Error(
            InSufficientBalanceDomainException()
        )
        val commissionAmount: Double =
            getCommissionAmount(request.fromAmount, CommissionStrategyDomainModel.Default())

        val exchangeRateFirstCurrencyToBase = 1.0 / fromCurrencyRate
        val amountInBaseCurrency = (request.fromAmount - commissionAmount) * exchangeRateFirstCurrencyToBase
        val amountInSecondCurrency = amountInBaseCurrency * toCurrencyRate
        val timeStamp = System.currentTimeMillis()

        return ConversionDomainModel.Conversion(
            fromCurrency = request.fromCurrency,
            toCurrency = request.toCurrency,
            fromAmount = request.fromAmount,
            toAmount = amountInSecondCurrency,
            commission = CommissionDomainModel(
                amount = commissionAmount,
                currency = request.fromCurrency
            ),
            timeStamp = timeStamp
        )
    }


    private fun getCommissionAmount(
        amountToSell: Double,
        commissionStrategy: CommissionStrategyDomainModel
    ): Double {
        return when (commissionStrategy) {
            is CommissionStrategyDomainModel.Default -> {
                val previousConversionsCount = conversionRepository.getPreviousConversionsCount()
                if (previousConversionsCount <= commissionStrategy.freeConversions) 0.0 else amountToSell * commissionStrategy.percent / 100
            }

            is CommissionStrategyDomainModel.NoCommission -> {
                0.0
            }

            is CommissionStrategyDomainModel.FreeBelowValue -> {
                if (amountToSell < commissionStrategy.value) 0.0 else amountToSell * commissionStrategy.percent / 100
            }

            is CommissionStrategyDomainModel.Custom -> {
                amountToSell * commissionStrategy.percent / 100
            }
        }
    }

    private fun isBalanceSufficient(fromCurrency: String, fromAmount: Double): Boolean {
        val currencyBalance = currencyBalanceRepository.getCurrencyBalance(fromCurrency).getOrNull()
            ?: return false
        return currencyBalance.balance >= fromAmount
    }



}
