package com.example.gotravel.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.Trip
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.presentation.auth.login.LoginNavigationState
import com.example.gotravel.presentation.auth.login.LoginViewModel
import com.example.gotravel.presentation.auth.login.LoginViewModelFactory
import com.example.gotravel.presentation.trip_details.TripDetailsFragment
import com.example.gotravel.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory


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
        setUpFilterSpinner()
        initUI()
        setObservers()
        setListeners()
    }

    private fun initUI() {

        viewModel.refreshData()

        val sharedPreferences = requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        loginViewModelFactory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(requireActivity(), loginViewModelFactory).get(LoginViewModel::class.java)

        loginViewModel.authenticatedUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                text_log_in.visibility = View.GONE
                if (it.role == "admin")
                    floating_button_add_new_trip.visibility = View.VISIBLE
                else floating_button_add_new_trip.visibility = View.GONE
            }
            else {
                text_log_in.visibility = View.VISIBLE
                floating_button_add_new_trip.visibility = View.GONE
            }
        })
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

    private fun setUpFilterSpinner() {

        val searchFilterOptions = resources.getStringArray(R.array.SearchFilterOptions)
        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.filter_spinner_item,
            searchFilterOptions
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set selected item style
                if (position == spinner_filter_options.selectedItemPosition) {
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.baby_blue))
                }

                return view
            }
        }

        spinner_filter_options.adapter = adapter
        spinner_filter_options?.setSelection(0, false)

        spinner_filter_options?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sortBy(parent?.getItemAtPosition(position).toString())
            }
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setListeners() {

        image_search.setOnClickListener {
            //TODO: remove sending data from fragment
            val search = edit_text_search.text.toString()
            clearFields()
            viewModel.searchTripByName(search, (recycler_view_trips.adapter as TripAdapter).data)
        }

        image_refresh.setOnClickListener {
            image_refresh.clearAnimation()
            val anim = RotateAnimation(30F, 360F, (image_refresh.width / 2).toFloat(), (image_refresh.height / 2).toFloat())
            anim.fillAfter = true
            anim.repeatCount = 0
            anim.duration = 500
            image_refresh.startAnimation(anim)
            viewModel.refreshData()
        }

        text_log_in.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToLogin(LoginNavigationState.NavigatedFromHome))
        }

        floating_button_add_new_trip.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToAddTripFragment())
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

    private fun clearFields() {
        edit_text_search.hideKeyboard()
        edit_text_search.setText("")
        edit_text_search.clearFocus()
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