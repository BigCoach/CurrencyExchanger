package com.ua.currency.exchanger.architecture.domain.usecase

import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class ContinuousExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : UseCase<REQUEST, RESULT> {
    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
        withContext(coroutineContextProvider.io) {
            executeInBackground(input).collect { result ->
                withContext(coroutineContextProvider.main) {
                    onResult(result)
                }
            }
        }
    }

    abstract fun executeInBackground(request: REQUEST): Flow<RESULT>
}
