package barissaglam.client.movieapp.domain.mapper

import barissaglam.client.movieapp.base.extension.orZero
import barissaglam.client.movieapp.data.model.response.MovieResponse
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieViewItem
import barissaglam.client.movieapp.domain.decider.MovieItemDecider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieItemMapper @Inject constructor(private val itemDecider: MovieItemDecider) : Mapper<MovieResponse, MovieListViewItem?> {
    override fun mapFrom(item: MovieResponse): MovieListViewItem? {
        return MovieListViewItem(
            page = item.page.orZero(),
            totalPage = item.totalPages.orZero(),
            movies = item.results?.map { movie ->
                MovieViewItem(
                    id = movie.id.orZero(),
                    imagePath = itemDecider.provideImagePath(movie.posterPath).orEmpty(),
                    title = movie.title.orEmpty()
                )
            }.orEmpty()
        )
    }
}