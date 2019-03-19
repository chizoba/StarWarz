package com.github.chizoba.starwarz.ui.home.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.HOME_MOVIES
import com.github.chizoba.starwarz.ui.InfiniteScrollListener
import com.github.chizoba.starwarz.ui.MoviesRecyclerViewAdapter
import com.github.chizoba.starwarz.ui.OnItemClickListener
import com.github.chizoba.starwarz.ui.details.DetailsActivity
import com.github.chizoba.starwarz.ui.details.FROM_KEY
import com.github.chizoba.starwarz.ui.details.ID
import com.github.chizoba.starwarz.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.movies_fragment.*
import javax.inject.Inject

const val MOVIE_SCREEN = "MOVIE_SCREEN"

class FilmsFragment : DaggerFragment(), OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var homeViewModel: HomeViewModel
    private val moviesAdapter by lazy {
        MoviesRecyclerViewAdapter(
            HOME_MOVIES,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)[HomeViewModel::class.java]

        initViews()

        initObservers()
    }

    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.swipeRefreshing.postValue(true)
            homeViewModel.loadMovies()
        }

        recyclerView.let {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.layoutManager = layoutManager
            it.adapter = moviesAdapter
            it.setHasFixedSize(true)
            it.addOnScrollListener(object : InfiniteScrollListener(layoutManager, homeViewModel.swipeRefreshing) {
                override fun onLoadMore() {
                    homeViewModel.loadMovies()
                }
            })
        }
    }

    private fun initObservers() {
        homeViewModel.movies.observe(viewLifecycleOwner, Observer {
            moviesAdapter.submitList(it)
        })

        homeViewModel.swipeRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun onItemClick(id: Long) {
        startActivity(
            Intent(requireActivity(), DetailsActivity::class.java)
                .putExtra(ID, id).putExtra(FROM_KEY, MOVIE_SCREEN)
        )
    }
}
