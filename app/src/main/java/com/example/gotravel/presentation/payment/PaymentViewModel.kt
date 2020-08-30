package com.example.gotravel.presentation.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.utils.SingleLiveEvent
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaymentViewModel: ViewModel() {

    val state: MutableLiveData<PaymentViewState> = MutableLiveData(PaymentViewState())
    val paymentSuccessfullEvent = SingleLiveEvent<Unit>()


    fun startPaymentProcess() {
        viewModelScope.launch {
            state.mutatePost { copy(paymentProcessState = PaymentProcessState.Loading) }
            delay(3000L)
            state.mutatePost { copy(paymentProcessState = PaymentProcessState.Success) }
            paymentSuccessfullEvent.call()
        }
    }
}