package com.winteryy.nbcsearch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.winteryy.nbcsearch.R
import com.winteryy.nbcsearch.databinding.ActivityMainBinding
import com.winteryy.nbcsearch.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.commit {
            add(R.id.mainFragmentContainerView, SearchFragment())
        }
    }
}