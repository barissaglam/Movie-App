package barissaglam.client.movieapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import barissaglam.client.movieapp.RxImmediateSchedulerRule
import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.data.repository.RemoteRepository
import barissaglam.client.movieapp.domain.decider.CastItemDecider
import barissaglam.client.movieapp.domain.mapper.CastItemMapper
import barissaglam.client.movieapp.domain.usecase.MovieCreditUseCase
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

class MovieCreditUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    lateinit var remoteRepository: RemoteRepository

    @InjectMockKs
    lateinit var castItemMapper: CastItemMapper

    @InjectMockKs
    lateinit var castItemDecider: CastItemDecider

    private lateinit var useCase: MovieCreditUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = MovieCreditUseCase(remoteRepository, castItemMapper)


    }

    @Test
    fun `when getMovieCredits called then return observable data`() {
        // GIVEN
        val mockedCastResponse = TestUtil.getMockedCastResponse()
        every { remoteRepository.getMovieCredits(1) } returns Observable.just(mockedCastResponse)

        val testObserver = TestObserver<List<CastViewItem>>()

        // WHEN
        val result = useCase.getMovieCredits(1)
        result.subscribe(testObserver)
        val listResult = testObserver.values().first()

        // THEN
        verify { remoteRepository.getMovieCredits(1) }
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Truth.assertThat(listResult.first().character).isEqualTo(mockedCastResponse.cast?.first()?.character)
        Truth.assertThat(listResult.first().name).isEqualTo(mockedCastResponse.cast?.first()?.name)
        Truth.assertThat(listResult.first().profilePath).isEqualTo("${Constants.PROFILE_PATH}${mockedCastResponse.cast?.first()?.profilePath}")
        Truth.assertThat(listResult[1].character).isEqualTo(mockedCastResponse.cast?.get(1)?.character)
        Truth.assertThat(listResult[1].name).isEqualTo(mockedCastResponse.cast?.get(1)?.name)
        Truth.assertThat(listResult[1].profilePath).isEqualTo("${Constants.PROFILE_PATH}${mockedCastResponse.cast?.get(1)?.profilePath}")

    }
}