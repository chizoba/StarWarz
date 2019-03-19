package com.github.chizoba.starwarz.ui.details.movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.EventObserver
import com.github.chizoba.starwarz.ui.details.DetailsViewModel
import com.github.chizoba.starwarz.ui.gone
import com.github.chizoba.starwarz.ui.home.movies.Movie
import com.github.chizoba.starwarz.ui.visible
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.movie_details_fragment.*
import javax.inject.Inject

class MovieDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewModel = ViewModelProviders
            .of(requireActivity(), viewModelFactory)[DetailsViewModel::class.java]

        initObservers(detailViewModel)

        detailViewModel.getMovie()
    }

    private fun initObservers(detailViewModel: DetailsViewModel) {
        detailViewModel.movie.observe(viewLifecycleOwner, Observer {
            setMovieDataToView(it)
        })

        detailViewModel.loading.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                mdProgressBar.visible()
                return@EventObserver
            }
            mdProgressBar.gone()
        })
    }

    private fun setMovieDataToView(movie: Movie) {
        titleView.text = movie.title
        releaseDateView.text = getString(R.string.release_date, movie.releaseDate)
        directorView.text = getString(R.string.director, movie.director)
        producerView.text = getString(R.string.producer, movie.producer)
        openingCrawlView.text = movie.openingCrawl
    }
}
