package com.example.gotravel.presentation.trip_details.readreviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.Comment
import com.example.gotravel.common.model.Review
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.review_layout.*
import kotlinx.android.synthetic.main.review_layout.view.*

sealed class ReviewViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class Item(parent: ViewGroup, commentPostedCallback: (comment: Comment) -> Unit) :
        ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_layout, parent, false)
        ) {

        lateinit var review: Review

        init {

            itemView.button_write_comment.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    itemView.layout_comment.visibility = View.VISIBLE
                else itemView.layout_comment.visibility = View.GONE
            }

            itemView.text_post_comment.setOnClickListener {
                val commentText = edit_text_comment.text.toString()
                edit_text_comment.setText("")
                text_num_of_comments.text = (text_num_of_comments.text.toString().toInt() + 1).toString()
                val newComment = Comment(review.userName, commentText, "12.12.2020.")
                commentPostedCallback(newComment)
            }

            //TODO: return like status
        }

        fun bind(review: Review) {
            this.review = review
            container_review_main.animation = AnimationUtils.loadAnimation(containerView.context, R.anim.fade_scale_animation)
            text_username.text = review.userName
            text_createdAt.text = review.createdAt
            text_content.text = review.content
            text_num_of_likes.text = review.numOfLikes.toString()
            text_num_of_comments.text = review.numOfComments.toString()
            image_user.setImageResource(review.imgResourceId)
        }
    }
}