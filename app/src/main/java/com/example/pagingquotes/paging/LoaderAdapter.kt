package com.example.pagingquotes.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingquotes.R

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {
    inner class LoaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressbar)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.loader_ui, parent, false)
        )
    }
}