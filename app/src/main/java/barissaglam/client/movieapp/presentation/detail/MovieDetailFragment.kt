package barissaglam.client.movieapp.presentation.detail

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import barissaglam.client.movieapp.R
import barissaglam.client.movieapp.base.view.BaseDialogFragment
import barissaglam.client.movieapp.data.model.Genre
import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieViewItem
import barissaglam.client.movieapp.databinding.FragmentMovieDetailBinding
import barissaglam.client.movieapp.databinding.ItemCastBinding
import barissaglam.client.movieapp.presentation.movies.adapter.MoviePagerAdapter
import com.google.android.material.chip.Chip


class MovieDetailFragment : BaseDialogFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_movie_detail
    override val classTypeOfViewModel: Class<MovieDetailViewModel> = MovieDetailViewModel::class.java

    private val similarMoviesAdapter by lazy { MoviePagerAdapter(requireContext(), MoviePagerAdapter.ITEM_TYPE.SMALL) }
    private val recommendationMoviesAdapter by lazy { MoviePagerAdapter(requireContext(), MoviePagerAdapter.ITEM_TYPE.SMALL) }

    override fun init() {
        baseView = binding.baseView
    }

    override fun initStartRequest() {
        viewModel.getMovieDetail()
    }

    override fun setUpViewModelStateObservers() {
        viewModel.liveDataViewState_.observe(viewLifecycleOwner) {
            setViewState(it)
        }
    }

    override fun setupClickListeners() {
        similarMoviesAdapter.onMovieItemClick = ::onMovieItemClick
        recommendationMoviesAdapter.onMovieItemClick = ::onMovieItemClick
    }

    private fun onMovieItemClick(movieItem: MovieViewItem) {
        viewModel.movieId = movieItem.id
        initStartRequest()
    }


    private fun setViewState(fragmentViewState: MovieDetailFragmentViewState) {
        binding.viewState = fragmentViewState
        binding.executePendingBindings()

        setupSimilarMoviesViewPager(fragmentViewState.getSimilarMovies().movies)
        setupRecommendationMoviesViewPager(fragmentViewState.getRecommendationMovies().movies)
        setupGenresChipGroup(fragmentViewState.getMovieDetailItems().genres)
        setupCastLayout(fragmentViewState.getCasts())
    }

    private fun setupGenresChipGroup(genres: List<Genre>) {
        binding.chipGroupGenres.apply {
            for (genre in genres) {
                val chip = Chip(context)
                chip.text = genre.name
                addView(chip)
            }
        }
    }

    private fun setupSimilarMoviesViewPager(movies: List<MovieViewItem>) {
        this.similarMoviesAdapter.setItem(movies)
        binding.layoutSimilarMovies.viewPager.adapter = similarMoviesAdapter
    }

    private fun setupRecommendationMoviesViewPager(movies: List<MovieViewItem>) {
        this.recommendationMoviesAdapter.setItem(movies)
        binding.layoutRecommendationMovies.viewPager.adapter = recommendationMoviesAdapter
    }

    private fun setupCastLayout(casts: List<CastViewItem>) {
        binding.linearLayoutCastContent.removeAllViews()
        for (cast in casts) {
            val castItem = DataBindingUtil.inflate<ItemCastBinding>(LayoutInflater.from(requireContext()), R.layout.item_cast, null, false)
            castItem.cast = cast
            binding.linearLayoutCastContent.addView(castItem.root)
        }
    }

}