package com.raydev.mobile_test.feature.user_list

import androidx.lifecycle.*
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.domain.usecase.*
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.SortType
import com.raydev.mobile_test.util.ext.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getUserFilterUseCase: GetUserFilterUseCase
): ViewModel() {

    val observableGetUsers: LiveData<ResponseState<List<User>>>
        get() = _observableGetUsers
    private val _observableGetUsers = MutableLiveData<ResponseState<List<User>>>()

    val observableGetCities: LiveData<ResponseState<List<City>>>
        get() = _observableGetCities
    private val _observableGetCities = MutableLiveData<ResponseState<List<City>>>()

    val cityPosition = MutableLiveData<Int>().default(0)
    val cityList = mutableListOf<City>()

    val searchQueryFlow = MutableStateFlow("")
    val citySelectedFlow = MutableStateFlow("")
    val sortTypeFlow = MutableStateFlow(SortType.ASCENDING)

    private val filterFlow = combine(
        searchQueryFlow,
        citySelectedFlow,
        sortTypeFlow
    ){query, citySelected, sortType ->
        Triple(query, citySelected, sortType)
    }.flatMapLatest {(query, citySelected, sortType)->
        getUserFilterUseCase(query, citySelected, sortType)
    }

    val getFilter = filterFlow.asLiveData()

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

//    fun filterUser(sortType: SortType, positionCitySelected: Int){
//        cityPosition.value = positionCitySelected
//        sortTypeLiveData.value = sortType
//
//        val citySelected = cityList[positionCitySelected].name
//
//        viewModelScope.launch {
//            when(sortType){
//                SortType.ASCENDING -> {
//                    val result = getUserFilterUseCase(citySelected)
//                    result.collect {
//                        _observableGetUsers.postValue(ResponseState.Success(it))
//                    }
//                }
//                SortType.DESCENDING -> {
//                    val result = sortDescendingUseCase(citySelected)
//                    result.collect {
//                        _observableGetUsers.postValue(ResponseState.Success(it))
//                    }
//                }
//            }
//        }
//    }

    fun resetFilter(){
        searchQueryFlow.value = ""
        citySelectedFlow.value = ""
        sortTypeFlow.value = SortType.ASCENDING
//        viewModelScope.launch {
//            val result = getUserFilterUseCase("")
//            result.collect {
//                _observableGetUsers.postValue(ResponseState.Success(it))
//            }
//        }
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