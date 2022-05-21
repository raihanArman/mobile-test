package com.raydev.mobile_test.feature.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.domain.usecase.*
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.SortType
import com.raydev.mobile_test.util.ext.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val sortAscendingUseCase: SortAscendingUseCase,
    private val sortDescendingUseCase: SortDescendingUseCase
): ViewModel() {

    val observableGetUsers: LiveData<ResponseState<List<User>>>
        get() = _observableGetUsers
    private val _observableGetUsers = MutableLiveData<ResponseState<List<User>>>()

    val observableGetCities: LiveData<ResponseState<List<City>>>
        get() = _observableGetCities
    private val _observableGetCities = MutableLiveData<ResponseState<List<City>>>()

    val sortTypeLiveData = MutableLiveData<SortType>().default(SortType.ASCENDING)
    val cityPosition = MutableLiveData<Int>().default(0)
    val cityList = mutableListOf<City>()

    fun getCities(){
        viewModelScope.launch {
            val result = getCitiesUseCase()
            result.collect {
                _observableGetCities.postValue(it)
                if(it.data != null) {
                    cityList.clear()
                    cityList.addAll(it.data)
                }
            }
        }
    }

    fun filterUser(sortType: SortType, positionCitySelected: Int){
        cityPosition.value = positionCitySelected
        sortTypeLiveData.value = sortType

        val citySelected = cityList[positionCitySelected].name

        viewModelScope.launch {
            when(sortType){
                SortType.ASCENDING -> {
                    val result = sortAscendingUseCase(citySelected)
                    result.collect {
                        _observableGetUsers.postValue(ResponseState.Success(it))
                    }
                }
                SortType.DESCENDING -> {
                    val result = sortDescendingUseCase(citySelected)
                    result.collect {
                        _observableGetUsers.postValue(ResponseState.Success(it))
                    }
                }
            }
        }
    }

    fun resetFilter(){
        viewModelScope.launch {
            val result = sortAscendingUseCase("")
            result.collect {
                _observableGetUsers.postValue(ResponseState.Success(it))
            }
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            val result = getUsersUseCase()
            result.collect {
                _observableGetUsers.postValue(it)
            }
        }
    }
}