package com.ua.currency.exchanger.architecture.ui.navigation.exception

import com.ua.currency.exchanger.architecture.presentation.navigation.PresentationDestination

class UnhandledDestinationException(
    destination: PresentationDestination
) : IllegalArgumentException(
    "Navigation to ${destination::class.simpleName} was not handled."
)
