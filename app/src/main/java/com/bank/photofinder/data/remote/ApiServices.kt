package com.bank.photofinder.data.remote

import com.bank.photofinder.BuildConfig
import com.bank.photofinder.data.remote.response.SearchImageResponse
import com.bank.photofinder.data.remote.response.SearchVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiServices {

    @Headers("Authorization:KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("/v2/search/vclip")
    suspend fun searchVideos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchVideoResponse>


    @Headers("Authorization:KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("/v2/search/image")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchImageResponse>

}