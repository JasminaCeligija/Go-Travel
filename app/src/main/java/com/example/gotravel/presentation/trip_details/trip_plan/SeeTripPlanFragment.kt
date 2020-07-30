package com.example.gotravel.presentation.trip_details.trip_plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.TripDay
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_see_trip_plan.*

class SeeTripPlanFragment : Fragment() {

    private lateinit var viewModel: TripPlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_see_trip_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TripPlanViewModel::class.java)

        setTripDaysAdapter()
        setObservers()
        setListeners()
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

    private fun setTripDaysAdapter() {

        recycler_view_trip_days.apply {
             layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
             itemAnimator = DefaultItemAnimator()
             setHasFixedSize(true)
             adapter = TripPlanAdapter()
         }
    }

    private fun render(state: SeeTripPlanViewState) {

        when (state.tripPlanViewState) {
            is TripPlanState.Loading -> {
            }
            is TripPlanState.Error -> {
            }
            is TripPlanState.Content -> {
                (recycler_view_trip_days.adapter as TripPlanAdapter).data = state.tripPlanViewState.tripDays
            }
        }
    }
}