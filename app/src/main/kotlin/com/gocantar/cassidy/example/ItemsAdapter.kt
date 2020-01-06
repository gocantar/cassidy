package com.gocantar.cassidy.example

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gocantar.cassidy.app.R
import com.gocantar.cassidy.example.holders.ItemViewHolder
import com.gocantar.cassidy.tools.extensions.inflate

class ItemsAdapter(
    private val items: List<String>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent.inflate(R.layout.item_view_holder))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }
}