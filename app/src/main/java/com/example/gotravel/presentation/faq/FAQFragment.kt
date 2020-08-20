package com.example.gotravel.presentation.faq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.R
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_f_a_q.*

class FAQFragment : Fragment() {

    private lateinit var viewModel: FAQViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_f_a_q, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FAQViewModel::class.java)

        setUpFAQSAdapter()
        setObservers()
        setListeners()
    }

    private fun setUpFAQSAdapter() {
        recycler_view_faq.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = FAQAdapter()
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setListeners() {
        image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        text_ask_us.setOnClickListener {
            findNavController().navigate(FAQFragmentDirections.actionFaqToAskAQuestionFragment())
        }
    }

    private fun render(state: FAQViewState) {

        when(state.faqListState) {
            is FAQListState.Loading -> {

            }
            is FAQListState.Error -> {

            }
            is FAQListState.Content -> {
                (recycler_view_faq.adapter as FAQAdapter).data = state.faqListState.faqs
            }
        }
    }
}