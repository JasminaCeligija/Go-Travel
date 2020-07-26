package com.example.gotravel.presentation.trip_details

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.TripDay
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.trip_day.*

sealed class TripDayViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class Item(parent: ViewGroup, tripDayClickCallback: (tripDay: TripDay) -> Unit) :
        TripDayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.trip_day, parent, false)
        ) {

        lateinit var tripDay: TripDay

        init {
            itemView.setOnClickListener {
                tripDayClickCallback(tripDay)
            }
        }

        fun bind(tripDay: TripDay) {
            this.tripDay = tripDay
            tripDay.apply {
                setTripData(
                    title, text_trip_day,
                    description, text_day_description)
            }
        }
    }

    companion object {
        fun setTripData(
            title: String, titleTextView: TextView,
            description: String, descriptionTextView: TextView
        ) {
            Log.d("myTag", "tripplaaanite")
            titleTextView.text = title
            descriptionTextView.text = description
        }
    }

}