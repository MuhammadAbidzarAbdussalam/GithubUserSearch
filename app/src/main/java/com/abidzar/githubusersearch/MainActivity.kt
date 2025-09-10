package com.abidzar.githubusersearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.ui.DetailActivity
import com.abidzar.githubusersearch.ui.SearchViewModel
import com.abidzar.githubusersearch.ui.UserAdapter
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        val stateText = findViewById<TextView>(R.id.stateText)
        val search = findViewById<TextInputEditText>(R.id.searchEditText)

        val adapter = UserAdapter { user -> openDetail(user) }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel.loading.observe(this) { loading ->
            if (loading) {
                progress.visibility = View.VISIBLE
                progress.animate()
            } else {
                progress.visibility = View.GONE
                progress.clearAnimation()
            }
        }
        viewModel.error.observe(this) { err ->
            stateText.text = err ?: ""
            stateText.visibility = if (err != null) View.VISIBLE else View.GONE
        }
        viewModel.results.observe(this) { pagingData ->
            lifecycleScope.launchWhenStarted {
                adapter.submitData(pagingData)
            }
            stateText.visibility = View.GONE
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onQueryChanged(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun openDetail(user: UserSummary) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
        intent.putExtra(DetailActivity.EXTRA_AVATAR, user.avatarUrl)
        startActivity(intent)
    }
}
