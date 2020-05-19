package barissaglam.client.movieapp

import barissaglam.client.movieapp.domain.usecase.MovieCreditUseCase
import barissaglam.client.movieapp.domain.usecase.MovieDetailUseCase
import barissaglam.client.movieapp.domain.usecase.SearchMovieUseCase
import barissaglam.client.movieapp.usecase.MoviesListUseCaseTest
import barissaglam.client.movieapp.viewmodel.MovieDetailViewModelTest
import barissaglam.client.movieapp.viewmodel.MoviesViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    MoviesViewModelTest::class,
    MovieDetailViewModelTest::class,
    MoviesListUseCaseTest::class,
    MovieCreditUseCase::class,
    MovieDetailUseCase::class,
    SearchMovieUseCase::class
)
class TestSuite
