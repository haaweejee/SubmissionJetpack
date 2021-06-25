package id.haweje.submission_jetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.local.LocalDataSource
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.data.remote.RemoteDataSource
import id.haweje.submission_jetpack.utils.AppExecutors
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.utils.LiveDataTestUtil
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieTvRepository = FakeMovieTvRepository(remote, local, appExecutors)

    private val dummyMovieData = DataDummy.getMovieList()
    private val movieId = dummyMovieData[0].id
    private val dummyTvData = DataDummy.getDummyTv()
    private val tvId = dummyTvData[0].id
    private val movieDataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
    private val tvDataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Tv>


    @Test
    fun getMovieList() {
       val dummyMovie = MutableLiveData<List<Movie>>()
       dummyMovie.value = DataDummy.getMovieList()
        `when`(local.getMovieList()).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(movieTvRepository.getMovieList())
        verify(local).getMovieList()
        assertNotNull(movieEntities.data)
    }

    @Test
    fun getMovieDetails(){
        val dummyDetailMovie = MutableLiveData<Movie>()
        dummyDetailMovie.value = DataDummy.getMovieDetails(movieId)
        `when`(local.getDetailMovie(movieId)).thenReturn(dummyDetailMovie)

        val movieDetails = LiveDataTestUtil.getValue(movieTvRepository.getMovieDetails(movieId))
        verify(local).getDetailMovie(movieId)
        assertNotNull(movieDetails.data)
    }

    @Test
    fun getTvShowList(){
        val dummyTv = MutableLiveData<List<Tv>>()
        dummyTv.value = DataDummy.getDummyTv()
        `when`(local.getTvList()).thenReturn(dummyTv)

        val tvEntities = LiveDataTestUtil.getValue(movieTvRepository.getTvShowList())
        verify(local).getTvList()
        assertNotNull(tvEntities)
    }

    @Test
    fun getTvDetails(){
        val dummyDetailTv = MutableLiveData<Tv>()
        dummyDetailTv.value = DataDummy.getTvDetails(tvId)
        `when`(local.getDetailTv(tvId)).thenReturn(dummyDetailTv)

        val tvDetails = LiveDataTestUtil.getValue(movieTvRepository.getTvDetails(tvId))
        verify(local).getDetailTv(tvId)
        assertNotNull(tvDetails)
    }

    @Test
    fun getMovieFavorite(){
        `when`(local.getFavoriteMovie()).thenReturn(movieDataSource)
        movieTvRepository.getFavoriteMovie()
        verify(local).getFavoriteMovie()
    }

    @Test
    fun getTvFavorite(){
        `when`(local.getFavoriteTv()).thenReturn(tvDataSource)
        movieTvRepository.getFavoriteTv()
        verify(local).getFavoriteTv()
    }



}