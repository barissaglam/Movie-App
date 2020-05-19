package barissaglam.client.movieapp.domain.usecase

import barissaglam.client.movieapp.data.model.uimodel.MovieDetailViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.mapper.MovieDetailItemMapper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val itemMapper: MovieDetailItemMapper
) {
    fun getMovieDetail(movieId: Int): Observable<MovieDetailViewItem> {
        return repository.getMovieDetail(movieId = movieId).map { itemMapper.mapFrom(it) }
    }
}



