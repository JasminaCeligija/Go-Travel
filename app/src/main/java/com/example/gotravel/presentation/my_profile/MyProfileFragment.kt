package com.example.gotravel.presentation.my_profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.common.model.User
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.presentation.auth.login.LoginViewModel
import com.example.gotravel.presentation.auth.login.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_my_profile.*


class MyProfileFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var aboutMeViewModel: AboutMeViewModel
    private lateinit var aboutMeViewModelFactory: AboutMeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)

        aboutMeViewModelFactory = AboutMeViewModelFactory(repository)
        aboutMeViewModel = ViewModelProvider(requireActivity(), aboutMeViewModelFactory).get(AboutMeViewModel::class.java)

        setListeners()
        setObservers()
    }

    private fun setListeners() {

        toolbar.image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        layout_settings.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileToAboutMeFragment())
        }

        layout_privacy.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileToChangePasswordFragment())
        }

        layout_log_out.setOnClickListener {
            showLogOutDialog()
        }
    }

    private fun setObservers() {
        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer { showProgressBar ->
            if (showProgressBar) showProgressBar()
            else hideProgressBar()
        })

        viewModel.successfulLogoutEvent.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        aboutMeViewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun render(state: AboutMeViewState) {

        when (state.userData) {
            is UserDataState.Loading -> {
                // Implement loading state
            }
            is UserDataState.Error -> {

            }
            is UserDataState.Content -> {
                fillUserData(state.userData.data)
            }
        }
    }

    private fun fillUserData(user: User) {
        text_first_name.text = user.firstName
        text_last_name.text = user.lastName
    }


    private fun showProgressBar() {
        image_log_out.visibility = View.INVISIBLE
        progressBar_log_out.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        image_log_out.visibility = View.VISIBLE
        progressBar_log_out.visibility = View.INVISIBLE
    }

    private fun showLogOutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.log_out_title))
            .setMessage(resources.getString(R.string.log_out_message))

            .setNegativeButton(resources.getString(R.string.log_out_no)) { _, _ ->

            }
            .setPositiveButton(resources.getString(R.string.log_out_yes)) { _, _ ->
                viewModel.logoutUser()
            }
            .show()
    }

}