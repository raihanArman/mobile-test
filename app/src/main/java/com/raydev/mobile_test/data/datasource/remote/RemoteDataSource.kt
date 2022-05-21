package com.raydev.mobile_test.data.datasource.remote
import android.net.ConnectivityManager
import android.util.Log
import com.raydev.mobile_test.data.datasource.DataResource
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.network.ApiRequest
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.util.CheckConnection
import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject;


class RemoteDataSource @Inject constructor(
    private val apiRequest: ApiRequest
): DataResource() {
    fun getUsers(): Flow<ResponseState<List<User>>> = getResult {
        apiRequest.getUsers()
    }

    fun getCities(): Flow<ResponseState<List<City>>> = getResult {
        apiRequest.getCities()
    }

    suspend fun addUser(user: UserRequest): User? = getResultWithoutState {
         apiRequest.addUser(user)
    }
}