package barissaglam.client.movieapp.domain.usecase

import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.data.enum.MovieType.*
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.mapper.MovieItemMapper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieListUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val itemMapper: MovieItemMapper
) {
    fun getMovies(movieType: MovieType, page: Int = 1, movieId: Int = 0): Observable<MovieListViewItem> {
        return when (movieType) {
            NOW_PLAYING -> repository.getNowPlayingMovies(page).map { itemMapper.mapFrom(it) }
            POPULAR -> repository.getPopularMovies(page).map { itemMapper.mapFrom(it) }
            RECOMMENDATION -> repository.getRecommendationMovies(movieId).map { itemMapper.mapFrom(it) }
            SIMILAR -> repository.getSimilarMovies(movieId).map { itemMapper.mapFrom(it) }
            UPCOMING -> repository.getUpComingMovies(page).map { itemMapper.mapFrom(it) }
        }
    }
}