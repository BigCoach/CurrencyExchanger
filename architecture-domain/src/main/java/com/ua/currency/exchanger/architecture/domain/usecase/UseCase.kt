package com.ua.currency.exchanger.architecture.domain.usecase

interface UseCase<REQUEST, RESULT> {
    suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit)
}
