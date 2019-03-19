package com.github.chizoba.starwarz.ui.details.characters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.domain.models.SpecieResponse
import com.github.chizoba.starwarz.ui.*
import com.github.chizoba.starwarz.ui.details.DetailsViewModel
import com.github.chizoba.starwarz.ui.home.characters.Character
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.character_details_fragment.*
import javax.inject.Inject

class CharacterDetailsFragment : DaggerFragment(), OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailsViewModel
    private val moviesAdapter by lazy {
        MoviesRecyclerViewAdapter(
            CHARACTER_MOVIES,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailViewModel = ViewModelProviders
            .of(requireActivity(), viewModelFactory)[DetailsViewModel::class.java]

        moviesRecyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = moviesAdapter
        }

        initObservers()

        if (detailViewModel.character.value == null) {
            detailViewModel.getCharacter()
        }
    }

    private fun initObservers() {
        detailViewModel.loading.observe(viewLifecycleOwner, EventObserver {
            cdProgressBar.visibility(it)
        })

        detailViewModel.character.observe(viewLifecycleOwner, Observer {
            makeCharacterViewsVisible()
            setCharacterDataToViews(it)
        })

        detailViewModel.specie.observe(viewLifecycleOwner, Observer {
            makeSpecieViewsVisible()
            setSpecieData(it)
        })

        detailViewModel.population.observe(viewLifecycleOwner, Observer { population ->
            populationView.visible()
            populationView.text = population
        })

        detailViewModel.movies.observe(viewLifecycleOwner, Observer {
            moviesAdapter.submitList(it)
        })

        detailViewModel.loadingMovies.observe(viewLifecycleOwner, EventObserver {
            moviesProgressBar.visibility(it)
        })

        detailViewModel.loadingSpecie.observe(viewLifecycleOwner, EventObserver {
            specieProgressBar.visibility(it)
        })

        detailViewModel.loadingPlanet.observe(viewLifecycleOwner, EventObserver {
            planetProgressBar.visibility(it)
        })

        detailViewModel.errorMessage.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun makeCharacterViewsVisible() {
        nameView.visible()
        birthYearView.visible()
        heightView.visible()
    }

    private fun setCharacterDataToViews(character: Character) {
        nameView.text = getString(R.string.character_name, character.name)
        birthYearView.text = getString(R.string.birth_year, character.birthYear)
        heightView.text = getString(R.string.height, character.height)
    }

    private fun makeSpecieViewsVisible() {
        specieNameView.visible()
        speciesLanguageView.visible()
        speciesHomeworldView.visible()
    }

    private fun setSpecieData(it: SpecieResponse) {
        specieNameView.text = getString(R.string.specie_name, it.name)
        speciesLanguageView.text = getString(R.string.specie_language, it.language)
        speciesHomeworldView.text = getString(R.string.specie_homeworld, it.homeworld)
    }

    override fun onItemClick(id: Long) {
        detailViewModel.idExtra = id
        detailViewModel.navigate(MovieDetails)
    }
}
