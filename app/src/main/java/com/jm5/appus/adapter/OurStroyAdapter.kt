package com.jm5.appus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jm5.appus.R
import com.jm5.appus.dataForm.OurStoryList

class OurStoryAdapter(var list : ArrayList<OurStoryList>) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.ourstory_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val result = list[position]
        holder.textView.text = result.text
    }

//    override fun onBindViewHolder(holder: PlantAdapter.ViewHolder, position: Int) {
//        val result = list[position]
//        var url = result.img
//        Glide.with(context)
//                .load(url)
//                .centerCrop()
//                .into(holder.img)
//        holder.name.text=result.name
//        holder.price.text=result.prices
//    }

    override fun getItemCount(): Int {
        return list.size
    }

}
class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.findViewById<TextView>(R.id.tv_ourstory)
}