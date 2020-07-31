package com.example.gotravel.presentation.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import com.example.gotravel.common.model.FAQ
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.faq_item.*


sealed class FAQViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class Item(parent: ViewGroup) :
        FAQViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.faq_item, parent, false)
        ) {

        lateinit var faq: FAQ

        fun bind(faq: FAQ) {
            this.faq = faq
            faq.apply {
                text_question.text = faq.question
                text_answer.text = faq.answer
                text_created_at.text = faq.createdAt
            }
        }
    }
}