package com.example.gotravel.presentation.trip_details.readreviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.Comment
import kotlinx.android.synthetic.main.fragment_read_reviews.*
import kotlinx.android.synthetic.main.fragment_read_reviews.image_back

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
                commentPostedCallback = ::getNewComment
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


    private fun getNewComment(comment: Comment) {
        Toast.makeText(context, comment.text, Toast.LENGTH_SHORT).show()
    }

}