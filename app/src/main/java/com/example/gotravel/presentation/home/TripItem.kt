package com.example.gotravel.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.TravelMode
import com.example.gotravel.common.model.Trip
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_trip.*

sealed class TripViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class Item(parent: ViewGroup, tripClickCallback: (trip: Trip) -> Unit) :
        TripViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_trip, parent, false)
        ) {

        lateinit var trip: Trip

        init {
            itemView.setOnClickListener {
                tripClickCallback(trip)
            }
        }

        fun bind(trip: Trip) {
            this.trip = trip
            trip.apply {
                text_name.text = trip.name
                text_date.text = trip.startDate.plus(" | ").plus(trip.endDate)
                text_description.text = trip.shortDescription
                text_num_of_days.text = trip.numOfDays.plus(" days")
                text_price.text = trip.price.plus(" $")
                image_trip.setImageResource(trip.photoSrc)
                if(trip.travelMode == TravelMode.Airplane)
                    image_transport.setImageResource(R.drawable.ic_airplane)
                else image_transport.setImageResource(R.drawable.ic_bus)
            }
        }
    }
}