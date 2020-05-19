package barissaglam.client.movieapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.data.enum.MovieType
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.decider.MovieItemDecider
import barissaglam.client.movieapp.domain.mapper.MovieItemMapper
import barissaglam.client.movieapp.domain.usecase.MovieListUseCase
import barissaglam.client.movieapp.util.Constants
import barissaglam.client.movieapp.util.TestUtil
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesListUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var remoteRepository: RemoteRepository

    @InjectMockKs
    lateinit var movieItemDecider: MovieItemDecider

    @InjectMockKs
    lateinit var movieItemMapper: MovieItemMapper

    private lateinit var useCase: MovieListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = MovieListUseCase(remoteRepository, movieItemMapper)


    }

    @Test
    fun `given movie type is NOW_PLAYING when getMovies called then return observable data`() {
        // GIVEN
        val mockedMovieResponse = TestUtil.getMockedMovieResponse("NOW PLAYING")
        every { remoteRepository.getNowPlayingMovies(1) } returns Observable.just(mockedMovieResponse)

        val testObserver = TestObserver<MovieListViewItem>()

        // WHEN
        val result = useCase.getMovies(MovieType.NOW_PLAYING)


        // THEN
        result.subscribe(testObserver)
        val listResult = testObserver.values().first()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Truth.assertThat(listResult.movies.first().title).isEqualTo("NOW PLAYING")
        Truth.assertThat(listResult.movies.first().imagePath).isEqualTo("${Constants.PROFILE_PATH}${mockedMovieResponse.results?.first()?.backdropPath}")

    }
}