package com.bank.photofinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Photo(
    val thumbnail_url: String,
    val datetime: String,
    ) : Parcelable