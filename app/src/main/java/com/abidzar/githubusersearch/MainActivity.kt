package com.abidzar.githubusersearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.abidzar.githubusersearch.ui.DetailActivity
import com.abidzar.githubusersearch.ui.SearchViewModel
import com.abidzar.githubusersearch.ui.UserAdapter
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

        val recycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        val progress = findViewById<android.widget.ProgressBar>(R.id.progressBar)
        val stateText = findViewById<android.widget.TextView>(R.id.stateText)
        val search = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.searchEditText)

        val adapter = UserAdapter { user -> openDetail(user) }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel.loading.observe(this) { loading ->
            progress.visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        }
        viewModel.error.observe(this) { err ->
            stateText.text = err ?: ""
            stateText.visibility = if (err != null) android.view.View.VISIBLE else android.view.View.GONE
        }
        viewModel.results.observe(this) { list ->
            adapter.submit(list)
            if (list.isEmpty() && (viewModel.error.value == null)) {
                stateText.text = "No results"
                stateText.visibility = android.view.View.VISIBLE
            } else if (list.isNotEmpty()) {
                stateText.visibility = android.view.View.GONE
            }
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
