package barissaglam.client.movieapp.domain.mapper

import barissaglam.client.movieapp.data.model.response.CastResponse
import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.domain.decider.CastItemDecider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastItemMapper @Inject constructor(private val itemDecider: CastItemDecider) : Mapper<CastResponse, List<CastViewItem>?> {
    override fun mapFrom(item: CastResponse): List<CastViewItem>? {
        return item.cast?.map { cast ->
            CastViewItem(
                name = cast.name.orEmpty(),
                character = cast.character.orEmpty(),
                profilePath = itemDecider.provideImagePath(cast.profilePath).orEmpty()
            )
        }
    }
}