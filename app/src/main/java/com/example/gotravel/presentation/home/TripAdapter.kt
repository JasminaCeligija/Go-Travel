package com.example.gotravel.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.common.model.Trip


class TripAdapter(private val tripClickCallback: (trip: Trip) -> Unit) :
    RecyclerView.Adapter<TripViewHolder>() {

    var data = listOf<Trip>()
        set(value) {
            val oldTripsList = data
            field = value
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                TripsDiffCallback(
                    oldTripsList,
                    data
                )
            )
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder.Item(
            parent,
            tripClickCallback
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = data[position]
        (holder as TripViewHolder.Item).bind(trip)

    }

    class TripsDiffCallback(
        private var oldTripsList: List<Trip>,
        private var newTripsList: List<Trip>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTrip = oldTripsList[oldItemPosition]
            val newTrip = newTripsList[newItemPosition]
            return oldTrip.id == newTrip.id
        }

        override fun getOldListSize(): Int {
            return oldTripsList.size
        }

        override fun getNewListSize(): Int {
            return newTripsList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTrip = oldTripsList[oldItemPosition]
            val newTrip = newTripsList[newItemPosition]
            return oldTrip == newTrip
        }
    }
}