package id.haweje.submission_jetpack.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.databinding.FragmentMovieFavoriteBinding
import id.haweje.submission_jetpack.ui.detail.DetailMovieActivity
import id.haweje.submission_jetpack.utils.OnItemClickMovieCallback
import id.haweje.submission_jetpack.utils.ViewModelFactory

class MovieFavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding : FragmentMovieFavoriteBinding
    private lateinit var movieAdapter: FavoriteMovieAdapter
    private lateinit var viewModel: MovieFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding =
            FragmentMovieFavoriteBinding.inflate(inflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            movieAdapter = FavoriteMovieAdapter()
            movieAdapter.notifyDataSetChanged()
            movieAdapter.setOnItemClick(object : OnItemClickMovieCallback {
                override fun onItemClickedMovie(movie: Movie) {
                    Intent(activity, DetailMovieActivity::class.java).also {
                        it.putExtra(DetailMovieActivity.EXTRA_ID, movie.id)
                        startActivity(it)
                    }
                }
            })

            fragmentFavoriteBinding.apply {
                rvMovie.layoutManager = LinearLayoutManager(view.context)
                rvMovie.adapter = movieAdapter
                rvMovie.setHasFixedSize(true)
            }
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]

        viewModel.getFavoriteMovie().observe(viewLifecycleOwner,{
            if (it != null){
                movieAdapter.submitList(it)
            }
        })
    }

}