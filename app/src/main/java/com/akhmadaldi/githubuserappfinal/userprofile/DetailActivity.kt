package com.akhmadaldi.githubuserappfinal.userprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akhmadaldi.githubuserappfinal.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://github.com/${binding.username2.text}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        val username = intent.getStringExtra(EXTRA_USER).toString()
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        viewModel = ViewModelProvider(this)[DetailProfileViewModel::class.java]


        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this){
            if (it != null){
                binding.apply {
                    name.text = it.name
                    username2.text = it.login
                    followers.text = it.followers.toString()
                    following.text = it.following.toString()
                    repository.text = it.publicRepos.toString()
                    location.text = it.location
                    work.text = it.company
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(image)
                }
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.favoriteToggle.isChecked = true
                        isChecked = true
                    } else{
                        binding.favoriteToggle.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.favoriteToggle.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                viewModel.addToFavorite(username, id, avatar)
                Toast.makeText(this, username+ "_Added to Favorites", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.removeUser(id)
                Toast.makeText(this, username+ "_removed from Favorites", Toast.LENGTH_SHORT).show()
            }
            binding.favoriteToggle.isChecked = isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_url"
    }
}