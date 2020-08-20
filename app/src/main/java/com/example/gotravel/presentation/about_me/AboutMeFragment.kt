package com.example.gotravel.presentation.about_me

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gotravel.R
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase

class AboutMeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        //viewModelFactory = AboutMeViewModelFactory(repository)
      //  viewModel = ViewModelProvider(this, viewModelFactory).get(AboutMeViewModel::class.java)


    }


}