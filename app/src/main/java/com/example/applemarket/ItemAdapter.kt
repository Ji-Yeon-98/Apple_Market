package com.example.applemarket

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ItemRecyclerBinding
import java.text.DecimalFormat

class ItemAdapter(val itemList:MutableList<ItemModel>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    interface ItemClick{
        fun onClick(view : View, position : Int)
    }

    interface ItemLongClick{
        fun onLongClick(view : View, position : Int):Boolean
    }

    var itemClick : ItemClick? = null
    var itemLongClick: ItemLongClick?= null

    inner class ViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val address = binding.tvAddress
        val price = binding.tvPrice
        val itemImg = binding.ivItem
        val comment = binding.tvComment
        var heart = binding.tvHeart
        var heartImg = binding.ivHeart
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClick?.onLongClick(it, position)!!
        }

        holder.title.text = itemList[position].title

        holder.address.text = itemList[position].address
        holder.price.text = DecimalFormat("#,###").format(itemList[position].price) + "원"

        holder.itemImg.setImageResource(itemList[position].itemImg)
        holder.itemImg.clipToOutline = true

        holder.comment.text = itemList[position].comment.toString()

        if(itemList[position].heartClicked){
            holder.heartImg.setImageResource(R.drawable.full_heart)
        }else{
            holder.heartImg.setImageResource(R.drawable.heart)
        }

        holder.heart.text = itemList[position].heart.toString()

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun deleteItem(position: Int){
        itemList.removeAt(position)
        notifyDataSetChanged()
    }

}