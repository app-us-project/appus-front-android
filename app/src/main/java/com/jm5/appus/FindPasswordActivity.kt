package com.jm5.appus

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity() {
    var checkSum1=false
    var checkSum2=false
    var checkSum3=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        searchPw_back.setOnClickListener {
            onBackPressed()
        }
        searchPw_edit_email.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(searchPw_edit_email.text.toString().equals("")||searchPw_edit_email.text.toString()==null){
                    checkSum1=false
                    searchPw_edit_email.setBackgroundResource(R.drawable.roundselector)
                }
                else if(!(searchPw_edit_email.text.toString().equals("")||searchPw_edit_email.text.toString()==null)){
                    checkSum1=true
                    searchPw_edit_email.setBackgroundResource(R.drawable.box)
                }
                if((checkSum1&&checkSum2)&&checkSum3){
                    searchPw_change_password.isClickable=true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                }
                else{
                    searchPw_change_password.isClickable=false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        searchPw_edit_phone.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(searchPw_edit_phone.text.toString().equals("")||searchPw_edit_phone.text.toString()==null){
                    checkSum2=false
                    searchPw_edit_phone.setBackgroundResource(R.drawable.roundselector)
                }else if(!(searchPw_edit_phone.text.toString().equals("")||searchPw_edit_phone.text.toString()==null)){
                    checkSum2=true
                    searchPw_edit_phone.setBackgroundResource(R.drawable.box)
                }
                if((checkSum1&&checkSum2)&&checkSum3){
                    searchPw_change_password.isClickable=true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                }
                else{
                    searchPw_change_password.isClickable=false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        searchPw_edit_verification.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(searchPw_edit_verification.text.toString().equals("")||searchPw_edit_verification.text.toString()==null){
                    checkSum3=false
                    searchPw_edit_verification.setBackgroundResource(R.drawable.roundselector)
                }else if(!(searchPw_edit_verification.text.toString().equals("")||searchPw_edit_verification.text.toString()==null)){
                    checkSum3=true
                    searchPw_edit_verification.setBackgroundResource(R.drawable.box)
                }
                if((checkSum1&&checkSum2)&&checkSum3){
                    searchPw_change_password.isClickable=true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                }
                else{
                    searchPw_change_password.isClickable=false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        searchPw_change_password
    }
    //focus가 아닌 곳 터치시 키패드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev!!.x.toInt()
            val y = ev!!.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm != null) imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}