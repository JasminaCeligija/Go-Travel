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
                setTripData(
                    name, text_question,
                    startDate, endDate, text_date,
                    description, text_description,
                    numOfDays, text_num_of_days,
                    price, text_price,
                    photoSrc, image_trip,
                    travelMode, image_transport
                )
            }
        }
    }

    companion object {
        fun setTripData(
            title: String, titleTextView: TextView,
            startDate: String, endDate: String, dateTextView: TextView,
            description: String, descriptionTextView: TextView,
            numOfDays: String, numOfDaysTextView: TextView,
            price: String, priceTextView: TextView,
            photoSrc: Int, tripImage: ImageView,
            travelMode: TravelMode, transportImageView: ImageView) {

            titleTextView.text = title
            dateTextView.text = startDate.plus(" | ").plus(endDate)
            descriptionTextView.text = description
            numOfDaysTextView.text = numOfDays.plus(" days")
            priceTextView.text = price.plus(" $")
            tripImage.setImageResource(photoSrc)
            if (travelMode == TravelMode.Airplane)
                transportImageView.setImageResource(R.drawable.ic_airplane)
            else transportImageView.setImageResource(R.drawable.ic_bus)
        }
    }
}