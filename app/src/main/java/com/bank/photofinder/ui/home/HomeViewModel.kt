package com.bank.photofinder.ui.home

import android.util.Log
import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.bank.photofinder.data.repository.PhotoRepository
import com.bank.photofinder.model.Photo
import com.bank.photofinder.utils.SingleLiveEvent
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

    private val _currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    private val _savePhotoList = MutableLiveData<List<Photo>>()

    val currentQuery: LiveData<String> get() = _currentQuery
    val savePhotoList: LiveData<List<Photo>> get() = _savePhotoList

    val photo = _currentQuery.switchMap { queryString ->
        photoRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhoto(query: String) {
        _currentQuery.value = query
    }

    //이미지 저장 클릭 이벤트
    fun onClickSavePhoto(photo: Photo) {
        if (_savePhotoList.value.isNullOrEmpty()) {
            savePhoto(listOf(photo))
        } else {
            val list = _savePhotoList.value?.toMutableList()
            list?.add(0, photo)
            list?.let { savePhoto(it) }
        }

    }

    //이미지 삭제 클릭 이벤트
    fun onClickDeletePhoto(photo: Photo) {
        if (!_savePhotoList.value.isNullOrEmpty()) {
            val list = _savePhotoList.value?.toMutableList()
            list?.removeIf {
                it.thumbnail_url == photo.thumbnail_url
            }
            list?.let { deletePhoto(it) }
        }
    }


    private fun deletePhoto(photoList: List<Photo>) {
        _savePhotoList.value = photoList

    }

    private fun savePhoto(photoList: List<Photo>) {
        _savePhotoList.value = photoList
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "home"
    }


}