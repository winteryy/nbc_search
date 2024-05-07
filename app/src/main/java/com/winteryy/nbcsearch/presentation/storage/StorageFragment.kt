package com.winteryy.nbcsearch.presentation.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.winteryy.nbcsearch.databinding.FragmentStorageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StorageFragment: Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val storageViewModel: StorageViewModel by viewModels()
    private val adapter by lazy {
        StorageRVAdapter {
            storageViewModel.removeItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.storeRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            storageViewModel.favoriteList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                adapter.submitList(it.list)
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}