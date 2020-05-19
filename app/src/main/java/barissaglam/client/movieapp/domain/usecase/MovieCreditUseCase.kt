package barissaglam.client.movieapp.domain.usecase

import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.mapper.CastItemMapper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieCreditUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val castItemMapper: CastItemMapper
) {
    fun getMovieCredits(movieId: Int): Observable<List<CastViewItem>> {
        return repository.getMovieCredits(movieId).map {
            castItemMapper.mapFrom(it).orEmpty()
        }
    }
}

