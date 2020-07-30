package com.example.gotravel.presentation.trip_details.trip_plan

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.common.model.TripDay

class TripPlanAdapter :
    RecyclerView.Adapter<TripDayViewHolder>() {

    var data = listOf<TripDay>()
        set(value) {
            val oldTripPlanList = data
            field = value
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                TripDaysDiffCallback(
                    oldTripPlanList,
                    data
                )
            )
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripDayViewHolder {
        return TripDayViewHolder.Item(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TripDayViewHolder, position: Int) {
        val tripDay = data[position]
        (holder as TripDayViewHolder.Item).bind(tripDay)
    }

    class TripDaysDiffCallback(
        private var oldTripDaysList: List<TripDay>,
        private var newTripDaysList: List<TripDay>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTripDay = oldTripDaysList[oldItemPosition]
            val newTripDay = newTripDaysList[newItemPosition]
            return oldTripDay.id == newTripDay.id
        }

        override fun getOldListSize(): Int {
            return oldTripDaysList.size
        }

        override fun getNewListSize(): Int {
            return newTripDaysList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTripDay = oldTripDaysList[oldItemPosition]
            val newTripDay = newTripDaysList[newItemPosition]
            return oldTripDay == newTripDay
        }
    }
}