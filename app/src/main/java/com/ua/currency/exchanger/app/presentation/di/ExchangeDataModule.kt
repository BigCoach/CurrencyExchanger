package com.ua.currency.exchanger.app.presentation.di

import android.content.Context
import androidx.room.Room
import com.ua.currency.exchanger.exchange.data.db.AppDatabase
import com.ua.currency.exchanger.exchange.data.db.dao.ConversionDao
import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyBalanceDao
import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyRateDao
import com.ua.currency.exchanger.exchange.data.mapper.CommissionToDataMapper
import com.ua.currency.exchanger.exchange.data.mapper.ConversionToDataMapper
import com.ua.currency.exchanger.exchange.data.mapper.CurrencyBalanceMapper
import com.ua.currency.exchanger.exchange.data.mapper.CurrencyRateMapper
import com.ua.currency.exchanger.exchange.data.network.CurrencyRateService
import com.ua.currency.exchanger.exchange.data.repository.ConversionRepositoryImpl
import com.ua.currency.exchanger.exchange.data.repository.CurrencyBalanceRepositoryImpl
import com.ua.currency.exchanger.exchange.data.repository.CurrencyRateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExchangeDataModule {

    private const val ROOM_DATABASE_NAME = "db"

    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            ROOM_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideConversionDao(appDatabase: AppDatabase): ConversionDao {
        return appDatabase.conversionDao()
    }

    @Provides
    fun provideCurrencyBalanceDao(appDatabase: AppDatabase): CurrencyBalanceDao {
        return appDatabase.currencyBalanceDao()
    }

    @Provides
    fun provideCurrencyRateDao(appDatabase: AppDatabase): CurrencyRateDao {
        return appDatabase.currencyRateDao()
    }

    @Provides
    fun provideCommissionToDataMapper() = CommissionToDataMapper()

    @Provides
    fun provideConversionToDataMapper(
        commissionToDataMapper: CommissionToDataMapper
    ) = ConversionToDataMapper(
        commissionToDataMapper
    )

    @Provides
    fun provideCurrencyBalanceMapper() = CurrencyBalanceMapper()

    @Provides
    fun provideCurrencyRateMapper() = CurrencyRateMapper()

    @Provides
    fun provideConversionRepositoryImpl(
        conversionDao: ConversionDao,
        conversionToDataMapper: ConversionToDataMapper
    ) = ConversionRepositoryImpl(conversionDao, conversionToDataMapper)

    @Provides
    fun provideCurrencyBalanceRepositoryImpl(
        currencyBalanceDao: CurrencyBalanceDao,
        currencyBalanceMapper: CurrencyBalanceMapper
    ) = CurrencyBalanceRepositoryImpl(currencyBalanceDao, currencyBalanceMapper)

    @Provides
    fun provideCurrencyRateRepositoryImpl(
        currencyRateDao: CurrencyRateDao,
        currencyRateMapper: CurrencyRateMapper,
        currencyRateService: CurrencyRateService
    ) = CurrencyRateRepositoryImpl(currencyRateDao, currencyRateMapper, currencyRateService)

}
