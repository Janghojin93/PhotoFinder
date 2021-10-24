package com.bank.photofinder.data.remote.response

import PAGE_SIZE
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bank.photofinder.data.remote.ApiServices
import com.bank.photofinder.model.Photo
import retrofit2.HttpException
import java.io.IOException

private const val PHOTO_STARTING_PAGE_INDEX = 1

class PhotoPagingSource(private val apiServices: ApiServices, private val query: String) :
    PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val pageNum = params.key ?: PHOTO_STARTING_PAGE_INDEX

        return try {

            val photoList = mutableListOf<Photo>()

            //이미지 api는 50페이지까지 요청가능, 비디오 api는 15페이지까지 요청가능 -> pageNum 가 16이상이라면 이미지 api만 호출.
            if (pageNum < 16) {
                val responseImages = apiServices.searchImages(query, pageNum, PAGE_SIZE)
                val responseVideos = apiServices.searchVideos(query, pageNum, PAGE_SIZE)
                val imageList = responseImages.body()?.documents ?: emptyList()
                val videoList = responseVideos.body()?.documents ?: emptyList()

                //통신체크
                if (responseImages.code() != 200) throw HttpException(responseImages)
                if (responseVideos.code() != 200) throw HttpException(responseVideos)

                imageList.forEach {
                    val data = Photo(thumbnail_url = it.thumbnail_url, datetime = it.datetime)
                    photoList.add(data)
                }
                videoList.forEach {
                    val data = Photo(thumbnail_url = it.thumbnail, datetime = it.datetime)
                    photoList.add(data)
                }
                //마지막페이지인지 아닌지 체크
                val isEndImage = responseImages.body()?.meta?.is_end
                val isEndVideo = responseVideos.body()?.meta?.is_end

                //최신순으로 정렬
                photoList.sortWith(Comparator { a, b ->
                    if (a.datetime <= b.datetime) 1 else -1;
                })


                LoadResult.Page(
                    data = photoList,
                    prevKey = if (pageNum > 1) pageNum - 1 else null,
                    nextKey = if ((isEndImage == false || isEndVideo == false) && pageNum < 51) pageNum + 1 else null
                )

            } else {

                val responseImages = apiServices.searchImages(query, pageNum, PAGE_SIZE)

                //통신체크
                if (responseImages.code() != 200) throw HttpException(responseImages)

                val imageList = responseImages.body()?.documents ?: emptyList()

                imageList.forEach {
                    val data = Photo(thumbnail_url = it.thumbnail_url, datetime = it.datetime)
                    photoList.add(data)
                }

                //마지막페이지인지 아닌지 체크
                val isEndImage = responseImages.body()?.meta?.is_end

                LoadResult.Page(
                    data = photoList,
                    prevKey = if (pageNum > 1) pageNum - 1 else null,
                    nextKey = if ((isEndImage == false) && pageNum < 51) pageNum + 1 else null
                )

            }


        } catch (exception: IOException) {
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null

}