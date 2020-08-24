package com.example.gotravel.presentation.trip_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.gotravel.R
import com.example.gotravel.common.model.Trip
import com.example.gotravel.presentation.BookNowFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_trip_details.*
import kotlin.math.abs


class TripDetailsFragment : Fragment() {

    private lateinit var viewModel: TripDetailsViewModel
    private lateinit var selectedTrip: Trip

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trip_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getSerializable(TRIP_ARGUMENT) as? Trip)?.let {trip ->
            setUpTripData(trip)
        } ?: run {
            showErrorDialog()
        }

        viewModel = ViewModelProvider(this).get(TripDetailsViewModel::class.java)

        setViewPager()
        setListeners()
    }

    private fun setUpTripData(trip: Trip) {
        selectedTrip = trip
        text_name.text = trip.name
        text_start_date.text = trip.startDate
        text_end_date.text = trip.endDate
        text_num_of_days.text = trip.numOfDays
        text_price.text = String.format(resources.getString(R.string.trip_details_price), trip.price)
        text_description.text = trip.description
    }

    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error!")
            .setMessage("There's an issue loading this trip. Please try again later.")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

    private fun setListeners() {

        button_see_trip_plan.setOnClickListener {
            findNavController().navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToSeeTripPlanFragment())
        }

        button_read_reviews.setOnClickListener {
            findNavController().navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToReadReviewsFragment())
        }

        button_ask_a_question.setOnClickListener {
            findNavController().navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToAskAQuestionFragment())
        }

        button_book_now.setOnClickListener {
            findNavController().navigate(
                R.id.action_tripDetailsFragment_to_bookNowFragment,
                BookNowFragment.createBundle(
                    selectedTrip
                )
            )
        }

        image_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setViewPager() {

        val myPager = ScreenSlidePagerAdapter(requireActivity(), selectedTrip.imageResources)
        view_pager_trip_images.adapter = myPager
        view_pager_trip_images.clipToPadding = false
        view_pager_trip_images.clipChildren = false
        view_pager_trip_images.offscreenPageLimit = 3
        view_pager_trip_images.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        view_pager_trip_images.doOnLayout {
            view_pager_trip_images.currentItem = 1
        }

        val animTransformer = ViewPager2.PageTransformer { page, position ->
            page.apply {
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        val marginTransformer = MarginPageTransformer(15)
        val transformer = CompositePageTransformer().apply {
            addTransformer(animTransformer)
            addTransformer(marginTransformer)
        }

        view_pager_trip_images.setPageTransformer(transformer)
    }

    companion object {
        fun createBundle(trip: Trip) = bundleOf(TRIP_ARGUMENT to trip)
        private const val TRIP_ARGUMENT = "trip"
    }
}
