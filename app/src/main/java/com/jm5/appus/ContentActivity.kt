package com.jm5.appus

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val name : String? = intent.getStringExtra("plantName")
        val img : String? = intent.getStringExtra("plantImg")
        val price : String? = intent.getStringExtra("plantPrice")
        var total_count:Int =1
        var whole_price:Int

        initInfo(name, img, price)

        var isUp = false
        val translateup: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.translate_up)
        val translatedown: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.translate_down)

        //slidingPage 이 외의 영역 터치시 slidingpage 내림
        whole_view.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (isUp == true){
                    sliding_page.startAnimation(translatedown)
                    sliding_page.visibility=View.GONE
                    bot_button.visibility=View.VISIBLE
                    bot_button.startAnimation(translateup)
                    isUp = false
                }
                return false
            }
        })
        scrollview.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if(p1?.action==MotionEvent.ACTION_DOWN) {
                    if (isUp == true){
                        sliding_page.startAnimation(translatedown)
                        sliding_page.visibility=View.GONE
                        bot_button.visibility=View.VISIBLE
                        bot_button.startAnimation(translateup)
                        isUp = false
                    }
                }
                return false
            }
        })

        back_bot.setOnClickListener {
            onBackPressed()
        }
        back_top.setOnClickListener {
            onBackPressed()
        }

        up_order.setOnClickListener {
            isUp = true
            bot_button.startAnimation(translatedown)
            bot_button.visibility=View.GONE
            sliding_page.visibility=View.VISIBLE
            //뒷 레이아웃 터치를 막음
            sliding_page.isClickable=true
            sliding_page.startAnimation(translateup)
        }
        plus_button.setOnClickListener {
            count_product.text=(++total_count).toString()
            whole_price=total_count*price!!.toInt()
            total_price.text= whole_price.toString()
        }
        min_button.setOnClickListener {
            if(total_count<=0) whole_price=0
            else{
                count_product.text=(--total_count).toString()
                whole_price=total_count*price!!.toInt()
                total_price.text= whole_price.toString()
            }
        }

    }
    fun initInfo(name:String?,img:String?, price:String?){
        content_name_top.text=name
        content_name.text=name
        content_price.text=price
        total_name.text=name
        total_price.text=price
        count_product.text= 1.toString()

        var url = img
        Glide.with(this)
            .load(url)
            .centerCrop()
            .error(R.drawable.appus_logo)
            .fallback(R.drawable.appus_logo)
            .into(content_img)

    }
}