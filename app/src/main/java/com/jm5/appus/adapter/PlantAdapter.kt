package com.jm5.appus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jm5.appus.R
import com.jm5.appus.dataForm.PlantsList
//PlantList
//    val name:String?=null,
//    val prices :String? = null,
//    val img : Int?=null

class PlantAdapter(var context: Context, var list : ArrayList<PlantsList>) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var img : ImageView = itemView.findViewById(R.id.plant_img)
        var name : TextView = itemView.findViewById(R.id.plant_name)
        var price : TextView = itemView.findViewById(R.id.plant_price)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.plants_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = list[position]
        var url = result.img
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(holder.img);
        holder.name.text=result.name
        holder.price.text=result.prices
    }
}