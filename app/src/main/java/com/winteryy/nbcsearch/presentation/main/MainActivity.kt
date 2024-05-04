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

        showFragment("SearchFragment")

        binding.searchTabButton.setOnClickListener {
            showFragment("SearchFragment")
        }

        binding.storageTabButton.setOnClickListener {
            showFragment("StorageFragment")
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
                "SearchFragment" -> SearchFragment()
                "StorageFragment" -> StorageFragment()
                else -> return
            }

            transaction.add(R.id.mainFragmentContainerView, fragment, tag)
        }else {
            transaction.show(fragment)
        }

        transaction.commit()
    }
}