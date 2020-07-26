package com.example.gotravel.presentation.trip_details.readreviews

import com.example.gotravel.common.model.Review

data class ReadReviewsViewState(
    val reviewsViewState: ReviewsViewState = ReviewsViewState.Loading
)

sealed class ReviewsViewState {
    object Loading: ReviewsViewState()
    object Error: ReviewsViewState()
    data class Content(val reviews: List<Review>): ReviewsViewState()
}