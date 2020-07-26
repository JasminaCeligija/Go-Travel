package com.example.gotravel.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.mutate(mutator: T.() -> T) {
    value?.let { value = mutator(it) }
}

fun <T> MutableLiveData<T>.mutatePost(mutator: T.() -> T) {
    value?.let { postValue(mutator(it)) }
}
