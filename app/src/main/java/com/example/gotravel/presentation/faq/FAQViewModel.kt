package com.example.gotravel.presentation.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.model.FAQ
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.Dispatchers
import com.example.gotravel.common.Result
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FAQViewModel : ViewModel() {


    val state: MutableLiveData<FAQViewState> = MutableLiveData(FAQViewState())

    init {
        viewModelScope.launch {
            getFAQs()
        }
    }

    private fun getFAQs() {
        state.mutatePost { copy(faqListState = FAQListState.Loading) }
        viewModelScope.launch {
            when (val result = getFAQSUseCase()) {
                is Result.Success -> {
                    state.mutatePost { copy(faqListState = FAQListState.Content(result.data)) }
                }
            }
        }
    }

    private suspend fun getFAQSUseCase() = withContext(Dispatchers.IO) {
        val mockedFaqs = listOf(
            FAQ("1", "Do I need to register to use GoTravel app?", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.", "01/08/2020"),
            FAQ("2", "Is there any cost involved in using this app?", "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.", "27/07/2020"),
            FAQ("3", "How do I register?", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.", "07/07/2020"),
            FAQ("4", "I’m having difficulty with booking – a credit card error or other technical problem. What do I do?", "Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.", "30/06/2020"),
            FAQ("5", "How do I book a hotel?", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "28/06/2020"),
            FAQ("6", "Who is charging me for the Hotel Booking?", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.", "20/05/2020."),
            FAQ("7", "Where Can I find contact information?", "If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.", "27/02/2020"),
            FAQ("8", "I don’t have a credit card – can I still make a hotel booking?", "All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words.", "01/01/2020")
        )
        return@withContext Result.Success(mockedFaqs)
    }
}