package com.ono.streamer.ui.helper

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ono.streamer.MediaAdapter
import com.ono.streamer.R

private const val TAG = "HANDLER"

@BindingAdapter("loadThumbnail")
fun loadThumbnail(view: View, imageUrl: String?) {
    if (imageUrl.isNullOrBlank()) {
        return
    }
    Glide.with(view.context).load(imageUrl).centerCrop().placeholder(R.drawable.placeholder).into(view as ImageView)
}

@BindingAdapter("setTitleText")
fun setTitleText(view: View, result: com.ono.streamerlibrary.models.Result) {
    val value = when (result.media_type) {
        "movie" -> result.title
        "tv" -> result.name
        "person" -> result.name
        else -> {}
    }

    (view as TextView).text = value as CharSequence?
}

@BindingAdapter("handleVisibility")
fun handleVisibility(view: View, result: com.ono.streamerlibrary.models.Result) {
    when (result.media_type) {
        "movie, tv" -> view.visibility = View.VISIBLE
        "person" -> view.visibility = View.GONE
        else -> {}
    }
}

@BindingAdapter("showMediaDescription")
fun showMediaDescription(view: View, result: com.ono.streamerlibrary.models.Result) {
    Log.e(TAG, "showMediaDescription: $result")
    val desc = when (result.media_type) {
        "movie" -> result.overview
        "tv" -> result.overview
        else -> {}
    }
    Log.e(TAG, "showMediaDescription: $desc")
    (view as TextView).text = StringBuilder().append(desc)
}


@BindingAdapter("rvAdapter")
fun rvAdapter(recyclerView: RecyclerView, data: List<com.ono.streamerlibrary.models.Result>) {
    val adapter = recyclerView.adapter as MediaAdapter
    data.let { adapter.submitMediaList(recyclerView, data) }
}

