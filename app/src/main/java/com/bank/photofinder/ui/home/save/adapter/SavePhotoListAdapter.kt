package com.bank.photofinder.ui.home.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bank.photofinder.databinding.ListItemSavePhotoBinding
import com.bank.photofinder.model.Photo
import com.bank.photofinder.utils.onThrottleClick


class SavePhotoListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Photo, SavePhotoListAdapter.ViewHolder>(DataDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemSavePhotoBinding.inflate(
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

    inner class ViewHolder(val binding: ListItemSavePhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageRemoveButton.onThrottleClick {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { item -> listener.onItemRemoveClick(item) }
                }
            }
        }
    }


    interface OnItemClickListener {
        fun onItemRemoveClick(photo: Photo)
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