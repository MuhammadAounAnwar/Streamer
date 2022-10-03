package com.ono.streamer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ono.streamer.databinding.RvItemLayoutBinding

class Adapter(private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    val mediaFiles = mutableListOf<String>()
    override fun getItemCount(): Int {
        return mediaFiles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val binding = RvItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder.bind()
    }

    fun submitMediaList(recyclerView: RecyclerView, mediaFiles: List<String>) {
        recyclerView.post {
            if (this.mediaFiles.isEmpty()) {
                this.mediaFiles.addAll(mediaFiles)
                notifyDataSetChanged()
            }
        }

        mediaFiles.let {
            val diffUtil = DiffUtil.calculateDiff(ItemsDiffUtil(this.mediaFiles, it))
            this.mediaFiles.clear()
            this.mediaFiles.addAll(it)
            diffUtil.dispatchUpdatesTo(this)
        }
    }


    inner class ViewHolder(val binding: RvItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            binding.executePendingBindings()
        }
    }

    inner class ItemsDiffUtil(private val oldMembers: List<String>, private val newMembers: List<String>) :
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