package com.abidzar.githubusersearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abidzar.githubusersearch.R
import com.abidzar.githubusersearch.domain.model.UserSummary
import com.bumptech.glide.Glide

class UserAdapter(
    private val onClick: (UserSummary) -> Unit
) : RecyclerView.Adapter<UserAdapter.VH>() {

    private val items = mutableListOf<UserSummary>()

    fun submit(list: List<UserSummary>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_summary, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.avatarImage)
        private val username: TextView = itemView.findViewById(R.id.usernameText)
        fun bind(item: UserSummary, onClick: (UserSummary) -> Unit) {
            username.text = item.username
            Glide.with(avatar).load(item.avatarUrl).into(avatar)
            itemView.setOnClickListener { onClick(item) }
        }
    }
}
