package com.bank.photofinder.data.remote.response

import com.bank.photofinder.data.remote.model.Image
import com.bank.photofinder.data.remote.model.State


data class SearchImageResponse(
    val meta: State,
    val documents: List<Image>
)

