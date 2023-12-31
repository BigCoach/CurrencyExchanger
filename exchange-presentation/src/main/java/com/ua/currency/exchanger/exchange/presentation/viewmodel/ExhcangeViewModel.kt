package com.ua.currency.exchanger.exchange.presentation.viewmodel

import com.ua.currency.exchanger.architecture.domain.UseCaseExecutor
import com.ua.currency.exchanger.architecture.presentation.viewmodel.BaseViewModel
import com.ua.currency.exchanger.exchange.domain.model.ConversionInputDomainModel
import com.ua.currency.exchanger.exchange.domain.model.CurrencyBalanceListDomainModel
import com.ua.currency.exchanger.exchange.domain.usecase.CalculateConversionUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.GetCurrencyBalanceListUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.ExecuteAndSaveConversionUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.UpdateCurrencyRatesUseCase
import com.ua.currency.exchanger.exchange.presentation.mapper.ConversionMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.ConversionStateToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.CurrencyBalanceToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.ExceptionToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.model.ExchangePresentationNotification
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ExchangeViewModel(
    private val getCurrencyBalanceListUseCase: GetCurrencyBalanceListUseCase,
    private val executeAndSaveConversionUseCase: ExecuteAndSaveConversionUseCase,
    private val updateCurrencyRatesUseCase: UpdateCurrencyRatesUseCase,
    private val calculateConversionUseCase: CalculateConversionUseCase,
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper,
    private val currencyBalanceToPresentationMapper: CurrencyBalanceToPresentationMapper,
    private val conversionStateToPresentationMapper: ConversionStateToPresentationMapper,
    private val conversionMapper: ConversionMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<ExchangeViewState, ExchangePresentationNotification>(useCaseExecutor),
    CoroutineScope {
    override val initialViewState = ExchangeViewState.Loading

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()


    fun onEnter() {
        updateViewState(ExchangeViewState.Loading)
        updateCurrencyRates()
        getCurrencyBalanceList()
        launch {
            while (isActive) {
                delay(5.seconds)
                updateCurrencyRates()
            }
        }
    }

    private fun updateCurrencyRates() {
        updateCurrencyRatesUseCase(
            onResult = { result ->
                launch {
                    viewState.first().let { viewState ->
                        if (viewState is ExchangeViewState.Loaded) {
                            println("viewState first: $viewState")
                            calculateConversion(viewState)
                        }
                    }
                }
            },
            onException = { exception ->
                notify(ExchangePresentationNotification.FailedToUpdateRates)
            }
        )
    }

    private fun getCurrencyBalanceList() {
        getCurrencyBalanceListUseCase(
            onResult = { result ->
                println("getCurrencyBalanceList result = $result")
                when (result) {
                    is CurrencyBalanceListDomainModel.BalanceList -> {
                        launch {
                            viewState.first().let { viewState ->
                                if (viewState is ExchangeViewState.Loaded) {
                                    updateViewState(
                                        currencyBalanceToPresentationMapper.toPresentationWithViewStateLoaded(
                                            result,
                                            viewState
                                        )
                                    )
                                } else {
                                    updateViewState(
                                        currencyBalanceToPresentationMapper.toPresentation(
                                            result
                                        )
                                    )
                                }
                            }
                        }
                    }

                    is CurrencyBalanceListDomainModel.Error -> {
                        updateViewState(
                            ExchangeViewState.Error(
                                exceptionToPresentationMapper.toPresentation(
                                    result.exception
                                )
                            )
                        )
                    }
                }
            },
            onException = { exception ->
                updateViewState(
                    ExchangeViewState.Error(
                        exceptionToPresentationMapper.toPresentation(
                            exception
                        )
                    )
                )
            }
        )
    }

    fun onFirstCurrencyChangedAction(currency: String, viewState: ExchangeViewState.Loaded) {
        launch {
            val selectedCurrencyBalance = viewState.balances.firstOrNull { it.symbol == currency }
            if (selectedCurrencyBalance == null) {
                notify(ExchangePresentationNotification.CurrencyBalanceNotFound(currency))
                return@launch
            }
            val amountToSell = if (viewState.amountToSell > selectedCurrencyBalance.balance) {
                selectedCurrencyBalance.balance
            } else {
                viewState.amountToSell
            }
            val updatedState = viewState.copy(
                firstSelectedCurrency = currency,
                amountToSellMaxValue = selectedCurrencyBalance.balance,
                amountToSell = amountToSell
            )
            updateViewState(
                newState = updatedState
            )
            calculateConversion(updatedState)
        }
    }

    fun onSecondCurrencyChangedAction(currency: String, viewState: ExchangeViewState.Loaded) {
        val updatedState = viewState.copy(secondSelectedCurrency = currency)
        println("onSecondCurrencyChangedAction: $currency")
        println("onSecondCurrencyChangedAction: $updatedState")
        updateViewState(newState = updatedState)
        calculateConversion(updatedState)
    }

    fun onSellAmountChangedAction(amount: Double, viewState: ExchangeViewState.Loaded) {
        println("onSellAmountChangedAction: $amount")
        val updatedState = viewState.copy(amountToSell = amount)
        updateViewStateWithAction(newState = updatedState) {
            if (amount > viewState.amountToSellMaxValue) {
                notify(ExchangePresentationNotification.AmountToSellExceedsBalance)
            } else {
                calculateConversion(updatedState)
            }
        }
    }

    private fun calculateConversion(viewState: ExchangeViewState.Loaded) {
        println("calculateConversion: ${viewState.amountToSell}, ${viewState.amountToSellMaxValue}")
        if (viewState.amountToSell > viewState.amountToSellMaxValue) return
        if (viewState.amountToSell == 0.0) updateViewState(viewState.copy(amountToReceive = 0.0))
        calculateConversionUseCase(
            value = ConversionInputDomainModel(
                fromCurrency = viewState.firstSelectedCurrency,
                toCurrency = viewState.secondSelectedCurrency,
                fromAmount = viewState.amountToSell
            ),
            onResult = { result ->
                println("result = $result")
                updateViewState(
                    conversionStateToPresentationMapper.toPresentation(result, viewState)
                )
            },
            onException = { exception ->
                updateViewState(
                    ExchangeViewState.Error(
                        exceptionToPresentationMapper.toPresentation(
                            exception
                        )
                    )
                )
            }
        )
    }

    fun onSubmitAction(viewState: ExchangeViewState.Loaded) {
        val conversion = viewState.calculatedConversion
        if (conversion == null) {
            notify(ExchangePresentationNotification.ConversionNotCalculated)
            return
        }
        updateViewState(
            newState = viewState.copy(
                isRefreshing = true
            )
        )
        executeAndSaveConversionUseCase(
            value = conversionMapper.toDomain(conversion),
            onResult = { result ->
                getCurrencyBalanceList()
                notify(
                    ExchangePresentationNotification.CurrencyConverted(
                        convertedFromAmount = conversion.fromAmount,
                        convertedFromCurrency = conversion.fromCurrency,
                        convertedToAmount = conversion.toAmount,
                        convertedToCurrency = conversion.toCurrency,
                        commissionAmount = conversion.commission.amount,
                        commissionCurrency = conversion.commission.currency,
                        timeStamp = conversion.timeStamp
                    )
                )
            },
            onException = { exception ->
                notify(ExchangePresentationNotification.FailedToExecuteConversion)
                updateViewState(newState = viewState.copy(isRefreshing = false))
            }
        )
    }

}
