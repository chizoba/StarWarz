package com.github.chizoba.starwarz.ui.home.characters


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.*
import com.github.chizoba.starwarz.ui.details.DetailsActivity
import com.github.chizoba.starwarz.ui.details.FROM_KEY
import com.github.chizoba.starwarz.ui.details.ID
import com.github.chizoba.starwarz.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.characters_fragment.*
import javax.inject.Inject

const val CHARACTER_SCREEN = "CHARACTER_SCREEN"

class CharactersFragment : DaggerFragment(), OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val charactersAdapter by lazy { CharacterRecyclerViewAdapter(this) }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mQuery: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)[HomeViewModel::class.java]

        initViews()

        initObservers()
    }

    private fun initViews() {
        characterRecyclerView.let {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.layoutManager = layoutManager
            it.adapter = charactersAdapter
            it.setHasFixedSize(true)
            it.addOnScrollListener(object : InfiniteScrollListener(layoutManager, homeViewModel.loading) {
                override fun onLoadMore() {
                    homeViewModel.searchCharacters(mQuery)
                }
            })
        }

        searchView.setIconifiedByDefault(false)
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progressBar.visible()
                    emptyView.gone()
                    characterRecyclerView.gone()
                    mQuery = query
                    charactersAdapter.resetList()
                    homeViewModel.resetPage()
                    homeViewModel.searchCharacters(query)
                }
                return false
            }

        })
    }

    private fun initObservers() {
        homeViewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility(it)
        })

        homeViewModel.characters.observe(viewLifecycleOwner, Observer {
            progressBar.gone()
            if (it.isNullOrEmpty()) {
                characterRecyclerView.gone()
                emptyView.visible()
                return@Observer
            }
            characterRecyclerView.visible()
            emptyView.gone()
            charactersAdapter.submitList(it)
        })
    }

    override fun onItemClick(id: Long) {
        startActivity(
            Intent(requireActivity(), DetailsActivity::class.java)
                .putExtra(ID, id).putExtra(FROM_KEY, CHARACTER_SCREEN)
        )
    }
}
