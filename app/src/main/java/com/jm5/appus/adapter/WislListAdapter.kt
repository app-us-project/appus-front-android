//package com.jm5.appus.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.jm5.appus.R
//import com.jm5.appus.dataForm.WishPlantsList
//
//class WislListAdapter( val plantList: ArrayList<WishPlantsList>): RecyclerView.Adapter<Holder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist, parent, false )
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return plantList.size
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.plant.setImageResource(plantList.get(position).plant)
//        holder.name.text = plantList.get(position).name
//    }
//}
//
//class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
//    val plant = itemView.findViewById<ImageView>(R.id.wishPlant) // 식물 이미지
//    val name = itemView.findViewById<TextView>(R.id.wishText) // 식물 이름
//    val price = itemView.findViewById<TextView>(R.id.wishPrice) // 식물 가격
//    val like = itemView.findViewById<ImageView>(R.id.like) // 하트 표시
//}