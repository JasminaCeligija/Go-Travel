package com.example.gotravel.presentation.about_me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.utils.showSnackbar
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {

        toolbar.image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        button_save_changes.setOnClickListener {
            showSnackbar(resources.getString(R.string.change_password_success_message), change_password_parent_layout)
        }
    }
}