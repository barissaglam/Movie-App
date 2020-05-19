package barissaglam.client.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.base.domain.BaseViewViewState
import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.domain.usecase.MovieCreditUseCase
import barissaglam.client.movieapp.domain.usecase.MovieDetailUseCase
import barissaglam.client.movieapp.domain.usecase.MovieListUseCase
import barissaglam.client.movieapp.presentation.detail.MovieDetailFragmentViewState
import barissaglam.client.movieapp.presentation.detail.MovieDetailViewModel
import barissaglam.client.movieapp.util.TestUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieDetailViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var movieListUseCase: MovieListUseCase

    @MockK
    lateinit var creditUseCase: MovieCreditUseCase

    @MockK
    lateinit var movieDetailUseCase: MovieDetailUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieDetailViewModel = MovieDetailViewModel(movieListUseCase, creditUseCase, movieDetailUseCase)
        //  movieDetailViewModel.movieId = 7
    }


    @Test
    fun `given request status is loading when getMovies called then showLoading in base live data`() {
        // Given
        val viewStateObserver: Observer<BaseViewViewState> = mockk(relaxUnitFun = true)
        movieDetailViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        every { movieListUseCase.getMovies(any()) } returns Observable.just(TestUtil.getEmptyMovieListViewItem())
        every { movieDetailUseCase.getMovieDetail(any()) } returns Observable.just(TestUtil.getEmptyMovieDetailViewItem(movieId = movieDetailViewModel.movieId))
        every { creditUseCase.getMovieCredits(any()) } returns Observable.just(emptyList())

        // When
        movieDetailViewModel.getMovieDetail()

        // Then
        verify { movieListUseCase.getMovies(any()) }
        verify { movieDetailUseCase.getMovieDetail(any()) }
        verify { creditUseCase.getMovieCredits(any()) }

        verify { viewStateObserver.onChanged(BaseViewViewState(showLoading = true)) }
    }

    @Test
    fun `given request status is error when getMovies called then showError in base live data  is true and throwable is equals to error throwable`() {
        // Given
        val viewStateObserver: Observer<BaseViewViewState> = mockk(relaxUnitFun = true)
        movieDetailViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        val throwable = Throwable("test error message")
        every { movieListUseCase.getMovies(any()) } returns Observable.error(throwable)
        every { movieDetailUseCase.getMovieDetail(any()) } returns Observable.error(throwable)
        every { creditUseCase.getMovieCredits(any()) } returns Observable.error(throwable)

        // When
        movieDetailViewModel.getMovieDetail()

        // Then
        verify { movieListUseCase.getMovies(movieType = MovieType.SIMILAR, movieId = movieDetailViewModel.movieId) }
        verify { movieListUseCase.getMovies(movieType = MovieType.RECOMMENDATION, movieId = movieDetailViewModel.movieId) }
        verify { movieDetailUseCase.getMovieDetail(movieDetailViewModel.movieId) }
        verify { creditUseCase.getMovieCredits(movieDetailViewModel.movieId) }

        // Then
        val baseViewStateSlots = mutableListOf<BaseViewViewState>()
        verify { viewStateObserver.onChanged(capture(baseViewStateSlots)) }

        // Then
        val loadingState = baseViewStateSlots[0]
        assertThat(loadingState.showLoading).isEqualTo(true)

        // Then
        val errorState = baseViewStateSlots[1]
        assertThat(errorState.showError).isEqualTo(true)
        assertThat(errorState.throwable).isEqualTo(throwable)
    }

    @Test
    fun `given request status is success when getMovies called then showContent in base live data  is true`() {
        // Given
        val viewStateObserver: Observer<BaseViewViewState> = mockk(relaxUnitFun = true)
        movieDetailViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        every { movieListUseCase.getMovies(any()) } returns Observable.just(TestUtil.getEmptyMovieListViewItem())
        every { movieDetailUseCase.getMovieDetail(any()) } returns Observable.just(TestUtil.getEmptyMovieDetailViewItem(movieId = movieDetailViewModel.movieId))
        every { creditUseCase.getMovieCredits(any()) } returns Observable.just(TestUtil.getMockCreditList())

        // When
        movieDetailViewModel.getMovieDetail()

        // Then
        verify { movieListUseCase.getMovies(movieType = MovieType.SIMILAR, movieId = movieDetailViewModel.movieId) }
        verify { movieListUseCase.getMovies(movieType = MovieType.RECOMMENDATION, movieId = movieDetailViewModel.movieId) }
        verify { movieDetailUseCase.getMovieDetail(movieDetailViewModel.movieId) }
        verify { creditUseCase.getMovieCredits(movieDetailViewModel.movieId) }

        val baseViewStateSlots = mutableListOf<BaseViewViewState>()
        verify { viewStateObserver.onChanged(capture(baseViewStateSlots)) }

        // Then
        val loadingState = baseViewStateSlots[0]
        assertThat(loadingState.showLoading).isEqualTo(true)

        // Then
        val successState = baseViewStateSlots[1]
        assertThat(successState.showContent).isEqualTo(true)

    }

    @Test
    fun `given request status is success when getMovies called then view state class is filled`() {
        val movieDetailFragmentViewStateObserver: Observer<MovieDetailFragmentViewState> = mockk(relaxUnitFun = true)
        movieDetailViewModel.liveDataViewState_.observeForever(movieDetailFragmentViewStateObserver)


        every { movieListUseCase.getMovies(movieType = MovieType.SIMILAR) } returns Observable.just(TestUtil.getSimilarMovieList())
        every { movieListUseCase.getMovies(movieType = MovieType.RECOMMENDATION) } returns Observable.just(TestUtil.getRecommendationMovieList())
        every { movieDetailUseCase.getMovieDetail(any()) } returns Observable.just(TestUtil.getEmptyMovieDetailViewItem(movieId = movieDetailViewModel.movieId))
        every { creditUseCase.getMovieCredits(any()) } returns Observable.just(TestUtil.getMockCreditList())

        // When
        movieDetailViewModel.getMovieDetail()

        // Then
        verify { movieListUseCase.getMovies(movieType = MovieType.SIMILAR, movieId = movieDetailViewModel.movieId) }
        verify { movieListUseCase.getMovies(movieType = MovieType.RECOMMENDATION, movieId = movieDetailViewModel.movieId) }
        verify { movieDetailUseCase.getMovieDetail(movieDetailViewModel.movieId) }
        verify { creditUseCase.getMovieCredits(movieDetailViewModel.movieId) }


        val fragmentViewStateSlot = slot<MovieDetailFragmentViewState>()
        verify { movieDetailFragmentViewStateObserver.onChanged(capture(fragmentViewStateSlot)) }

        assertThat(fragmentViewStateSlot.captured.getRecommendationMovies()).isEqualTo(TestUtil.getRecommendationMovieList())
        assertThat(fragmentViewStateSlot.captured.getRecommendationMovies().movies.first().title).isEqualTo("recommendationTitle")
        assertThat(fragmentViewStateSlot.captured.getRecommendationMoviesViewPagerTitle()).isEqualTo("Recommendation Movies")

        assertThat(fragmentViewStateSlot.captured.getSimilarMovies()).isEqualTo(TestUtil.getSimilarMovieList())
        assertThat(fragmentViewStateSlot.captured.getSimilarMovies().movies.first().title).isEqualTo("similarTitle")
        assertThat(fragmentViewStateSlot.captured.getSimilarMoviesViewPagerTitle()).isEqualTo("Similar Movies")

        assertThat(fragmentViewStateSlot.captured.getMovieDetailItems().title).isEqualTo("test title")
        assertThat(fragmentViewStateSlot.captured.getMovieDetailItems().id).isEqualTo(movieDetailViewModel.movieId)


    }
}