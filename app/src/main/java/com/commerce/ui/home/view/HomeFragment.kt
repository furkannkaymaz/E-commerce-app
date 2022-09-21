package com.commerce.ui.home.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commerce.R
import com.commerce.base.BaseFragment
import com.commerce.databinding.FragmentHomeBinding
import com.commerce.ui.home.adapter.HomeAdapter
import com.commerce.ui.home.viewmodel.HomeViewModel
import com.commerce.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: HomeAdapter

    override fun configureUiItems() {
        bindRecycleView()
        tabLayoutConfigure()

    }

    override fun onCreateFinished() {
        viewModel.getData(requireActivity())

    }

    override fun observerData() {
        super.observerData()
        viewModel.getData.observe(viewLifecycleOwner) {
            adapter.set(it?.data?.flights?.departure)
            adapter.notifyDataSetChanged()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            context?.toast(getString(R.string.unExpectedError))
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {

        }
    }

    private fun tabLayoutConfigure() {
        binding?.tbDate?.newTab()?.setText("Önceki Gün \n 1.450 TL")
            ?.let { binding?.tbDate?.addTab(it) }
        binding?.tbDate?.newTab()?.setText("28 Haz Sal \n 1.450 TL")
            ?.let { binding?.tbDate?.addTab(it) }
        binding?.tbDate?.newTab()?.setText("Sonraki Gün \n 1.450 TL")
            ?.let { binding?.tbDate?.addTab(it) }
    }

    private fun bindRecycleView() {
        binding?.rvFlights?.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        adapter = HomeAdapter {}
        binding?.rvFlights?.adapter = adapter

    }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

}