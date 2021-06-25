package id.haweje.submission_jetpack.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.vo.Resource
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
class TvShowViewModelTest {
    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var repository = Mockito.mock(MovieTvRepository::class.java)

    @Mock
    private lateinit var observer: Observer<Resource<List<Tv>>>

    private lateinit var viewModel: TvShowViewModel
    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun getTvShowData() {
        val dummyTv = Resource.success(DataDummy.getDummyTv())
        val tvs = MutableLiveData<Resource<List<Tv>>>()
        tvs.value = dummyTv

        `when`(repository.getTvShowList()).thenReturn(tvs)
        val tvShowData = viewModel.getTvshowData().value?.data
        verify(repository).getTvShowList()
        assertNotNull(tvShowData)


        viewModel.getTvshowData().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}