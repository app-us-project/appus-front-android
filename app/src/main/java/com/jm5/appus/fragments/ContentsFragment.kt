package com.jm5.appus.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jm5.appus.ContentActivity
import com.jm5.appus.OnItemClickListener
import com.jm5.appus.R
import com.jm5.appus.adapter.OurStoryAdapter
import com.jm5.appus.adapter.PlantAdapter
import com.jm5.appus.dataForm.OurStoryList
import com.jm5.appus.dataForm.PlantsList
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class ContentsFragment : Fragment() {
    var item = ArrayList<PlantsList>()
    var list = ArrayList<OurStoryList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_contents, container, false)
        updateList()
        var context = view.context
        var recyclerView= view.findViewById<RecyclerView>(R.id.recylerview)
        val recyclerView2: RecyclerView = view.findViewById(R.id.recyclerview2)
//        recyclerView.setHasFixedSize(true)

        var layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager=layoutManager.also { it.orientation=LinearLayoutManager.HORIZONTAL }

        var adapter = PlantAdapter(context,item)
        recyclerView.adapter=adapter

        recyclerView2.layoutManager = LinearLayoutManager(activity)
        recyclerView2.adapter = OurStoryAdapter(list)

        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(holder: PlantAdapter.ViewHolder, view: View, position: Int) {
                var item = adapter.getItem(position)

                var intent = Intent(context,ContentActivity::class.java)
                intent.putExtra("plantName",item.name)
                intent.putExtra("plantImg",item.img)
                intent.putExtra("plantPrice",item.prices)

                startActivity(intent)
            }
        })
        return view

//        var recyclerView1= view.findViewById<RecyclerView>(R.id.recylerview)
//        val recyclerView2: RecyclerView = view.findViewById(R.id.rv_ourstory)
//
//        recyclerView1.setHasFixedSize(true)
//        var layoutManager = GridLayoutManager(context,2)
//        recyclerView1.layoutManager=layoutManager
//        var adapter = PlantAdapter(context,item)
//        recyclerView1.adapter=adapter
//
//        recyclerView2.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }
//        recyclerView2.adapter = OurStoryAdapter(list)
//
//        adapter.setItemListener(object : OnItemClickListener {
//            override fun onItemClick(holder: PlantAdapter.ViewHolder, view: View, position: Int) {
//                var item = adapter.getItem(position)
//
//                var intent = Intent(context, ContentActivity::class.java)
//                intent.putExtra("plantName",item.name)
//                intent.putExtra("plantImg",item.img)
//                intent.putExtra("plantPrice",item.prices)
//
//                startActivity(intent)
//            }
//        })
//        return view
    }
    fun updateList(){
        list.clear()
        list.add(OurStoryList("test1"))
        list.add(OurStoryList("test2"))
        list.add(OurStoryList("test3"))
        list.add(OurStoryList("test4"))
        list.add(OurStoryList("test5"))

        item.clear()
        item.add(PlantsList("식물A","1000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물B","2000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
        item.add(PlantsList("식물C","3000","https://cdn.pixabay.com/photo/2016/03/21/23/25/link-1271843_1280.png"))
    }
}