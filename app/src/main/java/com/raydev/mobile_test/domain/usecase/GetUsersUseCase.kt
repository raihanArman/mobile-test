package com.raydev.mobile_test.domain.usecase

import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.domain.repository.DataRepository
import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(): Flow<ResponseState<List<User>>> {
        return dataRepository.getUsers()
    }
}