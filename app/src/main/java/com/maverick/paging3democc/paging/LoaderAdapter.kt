package com.maverick.paging3democc.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maverick.paging3democc.databinding.ItemLoaderBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    inner class LoaderViewHolder(internal var itemBinding: ItemLoaderBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.itemBinding.progressBar.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemLoaderBinding.inflate(inflater, parent, false)
        return LoaderViewHolder(itemBinding)
    }

}