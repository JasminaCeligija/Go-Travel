package com.example.gotravel.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookNowViewModel : ViewModel() {

    val showCouponPriceProgressBar = MutableLiveData<Boolean>()
    val successfullyAppliedCouponCode = SingleLiveEvent<Unit>()

    //todo: implement this function properly
    fun calculateTotalPriceWithCouponCode() {
        viewModelScope.launch {
            showCouponPriceProgressBar.postValue(true)
            delay(1500L)
            showCouponPriceProgressBar.postValue(false)
            successfullyAppliedCouponCode.call()
        }
    }

}