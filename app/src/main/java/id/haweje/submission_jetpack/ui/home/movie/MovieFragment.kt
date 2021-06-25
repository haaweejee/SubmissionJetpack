package id.haweje.submission_jetpack.ui.home.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.databinding.FragmentMovieBinding
import id.haweje.submission_jetpack.ui.detail.DetailMovieActivity
import id.haweje.submission_jetpack.utils.OnItemClickMovieCallback
import id.haweje.submission_jetpack.utils.ViewModelFactory
import id.haweje.submission_jetpack.vo.Status


class MovieFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private lateinit var movieAdapter: ListMovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            if (activity != null){
                movieAdapter = ListMovieAdapter()
                movieAdapter.notifyDataSetChanged()
                movieAdapter.setOnItemClick(object : OnItemClickMovieCallback{
                    override fun onItemClickedMovie(movie: Movie) {
                        Intent(activity, DetailMovieActivity::class.java).also {
                            it.putExtra(DetailMovieActivity.EXTRA_ID, movie.id)
                            startActivity(it)
                        }
                    }
                })

                fragmentMovieBinding.apply {
                    rvMovie.layoutManager = LinearLayoutManager(view.context)
                    rvMovie.adapter = movieAdapter
                    rvMovie.setHasFixedSize(true)
                }

                val factory = ViewModelFactory.getInstance(requireContext())
                viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
                showLoading(true)
                viewModel.getMovieData().observe(viewLifecycleOwner,{
                    if (it != null){
                        when(it.status){
                            Status.LOADING -> showLoading(true)
                            Status.SUCCESS -> {
                                showLoading(false)
                                it.data?.let { list -> movieAdapter.setMovies(list) }
                                movieAdapter.notifyDataSetChanged()
                                fragmentMovieBinding.rvMovie.visibility = View.VISIBLE
                            }
                            Status.ERROR ->{
                                showLoading(false)
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                )

            }
        }

    private fun showLoading(state: Boolean) {
        if (state)
            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
        else
            fragmentMovieBinding.progressBar.visibility = View.GONE
        }
    }
