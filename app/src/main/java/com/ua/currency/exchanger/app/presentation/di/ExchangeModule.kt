package com.ua.currency.exchanger.app.presentation.di

import com.ua.currency.exchanger.architecture.domain.UseCaseExecutor
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.data.repository.ConversionRepositoryImpl
import com.ua.currency.exchanger.exchange.data.repository.CurrencyBalanceRepositoryImpl
import com.ua.currency.exchanger.exchange.data.repository.CurrencyRateRepositoryImpl
import com.ua.currency.exchanger.exchange.domain.usecase.CalculateConversionUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.ExecuteAndSaveConversionUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.GetCurrencyBalanceListUseCase
import com.ua.currency.exchanger.exchange.domain.usecase.UpdateCurrencyRatesUseCase
import com.ua.currency.exchanger.exchange.presentation.mapper.BalanceToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.CommissionMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.ConversionMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.ConversionStateToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.CurrencyBalanceToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.mapper.ExceptionToPresentationMapper
import com.ua.currency.exchanger.exchange.presentation.viewmodel.ExchangeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExchangeModule {

    @Provides
    fun provideCalculateConversionUseCase(
        conversionRepository: ConversionRepositoryImpl,
        currencyRateRepository: CurrencyRateRepositoryImpl,
        currencyBalanceRepository: CurrencyBalanceRepositoryImpl,
        coroutineContextProvider: CoroutineContextProvider,
    ) = CalculateConversionUseCase(
        conversionRepository,
        currencyRateRepository,
        currencyBalanceRepository,
        coroutineContextProvider
    )

    @Provides
    fun provideExecuteAndSaveConversionUseCase(
        conversionRepository: ConversionRepositoryImpl,
        currencyBalanceRepository: CurrencyBalanceRepositoryImpl,
        coroutineContextProvider: CoroutineContextProvider,
    ) = ExecuteAndSaveConversionUseCase(
        conversionRepository,
        currencyBalanceRepository,
        coroutineContextProvider
    )

    @Provides
    fun provideGetCurrencyBalanceListUseCase(
        currencyRateRepository: CurrencyRateRepositoryImpl,
        currencyBalanceRepository: CurrencyBalanceRepositoryImpl,
        coroutineContextProvider: CoroutineContextProvider,
    ) = GetCurrencyBalanceListUseCase(
        currencyRateRepository,
        currencyBalanceRepository,
        coroutineContextProvider
    )

    @Provides
    fun provideUpdateCurrencyRatesUseCase(
        currencyRateRepository: CurrencyRateRepositoryImpl,
        coroutineContextProvider: CoroutineContextProvider,
    ) = UpdateCurrencyRatesUseCase(
        currencyRateRepository,
        coroutineContextProvider
    )

    @Provides
    fun provideBalanceToPresentationMapper() = BalanceToPresentationMapper()

    @Provides
    fun provideCommissionMapper() = CommissionMapper()

    @Provides
    fun provideConversionMapper(
        commissionMapper: CommissionMapper
    ) = ConversionMapper(
        commissionMapper
    )

    @Provides
    fun provideExceptionToPresentationMapper() = ExceptionToPresentationMapper()

    @Provides
    fun provideConversionStateToPresentationMapper(
        exceptionToPresentationMapper: ExceptionToPresentationMapper,
        conversionMapper: ConversionMapper
    ) = ConversionStateToPresentationMapper(
        exceptionToPresentationMapper,
        conversionMapper
    )

    @Provides
    fun provideCurrencyBalanceToPresentationMapper(
        balanceToPresentationMapper: BalanceToPresentationMapper,
        exceptionToPresentationMapper: ExceptionToPresentationMapper,
    ) = CurrencyBalanceToPresentationMapper(
        balanceToPresentationMapper,
        exceptionToPresentationMapper,
    )

    @Provides
    fun provideExchangeViewModel(
        getCurrencyBalanceListUseCase: GetCurrencyBalanceListUseCase,
        executeAndSaveConversionUseCase: ExecuteAndSaveConversionUseCase,
        updateCurrencyRatesUseCase: UpdateCurrencyRatesUseCase,
        calculateConversionUseCase: CalculateConversionUseCase,
        exceptionToPresentationMapper: ExceptionToPresentationMapper,
        currencyBalanceToPresentationMapper: CurrencyBalanceToPresentationMapper,
        conversionStateToPresentationMapper: ConversionStateToPresentationMapper,
        conversionMapper: ConversionMapper,
        useCaseExecutor: UseCaseExecutor,
    ) = ExchangeViewModel(
        getCurrencyBalanceListUseCase,
        executeAndSaveConversionUseCase,
        updateCurrencyRatesUseCase,
        calculateConversionUseCase,
        exceptionToPresentationMapper,
        currencyBalanceToPresentationMapper,
        conversionStateToPresentationMapper,
        conversionMapper,
        useCaseExecutor
    )

}
