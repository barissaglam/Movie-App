package barissaglam.client.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.base.domain.BaseViewViewState
import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.domain.usecase.MovieListUseCase
import barissaglam.client.movieapp.presentation.movies.MoviesFragmentViewState
import barissaglam.client.movieapp.presentation.movies.MoviesViewModel
import barissaglam.client.movieapp.util.TestUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MoviesViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var moviesUseCase: MovieListUseCase

    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        moviesViewModel = MoviesViewModel(moviesUseCase)
    }


    @Test
    fun `given request status is loading when getMovies called then showLoading in base live data`() {
        // Given
        val viewStateObserver: Observer<BaseViewViewState> = mockk(relaxUnitFun = true)
        moviesViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        every { moviesUseCase.getMovies(any()) } returns Observable.just(TestUtil.getEmptyMovieListViewItem())

        // When
        moviesViewModel.getMovies()

        // Then
        verify { moviesUseCase.getMovies(any()) }

        verify { viewStateObserver.onChanged(BaseViewViewState(showLoading = true)) }
    }

    @Test
    fun `given request status is error when getMovies called then showError in base live data  is true and throwable is equals to error throwable`() {
        // Given
        val viewStateObserver: Observer<BaseViewViewState> = mockk(relaxUnitFun = true)
        moviesViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        val throwable = Throwable("test error message")
        every { moviesUseCase.getMovies(any()) } returns Observable.error(throwable)

        // When
        moviesViewModel.getMovies()

        // Then
        verify { moviesUseCase.getMovies(any()) }

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
        moviesViewModel.baseViewViewStateLiveData.observeForever(viewStateObserver)

        every { moviesUseCase.getMovies(any()) } returns Observable.just(TestUtil.getEmptyMovieListViewItem())

        // When
        moviesViewModel.getMovies()

        // Then
        verify { moviesUseCase.getMovies(any()) }

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
        val moviesFragmentViewStateObserver: Observer<MoviesFragmentViewState> = mockk(relaxUnitFun = true)
        moviesViewModel.liveDataViewState_.observeForever(moviesFragmentViewStateObserver)

        every { moviesUseCase.getMovies(MovieType.POPULAR) } returns Observable.just(TestUtil.getPopularMovieList())
        every { moviesUseCase.getMovies(MovieType.UPCOMING) } returns Observable.just(TestUtil.getUpcomingMovieList())
        every { moviesUseCase.getMovies(MovieType.NOW_PLAYING) } returns Observable.just(TestUtil.getNowPlayingMovieList())

        // When
        moviesViewModel.getMovies()

        // Then
        verify { moviesUseCase.getMovies(MovieType.POPULAR) }
        // Then
        verify { moviesUseCase.getMovies(MovieType.UPCOMING) }
        // Then
        verify { moviesUseCase.getMovies(MovieType.NOW_PLAYING) }

        val fragmentViewStateSlot = slot<MoviesFragmentViewState>()
        verify { moviesFragmentViewStateObserver.onChanged(capture(fragmentViewStateSlot)) }

        // Then
        assertThat(fragmentViewStateSlot.captured.getNowPlayingMovies().movies).isEqualTo(TestUtil.getNowPlayingMovieList().movies)
        assertThat(fragmentViewStateSlot.captured.getNowPlayingMovies().movies[0].title).isEqualTo("nowPlayingTitle")
        // Then
        assertThat(fragmentViewStateSlot.captured.getPopularMovies().movies).isEqualTo(TestUtil.getPopularMovieList().movies)
        assertThat(fragmentViewStateSlot.captured.getPopularMovies().movies[0].title).isEqualTo("popularTitle")
        // Then
        assertThat(fragmentViewStateSlot.captured.getUpComingMovies().movies).isEqualTo(TestUtil.getUpcomingMovieList().movies)
        assertThat(fragmentViewStateSlot.captured.getUpComingMovies().movies[0].title).isEqualTo("upcomingTitle")

        // Then
        assertThat(fragmentViewStateSlot.captured.getUpComingMoviesViewPagerTitle()).isEqualTo("Upcoming")
        assertThat(fragmentViewStateSlot.captured.getPopularMoviesViewPagerTitle()).isEqualTo("Popular")
    }
}