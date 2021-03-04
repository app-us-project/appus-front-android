package com.jm5.appus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var checkSum1=false
    var checkSum2=false
    var backPressedTime :Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        }
        find_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity,FindPasswordActivity::class.java))
        }
        register.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignActivity::class.java))
        }
        //입력하지 않았을때 빨게지게 + 로그인 버튼 활성/비활성
        edit_login_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
               if(edit_login_email.text.toString().equals("")||edit_login_email.text.toString()==null) {
                   checkSum1=false
                   edit_login_email.setBackgroundResource(R.drawable.roundselector)
               }
               else if(!(edit_login_email.text.toString().equals("")||edit_login_email.text.toString()==null)) {
                   checkSum1=true
                   edit_login_email.setBackgroundResource(R.drawable.box)
               }
                if(checkSum1&&checkSum2){
                    btn_login.isClickable=true
                    btn_login.setBackgroundResource(R.drawable.buttonbackground)
                }
                else{
                    btn_login.isClickable=false
                    btn_login.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        edit_login_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(edit_login_password.text.toString().equals("")||edit_login_password.text.toString()==null) {
                    checkSum2=false
                    edit_login_password.setBackgroundResource(R.drawable.roundselector)
                }
                else if(!(edit_login_password.text.toString().equals("")||edit_login_password.text.toString()==null)) {
                    checkSum2=true
                    edit_login_password.setBackgroundResource(R.drawable.box)
                }
                if(checkSum1&&checkSum2){
                    btn_login.isClickable=true
                    btn_login.setBackgroundResource(R.drawable.buttonbackground)
                }
                else{
                    btn_login.isClickable=false
                    btn_login.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })


    }
    //뒤로가기 두번 누를 시 종료
    override fun onBackPressed() {
        if(System.currentTimeMillis()>backPressedTime+2000){
            backPressedTime=System.currentTimeMillis()
            Toast.makeText(this,"뒤로가기 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }else{
            moveTaskToBack(true)// 태스크를 백그라운드로 이동
            finishAndRemoveTask()// 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid())// 앱 프로세스 종료
        }
    }
}