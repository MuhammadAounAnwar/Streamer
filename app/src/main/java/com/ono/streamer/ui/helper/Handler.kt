package com.ono.streamer.ui.helper

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ono.streamer.MediaAdapter
import com.ono.streamer.R


@BindingAdapter("loadThumbnail")
fun loadThumbnail(view: View, imageUrl: String) {
    if (imageUrl.isNullOrBlank()) {
        return
    }
    Glide.with(view.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.placeholder)
        .into(view as ImageView)
}

@BindingAdapter("rvAdapter")
fun rvAdapter(recyclerView: RecyclerView, data: List<com.ono.streamerlibrary.models.Result>) {
    val adapter = recyclerView.adapter as MediaAdapter
    data.let { adapter.submitMediaList(recyclerView, data) }
}
