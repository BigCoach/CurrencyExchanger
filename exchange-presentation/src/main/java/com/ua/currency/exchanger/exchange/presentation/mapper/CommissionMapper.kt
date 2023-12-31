package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.exchange.domain.model.CommissionDomainModel
import com.ua.currency.exchanger.exchange.presentation.model.CommissionPresentationModel

class CommissionMapper {
    fun toDomain(commissionPresentationModel: CommissionPresentationModel): CommissionDomainModel {
        return CommissionDomainModel(
            amount = commissionPresentationModel.amount,
            currency = commissionPresentationModel.currency
        )
    }

    fun toPresentation(commissionDomainModel: CommissionDomainModel): CommissionPresentationModel {
        return CommissionPresentationModel(
            amount = commissionDomainModel.amount,
            currency = commissionDomainModel.currency
        )
    }
}
