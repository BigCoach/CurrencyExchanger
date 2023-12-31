package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.exchange.domain.model.BalanceDomainModel
import com.ua.currency.exchanger.exchange.presentation.model.BalancePresentationModel

class BalanceToPresentationMapper {

    fun toPresentation(list: List<BalanceDomainModel>) : List<BalancePresentationModel> {
        return list.map {
            BalancePresentationModel(
                symbol = it.symbol,
                balance = it.balance
            )
        }
    }

}
