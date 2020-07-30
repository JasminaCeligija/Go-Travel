package com.example.gotravel.presentation.trip_details.trip_plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.TripDay
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.trip_day.*

sealed class TripDayViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class Item(parent: ViewGroup) :
        TripDayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.trip_day, parent, false)
        ) {

        lateinit var tripDay: TripDay

        fun bind(tripDay: TripDay) {
            this.tripDay = tripDay
            tripDay.apply {
                text_trip_day.text = tripDay.title
                text_day_description.text = tripDay.description
            }
        }
    }
}