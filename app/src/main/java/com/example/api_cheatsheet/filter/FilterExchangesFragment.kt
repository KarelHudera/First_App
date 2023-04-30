package com.example.api_cheatsheet.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_cheatsheet.databinding.FragmentFilterBinding
import com.example.api_cheatsheet.extensions.safeNavigate
import com.example.api_cheatsheet.listExchanges.ExchangeAdapter
import com.example.api_cheatsheet.listExchanges.ScreenState

class FilterExchangesFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FilterViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exchangeAdapter = ExchangeAdapter {
            findNavController().safeNavigate(
                FilterExchangesFragmentDirections.actionToBottomFragment(
                    it.id.orEmpty()
                )
            )
        }

        binding.recyclerExchanges.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exchangeAdapter
        }

        binding.editTextFilter.addTextChangedListener {
            if (it != null) {
                viewModel.onTextChanged(it.toString())
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Error -> Toast.makeText(
                    requireContext(),
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
                is ScreenState.Loading -> {}
                else -> {}
            }
        }

        viewModel.exchangesList.observe(viewLifecycleOwner) {
            exchangeAdapter.submitList(it)
        }
    }
}

