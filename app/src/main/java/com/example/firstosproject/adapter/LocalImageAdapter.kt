package com.example.firstosproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.entities.LocalImage
import com.example.firstosproject.databinding.LocalImageBinding

class LocalImageAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<LocalImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: LocalImage)
    }

    val listOfImage: ArrayList<LocalImage> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(image: LocalImage) {
        listOfImage.add(image)
        notifyDataSetChanged()
    }

    fun updateListWithAllImage(images: ArrayList<LocalImage>) {
        listOfImage.addAll(images)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalImageViewHolder {
        val binding = LocalImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocalImageViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: LocalImageViewHolder, position: Int) {
        val image = listOfImage.get(position)
        holder.bind(image, position)
    }

    override fun getItemCount(): Int {
        return listOfImage.size
    }

    fun getItem(position: Int): LocalImage {
        return listOfImage.get(position)
    }


}