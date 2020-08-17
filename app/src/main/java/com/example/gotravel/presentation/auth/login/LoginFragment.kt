package com.example.gotravel.presentation.auth.login

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.utils.closeKeyboard
import com.example.gotravel.utils.isValidEmail
import com.google.android.material.snackbar.Snackbar
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

        val sharedPreferences =
            requireContext().getSharedPreferences("com.example.gotravel.pref", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer { showProgress ->
            if (showProgress) showProgressBar()
            else hideProgressBar()
        })

        viewModel.errorMessageEvent.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            showSnackbar(it)
        })

        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome())
        })
    }

    private fun showProgressBar() {
        progress_login.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_login.visibility = View.INVISIBLE
    }

    private fun setListeners() {
        button_login.setOnClickListener {
            loginUser()
        }

        text_create_account.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }

        text_skip_and_explore.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome())
        }
    }

    private fun loginUser() {
        val email = edit_text_email.text.toString()
        val password = edit_text_password.text.toString()
        if (email.isValidEmail()) {
            clearFields()
            viewModel.loginUser(email, password)
        }
        else showSnackbar("Invalid email address.")
    }

    private fun clearFields() {
        closeKeyboard(requireActivity())
        edit_text_email.setText("")
        edit_text_password.setText("")
    }

    private fun showSnackbar(message: String) {
        val snackBar = Snackbar.make(login_parent_layout, message, Snackbar.LENGTH_LONG)
        val mainTextView = snackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackBar.show()
    }
}
