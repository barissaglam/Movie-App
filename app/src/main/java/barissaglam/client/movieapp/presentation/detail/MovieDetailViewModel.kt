package barissaglam.client.movieapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import barissaglam.client.movieapp.base.viewmodel.BaseViewModel
import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.domain.usecase.MovieCreditUseCase
import barissaglam.client.movieapp.domain.usecase.MovieDetailUseCase
import barissaglam.client.movieapp.domain.usecase.MovieListUseCase
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val creditUseCase: MovieCreditUseCase,
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {
    /** LiveData for ViewState **/
    private val liveDataViewState = MutableLiveData<MovieDetailFragmentViewState>()
    val liveDataViewState_: LiveData<MovieDetailFragmentViewState> = liveDataViewState

    var movieId = 0

    override fun handleIntent(extras: Bundle) {
        val args = MovieDetailFragmentArgs.fromBundle(extras)
        this.movieId = args.movieId
    }

    fun getMovieDetail() {
        return Observables.zip(
            movieDetailUseCase.getMovieDetail(movieId = movieId),
            movieListUseCase.getMovies(movieType = MovieType.SIMILAR, movieId = movieId),
            creditUseCase.getMovieCredits(movieId = movieId),
            movieListUseCase.getMovies(movieType = MovieType.RECOMMENDATION, movieId = movieId),
            MovieDetailItemCombiner()
        ).sendRequest {
            liveDataViewState.value = it
        }
    }
}