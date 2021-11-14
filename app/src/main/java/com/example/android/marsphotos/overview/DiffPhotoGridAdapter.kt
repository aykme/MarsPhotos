package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto


class DiffPhotoGridAdapter : RecyclerView.Adapter<DiffPhotoGridAdapter.DiffMarsPhotoViewHolder>() {

    private var currentItemList: List<MarsPhoto> = listOf()

    class DiffCallback(
        var oldItemList: List<MarsPhoto>,
        var newItemList: List<MarsPhoto>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItemList.size
        }

        override fun getNewListSize(): Int {
            return newItemList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemList[oldItemPosition].id == newItemList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemList[oldItemPosition].imgSrcUrl == newItemList[newItemPosition].imgSrcUrl
        }

    }

    class DiffMarsPhotoViewHolder(
        private val binding: GridViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(marsPhoto: MarsPhoto) {
            binding.photo = marsPhoto
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffMarsPhotoViewHolder {
        val binding = GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return DiffMarsPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiffMarsPhotoViewHolder, position: Int) {
        val marsPhoto = currentItemList[position]
        holder.bind(marsPhoto)
    }

    override fun getItemCount(): Int {
        return currentItemList.size
    }

    fun submit(newItemList: List<MarsPhoto>) {
        val oldItemList = currentItemList
        val diffResult = DiffUtil.calculateDiff(
            DiffCallback(oldItemList, newItemList)
        )
        currentItemList = newItemList
        diffResult.dispatchUpdatesTo(this)
    }
}