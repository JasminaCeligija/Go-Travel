package com.example.gotravel.utils

import android.content.Context
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.gotravel.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_ask_a_question.*

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = this.length > 6

fun showSnackbar(message: String, layout: CoordinatorLayout) {
    val snackBar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
    val mainTextView = snackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
    mainTextView.gravity = Gravity.CENTER_HORIZONTAL
    mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackBar.show()
}
