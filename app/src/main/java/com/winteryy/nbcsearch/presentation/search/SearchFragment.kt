package com.winteryy.nbcsearch.presentation.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.view.isVisible
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
        setRecyclerView()
        setButtonListeners()

        // 마지막 검색 기록이 있다면 이를 검색창에 입력합니다.
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

    /**
     * 리사이클러뷰에 필요한 값들을 할당하는 메소드입니다.
     */
    private fun setRecyclerView() {
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.addOnScrollListener(object : OnScrollListener() {
            var topFlag = true

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //현재 어댑터가 가진 아이템 갯수-10번째 아이템이 노출되는 상태가 되면 다음 페이지 로드를 시도
                val lastPos =
                    (binding.searchRecyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemNum = adapter.itemCount - 1

                if (itemNum <= lastPos + RV_THRESHOLD) {
                    searchViewModel.loadMore()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && !recyclerView.canScrollVertically(-1)) {
                    binding.scrollToTopFAB.startAnimation(
                        AlphaAnimation(
                            1f,
                            0f
                        ).apply { duration = 500 }
                    )
                    binding.scrollToTopFAB.isVisible = false
                    topFlag = true
                } else {
                    //topFlag를 통해 매번 애니메이션이 노출되는 것을 방지
                    if (topFlag) {
                        binding.scrollToTopFAB.startAnimation(
                            AlphaAnimation(
                                0f,
                                1f
                            ).apply { duration = 500 }
                        )
                        binding.scrollToTopFAB.isVisible = true
                        topFlag = false
                    }
                }
            }
        })
    }

    private fun setButtonListeners() {
        binding.scrollToTopFAB.setOnClickListener {
            binding.searchRecyclerView.smoothScrollToPosition(0)
        }

        binding.searchButton.setOnClickListener {
            val keyword = binding.searchEditText.text.toString()
            searchViewModel.searchListItem(keyword)
            //마지막 검색어 갱신
            with(sharedPref.edit()) {
                putString(LAST_KEYWORD, keyword)
                apply()
            }
            binding.searchRecyclerView.scrollToPosition(0)
            binding.root.hideSoftKeyboard()
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