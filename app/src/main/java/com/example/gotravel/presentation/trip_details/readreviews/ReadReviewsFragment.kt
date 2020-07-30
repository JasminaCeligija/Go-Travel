package com.example.gotravel.presentation.trip_details.readreviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.Comment
import com.example.gotravel.common.model.Review
import com.example.gotravel.utils.hideKeyboard
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_read_reviews.*
import java.text.SimpleDateFormat
import java.util.*

class ReadReviewsFragment : Fragment() {

    private lateinit var viewModel: ReadReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ReadReviewsViewModel::class.java)

        setUpReviewsAdapter()
        setObservers()
        setListeners()
    }

    private fun setUpReviewsAdapter() {
        recycler_view_reviews.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = ReviewAdapter(
                commentPostedCallback = ::saveNewComment
            )

        }
    }

    private fun setObservers() {

        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setListeners() {
        image_back.setOnClickListener {
            findNavController().popBackStack()
        }
        button_submit_review.setOnClickListener {
            prepareReviewForPosting()
        }
    }

    private fun render(state: ReadReviewsViewState) {

        when (state.reviewsViewState) {
            is ReviewsViewState.Loading -> {

            }
            is ReviewsViewState.Error -> {

            }
            is ReviewsViewState.Content -> {
                (recycler_view_reviews.adapter as ReviewAdapter).data = state.reviewsViewState.reviews
            }

        }

    }

    private fun prepareReviewForPosting() {
        val reviewText = edit_text_review.text.toString()
        val createdAt = convertDate(System.currentTimeMillis()) ?: "30.12.2020."
        if (reviewText.isNotBlank()) {
            val newReview = Review(1, "Orion", reviewText, createdAt, 0, 0, R.drawable.img_user)
            //TODO: remove sending data from fragment
            viewModel.postReview(newReview, (recycler_view_reviews.adapter as ReviewAdapter).data)
        }
        clearFields()
    }

    private fun clearFields() {
        edit_text_review.hideKeyboard()
        edit_text_review.setText("")
        edit_text_review.clearFocus()
    }

    private fun saveNewComment(comment: Comment) {
        //Toast.makeText(context, comment.text, Toast.LENGTH_SHORT).show()
    }

    private fun convertDate(dateInMilliseconds: Long): String? {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
        return  simpleDateFormat.format(dateInMilliseconds)
    }
}