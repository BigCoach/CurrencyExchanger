package com.ua.currency.exchanger.exchange.domain.model

import com.ua.currency.exchanger.architecture.domain.exception.DomainException

sealed interface CurrencyBalanceListDomainModel{
    data class BalanceList(val balanceList: List<BalanceDomainModel>): CurrencyBalanceListDomainModel
    data class Error(val exception: DomainException): CurrencyBalanceListDomainModel
}




