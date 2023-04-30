package com.example.api_cheatsheet.volume

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.api_cheatsheet.databinding.FragmentVolumeBinding
import com.example.api_cheatsheet.databinding.LayoutErrorLoadingBinding
import com.example.api_cheatsheet.listExchanges.ScreenState
import com.example.api_cheatsheet.listExchanges.updateVisibility
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class VolumeChartFragment : Fragment() {

    private var _binding: FragmentVolumeBinding? = null
    private val binding get() = _binding!!
    private var _mergeBinding: LayoutErrorLoadingBinding? = null
    private val mergeBinding get() = _mergeBinding!!

    private val viewModel by viewModels<VolumeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVolumeBinding.inflate(layoutInflater)
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
            viewModel.refreshVolume()
        }
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            mergeBinding.updateVisibility(state)
            when (state) {
                is ScreenState.Error -> {
                    mergeBinding.errorMessageErrorLayout.text = "Error: timeout"
                }
                is ScreenState.Loading -> {
                    //
                }
                is ScreenState.Success -> {
                    val pieEntries = state.data.map {
                        PieEntry(
                            it.tradeVolume24hBtc?.toFloat() ?: 0.0f,
                            it.name,
                        )
                    }

                    val pieChart = binding.pieChart

                    val colors = if (isDarkModeEnabled(requireContext())) {
                        listOf(
                            rgb(0, 63, 92),//7
                            rgb(55, 76, 128),//6
                            rgb(122, 81, 149),//5
                            rgb(188, 80, 144),//4
                            rgb(239, 86, 117),//3
                            rgb(255, 118, 74),//2
                            rgb(255, 166, 0),//1
                        )
                    } else {
                        listOf(
                            rgb(222, 66, 91),//1
                            rgb(233, 113, 82),//2
                            rgb(246, 160, 94),//3
                            rgb(255, 206, 122),//4
                            rgb(199, 185, 86),//5
                            rgb(139, 165, 61),//6
                            rgb(72, 143, 49),//7
                        )
                    }

                    if (isDarkModeEnabled(requireContext())) {
                        pieChart.setCenterTextColor(Color.WHITE)
                        pieChart.setEntryLabelColor(Color.WHITE)
                    } else {
                        pieChart.setCenterTextColor(Color.BLACK)
                        pieChart.setEntryLabelColor(Color.BLACK)
                    }

                    val dataSet = PieDataSet(pieEntries, "Test")
                    dataSet.colors = colors


                    val data = PieData(dataSet)

                    pieChart.data = data
                    pieChart.description.isEnabled = false
                    pieChart.legend.isEnabled = false
                    pieChart.setEntryLabelTextSize(16f)
                    pieChart.animateY(400)
                    pieChart.invalidate()
                    pieChart.transparentCircleRadius = 0f
                    pieChart.setHoleColor(Color.TRANSPARENT)
                    pieChart.holeRadius = 45f
                    binding.textViewHeader.text = "Volume"
                }
            }
        }
    }
}

fun isDarkModeEnabled(context: Context): Boolean {
    val currentNightMode =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}
