package com.example.api_cheatsheet.listExchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_cheatsheet.databinding.FragmentListExchangesBinding
import com.example.api_cheatsheet.databinding.LayoutErrorLoadingBinding
import com.example.api_cheatsheet.extensions.safeNavigate

class ExchangesList : Fragment() {
    private var _binding: FragmentListExchangesBinding? = null
    private val binding get() = _binding!!

    private var _mergeBinding: LayoutErrorLoadingBinding? = null
    private val mergeBinding get() = _mergeBinding!!

    private val viewModel by viewModels<ExchangesListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListExchangesBinding.inflate(layoutInflater)
        _mergeBinding = LayoutErrorLoadingBinding.bind(binding.root)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mergeBinding.refreshButtonErrorLayout.setOnClickListener {
            viewModel.refreshExchanges()
        }
        binding.headerView.text = "Exchanges"
        val exchangeAdapter = ExchangeAdapter {
            findNavController().safeNavigate(
                ExchangesListDirections.actionToBottomFragment(
                    it.id.orEmpty()
                )
            )
        }

        binding.recyclerExchanges.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exchangeAdapter
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            mergeBinding.updateVisibility(state)
            binding.recyclerExchanges.isVisible = state is ScreenState.Success
            when (state) {
                is ScreenState.Error -> {
                    mergeBinding.errorMessageErrorLayout.text = "Error: timeout"
                }
                is ScreenState.Loading -> {
                    // left blank intentionally
                }
                is ScreenState.Success -> {
                    exchangeAdapter.submitList(state.data)
                    binding.filterExhangesFragment.setOnClickListener {
                        findNavController().safeNavigate(
                            ExchangesListDirections.actionToFilter()
                        )
                    }
                }
            }
        }
    }
}

fun LayoutErrorLoadingBinding.updateVisibility(state: ScreenState) {
    refreshButtonErrorLayout.isVisible = state is ScreenState.Error
    errorMessageErrorLayout.isVisible = state is ScreenState.Error
    progressBarErrorLayout.isVisible = state is ScreenState.Loading
}
