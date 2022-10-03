package com.akhmadaldi.githubuserappfinal.favorit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhmadaldi.githubuserappfinal.data.local.UserFavorite
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import com.akhmadaldi.githubuserappfinal.databinding.ActivityFavoriteBinding
import com.akhmadaldi.githubuserappfinal.main.UserAdapter
import com.akhmadaldi.githubuserappfinal.userprofile.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]


        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemItems) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<ItemItems> {
        val listUser = ArrayList<ItemItems>()
        for (user in users){
            val userMapped = ItemItems(
                user.login,
                user.id,
                user.avatar
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}