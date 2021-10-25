package com.bank.photofinder.ui.home.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bank.photofinder.databinding.ListItemSearchPhotoBinding
import com.bank.photofinder.model.Photo
import com.bank.photofinder.utils.onThrottleClick


class SearchPhotoListAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Photo, SearchPhotoListAdapter.ViewHolder>(DataDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemSearchPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position) as Photo
        holder.binding.apply {
            photoModel = photo
            executePendingBindings()
        }

    }


    inner class ViewHolder(val binding: ListItemSearchPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageSaveButton.onThrottleClick {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { item -> listener.onItemSaveClick(item) }
                }
            }
        }

    }


    interface OnItemClickListener {
        fun onItemSaveClick(photo: Photo)
    }

    object DataDiff : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}