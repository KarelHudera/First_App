package com.example.api_cheatsheet.listExchanges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.api_cheatsheet.api.Exchanges
import com.example.api_cheatsheet.databinding.ItemExchangeBinding

class ExchangeAdapter(private val clickListener: OnExchangeClicked) :
    ListAdapter<Exchanges, ExchangeAdapter.ItemViewHolder>(ExchangesDiffCallBack()) {

    inner class ItemViewHolder(private val binding: ItemExchangeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Exchanges) {
            with(binding) {
                imageView.load(item.image)
                textViewName.text = item.name
                root.setOnClickListener { clickListener.onExchangeClicked(item) }
            }
        }
    }

    private class ExchangesDiffCallBack : DiffUtil.ItemCallback<Exchanges>() {
        override fun areItemsTheSame(
            oldExchangeData: Exchanges,
            newExchangeData: Exchanges
        ): Boolean =
            oldExchangeData == newExchangeData

        override fun areContentsTheSame(
            oldExchangeData: Exchanges,
            newExchangeData: Exchanges
        ): Boolean =
            oldExchangeData == newExchangeData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun interface OnExchangeClicked {
        fun onExchangeClicked(exchanges: Exchanges)
    }
}
