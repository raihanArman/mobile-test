package com.raydev.mobile_test.work

import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.work.*
import com.raydev.mobile_test.data.datasource.remote.RemoteDataSource
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.util.Constant
import com.raydev.mobile_test.util.Constant.KEY_ADDRESS
import com.raydev.mobile_test.util.Constant.KEY_CITY
import com.raydev.mobile_test.util.Constant.KEY_EMAIL
import com.raydev.mobile_test.util.Constant.KEY_NAME
import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserHelper @Inject constructor(
    private val workManager: WorkManager
) {

    fun addUser(
        userRequest: UserRequest,
    ): Flow<WorkInfo>{
        val data = Data.Builder()

        data.apply {
            putString(KEY_NAME, userRequest.name)
            putString(KEY_EMAIL, userRequest.email)
            putString(KEY_ADDRESS, userRequest.address)
            putString(KEY_CITY, userRequest.city)
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val userWorker = OneTimeWorkRequestBuilder<UserWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            userWorker
        )

        return workManager.getWorkInfoByIdLiveData(userWorker.id).asFlow()

    }

}