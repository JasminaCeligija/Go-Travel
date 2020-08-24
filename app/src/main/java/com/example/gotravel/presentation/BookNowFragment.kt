package com.example.gotravel.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.common.model.Trip
import com.example.gotravel.presentation.trip_details.TripDetailsFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_book_now.*


class BookNowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_now, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getSerializable(TRIP_ARGUMENT) as? Trip)?.let { trip ->
            setUpTripData(trip)
        } ?: run {
            showErrorDialog()
        }

        setListeners()

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

    private fun setUpTripData(trip: Trip) {
        toolbar.setToolbarTitle(trip.name)
        text_start_date.text = trip.startDate
        text_end_date.text = trip.endDate
        image_trip_photo.setImageResource(trip.photoSrc)
        text_price.text = trip.price
    }

    private fun setListeners() {
        toolbar.image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        button_next.setOnClickListener {
            findNavController().navigate(BookNowFragmentDirections.actionBookNowFragmentToPaymentFragment())
        }
    }

    companion object {
        fun createBundle(trip: Trip) = bundleOf(TRIP_ARGUMENT to trip)
        private const val TRIP_ARGUMENT = "trip"
    }

}