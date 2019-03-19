package com.github.chizoba.starwarz.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.home.characters.CharactersFragment
import com.github.chizoba.starwarz.ui.home.movies.FilmsFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    companion object {
        private const val NUM_PAGES = 2
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FilmsFragment()
                else -> CharactersFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> getString(R.string.movies)
                else -> getString(R.string.characters)
            }
        }
    }
}
