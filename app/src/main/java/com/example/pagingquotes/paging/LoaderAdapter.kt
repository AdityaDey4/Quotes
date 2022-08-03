package com.example.pagingquotes.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingquotes.R

class LoaderAdapter(
    private val retry: ()-> Unit
): LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {
    inner class LoaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val reload: Button = itemView.findViewById(R.id.reload)
        init {
            reload.setOnClickListener {
                retry.invoke()
            }
        }
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressbar)
        val loadingTV: TextView = itemView.findViewById(R.id.loadingTV)

    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        if(loadState is LoadState.Loading){
            holder.progressBar.isVisible = true
            holder.loadingTV.isVisible = true
            holder.reload.isVisible= false
        }
        else if(loadState is LoadState.Error){
            holder.reload.isVisible= true
            holder.progressBar.isVisible = false
            holder.loadingTV.isVisible = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.loader_ui, parent, false)
        )
    }
}