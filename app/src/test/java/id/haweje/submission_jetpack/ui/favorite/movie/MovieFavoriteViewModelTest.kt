package id.haweje.submission_jetpack.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieFavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Mock
    private var repository = mock(MovieTvRepository::class.java)

    private lateinit var viewModel: MovieFavoriteViewModel

    @Before
    fun setUp(){
        viewModel = MovieFavoriteViewModel(repository)
    }


    @Test
    fun getFavoriteMovie() {
        val dummyFavoriteMovie = pagedList
        `when`(dummyFavoriteMovie.size).thenReturn(10)
        val favoriteMovie = MutableLiveData<PagedList<Movie>>()
        favoriteMovie.value = dummyFavoriteMovie

        `when`(repository.getFavoriteMovie()).thenReturn(favoriteMovie)
        val movieEntities = viewModel.getFavoriteMovie().value
        verify(repository).getFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getFavoriteMovie().observeForever(observer)
        verify(observer).onChanged(dummyFavoriteMovie)
    }
}