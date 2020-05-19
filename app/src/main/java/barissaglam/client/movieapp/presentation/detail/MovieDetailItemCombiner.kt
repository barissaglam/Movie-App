package barissaglam.client.movieapp.presentation.detail

import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieDetailViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem

class MovieDetailItemCombiner : (MovieDetailViewItem, MovieListViewItem, List<CastViewItem>, MovieListViewItem) -> MovieDetailFragmentViewState {
    override fun invoke(p1: MovieDetailViewItem, p2: MovieListViewItem, p3: List<CastViewItem>, p4: MovieListViewItem): MovieDetailFragmentViewState {
        return MovieDetailFragmentViewState(movieDetailViewItem = p1, similarMovies = p2, casts = p3, recommendationMovies = p4)
    }
}