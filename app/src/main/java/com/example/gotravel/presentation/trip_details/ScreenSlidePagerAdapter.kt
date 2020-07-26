package com.example.gotravel.presentation.trip_details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gotravel.presentation.trip_details.TripPhotosSlideFragment

class ScreenSlidePagerAdapter(activity: FragmentActivity, private val imageResources: List<Int>): FragmentStateAdapter(activity) {

   override fun getItemCount(): Int {
        return imageResources.size
    }

    override fun createFragment(position: Int): Fragment {
        return TripPhotosSlideFragment.newInstance(imageResources[position])
    }
}
