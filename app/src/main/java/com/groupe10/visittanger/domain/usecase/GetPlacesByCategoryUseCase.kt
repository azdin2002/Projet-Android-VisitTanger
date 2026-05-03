package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesByCategoryUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    operator fun invoke(category: Category): Flow<List<Place>> =
        repository.getPlacesByCategory(category)
}
