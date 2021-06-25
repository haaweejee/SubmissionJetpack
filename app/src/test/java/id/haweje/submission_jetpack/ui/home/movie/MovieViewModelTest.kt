package id.haweje.submission_jetpack.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.vo.Resource
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository : MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<Movie>>>

    private lateinit var viewModel: MovieViewModel


    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)

    }

    @Test
    fun getMovieList() {
        val dummyMovies = Resource.success(DataDummy.getMovieList())
        val movies = MutableLiveData<Resource<List<Movie>>>()
        movies.value = dummyMovies

        `when`(repository.getMovieList()).thenReturn(movies)
        val moviesData = viewModel.getMovieData().value?.data
        verify(repository).getMovieList()
        assertNotNull(moviesData)

        viewModel.getMovieData().observeForever(observer)
        verify(observer).onChanged(dummyMovies)

    }
}