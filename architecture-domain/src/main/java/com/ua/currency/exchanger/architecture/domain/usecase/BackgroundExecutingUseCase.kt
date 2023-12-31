package com.ua.currency.exchanger.architecture.domain.usecase

import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import kotlinx.coroutines.withContext


abstract class BackgroundExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : UseCase<REQUEST, RESULT> {

    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
        val result = withContext(coroutineContextProvider.default) {
            executeInBackground(input)
        }
        onResult(result)
    }

    abstract fun executeInBackground(request: REQUEST): RESULT
}
