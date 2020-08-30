package com.example.gotravel.presentation.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gotravel.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {

    private lateinit var viewModel: PaymentViewModel

    private val args by navArgs<PaymentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        initUI()
        setUpMonthsSpinner()
        setUpYearsSpinner()
        setListeners()
        setObservers()
    }

    private fun initUI() {
        text_num_od_days.text = args.trip.numOfDays
        text_trip_name.text = args.trip.name
        text_num_of_people.text = args.numberOfPeople
        text_total_amount.text = args.totalAmount
    }

    private fun setUpMonthsSpinner() {
        val monthsOptions = resources.getStringArray(R.array.MonthsOptions)
        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.gender_spinner_item,
            monthsOptions
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set selected item style
                if (position == spinner_months.selectedItemPosition) {
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.baby_blue))
                }

                return view
            }
        }

        spinner_months.adapter = adapter

        spinner_months?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
    }

    private fun setUpYearsSpinner() {
        val yearsOptions = resources.getStringArray(R.array.YearsOptions)
        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.gender_spinner_item,
            yearsOptions
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set selected item style
                if (position == spinner_years.selectedItemPosition) {
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.baby_blue))
                }

                return view
            }
        }

        spinner_years.adapter = adapter

        spinner_years?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
    }

    private fun setObservers() {

        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })

        viewModel.paymentSuccessfullEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToMessageFragment())
        })
    }

    private fun render(state: PaymentViewState) {

        when (state.paymentProcessState) {
            is PaymentProcessState.Loading -> {
                progressBar_book_a_trip.visibility = View.VISIBLE
                button_finish.text = "Please wait..."
            }
            is PaymentProcessState.Error -> {
                Toast.makeText(requireContext(), "Error has occurred.", Toast.LENGTH_LONG).show()
            }
            is PaymentProcessState.Success -> {
                progressBar_book_a_trip.visibility = View.INVISIBLE
            }
        }
    }

    private fun setListeners() {

        toolbar.image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        button_finish.setOnClickListener {
            viewModel.startPaymentProcess()
        }
    }
}