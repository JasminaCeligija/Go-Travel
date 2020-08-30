package com.example.gotravel.presentation.my_profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.common.model.User
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.utils.closeKeyboard
import com.example.gotravel.utils.isValidEmail
import com.example.gotravel.utils.showSnackbar
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_about_me.*

class AboutMeFragment : Fragment() {

    private lateinit var viewModel: AboutMeViewModel
    private lateinit var viewModelFactory: AboutMeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        viewModelFactory = AboutMeViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(AboutMeViewModel::class.java)

        setObservers()
        setListeners()
    }

    private fun setListeners() {

        toolbar.image_back.setOnClickListener {
            closeKeyboard(requireActivity()                                                     )
            findNavController().popBackStack()
        }

        text_upload_photo.setOnClickListener {
            showSnackbar("Needs to be implemented :)", about_me_parent_layout)
        }

        button_save_changes.setOnClickListener {
            saveChanges()
        }
    }

    private fun setObservers() {

        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })

        viewModel.successMessageEvent.observe(viewLifecycleOwner, Observer {
            showSnackbar(it, about_me_parent_layout)
        })

        viewModel.errorMessageEvent.observe(viewLifecycleOwner, Observer {
            showSnackbar(it, about_me_parent_layout)
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
        edit_text_first_name.setText(user.firstName)
        edit_text_last_name.setText(user.lastName)
        edit_text_email.setText(user.email)
        button_select_birth_date.text = user.birthDate
        setUpGenderSpinner(user.gender)
    }

    private fun setUpGenderSpinner(gender: String) {

        val genderOptions = resources.getStringArray(R.array.GenderOptions)
        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.gender_spinner_item,
            genderOptions
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
                if (position == spinner_gender.selectedItemPosition) {
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.baby_blue))
                }

                return view
            }
        }

        spinner_gender.adapter = adapter
        spinner_gender?.setSelection(adapter.getPosition(gender))

        spinner_gender?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
    }
    private fun saveChanges() {
        val firstName = edit_text_first_name.text.toString()
        val lastName = edit_text_last_name.text.toString()
        val email = edit_text_email.text.toString()
        val gender = spinner_gender.selectedItem.toString()
        val birthDate = button_select_birth_date.text.toString()

        if(email.isValidEmail())
        viewModel.saveChanges(firstName, lastName, email, gender, birthDate)
        else showSnackbar("Invalid email address.", about_me_parent_layout)
    }
}