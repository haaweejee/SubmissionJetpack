package id.haweje.submission_jetpack.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.ui.detail.DetailMovieViewModel
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.getMovieList()[0]
    private val movieId = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var repository = Mockito.mock(MovieTvRepository::class.java)

    @Mock
    private lateinit var observer: Observer<Resource<Movie>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(repository)
        viewModel.setMovieId(movieId)
    }

    @Test
    fun getMovieDetail() {
        val dummyDetailMovie = Resource.success(DataDummy.getMovieDetails(movieId))
        val movies = MutableLiveData<Resource<Movie>>()
        movies.value = dummyDetailMovie

        `when`(repository.getMovieDetails(movieId)).thenReturn(movies)
        viewModel.getDetailMovie.observeForever(observer)
        verify(repository).getMovieDetails(movieId)
        verify(observer).onChanged(dummyDetailMovie)
        assertNotNull(viewModel.getDetailMovie)
        assertEquals(viewModel.getDetailMovie.value?.data?.original_title, dummyMovie.original_title)
        assertEquals(viewModel.getDetailMovie.value?.data?.release_date, dummyMovie.release_date)
        assertEquals(viewModel.getDetailMovie.value?.data?.overview, dummyMovie.overview)
        assertEquals(viewModel.getDetailMovie.value?.data?.poster_path, dummyMovie.poster_path)
        assertEquals(viewModel.getDetailMovie.value?.data?.backdrop_path, dummyMovie.backdrop_path)
        assertEquals(viewModel.getDetailMovie.value?.data?.favorite, dummyMovie.favorite)
    }

    @Test
    fun setFavorite(){
        val dummyMovieFavorite = Resource.success(DataDummy.getMovieList()[0])
        val movie = MutableLiveData<Resource<Movie>>()
        movie.value = dummyMovieFavorite

        `when`(repository.getMovieDetails(movieId)).thenReturn(movie)
        viewModel.getDetailMovie.observeForever(observer)
        verify(observer).onChanged(movie.value)
        viewModel.setFavorite()
        verify(repository).setFavoriteMovie(movie.value?.data as Movie, true)
    }
}