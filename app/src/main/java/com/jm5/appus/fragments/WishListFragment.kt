//package com.jm5.appus.fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.GridLayoutManager
//import com.jm5.appus.dataForm.WishPlantsList
//import com.jm5.appus.R
//import kotlinx.android.synthetic.main.fragment_wish_list.*
//
//class WishListFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//
//        return inflater.inflate(R.layout.fragment_complete_order, container, false)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_wish_list)
//
//        val plantList = arrayListOf(
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기", "7800원", R.drawable.like),
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.flower, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.flower, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.flower, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.plant, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//            WishPlantsList(R.drawable.flower, "너만바라볼거야 해바라기","7800원",R.drawable.like),
//        )
//
//        recyclerView.layoutManager = GridLayoutManager(this,2)
//        recyclerView.setHasFixedSize(true) // 성능개선
//
//        recyclerView.adapter = WishListAdapter(plantList)
//    }
//}