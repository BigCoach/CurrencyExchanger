package com.ua.currency.exchanger.exchange.domain.model

sealed class CommissionStrategyDomainModel {
    data class Default(val percent: Double = 0.7, val freeConversions: Int = 5): CommissionStrategyDomainModel()
    data class FreeBelowValue(val percent: Double, val value: Double): CommissionStrategyDomainModel()
    data class Custom(val percent: Double): CommissionStrategyDomainModel()
    data object NoCommission : CommissionStrategyDomainModel()
}
