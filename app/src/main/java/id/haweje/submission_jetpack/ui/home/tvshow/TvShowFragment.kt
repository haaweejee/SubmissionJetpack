package id.haweje.submission_jetpack.ui.home.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.databinding.FragmentTvShowBinding
import id.haweje.submission_jetpack.ui.detail.DetailTvShowActivity
import id.haweje.submission_jetpack.utils.OnItemClickTvshowCallback
import id.haweje.submission_jetpack.utils.ViewModelFactory
import id.haweje.submission_jetpack.vo.Status

class TvShowFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding
    private lateinit var tvShowAdapter: ListTvAdapter
    private lateinit var tvShowViewModel: TvShowViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            tvShowAdapter = ListTvAdapter()
            tvShowAdapter.notifyDataSetChanged()
            tvShowAdapter.setOnItemClick(object : OnItemClickTvshowCallback{
                override fun onItemOnClicked(tv: Tv) {
                    Intent(activity, DetailTvShowActivity::class.java).also {
                        it.putExtra(DetailTvShowActivity.EXTRA_ID, tv.id)
                        startActivity(it)
                    }
                }
            })

            fragmentTvShowBinding.apply {
                rvTvShow.layoutManager = LinearLayoutManager(activity)
                rvTvShow.adapter = tvShowAdapter
                rvTvShow.setHasFixedSize(true)
            }


            val factory = ViewModelFactory.getInstance(requireContext())
            tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            showLoading(true)
            tvShowViewModel.getTvshowData().observe(viewLifecycleOwner, {
                if (it != null){
                    when(it.status){
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            it.data?.let { list -> tvShowAdapter.setTvShow(list) }
                            tvShowAdapter.notifyDataSetChanged()
                            fragmentTvShowBinding.rvTvShow.visibility = View.VISIBLE
                        }
                        Status.ERROR ->{
                            showLoading(false)
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }


    private fun showLoading(state: Boolean) {
        if (state)
            fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
        else
            fragmentTvShowBinding.progressBar.visibility = View.GONE
    }
}