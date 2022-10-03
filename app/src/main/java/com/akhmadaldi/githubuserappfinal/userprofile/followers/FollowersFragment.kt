package com.akhmadaldi.githubuserappfinal.userprofile.followers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhmadaldi.githubuserappfinal.R
import com.akhmadaldi.githubuserappfinal.databinding.FollowLayoutBinding
import com.akhmadaldi.githubuserappfinal.main.UserAdapter
import com.akhmadaldi.githubuserappfinal.userprofile.DetailActivity

class FollowersFragment: Fragment(R.layout.follow_layout) {
    private var followers : FollowLayoutBinding? = null
    private val binding get() = followers!!

    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments
        username = argument?.getString(DetailActivity.EXTRA_USER).toString()

        followers = FollowLayoutBinding.bind(view)

        adapter = UserAdapter()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
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
        followers = null
    }

}