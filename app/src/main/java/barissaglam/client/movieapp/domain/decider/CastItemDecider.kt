package barissaglam.client.movieapp.domain.decider

import barissaglam.client.movieapp.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastItemDecider @Inject constructor() {
    fun provideImagePath(path: String?): String? = "${Constants.PROFILE_PATH}$path"
}