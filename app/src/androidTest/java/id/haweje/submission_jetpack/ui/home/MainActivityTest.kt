package id.haweje.submission_jetpack.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.ui.favorite.movie.FavoriteMovieAdapter
import id.haweje.submission_jetpack.ui.favorite.tv.FavoriteTvAdapter
import id.haweje.submission_jetpack.ui.home.movie.ListMovieAdapter
import id.haweje.submission_jetpack.ui.home.tvshow.ListTvAdapter
import id.haweje.submission_jetpack.utils.DataDummy
import id.haweje.submission_jetpack.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    private val dummyMovie = DataDummy.getMovieList()
    private val dummyTvShow = DataDummy.getDummyTv()

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovieList(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadTvshowList(){
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun toDetailMovie(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<ListMovieAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.item_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_name)).check(matches(withText(dummyMovie[0].original_title)))
        onView(withId(R.id.item_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_date)).check(matches(withText(dummyMovie[0].release_date)))
        onView(withId(R.id.item_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_overview)).check(matches(withText(dummyMovie[0].overview)))
        onView(withId(R.id.item_detail_image)).check(matches(isDisplayed()))
        onView(withId(R.id.backdropImage)).check(matches(isDisplayed()))
        onView(withId(R.id.btnShare)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFav)).check(matches(isDisplayed()))
    }

    @Test
    fun toDetailTv(){
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<ListTvAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.item_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_name)).check(matches(withText(dummyTvShow[0].original_name)))
        onView(withId(R.id.item_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_date)).check(matches(withText(dummyTvShow[0].first_air_date)))
        onView(withId(R.id.item_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_overview)).check(matches(withText(dummyTvShow[0].overview)))
        onView(withId(R.id.item_detail_image)).check(matches(isDisplayed()))
        onView(withId(R.id.backdropImage)).check(matches(isDisplayed()))
        onView(withId(R.id.btnShare)).check(matches(isDisplayed()))
    }

    @Test
    fun makeMovieFavorite(){
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<ListMovieAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.btnFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<FavoriteMovieAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.item_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_name)).check(matches(withText(dummyMovie[0].original_title)))
        onView(withId(R.id.item_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_date)).check(matches(withText(dummyMovie[0].release_date)))
        onView(withId(R.id.item_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_overview)).check(matches(withText(dummyMovie[0].overview)))
        onView(withId(R.id.item_detail_image)).check(matches(isDisplayed()))
        onView(withId(R.id.backdropImage)).check(matches(isDisplayed()))
        onView(withId(R.id.btnShare)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFav)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun makeTvFavorite(){
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<ListTvAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.btnFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<FavoriteTvAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.item_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_name)).check(matches(withText(dummyTvShow[0].original_name)))
        onView(withId(R.id.item_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_date)).check(matches(withText(dummyTvShow[0].first_air_date)))
        onView(withId(R.id.item_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_detail_overview)).check(matches(withText(dummyTvShow[0].overview)))
        onView(withId(R.id.item_detail_image)).check(matches(isDisplayed()))
        onView(withId(R.id.backdropImage)).check(matches(isDisplayed()))
        onView(withId(R.id.btnShare)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFav)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

}