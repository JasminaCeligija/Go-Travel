package com.example.gotravel.presentation.trip_details.readreviews

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.common.model.Comment
import com.example.gotravel.common.model.Review


class ReviewAdapter(private val commentPostedCallback: (comment: Comment) -> Unit) :
    RecyclerView.Adapter<ReviewViewHolder>() {

    var data = listOf<Review>()
        set(value) {
            val oldReviewsList = data
            field = value
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                ReviewsDiffCallback(
                    oldReviewsList,
                    data
                )
            )
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.Item(parent, commentPostedCallback)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = data[position]
        (holder as ReviewViewHolder.Item).bind(review)
    }


    class ReviewsDiffCallback(
        private var oldReviewsList: List<Review>,
        private var newReviewsList: List<Review>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldReview = oldReviewsList[oldItemPosition]
            val newReview = newReviewsList[newItemPosition]
            return oldReview.id == newReview.id
        }

        override fun getOldListSize(): Int {
            return oldReviewsList.size
        }

        override fun getNewListSize(): Int {
            return newReviewsList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldReview = oldReviewsList[oldItemPosition]
            val newReview = newReviewsList[newItemPosition]
            return oldReview == newReview
        }
    }
}