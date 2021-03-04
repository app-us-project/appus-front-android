package com.jm5.appus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.Toast
import com.jm5.appus.dataForm.Sign
import com.jm5.appus.dataForm.TermsAgreementDto
import com.jm5.appus.dataForm.Verification
import com.jm5.appus.retrofit.MasterApplication
import com.jm5.appus.retrofit.RetrofitService
import kotlinx.android.synthetic.main.activity_sign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        backBtn.setOnClickListener { onBackPressed() }
        allCheckBtn.setOnClickListener { onCheckChanged(allCheckBtn) }
        firstCheckBtn.setOnClickListener { onCheckChanged(firstCheckBtn) }

        btnTakeNum.setOnClickListener {
            TakeNum(this)
        }

        btnOk.setOnClickListener {
            CheckVerification(this)
        }

        sign_button.setOnClickListener {
            register(this)
        }
    }
    fun TakeNum(activity: Activity){
        val phoneNum = editPhoneNumber.text.toString()

        (application as MasterApplication).service.phoneNumCheck(
            phoneNum
        ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "인증번호 보내기 실패!", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(activity, "인증번호 보내기 성공!", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun CheckVerification(activity: Activity){
        val phoneNum = editPhoneNumber.text.toString()
        val verificationNum = editSignNumber.text.toString()

        val checkBody= "$phoneNum/$verificationNum"

        (application as MasterApplication).service.checkVeri(
            checkBody
        ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "인증 실패", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(activity, "인증 성공", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun register(activity: Activity){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        val passwordCheck = editCheckPassword.text.toString()
        val phoneNumber = editPhoneNumber.text.toString()

        lateinit var user : Sign
        lateinit var agreement: ArrayList<TermsAgreementDto>

        user.email=email
        user.password=password
        user.passwordCheck=passwordCheck
        user.phoneNumber=phoneNumber

        if(allCheckBtn.isChecked){
            agreement.add(TermsAgreementDto(1,true))
            agreement.add(TermsAgreementDto(2,true))
            agreement.add(TermsAgreementDto(3,true))
        }
        user.terms=agreement

        (application as MasterApplication).service.register(
            user
        ).enqueue(object : Callback<Verification>{
            override fun onFailure(call: Call<Verification>, t: Throwable) {
                Toast.makeText(activity, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                Toast.makeText(activity,  response.body().toString(), Toast.LENGTH_LONG).show()

                val token = response.headers().get("X-AUTH-TOKEN").toString()
                    saveUserToken(token, activity)
                    (application as MasterApplication).createRetrofit()
//                    activity.startActivity(
//                        Intent(activity, LoginActivity::class.java)
//                    )
                finish()
            }
        })
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

    private fun onCheckChanged(compoundButton: CompoundButton) {
        when(compoundButton.id) {
            R.id.allCheckBtn -> {
                if (allCheckBtn.isChecked) {
                    firstCheckBtn.isChecked = true
                }else {
                    firstCheckBtn.isChecked = false
                }
            }
        }
    }
    // 토큰 받아서 SharedPreference에 저장
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.apply()
    }

    fun getPhoneNum():String{
        return editPhoneNumber.text.toString()
    }
}