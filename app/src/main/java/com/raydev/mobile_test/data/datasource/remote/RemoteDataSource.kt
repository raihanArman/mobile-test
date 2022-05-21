package com.raydev.mobile_test.data.datasource.remote
import android.net.ConnectivityManager
import android.util.Log
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
    private val apiRequest: ApiRequest,
) {
    fun getUsers(): Flow<ResponseState<List<User>>> {
        return flow {
            emit(ResponseState.Loading())
            try{
                val response = apiRequest.getUsers()
                if(response.isNotEmpty()){
                    emit(ResponseState.Success(response))
                }else{
                    emit(ResponseState.Empty)
                }
            }catch (e: Exception){
                Log.d("Error", "getUsers: $e")
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCities(): Flow<ResponseState<List<City>>> {
        return flow {
            emit(ResponseState.Loading())
            try{
                val response = apiRequest.getCities()
                if(response.isNotEmpty()){
                    emit(ResponseState.Success(response))
                }else{
                    emit(ResponseState.Empty)
                }
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addUser(user: UserRequest): User {
        return apiRequest.addUser(user)
    }
}