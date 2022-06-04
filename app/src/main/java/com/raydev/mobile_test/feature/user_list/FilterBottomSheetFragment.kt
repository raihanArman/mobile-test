package com.raydev.mobile_test.feature.user_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raydev.mobile_test.R
import com.raydev.mobile_test.base.BaseBottomSheet
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.databinding.FragmentFilterBottomSheetBinding
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.SortType
import com.raydev.mobile_test.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilterBottomSheetFragment : BaseBottomSheet<FragmentFilterBottomSheetBinding>() {

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        setupObserve()
        setupView()

        binding.btnFilter.setOnClickListener {
            applyFilter()
        }

        binding.btnReset.setOnClickListener {
            resetFilter()
        }

    }

    private fun resetFilter() {
        viewModel.resetFilter()
        dismiss()
    }

    private fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            when(viewModel.sortTypeFlow.first()){
                SortType.ASCENDING ->{
                    binding.rbAsc.isChecked = true
                }
                SortType.DESCENDING ->{
                    binding.rbDesc.isChecked = true
                }
            }
        }
        viewModel.cityPosition.observe(viewLifecycleOwner){
            binding.spCity.setSelection(it)
        }
    }

    private fun applyFilter() {
        val sortType: SortType = if(binding.rbAsc.isChecked)
            SortType.ASCENDING
        else if(binding.rbDesc.isChecked)
            SortType.DESCENDING
        else
            SortType.ASCENDING
//        viewModel.filterUser(sortType, binding.spCity.selectedItemPosition)
        viewModel.sortTypeFlow.value = sortType
        viewModel.citySelectedFlow.value = viewModel.cityList[binding.spCity.selectedItemPosition].name
        dismiss()
    }

    private fun setupObserve() {
        viewModel.observableGetCities.observe(this){response->
            when(response){
                is ResponseState.Loading->{

                }
                is ResponseState.Success->{
                    setDataSpinner(response.data)
                }
                is ResponseState.Error->{
                    toast(response.errorMessage)
                }
            }
        }
    }

    private fun setDataSpinner(data: List<City>?) {
        val citiesNameList = mutableListOf<String>()
        if (data != null) {
            for (i in data.indices) {
                citiesNameList.add(data[i].name)
            }
        }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(), R.layout.item_spinner, R.id.textView, citiesNameList)
        binding.spCity.adapter = arrayAdapter
    }

    override fun getViewBinding(): FragmentFilterBottomSheetBinding = FragmentFilterBottomSheetBinding.inflate(layoutInflater)

}