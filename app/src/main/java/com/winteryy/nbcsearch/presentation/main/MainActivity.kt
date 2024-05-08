package com.winteryy.nbcsearch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.winteryy.nbcsearch.R
import com.winteryy.nbcsearch.databinding.ActivityMainBinding
import com.winteryy.nbcsearch.presentation.search.SearchFragment
import com.winteryy.nbcsearch.presentation.storage.StorageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        showFragment(SEARCH_FRAGMENT_TAG)

        binding.searchTabButton.setOnClickListener {
            showFragment(SEARCH_FRAGMENT_TAG)
        }

        binding.storageTabButton.setOnClickListener {
            showFragment(STORAGE_FRAGMENT_TAG)
        }
    }

    private fun showFragment(tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)

        for(frag in supportFragmentManager.fragments) {
            transaction.hide(frag)
        }

        if(fragment==null) {
            fragment = when(tag) {
                SEARCH_FRAGMENT_TAG -> SearchFragment()
                STORAGE_FRAGMENT_TAG -> StorageFragment()
                else -> return
            }

            transaction.add(R.id.mainFragmentContainerView, fragment, tag)
        }else {
            transaction.show(fragment)
        }

        transaction.commit()
    }

    companion object {
        private const val SEARCH_FRAGMENT_TAG = "searchFragment"
        private const val STORAGE_FRAGMENT_TAG = "storageFragment"
    }
}