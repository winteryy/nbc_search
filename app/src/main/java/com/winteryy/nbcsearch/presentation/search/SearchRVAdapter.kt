package com.winteryy.nbcsearch.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.winteryy.nbcsearch.app.util.toUiString
import com.winteryy.nbcsearch.databinding.ItemContentBinding

class SearchRVAdapter(
    private val onClick: (SearchListItem) -> Unit
): ListAdapter<SearchListItem, SearchRVAdapter.SearchViewHolder>(DIFF_UTIL) {

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
                itemSiteTextView.text = item.siteName?.ifBlank { "제목 없음" }
                itemDateTextView.text = item.datetime.toUiString()
                favoriteMarkImageView.isVisible = item.isFavorite
            }
            binding.root.setOnClickListener {
                onClick(item)
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