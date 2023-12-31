package com.ua.currency.exchanger.architecture.ui.binder

import com.ua.currency.exchanger.architecture.ui.view.ViewsProvider

interface ViewStateBinder<in VIEW_STATE : Any, in VIEWS_PROVIDER : ViewsProvider> {
    fun VIEWS_PROVIDER.bindState(viewState: VIEW_STATE)
}
