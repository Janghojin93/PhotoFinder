package com.bank.photofinder.extensions

import android.content.res.Resources
import android.util.TypedValue


//Dp -> Px로 변환하기
fun Int.dpToPx(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
).toInt()
