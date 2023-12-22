package com.bangkit.farmtrade.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.farmtrade.databinding.ProductItemBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat

class ProductAdapter: ListAdapter<ProductItem, ProductAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(productItem: ProductItem) {
            Glide.with(this.itemView.context)
                .load(productItem.image)
                .into(binding.productImage)
            binding.productTitle.text = productItem.name
            binding.producer.text = productItem.producer
            binding.priceBefore.text = Companion.getCurrencyString(productItem.price)
            binding.priceBefore.paintFlags = binding.priceBefore.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            binding.priceAfter.text = Companion.getCurrencyString(Companion.afterDiscount(productItem.price)) + " /kg"
        }

        companion object {
            fun getCurrencyString(price: Int): String {
                val formatter : NumberFormat = DecimalFormat("#,###")
                return "Rp${formatter.format(price)}"
            }

            fun afterDiscount(price: Int): Int {
                return (price * 0.8).toInt()
            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(productItem: ProductItem)
    }
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = getItem(position)
        holder.bind(productItem)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(productItem) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

data class ProductItem(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val image: String,
    val producer: String,
    val phoneNumber: String
    // info penjual
)