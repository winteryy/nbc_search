package com.winteryy.nbcsearch.presentation.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.winteryy.nbcsearch.databinding.FragmentSearchBinding
import com.winteryy.nbcsearch.presentation.common.hideSoftKeyboard
import com.winteryy.nbcsearch.presentation.common.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter by lazy {
        SearchRVAdapter {
            if(it.isFavorite) {
                searchViewModel.removeFromStorage(it.thumbnailUrl!!)
            }else {
                searchViewModel.saveToStorage(it)
            }
        }
    }

    private val sharedPref: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPos = (binding.searchRecyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemNum = adapter.itemCount-1

                if(itemNum <= lastPos + RV_THRESHOLD) {
                    searchViewModel.loadMore()
                }
            }
        })

        binding.searchButton.setOnClickListener {
            val keyword = binding.searchEditText.text.toString()
            searchViewModel.searchListItem(keyword)
            with(sharedPref.edit()) {
                putString(LAST_KEYWORD, keyword)
                apply()
            }
            binding.searchRecyclerView.scrollToPosition(0)
            binding.root.hideSoftKeyboard()
        }

        val lastKeyword = sharedPref.getString(LAST_KEYWORD, null)
        if (lastKeyword != null) {
            binding.searchEditText.setText(lastKeyword)
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.combinedSearchList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    adapter.submitList(it.list)
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.errorEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    binding.root.showErrorSnackBar(it.msg)
                }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val SHARED_PREF = "search_app_pref"
        private const val LAST_KEYWORD = "last_keyword"

        private const val RV_THRESHOLD = 10
    }
}