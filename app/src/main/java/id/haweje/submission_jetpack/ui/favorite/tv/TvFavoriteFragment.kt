package id.haweje.submission_jetpack.ui.favorite.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.databinding.FragmentTvFavoriteBinding
import id.haweje.submission_jetpack.ui.detail.DetailTvShowActivity
import id.haweje.submission_jetpack.utils.OnItemClickTvshowCallback
import id.haweje.submission_jetpack.utils.ViewModelFactory

class TvFavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding: FragmentTvFavoriteBinding
    private lateinit var tvAdapter : FavoriteTvAdapter
    private lateinit var viewModel: TvFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding =
            FragmentTvFavoriteBinding.inflate(inflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            tvAdapter = FavoriteTvAdapter()
            tvAdapter.notifyDataSetChanged()
            tvAdapter.setOnItemClick(object : OnItemClickTvshowCallback{
                override fun onItemOnClicked(tv: Tv) {
                    Intent(activity, DetailTvShowActivity::class.java).also {
                        it.putExtra(DetailTvShowActivity.EXTRA_ID, tv.id)
                        startActivity(it)
                    }
                }
            })

            fragmentFavoriteBinding.apply {
                rvTvShow.layoutManager = LinearLayoutManager(view.context)
                rvTvShow.adapter = tvAdapter
                rvTvShow.setHasFixedSize(true)
            }
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TvFavoriteViewModel::class.java]

        viewModel.getFavoriteTv().observe(viewLifecycleOwner, {
            if (it != null){
                tvAdapter.submitList(it)
            }
        })
    }

}