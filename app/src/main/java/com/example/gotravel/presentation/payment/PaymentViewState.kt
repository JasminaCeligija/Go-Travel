package com.example.gotravel.presentation.payment

data class PaymentViewState(
    val paymentProcessState: PaymentProcessState = PaymentProcessState.Inital
)

sealed class PaymentProcessState {
    object Inital : PaymentProcessState()
    object Loading : PaymentProcessState()
    object Error : PaymentProcessState()
    object Success : PaymentProcessState()
}