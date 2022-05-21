package com.raydev.mobile_test.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydev.mobile_test.data.datasource.remote.RemoteDataSource
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.network.ApiRequest
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.util.CheckConnection
import com.raydev.mobile_test.util.Constant.KEY_ADDRESS
import com.raydev.mobile_test.util.Constant.KEY_CITY
import com.raydev.mobile_test.util.Constant.KEY_EMAIL
import com.raydev.mobile_test.util.Constant.KEY_NAME
import com.raydev.mobile_test.util.ResponseState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltWorker
class UserWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val remoteDataSource: RemoteDataSource
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val name = inputData.getString(KEY_NAME) ?: ""
        val email = inputData.getString(KEY_EMAIL) ?: ""
        val address = inputData.getString(KEY_ADDRESS) ?: ""
        val city = inputData.getString(KEY_CITY) ?: ""

        if(name.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty()){
            remoteDataSource.addUser(UserRequest(name, email, address, city))
            return Result.success()
        }else{
            return Result.failure()
        }
    }


}