package com.example.gotravel.presentation.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gotravel.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {

        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)

        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.toolbar, 0, 0)
                .apply {
                    try {
                        text_question.text = getString(R.styleable.toolbar_toolbar_title) ?: ""

                    } finally {
                        recycle()
                    }
                }
        }
    }

    fun setToolbarTitle(title: String) {
        text_question.text = title
    }

    fun onBackButtonClicked(clickListener: () -> Unit) {
        image_back.setOnClickListener {
            clickListener()
        }
    }
}