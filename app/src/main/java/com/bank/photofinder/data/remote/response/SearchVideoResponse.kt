package com.bank.photofinder.data.remote.response

import com.bank.photofinder.data.remote.model.State
import com.bank.photofinder.data.remote.model.Video


data class SearchVideoResponse(
    val meta: State,
    val documents: List<Video>
)
