package com.example.gotravel.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.data.GoTravelDatabase
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        viewModelFactory =
            LoginViewModelFactory(
                dataSource,
                application
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setListeners()
    }

    private fun setListeners() {

        button_login.setOnClickListener {
            viewModel.loginUser("orion@gmail.com", "000000")
        }

        text_create_account.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }

    }

}