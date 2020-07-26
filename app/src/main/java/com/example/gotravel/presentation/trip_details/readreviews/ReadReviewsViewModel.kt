package com.example.gotravel.presentation.trip_details.readreviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.R
import com.example.gotravel.common.Result
import com.example.gotravel.common.model.Review
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadReviewsViewModel : ViewModel() {

    val state: MutableLiveData<ReadReviewsViewState> = MutableLiveData(ReadReviewsViewState())

    init {
        viewModelScope.launch {
            getReviews()
        }
    }

    private fun getReviews() {
        state.mutatePost { copy(reviewsViewState = ReviewsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getReviewsUseCase()) {
                is Result.Success -> {
                    state.mutatePost { copy(reviewsViewState = ReviewsViewState.Content(result.data)) }
                }
            }
        }
    }

    private suspend fun getReviewsUseCase() = withContext(Dispatchers.IO) {
        val mockedReviews = listOf(
            Review(1, "Pamir Pekin", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s", "23.12.2020.", 128, 12, R.drawable.img_user_1),
            Review(2, "Anna Wilson", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", "01.12.2020.", 74, 52, R.drawable.img_user_2),
            Review(3, "Emilia Lewis", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "09.09.2020.", 7, 14, R.drawable.img_user_3),
            Review(4, "Blake Scott", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour", "07.06.2020.", 10, 0, R.drawable.img_user_4),
            Review(5, "Jackson Carter", "Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable.", "20.05.2020.", 2, 40, R.drawable.img_user_5),
            Review(6, "Elijah Rodriguez", "Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy.", "01.02.2020.", 96, 2, R.drawable.img_user_6),
            Review(7, "Riley Lopez", "All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words.", "09.01.2012.", 72, 9, R.drawable.img_user_7)
        )
        return@withContext Result.Success(mockedReviews)
    }

}