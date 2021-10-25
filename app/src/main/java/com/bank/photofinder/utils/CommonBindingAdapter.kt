package com.bank.photofinder.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bank.photofinder.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object CommonBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(Uri.parse(imageUrl))
            .transition(DrawableTransitionOptions.withCrossFade(250))
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.img_loading)
                    .error(R.drawable.img_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(view)
    }
}