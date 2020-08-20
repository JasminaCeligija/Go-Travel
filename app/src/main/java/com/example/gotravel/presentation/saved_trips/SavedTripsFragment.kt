package com.example.gotravel.presentation.saved_trips

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
import com.example.gotravel.common.model.Trip
import com.example.gotravel.data.DefaultContentRepository
import com.example.gotravel.presentation.home.TripAdapter
import com.example.gotravel.presentation.trip_details.TripDetailsFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_saved_trips.*

class SavedTripsFragment : Fragment() {

    private lateinit var viewModel: SavedTripsViewModel
    private lateinit var viewModelFactory: SavedTripsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = DefaultContentRepository()
        viewModelFactory = SavedTripsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedTripsViewModel::class.java)

        setUpSavedTripsAdapter()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setListeners() {
        toolbar.image_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpSavedTripsAdapter() {
        recycler_view_saved_trips.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter =
                TripAdapter(
                    tripClickCallback = ::navigateToTripDetails
                )
        }
    }

    private fun render(state: SavedTripsViewState) {

        when(state.savedTripsListState) {
            is SavedTripsListState.Loading -> {

            }
            is SavedTripsListState.Error -> {
                //Implement error state
            }
            is SavedTripsListState.Content -> {
                (recycler_view_saved_trips.adapter as TripAdapter).data = state.savedTripsListState.trips
            }
        }
    }

    private fun navigateToTripDetails(trip: Trip) {
        findNavController().navigate(
            R.id.action_saved_trips_to_tripDetailsFragment,
            TripDetailsFragment.createBundle(
                trip
            )
        )
    }
}