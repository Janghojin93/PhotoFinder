package com.bank.photofinder.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bank.photofinder.databinding.ListItemSearchPhotoBinding
import com.bank.photofinder.model.Photo


class SearchPhotoListAdapter :
    PagingDataAdapter<Photo, SearchPhotoListAdapter.ViewHolder>(DataDiff) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position) as Photo
        holder.binding.apply {
            photoModel = photo
            executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemSearchPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    class ViewHolder(val binding: ListItemSearchPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    object DataDiff : DiffUtil.ItemCallback<Photo>() {

        //두 객체를 비교해서 동일한 객체를 교체
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }
}