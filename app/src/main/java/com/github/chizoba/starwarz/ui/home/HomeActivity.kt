package com.github.chizoba.starwarz.ui.home

import android.os.Bundle
import com.github.chizoba.starwarz.R
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
