package com.jm5.appus

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.jm5.appus.dataForm.SearchPassword
import com.jm5.appus.retrofit.MasterApplication
import kotlinx.android.synthetic.main.activity_find_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class FindPasswordActivity : AppCompatActivity() {
    var checkSum1=false
    var checkSum2=false
    var checkSum3=false
    var checkSumPhone=false
    var checkSumPhoneVerifiy=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        searchPw_back.setOnClickListener{
            onBackPressed()
        }
        searchPw_edit_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (searchPw_edit_email.text.toString().equals("") || searchPw_edit_email.text.toString() == null) {
                    checkSum1 = false
                    searchPw_edit_email.setBackgroundResource(R.drawable.roundselector)
                } else if (!(searchPw_edit_email.text.toString().equals("") || searchPw_edit_email.text.toString() == null)) {
                    checkSum1 = true
                    searchPw_edit_email.setBackgroundResource(R.drawable.box)
                }
                if ((checkSum1 && checkSum2) && checkSum3) {
                    searchPw_change_password.isClickable= true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                } else {
                    searchPw_change_password.isClickable= false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        searchPw_edit_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (searchPw_edit_phone.text.toString().equals("") || searchPw_edit_phone.text.toString() == null) {
                    checkSum2 = false
                    searchPw_edit_phone.setBackgroundResource(R.drawable.roundselector)
                } else if (!(searchPw_edit_phone.text.toString().equals("") || searchPw_edit_phone.text.toString() == null)) {
                    checkSum2 = true
                    searchPw_edit_phone.setBackgroundResource(R.drawable.box)
                }
                if ((checkSum1 && checkSum2) && checkSum3) {
                    searchPw_change_password.isClickable= true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                } else {
                    searchPw_change_password.isClickable= false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        searchPw_edit_verification.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (searchPw_edit_verification.text.toString().equals("") || searchPw_edit_verification.text.toString() == null) {
                    checkSum3 = false
                    searchPw_edit_verification.setBackgroundResource(R.drawable.roundselector)
                } else if (!(searchPw_edit_verification.text.toString().equals("") || searchPw_edit_verification.text.toString() == null)) {
                    checkSum3 = true
                    searchPw_edit_verification.setBackgroundResource(R.drawable.box)
                }
                if ((checkSum1 && checkSum2) && checkSum3) {
                    searchPw_change_password.isClickable= true
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground)
                } else {
                    searchPw_change_password.isClickable= false
                    searchPw_change_password.setBackgroundResource(R.drawable.buttonbackground_false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //비밀번호 변경으로 화면전환
        searchPw_change_password.setOnClickListener{
            val nextIntent = Intent(this, ChangePasswordActivity::class.java)
            nextIntent.putExtra("email", searchPw_edit_email.text.toString())
            nextIntent.putExtra("phoneNumber", searchPw_edit_phone.text.toString())
            startActivity(nextIntent)
            finish()
        }
//휴대폰 인증 번호 요청시 null 체크 후, takeNum실행
        searchPw_verification_phone.setOnClickListener{
            if (!(searchPw_edit_phone.equals("") || searchPw_edit_phone == null))
                takeNum(this)
            else
                Toast.makeText(this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
//휴대폰 번호 유효성 검사
        searchPw_edit_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!Pattern.matches("^01(?:0|1|[6-9])\\d{4}\\d{4}$", searchPw_edit_phone.text.toString())) {
                    checkSumPhone = false
                    searchPw_verification_phone.isClickable= false
                    searchPw_edit_phone.setBackgroundResource(R.drawable.roundselector)
                } else {
                    searchPw_verification_phone.isClickable= true
                    searchPw_edit_phone.setBackgroundResource(R.drawable.box)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //인증번호 확인버튼 리스너
        searchPw_verification_ok.setOnClickListener{
            checkVerification(this)
        }
//인증번호 null 체크 + 확인버튼 활성/비활성
        searchPw_edit_verification.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!(searchPw_edit_verification.text.toString().equals("") || searchPw_edit_verification.text.toString() == null)) {
                    searchPw_verification_ok.isClickable= true
                } else {
                    searchPw_verification_ok.isClickable= false
                    checkSumPhoneVerifiy = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
    //번호 인증
    fun takeNum(activity: Activity){
        val phoneNum = searchPw_edit_phone.text.toString()
        val email = searchPw_edit_email.text.toString()

        val user = SearchPassword(email, phoneNum)
        (application as MasterApplication).service.searchPassword(
                user
        ).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                checkSumPhone=false
                Toast.makeText(activity, "인증번호 보내기 실패!", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","번호인증 실패,${t.message.toString()}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    checkSumPhone=true
                    Toast.makeText(activity, "인증번호 보내기 성공!", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 성공, ${response.message()}, ${response.body()?.toString()}")
                }else{
                    Toast.makeText(activity, "가입되지 않은 회원정보입니다.", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 err, ${response.errorBody()}, ${response.headers()}, ${response.body()?.toString()}")
                }
            }
        })
    }
    //인증번호 확인
    fun checkVerification(activity: Activity){
        val phoneNum = searchPw_edit_phone.text.toString()
        val verificationNum = searchPw_edit_verification.text.toString()

        val checkBody= "$phoneNum/$verificationNum"

        (application as MasterApplication).service.searchPasswordCheckVeri(
                checkBody
        ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "인증 실패", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","인증번호 확인 실패, ${t.message.toString()}")
                checkSumPhoneVerifiy=false
                searchPw_change_password.isClickable=false
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(activity, "인증 성공", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","인증번호 확인 성공, ${response.body().toString()}")
                checkSumPhoneVerifiy=true
                searchPw_change_password.isClickable=true
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