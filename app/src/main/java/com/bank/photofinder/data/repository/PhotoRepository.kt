package com.bank.photofinder.data.repository

import PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.bank.photofinder.data.remote.ApiServices
import com.bank.photofinder.data.remote.response.PhotoPagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PhotoRepository @Inject constructor(private val apiServices: ApiServices) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(apiServices, query) }
        ).liveData

}