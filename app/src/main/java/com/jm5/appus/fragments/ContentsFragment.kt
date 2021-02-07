package com.jm5.appus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jm5.appus.R
import com.jm5.appus.adapter.PlantAdapter
import com.jm5.appus.dataForm.PlantsList
import java.util.ArrayList

class ContentsFragment : Fragment() {
    var item = ArrayList<PlantsList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_contents, container, false)
        updateList()
        var context = view.context
        var recyclerView= view.findViewById<RecyclerView>(R.id.recylerview)
        recyclerView.setHasFixedSize(true)

        var layoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager=layoutManager

        var adapter = PlantAdapter(context,item)
        recyclerView.adapter=adapter
        return view
    }
    fun updateList(){
       item.clear()
        item.add(PlantsList("식물A","1000원","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물B","2000원","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물C","3000원","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
    }
}