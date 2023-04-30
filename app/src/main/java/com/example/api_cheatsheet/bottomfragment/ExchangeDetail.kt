package com.example.api_cheatsheet.bottomfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.api_cheatsheet.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExchangeDetail : BottomSheetDialogFragment() {

    private var _binding: LayoutBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BottomFragmentViewModel>()

    private val args by navArgs<ExchangeDetailArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutBottomSheetBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BottomFragmentScreenState.Error -> {
                    binding.progress.visibility = View.GONE
                    state.throwable.printStackTrace()
                }
                is BottomFragmentScreenState.Loading -> binding.progress.visibility = View.VISIBLE
                is BottomFragmentScreenState.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.imageView.load(state.data.image)
                    binding.textViewName.text = state.data.name
                    binding.textViewCountry.text = state.data.country
                    binding.textViewVolume.text = state.data.tradeVolume24hBtc.toString()
                    binding.textViewUrl.apply {
                        text = state.data.url
                        setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.data.url))
                            startActivity(intent)
                        }
                    }
                    binding.textViewId.text = state.data.trustScore.toString()
                }
            }
        }
    }
}