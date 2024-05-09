package com.winteryy.nbcsearch.presentation.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.winteryy.nbcsearch.R
import com.winteryy.nbcsearch.app.util.toUiString
import com.winteryy.nbcsearch.databinding.ItemContentBinding
import com.winteryy.nbcsearch.presentation.common.ItemType

class StorageRVAdapter(
    private val onClick: (String) -> Unit
): ListAdapter<StorageListItem, StorageRVAdapter.StorageViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageRVAdapter.StorageViewHolder {
        return StorageViewHolder(
            ItemContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StorageRVAdapter.StorageViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class StorageViewHolder(
        private val binding: ItemContentBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: StorageListItem) {
            binding.apply {
                itemImageView.load(item.thumbnailUrl?: R.drawable.image_error)
                itemTitleTextView.text = when(item.itemType) {
                    ItemType.IMAGE -> "[Image] ${item.title?:"제목 없음".ifBlank { "제목 없음" }}"
                    ItemType.VIDEO -> "[Video] ${item.title?:"제목 없음".ifBlank { "제목 없음" }}"
                }
                itemDateTextView.text = item.datetime.toUiString()
            }
            binding.root.setOnClickListener {
                onClick(item.thumbnailUrl!!)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<StorageListItem>() {
            override fun areItemsTheSame(
                oldItem: StorageListItem,
                newItem: StorageListItem
            ): Boolean {
                return oldItem.thumbnailUrl == newItem.thumbnailUrl
            }

            override fun areContentsTheSame(
                oldItem: StorageListItem,
                newItem: StorageListItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}