package com.raydev.mobile_test.domain.usecase

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.domain.repository.DataRepository
import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(user: UserRequest): Flow<WorkInfo> {
        return dataRepository.addUser(user)
    }
}