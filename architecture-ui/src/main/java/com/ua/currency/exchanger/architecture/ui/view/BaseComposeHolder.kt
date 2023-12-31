package com.ua.currency.exchanger.architecture.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ua.currency.exchanger.architecture.presentation.notification.PresentationNotification
import com.ua.currency.exchanger.architecture.presentation.viewmodel.BaseViewModel
import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider

abstract class BaseComposeHolder<VIEW_STATE : Any, NOTIFICATION : PresentationNotification>(
    private val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val notificationMapper: NotificationToUiMapper
) {

    @Composable
    fun ViewModelObserver() {

        val notification = viewModel.notification.collectAsState(
            initial = null,
            coroutineContextProvider.main
        )

        notification.value?.let { notificationValue ->
            Notify(notification = notificationValue)
        }
    }

    @Composable
    fun Notify(notification: PresentationNotification) {
        notificationMapper.toUi(notification).let { uiNotification ->
            LaunchedEffect(notification) {
                uiNotification.present()
            }
        }
    }
}
