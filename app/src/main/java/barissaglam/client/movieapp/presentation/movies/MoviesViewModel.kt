package barissaglam.client.movieapp.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import barissaglam.client.movieapp.base.viewmodel.BaseViewModel
import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.domain.usecase.MovieListUseCase
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : BaseViewModel() {
    /** LiveData for ViewState **/
    private val liveDataViewState = MutableLiveData<MoviesFragmentViewState>()
    val liveDataViewState_: LiveData<MoviesFragmentViewState> = liveDataViewState

    fun getMovies() {
        return Observables.zip(
            movieListUseCase.getMovies(MovieType.POPULAR),
            movieListUseCase.getMovies(MovieType.NOW_PLAYING),
            movieListUseCase.getMovies(MovieType.UPCOMING),
            MoviesItemCombiner()
        ).sendRequest {
            liveDataViewState.value = it
        }

    }
}