package com.jm5.appus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val name : String? = intent.getStringExtra("plantName")
        val img : String? = intent.getStringExtra("plantImg")
        val price : String? = intent.getStringExtra("plantPrice")

       initInfo(name, img, price)

        back_bot.setOnClickListener {
            onBackPressed()
        }
        back_top.setOnClickListener {
            onBackPressed()
        }

    }
    fun initInfo(name:String?,img:String?, price:String?){
        content_name_top.text=name
        content_name.text=name
        content_price.text=price
        var url = img
        Glide.with(this)
            .load(url)
            .centerCrop()
//            .into(img)
    }
}