package com.example.gotravel.presentation.trip_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.gotravel.R
import com.example.gotravel.common.model.Trip
import com.example.gotravel.common.model.TripDay
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_trip_details.*
import kotlin.math.abs


class TripDetailsFragment : Fragment() {

    private lateinit var viewModel: TripDetailsViewModel

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
        //findNavController().navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToReadReviewsFragment())

        viewModel = ViewModelProvider(this).get(TripDetailsViewModel::class.java)

        setTripDaysAdapter()
        setViewPager()
        setObservers()
        setListeners()
    }

    private fun setUpTripData(trip: Trip) {
        text_name.text = trip.name
        text_start_date.text = trip.startDate
        text_end_date.text = trip.endDate
        text_num_of_days.text = trip.numOfDays
        text_price.text = String.format(resources.getString(R.string.trip_details_price), trip.price)
        text_description.text = trip.description
    }

    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle("Error!")
            .setMessage("There's an issue loading this trip. Please try again later.")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }


    private fun setTripDaysAdapter() {

        /* recycler_view_trip_days.apply {
             layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
             itemAnimator = DefaultItemAnimator()
             setHasFixedSize(true)
             adapter = TripPlanAdapter(
                 tripDayClickCallback = ::dummy
             )
         } */
    }

    private fun setObservers() {

        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
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
            findNavController().navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToBookNowFragment())
        }

        button_save_trip.setOnClickListener {
            if (button_save_trip.drawable.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_outlined)?.constantState)
                button_save_trip.setImageResource(R.drawable.ic_heart_filled_red)
            else button_save_trip.setImageResource(R.drawable.ic_heart_outlined)
        }

        image_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun render(state: TripDetailsViewState) {

        when (state.tripPlanViewState) {
            is TripPlanViewState.Loading -> {
            }
            is TripPlanViewState.Error -> {
            }
            is TripPlanViewState.Content -> {

                // (recycler_view_trip_days.adapter as TripPlanAdapter).data = state.tripPlanViewState.tripDays
            }
        }
    }


    private fun setViewPager() {

        val myPager = ScreenSlidePagerAdapter(requireActivity(), getImageResources())
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


    //TODO: get images from api
    private fun getImageResources(): List<Int> {
        return listOf(
            R.drawable.img_bali_1,
            R.drawable.img_bali_2,
            R.drawable.img_bali_3,
            R.drawable.img_bali_4,
            R.drawable.img_dummy,
            R.drawable.img_dummy,
            R.drawable.img_dummy
        )
    }

    companion object {
        fun createBundle(trip: Trip) = bundleOf(TRIP_ARGUMENT to trip)
        private const val TRIP_ARGUMENT = "trip"
    }
}
