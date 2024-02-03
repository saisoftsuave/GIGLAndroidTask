package com.gigl.androidtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigl.androidtask.R
import com.gigl.androidtask.databinding.VideoCardViewBinding
import com.gigl.androidtask.models.StoredDetails

class VerticalVideoAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIDEO_VIEW = 1
    private val SHORT_RECYCLER_VIEW = 2
    private var detailList = ArrayList<Any>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIDEO_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = VideoCardViewBinding.inflate(inflater, parent, false)
                NormalViewHolder(binding)
            }

            SHORT_RECYCLER_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false)
                RecyclerViewViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun clear() {
        detailList.clear()
    }

    fun setDetails(list: MutableList<Any>) {
        clear()
        detailList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIDEO_VIEW -> {
                val item = detailList[position] as StoredDetails
                (holder as VerticalVideoAdapter.NormalViewHolder).bind(item)
            }

            SHORT_RECYCLER_VIEW -> {
                val item = detailList[position] as List<StoredDetails>
                (holder as VerticalVideoAdapter.RecyclerViewViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (detailList[position]) {
            is StoredDetails -> VIDEO_VIEW
            else -> SHORT_RECYCLER_VIEW
        }
    }

    inner class NormalViewHolder(private val binding: VideoCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoredDetails) {
            binding.videoDescription.text = item.description
            ImageUtils.loadImage(
                binding.videoThumbnail.context,
                binding.videoThumbnail,
                item.thumbnailUrl ?: "",
            )
            ImageUtils.loadImage(
                binding.circularImageView.context,
                binding.circularImageView,
                item.thumbnailUrl ?: "",
            )
            binding.videoShortDescription.text = item.description
        }
    }

    inner class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.innerRecyclerView)

        fun bind(shortVideos: List<StoredDetails>) {
            recyclerView.layoutManager = LinearLayoutManager(itemView.context).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            recyclerView.adapter = ShortVideoAdapter(shortVideos)
        }
    }
}
