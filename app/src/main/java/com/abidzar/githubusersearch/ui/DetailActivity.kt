package com.abidzar.githubusersearch.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abidzar.githubusersearch.R
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val avatar: ImageView = findViewById(R.id.avatarImage)
        val username: TextView = findViewById(R.id.usernameText)
        val name: TextView = findViewById(R.id.nameText)
        val bio: TextView = findViewById(R.id.bioText)
        val followers: TextView = findViewById(R.id.followersText)
        val following: TextView = findViewById(R.id.followingText)
        val company: TextView = findViewById(R.id.companyText)
        val location: TextView = findViewById(R.id.locationText)
        val blog: TextView = findViewById(R.id.blogText)
        val email: TextView = findViewById(R.id.emailText)
        val twitter: TextView = findViewById(R.id.twitterText)
        val publicCounts: TextView = findViewById(R.id.publicCountsText)
        val timestamps: TextView = findViewById(R.id.timestampsText)

        val usernameArg = intent.getStringExtra(EXTRA_USERNAME) ?: return
        val avatarArg = intent.getStringExtra(EXTRA_AVATAR)

        username.text = usernameArg
        if (!avatarArg.isNullOrBlank()) {
            Glide.with(avatar).load(avatarArg).into(avatar)
        }

        viewModel.user.observe(this) { user ->
            user?.let {
                username.text = it.username
                name.text = it.name.orEmpty()
                bio.text = it.bio.orEmpty()
                followers.text = "Followers: ${it.followers ?: 0}"
                following.text = "Following: ${it.following ?: 0}"
                company.text = "Company: ${it.company ?: "-"}"
                location.text = "Location: ${it.location ?: "-"}"
                blog.text = "Blog: ${it.blog ?: "-"}"
                email.text = "Email: ${it.email ?: "-"}"
                twitter.text = "Twitter: ${it.twitterUsername ?: "-"}"
                publicCounts.text = "Public repos: ${it.publicRepos ?: 0} | gists: ${it.publicGists ?: 0}"
                timestamps.text = "Created: ${it.createdAt ?: "-"} | Updated: ${it.updatedAt ?: "-"}"
            }
        }
        viewModel.load(usernameArg)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}
