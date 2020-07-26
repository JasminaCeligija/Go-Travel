package com.example.gotravel.presentation.home

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
import com.example.gotravel.common.model.Trip
import com.example.gotravel.presentation.trip_details.TripDetailsFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setUpTripsAdapter()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setListeners() {
        toolbar.onBackButtonClicked {
            //findNavController().popBackStack()
            activity?.finish()
        }
    }


    private fun render(state: HomeViewState) {

        when (state.tripsViewState) {
            is TripsViewState.Loading -> {
               // Toast.makeText(context, "Implement loading state", Toast.LENGTH_SHORT).show()
            }
            is TripsViewState.Error -> {
                Toast.makeText(context, "Implement error state", Toast.LENGTH_SHORT).show()
            }
            is TripsViewState.Content -> {
                (recycler_view_trips.adapter as TripAdapter).data = state.tripsViewState.trips
            }
        }

    }


    private fun setUpTripsAdapter() {
        recycler_view_trips.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter =
                TripAdapter(
                    tripClickCallback = ::navigateToTripDetails
                )
        }
    }


    private fun navigateToTripDetails(trip: Trip) {
        findNavController().navigate(
            R.id.action_home_to_tripDetailsFragment,
            TripDetailsFragment.createBundle(
                trip
            )
        )
    }
}