package com.jm5.appus

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jm5.appus.dataForm.ChangePassword
import com.jm5.appus.retrofit.MasterApplication
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {
    var checkSumPass1 = false
    var checkSumPass2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //비밀번호 형식 체크
        edit_new_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", edit_new_password.text.toString())){
                    edit_new_password.setBackgroundResource(R.drawable.roundselector)
                    checkSumPass1=false
                }else{
                    edit_new_password.setBackgroundResource(R.drawable.box)
                    checkSumPass1=true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //비밀번호 확인
        edit_new_password_again.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!edit_new_password_again.text.toString().equals(edit_new_password_again.text.toString())){
                    edit_new_password_again.setBackgroundResource(R.drawable.roundselector)
                    checkSumPass2=false
                }else{
                    edit_new_password_again.setBackgroundResource(R.drawable.box)
                    checkSumPass2=true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        btn_change_password_ok.setOnClickListener{
            if(checkSumPass1&&checkSumPass2) changePassword(this)
            else Toast.makeText(this,"정보를 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }
    //비밀번호변경
    private fun changePassword(activity: Activity){
        val intent =intent
        val email = intent.extras?.getString("email").toString()
        val password = edit_new_password.text.toString()
        val passwordCheck = edit_new_password_again.text.toString()
        val phoneNumber = intent.extras?.getString("phoneNumber").toString()
        Log.e("ChangePassword?>>", "${email}${password}${passwordCheck}${phoneNumber}")
        val user = ChangePassword(email, password, passwordCheck, phoneNumber)

        (application as MasterApplication).service.changePassword(
                user
        ).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, """비밀번호 변경에 실패했습니다
                   ${t.message}
                """.trimMargin(), Toast.LENGTH_LONG).show()
                Log.e("ChangePassword?>>","fail, ${t.message}")
            }
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(activity,  "비밀번호 변경 완료!", Toast.LENGTH_LONG).show()
                    Log.e("ChangePassword?>>","Success, ${response.body().toString()}")
//                    val token = response.headers().get("X-AUTH-TOKEN").toString()
//                    saveUserToken(token, activity)
//                    (application as MasterApplication).createRetrofit()
                    finish()
                }
                else {
                    Toast.makeText(activity,  response.body().toString(), Toast.LENGTH_LONG).show()
                    Log.e("ChangePassword?>>","Error, ${response.body().toString()}")
                }
            }
        })
    }

    //focus가 아닌 곳 터치시 키패드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView: View? =currentFocus
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