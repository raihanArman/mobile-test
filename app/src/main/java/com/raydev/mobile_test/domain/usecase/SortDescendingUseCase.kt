package com.raydev.mobile_test.domain.usecase

import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortDescendingUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(city: String): Flow<List<User>> {
        return dataRepository.sortDescending(city)
    }
}