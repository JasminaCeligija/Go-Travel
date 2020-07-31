package com.example.gotravel.presentation.faq

import com.example.gotravel.common.model.FAQ

data class FAQViewState(
    val faqListState: FAQListState = FAQListState.Loading
)

sealed class FAQListState {
    object Loading: FAQListState()
    object Error: FAQListState()
    data class Content(val faqs: List<FAQ>): FAQListState()
}
