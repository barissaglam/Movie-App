package barissaglam.client.movieapp.di

import barissaglam.client.movieapp.presentation.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindHomeActivity(): HomeActivity
}