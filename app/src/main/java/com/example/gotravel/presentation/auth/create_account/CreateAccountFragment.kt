package com.example.gotravel.presentation.auth.create_account

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
import androidx.lifecycle.ViewModelProvider
import com.example.gotravel.R
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import kotlinx.android.synthetic.main.fragment_create_account.*
import kotlinx.android.synthetic.main.fragment_home.*


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
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CreateAccountViewModel::class.java)

        setUpGenderSpinner()
        setListeners()
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

    private fun setListeners() {

        button_register.setOnClickListener {
            viewModel.createUser("aaaa", "bbb", "orion@gmail.com", "000000", 0L, "user")
        }

    }

}