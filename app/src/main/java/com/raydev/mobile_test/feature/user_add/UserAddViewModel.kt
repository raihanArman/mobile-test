package com.raydev.mobile_test.feature.user_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.domain.usecase.AddUserUseCase
import com.raydev.mobile_test.domain.usecase.GetCitiesUseCase
import com.raydev.mobile_test.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAddViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val addUserUseCase: AddUserUseCase
): ViewModel() {

    val observableGetCities: LiveData<ResponseState<List<City>>>
        get() = _observableGetCities
    private val _observableGetCities = MutableLiveData<ResponseState<List<City>>>()

    val observableAddUser: LiveData<ResponseState<String>>
        get() = _observableAddUser
    private val _observableAddUser = MutableLiveData<ResponseState<String>>()

    fun getCities(){
        viewModelScope.launch {
            val result = getCitiesUseCase()
            result.collect {
                _observableGetCities.postValue(it)
            }
        }
    }

    fun addUser(user: UserRequest){
        viewModelScope.launch {
            if(user.name.isNotEmpty() && user.email.isNotEmpty() && user.address.isNotEmpty() && user.city.isNotEmpty()) {
                val result = addUserUseCase(user)
                result.collect { info ->
                    info.let {
                        when (it.state) {
                            WorkInfo.State.SUCCEEDED -> _observableAddUser.postValue(
                                ResponseState.Success(
                                    "Berhasil Simpan"
                                )
                            )
                            WorkInfo.State.FAILED -> _observableAddUser.postValue(
                                ResponseState.Error(
                                    "Gagal simpan"
                                )
                            )
                            WorkInfo.State.RUNNING -> _observableAddUser.postValue(ResponseState.Loading())
                            else -> _observableAddUser.postValue(ResponseState.Error("Gagal simpan"))
                        }
                    }
                }
            }else{
                _observableAddUser.postValue(ResponseState.Error("Lengkapi form"))
            }
        }

    }

}