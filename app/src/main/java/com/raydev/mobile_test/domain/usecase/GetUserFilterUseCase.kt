package com.raydev.mobile_test.domain.usecase

import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.domain.repository.DataRepository
import com.raydev.mobile_test.util.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFilterUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(name: String, city: String, sortType: SortType): Flow<List<User>> {
        return dataRepository.getUserFilter(name, city, sortType)
    }

}