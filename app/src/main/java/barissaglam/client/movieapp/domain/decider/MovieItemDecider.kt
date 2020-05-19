package barissaglam.client.movieapp.domain.decider

import barissaglam.client.movieapp.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieItemDecider @Inject constructor() {
    fun provideImagePath(path: String?): String? = "${Constants.POSTER_PATH}$path"
}