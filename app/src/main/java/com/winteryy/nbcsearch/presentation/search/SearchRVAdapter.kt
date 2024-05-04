package com.winteryy.nbcsearch.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.winteryy.nbcsearch.databinding.ItemContentBinding

class SearchRVAdapter: ListAdapter<SearchListItem, SearchRVAdapter.SearchViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRVAdapter.SearchViewHolder {
        return SearchViewHolder(
            ItemContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchRVAdapter.SearchViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class SearchViewHolder(
        private val binding: ItemContentBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: SearchListItem) {
            binding.apply {
                itemImageView.load(item.thumbnailUrl)
                itemSiteTextView.text = item.siteName
                itemDateTextView.text = item.datetime.toString()
                favoriteMarkImageView.isVisible = item.isFavorite
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<SearchListItem>() {
            override fun areItemsTheSame(
                oldItem: SearchListItem,
                newItem: SearchListItem
            ): Boolean {
                return oldItem.thumbnailUrl == newItem.thumbnailUrl
            }

            override fun areContentsTheSame(
                oldItem: SearchListItem,
                newItem: SearchListItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}