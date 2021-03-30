package com.jm5.appus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.jm5.appus.adapter.FragmentAdapter
import com.jm5.appus.fragments.ContentsFragment
import com.jm5.appus.fragments.HomeFragment
import com.jm5.appus.fragments.LikeFragment
import com.jm5.appus.fragments.MyPageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentList = listOf(
            HomeFragment(), ContentsFragment(), LikeFragment(), MyPageFragment()
        )

        val adapter = FragmentAdapter(supportFragmentManager,1)
        adapter.fragmentList=fragmentList
        viewpager.adapter=adapter

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            // 네비게이션 메뉴 아이템 체크상태
            override fun onPageSelected(position: Int) {
                Log.e("check->","position : "+position)
                bottomnavi.menu.getItem(position).isChecked=true
                if(position==0){toolbar_title.text="심다"}
                if(position==1){toolbar_title.text="심다"}
                if(position==2){toolbar_title.text="찜"}
                if(position==3){ toolbar_title.text="마이페이지"}
            }
        })

        bottomnavi.setOnNavigationItemSelectedListener {
            when(it.itemId){
                // itemId에 따라 viewPager 바뀜
                R.id.menu_home -> viewpager.currentItem = 0
                R.id.menu_contents -> viewpager.currentItem = 1
                R.id.menu_like -> viewpager.currentItem = 2
                R.id.menu_mypage -> viewpager.currentItem = 3
            }
            true
        }

    }
}