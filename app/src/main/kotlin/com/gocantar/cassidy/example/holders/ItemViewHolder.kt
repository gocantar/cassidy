package com.gocantar.cassidy.example.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cassidy.widgets.image.avatar.models.Avatar
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view_holder.*

class ItemViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(title: String) {
        avatar.setLabel(Avatar(title))
        label.text = title
    }
}