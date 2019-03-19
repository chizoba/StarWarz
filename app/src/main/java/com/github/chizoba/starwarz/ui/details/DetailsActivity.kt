package com.github.chizoba.starwarz.ui.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.CharacterDetails
import com.github.chizoba.starwarz.ui.EventObserver
import com.github.chizoba.starwarz.ui.MovieDetails
import com.github.chizoba.starwarz.ui.details.characters.CharacterDetailsFragment
import com.github.chizoba.starwarz.ui.details.movies.MovieDetailsFragment
import com.github.chizoba.starwarz.ui.home.characters.CHARACTER_SCREEN
import com.github.chizoba.starwarz.ui.home.movies.MOVIE_SCREEN
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

const val ID = "ID"
const val FROM_KEY = "FROM"

class DetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        val detailViewModel = ViewModelProviders
            .of(this, viewModelFactory)[DetailsViewModel::class.java]

        detailViewModel.navigation.observe(this, EventObserver { destination ->
            when (destination) {
                MovieDetails -> replaceFragment(MovieDetailsFragment())
                CharacterDetails -> replaceFragment(CharacterDetailsFragment())
            }
        })

        resolveIntent(detailViewModel)
    }

    private fun resolveIntent(detailViewModel: DetailsViewModel) {
        detailViewModel.idExtra = intent.getLongExtra(ID, -1)

        when (intent.getStringExtra(FROM_KEY)) {
            MOVIE_SCREEN -> detailViewModel.navigate(MovieDetails)
            CHARACTER_SCREEN -> detailViewModel.navigate(CharacterDetails)
        }
    }

    private fun replaceFragment(fragment: DaggerFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.detailContainer, fragment)
            .addToBackStack("Fragment")
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            super.onBackPressed()
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}