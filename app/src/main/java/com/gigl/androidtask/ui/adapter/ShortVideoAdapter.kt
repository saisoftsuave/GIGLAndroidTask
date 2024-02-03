package com.gigl.androidtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigl.androidtask.databinding.ShortCardViewBinding
import com.gigl.androidtask.models.StoredDetails
import com.gigl.androidtask.utils.ImageUtils


class ShortVideoAdapter(private val data: List<StoredDetails>) :
    RecyclerView.Adapter<ShortVideoAdapter.ShortVideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortVideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShortCardViewBinding.inflate(inflater, parent, false)
        return ShortVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortVideoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ShortVideoViewHolder(private val binding: ShortCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = data[position]
            binding.shortDescription.text = item.description
            ImageUtils.loadImage(
                binding.shortImage.context,
                binding.shortImage,
                item.thumbnailUrl ?: "",
            )
        }
    }
}
