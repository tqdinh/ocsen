package com.example.firstosproject.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.data.local.entities.LocalImage
import com.example.firstosproject.databinding.LocalImageBinding

open class LocalImageViewHolder(
    val binding: LocalImageBinding,
    private val onItemClickListener: LocalImageAdapter.OnItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    private val circularProgressDrawable: CircularProgressDrawable =
        CircularProgressDrawable(itemView.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

    init {
        binding.imvEvent.clipToOutline = true
        bindPhoto("", binding.imvEvent)
    }

    open fun bind(item: LocalImage, position: Int) {
        item.imageInfo?.let {
            bindPhoto(it.path, binding.imvEvent)
        }
        binding.root.setOnClickListener({
            onItemClickListener.onItemClick(item)
        })
        binding.tvLatLng.text = "${item.imageInfo?.lat} -${item.imageInfo?.lon}"
//        binding.txtTitleEvent.text = Util.parseHtmlToStyledTextOrEmpty(item.title)
//        binding.descEvent.text = Util.parseHtmlToStyledTextOrEmpty(item.shortDesc)
//        binding.btnContinue.setOnClickListener { onItemClickListener.onItemClick(item) }
//        binding.btnGetStarted.setOnClickListener { onItemClickListener.onItemClick(item) }
    }

    private fun bindPhoto(url: String, imageView: ImageView) {
        Glide.with(itemView.context)
            .load(url)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }
}
