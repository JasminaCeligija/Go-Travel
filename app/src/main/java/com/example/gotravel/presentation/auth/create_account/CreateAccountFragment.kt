package com.example.gotravel.presentation.auth.create_account

import android.content.Context
import android.os.Bundle
import android.view.Gravity
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
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.utils.closeKeyboard
import com.example.gotravel.utils.isValidEmail
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_ask_a_question.*
import kotlinx.android.synthetic.main.fragment_create_account.*


class CreateAccountFragment : Fragment() {

    private lateinit var viewModel: CreateAccountViewModel
    private lateinit var viewModelFactory: CreateAccountViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        viewModelFactory = CreateAccountViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateAccountViewModel::class.java)

        setUpGenderSpinner()
        setListeners()
        setObservers()
    }

    private fun setObservers() {

        viewModel.successMessageEvent.observe(viewLifecycleOwner, Observer {
            showSnackbar(it)
        })

        viewModel.errorMessageEvent.observe(viewLifecycleOwner, Observer {
            showSnackbar(it)
        })
    }

    private fun setUpGenderSpinner() {

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
        spinner_gender?.setSelection(0, false)

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

    private fun showDatePicker() {
        val builder : MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        val picker : MaterialDatePicker<*> = builder.setTheme(R.style.MaterialCalendar).build()
        picker.show(requireActivity().supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            picker.dismiss()
            button_select_birth_date.text = picker.headerText.toString()
        }
    }

    private fun setListeners() {

        button_select_birth_date.setOnClickListener {
            showDatePicker()
        }

        text_log_in.setOnClickListener {
            findNavController().popBackStack()
        }

        text_upload_photo.setOnClickListener {
            showSnackbar("Needs to be implemented :)")
        }

        button_register.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {

        val firstName = edit_text_first_name.text.toString()
        val lastName = edit_text_last_name.text.toString()
        val email = edit_text_email.text.toString()
        val password = edit_text_password.text.toString()
        val confirmPassword = edit_text_confirm_password.text.toString()
        val gender =  spinner_gender.selectedItem.toString()
        val birthDate = button_select_birth_date.text.toString()

        if(email.isValidEmail() && password == confirmPassword){
            clearFields()
            viewModel.createUser(firstName, lastName, email, password, gender, birthDate)
        }
    }

    private fun clearFields() {
        closeKeyboard(requireActivity())
        edit_text_first_name.setText("")
        edit_text_last_name.setText("")
        edit_text_email.setText("")
        edit_text_password.setText("")
        edit_text_confirm_password.setText("")
    }

    private fun showSnackbar(message: String) {
        val snackBar = Snackbar.make(create_account_parent_layout, message, Snackbar.LENGTH_LONG)
        val mainTextView = snackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackBar.show()
    }
}