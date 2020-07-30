package com.example.gotravel.presentation.trip_details

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gotravel.R
import com.example.gotravel.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_ask_a_question.*
import kotlinx.android.synthetic.main.fragment_trip_details.*


class AskAQuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ask_a_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {

        image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        button_submit_question.setOnClickListener {
            validateFields()
            clearFields()
        }
    }

    private fun validateFields() {

        val question = edit_text_question?.text.toString().trim()
        if (question.isNotBlank()) {
            showSnackbar("Your message has been sent")
        }
    }

    private fun showSnackbar(message: String) {
        val snackBar = Snackbar.make(contact_us_parent_layout, message, Snackbar.LENGTH_LONG)
        val mainTextView = snackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackBar.show()
    }

    private fun clearFields() {
        edit_text_question.hideKeyboard()
        edit_text_question.setText("")
        edit_text_question.clearFocus()
    }
}