package com.jm5.appus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jm5.appus.OnItemClickListener
import com.jm5.appus.R
import com.jm5.appus.dataForm.PlantsList
//PlantList
//    val name:String?=null,
//    val prices :String? = null,
//    val img : Int?=null

class PlantAdapter(var context: Context, var list : ArrayList<PlantsList>) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(),
    OnItemClickListener {
    lateinit var listener : OnItemClickListener

    inner class ViewHolder(view : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view){
        var img : ImageView = itemView.findViewById(R.id.plant_img)
        var name : TextView = itemView.findViewById(R.id.plant_name)
        var price : TextView = itemView.findViewById(R.id.plant_price)
        val view=
        view.setOnClickListener {
            var position = adapterPosition
            listener.onItemClick(this,it,position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.plants_list,parent,false)
        return ViewHolder(view,this)
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
            .into(holder.img)
        holder.name.text=result.name
        holder.price.text=result.prices
    }
    fun setItemListener(listener: OnItemClickListener){
        this.listener=listener
    }
    override fun onItemClick(holder: ViewHolder, view: View, position: Int) {
        listener.onItemClick(holder, view, position)
    }
    fun getItem(position : Int) : PlantsList{
        return list.get(position)
    }
}