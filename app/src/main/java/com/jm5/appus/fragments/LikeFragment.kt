package com.jm5.appus.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jm5.appus.ContentActivity
import com.jm5.appus.OnItemClickListener
import com.jm5.appus.R
import com.jm5.appus.adapter.PlantAdapter
import com.jm5.appus.dataForm.PlantsList
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.fragment_like.view.*
import java.util.ArrayList

class LikeFragment : Fragment() {
    var item = ArrayList<PlantsList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_like, container, false)
        var context = view.context
        updateList()
        if(item.size==0) {
            view.if_exist_like.visibility = View.GONE
            view.if_no_like.visibility = View.VISIBLE
        }
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.setHasFixedSize(true)
        var layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        var adapter = PlantAdapter(context, item)
        recyclerView.adapter = adapter


        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(holder: PlantAdapter.ViewHolder, view: View, position: Int) {
                var item = adapter.getItem(position)

                var intent = Intent(context, ContentActivity::class.java)
                intent.putExtra("plantName",item.name)
                intent.putExtra("plantImg",item.img)
                intent.putExtra("plantPrice",item.prices)

                startActivity(intent)
            }
        })
        return view
    }
    fun updateList(){
        item.clear()
        item.add(PlantsList("식물A","1000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물B","2000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물C","3000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
    }
}