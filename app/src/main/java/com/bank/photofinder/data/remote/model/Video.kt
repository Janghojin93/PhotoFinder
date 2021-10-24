package com.bank.photofinder.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Video(
    val title: String,
    val play_time: Int,
    val thumbnail: String,
    val url: String,
    val datetime: String,
    val author: String
) : Parcelable


