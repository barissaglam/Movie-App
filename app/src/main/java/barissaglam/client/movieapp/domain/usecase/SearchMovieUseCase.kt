package barissaglam.client.movieapp.domain.usecase

import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.mapper.MovieItemMapper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMovieUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val itemMapper: MovieItemMapper
) {
    fun searchMovie(query: String, page: Int): Observable<MovieListViewItem> {
        return repository.searchMovie(query, page).map {
            itemMapper.mapFrom(it)
        }
    }
}

