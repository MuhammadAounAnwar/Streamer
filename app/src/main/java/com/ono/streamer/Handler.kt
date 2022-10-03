package com.ono.streamer

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object Handler {

    @BindingAdapter("loadThumbnail")
    fun loadThumbnail(view: View, imageUrl: String) {

    }

    @BindingAdapter("rvAdapter")
    fun rvAdapter(recyclerView: RecyclerView, data:List<String>) {

    }

}