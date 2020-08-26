package com.example.gotravel.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.common.model.Trip
import com.example.gotravel.utils.showSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_book_now.*
import kotlinx.coroutines.delay


class BookNowFragment : Fragment() {

    private lateinit var trip: Trip
    private lateinit var viewModel: BookNowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_now, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookNowViewModel::class.java)

        (arguments?.getSerializable(TRIP_ARGUMENT) as? Trip)?.let { trip ->
            setUpTripData(trip)
        } ?: run {
            showErrorDialog()
        }

        setListeners()
        setObservers()
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
        this.trip = trip
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

        edit_text_num_of_people.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                calculateTotalPriceForXNumOfPeople()
            }
        })

        button_apply_coupon_code.setOnClickListener {
            calculateTotalPriceWithCouponCode()
        }

        button_next.setOnClickListener {
            findNavController().navigate(BookNowFragmentDirections.actionBookNowFragmentToPaymentFragment())
        }
    }

    private fun setObservers() {

        viewModel.showCouponPriceProgressBar.observe(viewLifecycleOwner, Observer {
            if(it) progressBar_coupon_code.visibility = View.VISIBLE
            else progressBar_coupon_code.visibility = View.INVISIBLE
        })

        viewModel.successfullyAppliedCouponCode.observe(viewLifecycleOwner, Observer {
            showSnackbar("30% discount successfully applied", book_now_parent_layout)
        })
    }

    private fun calculateTotalPriceForXNumOfPeople() {
        if (edit_text_num_of_people.text.toString().trim().isNotBlank()) {
            val currentPrice = trip.price.toDouble()
            val numOfPeople = edit_text_num_of_people.text.toString().toDoubleOrNull()
            if (numOfPeople != null) {
                val totalPrice = currentPrice * numOfPeople
                text_price.text = totalPrice.toInt().toString()
            }
        }
    }

    private fun calculateTotalPriceWithCouponCode() {
        if (edit_text_promo_code.text.toString().trim().isNotBlank() && edit_text_promo_code.text.toString().length == 6) {
            val totalPriceParsed = text_price.text.toString().toDoubleOrNull()
            if (totalPriceParsed != null) {
                viewModel.calculateTotalPriceWithCouponCode()
                val totalPrice = totalPriceParsed * 0.7
                text_price.text = totalPrice.toInt().toString()
            }
        } else {
            showSnackbar("Coupon code should contain exactly 6 numbers", book_now_parent_layout)
        }
    }

    companion object {
        fun createBundle(trip: Trip) = bundleOf(TRIP_ARGUMENT to trip)
        private const val TRIP_ARGUMENT = "trip"
    }

}