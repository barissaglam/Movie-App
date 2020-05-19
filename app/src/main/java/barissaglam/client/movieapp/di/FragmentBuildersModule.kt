package barissaglam.client.movieapp.di

import barissaglam.client.movieapp.presentation.detail.MovieDetailFragment
import barissaglam.client.movieapp.presentation.list.MovieListFragment
import barissaglam.client.movieapp.presentation.movies.MoviesFragment
import barissaglam.client.movieapp.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment
}
