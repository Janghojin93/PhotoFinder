package com.bank.photofinder.ui.home

import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.bank.photofinder.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val TAG = "HomeViewModel.kt"

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    val checkQuery: LiveData<String> get() = currentQuery

    val photo = currentQuery.switchMap { queryString ->
        photoRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhoto(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "home"
    }


    // 이미지 저장 클릭 이벤트
    private fun onClickSaveImage() {

    }

}