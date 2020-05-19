package barissaglam.client.movieapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.decider.MovieItemDecider
import barissaglam.client.movieapp.domain.mapper.MovieItemMapper
import barissaglam.client.movieapp.domain.usecase.SearchMovieUseCase
import barissaglam.client.movieapp.util.Constants
import barissaglam.client.movieapp.util.TestUtil
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SearchMovieUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var remoteRepository: RemoteRepository

    @InjectMockKs
    lateinit var movieItemMapper: MovieItemMapper

    @InjectMockKs
    lateinit var movieItemDecider: MovieItemDecider

    private lateinit var useCase: SearchMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SearchMovieUseCase(remoteRepository, movieItemMapper)
    }

    @Test
    fun `when searchMovie called then return observable data`() {
        // GIVEN
        val page = 10
        val query = "query"
        val mockedMovieResponse = TestUtil.getMockedMovieResponse("searched", 10)

        every { remoteRepository.searchMovie(query, page) } returns Observable.just(mockedMovieResponse)

        val testObserver = TestObserver<MovieListViewItem>()

        // WHEN
        val result = useCase.searchMovie(query, page)
        result.subscribe(testObserver)
        val listResult = testObserver.values().first()

        // THEN
        verify { remoteRepository.searchMovie(query, page) }
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Truth.assertThat(listResult.movies.first().title).isEqualTo("searched")
        Truth.assertThat(listResult.movies.first().imagePath).isEqualTo("${Constants.PROFILE_PATH}${mockedMovieResponse.results?.first()?.backdropPath}")
        Truth.assertThat(listResult.page).isEqualTo(mockedMovieResponse.page)
        Truth.assertThat(listResult.totalPage).isEqualTo(mockedMovieResponse.totalPages)

    }
}