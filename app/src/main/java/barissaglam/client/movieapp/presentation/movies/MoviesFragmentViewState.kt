package barissaglam.client.movieapp.presentation.movies


import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem

class MoviesFragmentViewState(
    private val popularMovies: MovieListViewItem,
    private val nowPlayingMovies: MovieListViewItem,
    private val upComingMovies: MovieListViewItem
) {
    fun getPopularMovies(): MovieListViewItem = popularMovies

    fun getNowPlayingMovies(): MovieListViewItem = nowPlayingMovies

    fun getUpComingMovies(): MovieListViewItem = upComingMovies

    fun getPopularMoviesViewPagerTitle() = "Popular"

    fun getUpComingMoviesViewPagerTitle() = "Upcoming"


}

