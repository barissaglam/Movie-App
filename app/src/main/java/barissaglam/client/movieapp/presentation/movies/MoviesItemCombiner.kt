package barissaglam.client.movieapp.presentation.movies

import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem

class MoviesItemCombiner : (MovieListViewItem, MovieListViewItem, MovieListViewItem) -> MoviesFragmentViewState {
    override fun invoke(p1: MovieListViewItem, p2: MovieListViewItem, p3: MovieListViewItem): MoviesFragmentViewState {
        return MoviesFragmentViewState(popularMovies = p1, nowPlayingMovies = p2, upComingMovies = p3)
    }
}