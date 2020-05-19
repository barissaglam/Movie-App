package barissaglam.client.movieapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.data.model.uimodel.MovieDetailViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.decider.MovieDetailItemDecider
import barissaglam.client.movieapp.domain.mapper.MovieDetailItemMapper
import barissaglam.client.movieapp.domain.usecase.MovieDetailUseCase
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

class MovieDetailUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var remoteRepository: RemoteRepository

    @InjectMockKs
    lateinit var movieDetailItemMapper: MovieDetailItemMapper

    @InjectMockKs
    lateinit var movieDetailItemDecider: MovieDetailItemDecider

    private lateinit var useCase: MovieDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = MovieDetailUseCase(remoteRepository, movieDetailItemMapper)
    }

    @Test
    fun `when getMovieDetail called then return observable data`() {
        // GIVEN
        val mockedCastResponse = TestUtil.getMockedMovieDetailResponse("batman")
        every { remoteRepository.getMovieDetail(any()) } returns Observable.just(mockedCastResponse)

        val testObserver = TestObserver<MovieDetailViewItem>()

        // WHEN
        val result = useCase.getMovieDetail(5)
        result.subscribe(testObserver)
        val listResult = testObserver.values().first()

        // THEN
        verify { remoteRepository.getMovieDetail(any()) }
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Truth.assertThat(listResult.title).isEqualTo("batman")
        Truth.assertThat(listResult.backdropPath).isEqualTo("${Constants.BACKDROP_PATH}${mockedCastResponse.backdropPath}")
        Truth.assertThat(listResult.imagePath).isEqualTo("${Constants.POSTER_PATH}${mockedCastResponse.posterPath}")


    }
}