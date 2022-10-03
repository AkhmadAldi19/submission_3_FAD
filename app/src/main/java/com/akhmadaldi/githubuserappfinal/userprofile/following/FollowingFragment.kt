package com.akhmadaldi.githubuserappfinal.userprofile.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhmadaldi.githubuserappfinal.R
import com.akhmadaldi.githubuserappfinal.databinding.FollowLayoutBinding
import com.akhmadaldi.githubuserappfinal.main.UserAdapter
import com.akhmadaldi.githubuserappfinal.userprofile.DetailActivity

class FollowingFragment: Fragment(R.layout.follow_layout) {
    private var following : FollowLayoutBinding? = null
    private val binding get() = following!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments
        username = argument?.getString(DetailActivity.EXTRA_USER).toString()

        following = FollowLayoutBinding.bind(view)

        adapter = UserAdapter()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }

    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        following = null
    }
}