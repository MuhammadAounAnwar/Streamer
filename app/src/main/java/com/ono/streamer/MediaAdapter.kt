package com.ono.streamer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ono.streamer.databinding.RvItemLayoutBinding

private const val TAG = "MediaAdapter"

typealias OnItemClicked = (item: com.ono.streamerlibrary.models.Result) -> Unit

class MediaAdapter(private val context: Context, val onItemClicked: OnItemClicked) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    private val mediaFiles = mutableListOf<com.ono.streamerlibrary.models.Result>()
    override fun getItemCount(): Int {
        return mediaFiles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ViewHolder {
        val binding = RvItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaAdapter.ViewHolder, position: Int) {
        holder.bind(mediaFiles[position])
        holder.binding.root.setOnClickListener {
            onItemClicked(mediaFiles[position])
        }
    }

    fun addNewItems(mediaFiles: List<com.ono.streamerlibrary.models.Result>) {
        mediaFiles.let {
            val prevIndex = this.mediaFiles.size
            this.mediaFiles.addAll(it)
            notifyItemRangeInserted(prevIndex, mediaFiles.size)
        }
        Log.e(TAG, "addNewItems: ")
    }

    fun submitMediaList(recyclerView: RecyclerView, mediaFiles: List<com.ono.streamerlibrary.models.Result>) {
        mediaFiles.let {
            val diffUtil = DiffUtil.calculateDiff(ItemsDiffUtil(this.mediaFiles, it))
            this.mediaFiles.clear()
            this.mediaFiles.addAll(it)
            diffUtil.dispatchUpdatesTo(this)
        }
    }


    inner class ViewHolder(val binding: RvItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: com.ono.streamerlibrary.models.Result) {
            binding.result = result
            binding.executePendingBindings()
        }

    }

    inner class ItemsDiffUtil(private val oldMembers: List<com.ono.streamerlibrary.models.Result>, private val newMembers: List<com.ono.streamerlibrary.models.Result>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldMembers.size
        }

        override fun getNewListSize(): Int {
            return newMembers.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMembers[oldItemPosition].id == newMembers[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMembers[oldItemPosition].id == newMembers[newItemPosition].id
        }

    }

}