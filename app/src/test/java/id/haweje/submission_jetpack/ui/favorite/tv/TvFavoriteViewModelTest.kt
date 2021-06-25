package id.haweje.submission_jetpack.ui.favorite.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv
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
class TvFavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<Tv>>

    @Mock
    private lateinit var pagedList: PagedList<Tv>

    @Mock
    private var repository = Mockito.mock(MovieTvRepository::class.java)

    private lateinit var viewModel: TvFavoriteViewModel


    @Before
    fun setUp() {
        viewModel = TvFavoriteViewModel(repository)
    }

    @Test
    fun getFavoriteTv() {
        val dummyTv = pagedList
        `when`(dummyTv.size).thenReturn(10)
        val tvs = MutableLiveData<PagedList<Tv>>()
        tvs.value = dummyTv

        `when`(repository.getFavoriteTv()).thenReturn(tvs)
        val tvsEntities = viewModel.getFavoriteTv().value
        verify(repository).getFavoriteTv()
        assertNotNull(tvsEntities)
        assertEquals(10, tvsEntities?.size)

        viewModel.getFavoriteTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}