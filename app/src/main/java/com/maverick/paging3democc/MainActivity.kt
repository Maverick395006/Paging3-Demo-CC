package com.maverick.paging3democc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maverick.paging3democc.databinding.ActivityMainBinding
import com.maverick.paging3democc.models.Result
import com.maverick.paging3democc.paging.LoaderAdapter
import com.maverick.paging3democc.paging.QuotePagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var quoteViewModel: QuoteViewModel
    private val quoteAdapter by lazy { QuotePagingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        binding.quoteList.layoutManager = LinearLayoutManager(this)
        binding.quoteList.setHasFixedSize(true)
        binding.quoteList.adapter = quoteAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        quoteAdapter.setEventListener(object : QuotePagingAdapter.EventListener {
            override fun onItemClick(position: Int, item: Result) {
                Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        quoteViewModel.list.observe(this) {
            quoteAdapter.submitData(lifecycle, it)
        }

    }

}