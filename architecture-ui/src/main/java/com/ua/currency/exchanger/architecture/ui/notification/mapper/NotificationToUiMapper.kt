package com.ua.currency.exchanger.architecture.ui.notification.mapper

import com.ua.currency.exchanger.architecture.presentation.notification.PresentationNotification
import com.ua.currency.exchanger.architecture.ui.notification.model.UiNotification

interface NotificationToUiMapper {
    fun toUi(notification: PresentationNotification): UiNotification
}
