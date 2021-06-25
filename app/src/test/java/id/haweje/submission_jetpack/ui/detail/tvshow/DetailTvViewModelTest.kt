package id.haweje.submission_jetpack.ui.detail.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.ui.detail.DetailTvViewModel
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.vo.Resource
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {
    private lateinit var viewModel : DetailTvViewModel
    private val dummyTv = DataDummy.getDummyTv()[0]
    private val tvId = dummyTv.id

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var repository = mock(MovieTvRepository::class.java)

    @Mock
    private lateinit var observer: Observer<Resource<Tv>>

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel(repository)
        viewModel.setTvId(tvId)
    }

    @Test
    fun getDetailTv() {
        val dummyDetailTv = Resource.success(DataDummy.getTvDetails(tvId))
        val tv = MutableLiveData<Resource<Tv>>()
        tv.value = dummyDetailTv

        `when`(repository.getTvDetails(tvId)).thenReturn(tv)
        viewModel.getDetailTv.observeForever(observer)
        verify(repository).getTvDetails(tvId)
        verify(observer).onChanged(dummyDetailTv)
        assertNotNull(viewModel.getDetailTv)
        assertEquals(viewModel.getDetailTv.value?.data?.original_name, dummyTv.original_name)
        assertEquals(viewModel.getDetailTv.value?.data?.first_air_date, dummyTv.first_air_date)
        assertEquals(viewModel.getDetailTv.value?.data?.overview,  dummyTv.overview)
        assertEquals(viewModel.getDetailTv.value?.data?.poster_path, dummyTv.poster_path)
        assertEquals(viewModel.getDetailTv.value?.data?.backdrop_path, dummyTv.backdrop_path)
        assertEquals(viewModel.getDetailTv.value?.data?.favorite, dummyTv.favorite)
    }

    @Test
    fun setFavorite(){
        val dummyFavoriteTv = Resource.success(DataDummy.getDummyTv()[0])
        val tv = MutableLiveData<Resource<Tv>>()
        tv.value = dummyFavoriteTv
        `when`(repository.getTvDetails(tvId)).thenReturn(tv)
        viewModel.getDetailTv.observeForever(observer)
        verify(observer).onChanged(tv.value)
        viewModel.setFavorite()
        verify(repository).setFavoriteTv(tv.value?.data as Tv, true)
    }
}