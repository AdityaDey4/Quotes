package com.example.pagingquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingquotes.paging.LoaderAdapter
import com.example.pagingquotes.paging.QuotePagingAdapter
import com.example.pagingquotes.viewmodels.Viewmodels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: Viewmodels
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: QuotePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        viewModel = ViewModelProvider(this).get(Viewmodels::class.java)
        adapter = QuotePagingAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        viewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}