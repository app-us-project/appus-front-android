package com.jm5.appus

import android.view.View
import com.jm5.appus.adapter.PlantAdapter

public interface OnItemClickListener {
    fun onItemClick(holder:PlantAdapter.ViewHolder, view: View, position:Int)
}