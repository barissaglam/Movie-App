package barissaglam.client.movieapp.util

import androidx.paging.PagedList
import barissaglam.client.movieapp.data.model.Cast
import barissaglam.client.movieapp.data.model.Movie
import barissaglam.client.movieapp.data.model.response.CastResponse
import barissaglam.client.movieapp.data.model.response.MovieDetailReponse
import barissaglam.client.movieapp.data.model.response.MovieResponse
import barissaglam.client.movieapp.data.model.uimodel.CastViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieDetailViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieListViewItem
import barissaglam.client.movieapp.data.model.uimodel.MovieViewItem
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*

object TestUtil {

    fun getEmptyMovieListViewItem(type: String = ""): MovieListViewItem {
        return MovieListViewItem(
            page = 0, totalPage = 0, movies = arrayListOf(
                MovieViewItem(
                    0, "imagePath", type
                )
            )
        )
    }

    fun getEmptyMovieDetailViewItem(movieId: Int): MovieDetailViewItem {
        return MovieDetailViewItem(
            movieId,
            "test backdropPath",
            emptyList(),
            "test title",
            "test overview",
            0.toDouble(),
            "test imagePath",
            "test release data",
            "test runtime",
            "test status",
            0.toFloat()
        )
    }

    fun getMockCreditList(): List<CastViewItem> {
        return arrayListOf(
            CastViewItem("test name 1", "test character 1", "test profile path 1"),
            CastViewItem("test name 2", "test character 2", "test profile path 2"),
            CastViewItem("test name 3", "test character 3", "test profile path 3")
        )
    }

    fun getPopularMovieList() = MovieListViewItem(page = 0, totalPage = 0, movies = listOf(MovieViewItem(1, "popularPath", "popularTitle")))
    fun getUpcomingMovieList() = MovieListViewItem(page = 0, totalPage = 0, movies = listOf(MovieViewItem(1, "upcomingPath", "upcomingTitle")))
    fun getNowPlayingMovieList() = MovieListViewItem(page = 0, totalPage = 0, movies = listOf(MovieViewItem(1, "nowPlayingPath", "nowPlayingTitle")))
    fun getRecommendationMovieList() = MovieListViewItem(page = 0, totalPage = 0, movies = listOf(MovieViewItem(1, "recommendationPath", "recommendationTitle")))
    fun getSimilarMovieList() = MovieListViewItem(page = 0, totalPage = 0, movies = listOf(MovieViewItem(1, "similarPath", "similarTitle")))


    /*
    Response Data
     */

    fun getMockedMovieResponse(title: String = "", page: Int = 1): MovieResponse {
        return MovieResponse(
            page, 100, arrayListOf(
                Movie(
                    0.toDouble(),
                    0.toDouble(),
                    false,
                    "",
                    10,
                    false,
                    "",
                    "",
                    "",
                    ArrayList<Int>(),
                    title,
                    0.toDouble(),
                    "$title overview",
                    ""
                )
            )
        )
    }

    fun getMockedCastResponse(title: String = ""): CastResponse {
        return CastResponse(
            arrayListOf(
                Cast(
                    0,
                    "test character",
                    0,
                    "test name",
                    "test credit id",
                    "test profile path",
                    0,
                    0
                ),
                Cast(
                    1,
                    "test character 1",
                    1,
                    "test name 1",
                    "test credit id 1",
                    "test profile path 1",
                    1,
                    1
                )
            ), 0
        )
    }

    fun getMockedMovieDetailResponse(title: String = ""): MovieDetailReponse {
        return MovieDetailReponse(
            "testOriginalLanguage",
            "testImdbId",
            false,
            title,
            "testBackdropPath",
            0,
            emptyList(),
            0.0,
            emptyList(),
            0,
            0,
            0,
            "testOverView",
            "testOriginalTitle",
            0,
            "testPosterPath",
            emptyList(),
            emptyList(),
            "testReleaseDate",
            0.0,
            "testTagLine",
            false,
            "testHomePage",
            "testStatus"
        )
    }
}

fun <T> mockPagedList(list: List<T>): PagedList<T> {
    val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
    Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
        val index = invocation.arguments.first() as Int
        list[index]
    }
    Mockito.`when`(pagedList.size).thenReturn(list.size)
    return pagedList
}