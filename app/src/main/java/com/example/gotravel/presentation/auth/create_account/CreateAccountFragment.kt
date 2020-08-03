package com.example.gotravel.presentation.auth.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gotravel.R
import com.example.gotravel.data.GoTravelDatabase
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


        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        viewModelFactory = CreateAccountViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateAccountViewModel::class.java)

        setListeners()
    }

    private fun setListeners() {

        button_register.setOnClickListener {
            viewModel.createUser("aaaa", "bbb", "orion@gmail.com", "000000", 0L)
        }


    }

}