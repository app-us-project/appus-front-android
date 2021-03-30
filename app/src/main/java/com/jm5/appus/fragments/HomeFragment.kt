package com.jm5.appus.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jm5.appus.ContentActivity
import com.jm5.appus.OnItemClickListener
import com.jm5.appus.R
import com.jm5.appus.adapter.OurStoryAdapter
import com.jm5.appus.adapter.PlantAdapter
import com.jm5.appus.dataForm.LoadProducts
import com.jm5.appus.dataForm.OurStoryList
import com.jm5.appus.dataForm.PlantsList
import com.jm5.appus.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    var list = ArrayList<OurStoryList>()
    var item = java.util.ArrayList<PlantsList>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var context = view.context
        updateList()

        var recyclerView1= view.findViewById<RecyclerView>(R.id.recylerview)
        val recyclerView2: RecyclerView = view.findViewById(R.id.rv_ourstory)

        recyclerView1.setHasFixedSize(true)
        var layoutManager = GridLayoutManager(context,2)
        recyclerView1.layoutManager=layoutManager
        var adapter = PlantAdapter(context,item)
        recyclerView1.adapter=adapter

        recyclerView2.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        recyclerView2.adapter = OurStoryAdapter(list)

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

        val pref = context?.getSharedPreferences("login_token",Context.MODE_PRIVATE)
        val test1 = pref?.getString("login_token","null@@#")

        Log.e("loadProducts>>", test1.toString())

        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.ward-study.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.loadProducts(test1).enqueue(object : Callback<LoadProducts>{
            override fun onFailure(call: Call<LoadProducts>, t: Throwable) {
                Log.e("loadProducts>>","fail")
            }

            override fun onResponse(call: Call<LoadProducts>, response: Response<LoadProducts>) {
                if(response.isSuccessful){
                    Log.e("loadProducts>>",response.body().toString())

                }else{
                    Log.e("loadProducts>>","err, ${response.body()}, ${response.code()}")
                }
            }
        })
    }
}