package com.ua.currency.exchanger.exchange.data.mapper

import com.ua.currency.exchanger.exchange.data.model.CommissionDataModel
import com.ua.currency.exchanger.exchange.domain.model.CommissionDomainModel

class CommissionToDataMapper {

        fun toData(commission: CommissionDomainModel): CommissionDataModel =
            CommissionDataModel(
                amount = commission.amount,
                currency = commission.currency,
            )
}
