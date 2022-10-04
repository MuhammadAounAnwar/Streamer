package com.ono.streamer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ono.streamer.databinding.RvItemLayoutBinding

class MediaAdapter(private val context: Context) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

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
    }

    fun submitMediaList(recyclerView: RecyclerView, mediaFiles: List<com.ono.streamerlibrary.models.Result>) {
//        recyclerView.post {
//            if (this.mediaFiles.isEmpty()) {
//                this.mediaFiles.addAll(mediaFiles)
//                notifyDataSetChanged()
//            }
//        }

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
            return oldMembers[oldItemPosition] == newMembers[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMembers[oldItemPosition] == newMembers[newItemPosition]
        }

    }

}