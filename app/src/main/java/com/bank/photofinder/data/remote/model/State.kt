package com.bank.photofinder.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
) : Parcelable

