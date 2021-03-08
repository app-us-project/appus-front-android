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
import android.widget.CompoundButton
import android.widget.Toast
import com.jm5.appus.dataForm.Sign
import com.jm5.appus.dataForm.TermsAgreementDto
import com.jm5.appus.dataForm.Verification
import com.jm5.appus.retrofit.MasterApplication
import kotlinx.android.synthetic.main.activity_sign.*
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignActivity : AppCompatActivity() {
    var checkSumEmail = false
    var checkSumPass1 = false
    var checkSumPass2 = false
    var checkSumPhone = false
    var checkSumPhoneVerifiy = false
    var checkSumForm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        backBtn.setOnClickListener { onBackPressed() }
        allCheckBtn.setOnClickListener { onCheckChanged(allCheckBtn) }
        firstCheckBtn.setOnClickListener { onCheckChanged(firstCheckBtn) }

        //이메일 형식 체크
        editEmail.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editEmail.text.toString()).matches()){
                    editEmail.setBackgroundResource(R.drawable.roundselector)
                    checkSumEmail=false
                }else{
                    editEmail.setBackgroundResource(R.drawable.box)
                    checkSumEmail=true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        })
        //비밀번호 형식 체크
        editPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", editPassword.text.toString())){
                    editPassword.setBackgroundResource(R.drawable.roundselector)
                    checkSumPass1=false
                }else{
                    editPassword.setBackgroundResource(R.drawable.box)
                    checkSumPass1=true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //비밀번호 확인
        editCheckPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!editPassword.text.toString().equals(editCheckPassword.text.toString())){
                    editCheckPassword.setBackgroundResource(R.drawable.roundselector)
                    checkSumPass2=false
                }else{
                    editCheckPassword.setBackgroundResource(R.drawable.box)
                    checkSumPass2=true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //약관 전체 동의 버튼 리스너
        allCheckBtn.setOnCheckedChangeListener { p0, p1 ->
            if(!p1){
                checkSumForm=false
                sign_button.isClickable=false
                sign_button.setBackgroundResource(R.drawable.buttonbackground_false)
            }else checkSumForm=true
        }
        //휴대폰 인증 번호 요청시 null 체크 후, TakeNum실행
        btnTakeNum.setOnClickListener {
            if(!(editPhoneNumber.equals("")||editPhoneNumber==null))
                TakeNum(this)
            else
                Toast.makeText(this,"휴대폰 번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
        //휴대폰 번호 유효성 검사
        editPhoneNumber.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^01(?:0|1|[6-9])\\d{4}\\d{4}$", editPhoneNumber.text.toString())){
                    checkSumPhone=false
                    btnTakeNum.isClickable=false
                    editPhoneNumber.setBackgroundResource(R.drawable.roundselector)
                }
                else{
                    btnTakeNum.isClickable=true
                    editPhoneNumber.setBackgroundResource(R.drawable.box)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //인증번호 확인 버튼 리스너
        btnOk.setOnClickListener {
            CheckVerification(this)
        }
        //인증번호 null 체크 + 확인버튼 활성/비활성
        editSignNumber.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!(editSignNumber.text.toString().equals("")||editSignNumber.text.toString()==null)){
                    btnOk.isClickable = true
                }else {
                    btnOk.isClickable=false
                    checkSumPhoneVerifiy=false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        sign_button.setOnClickListener {
            if(checkSumEmail&&checkSumPass1&&checkSumPass2&&checkSumPhone&&checkSumPhoneVerifiy&&checkSumForm) register(this)
            else Toast.makeText(this,"정보를 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }
    //번호 인증
    fun TakeNum(activity: Activity){
        val phoneNum = editPhoneNumber.text.toString()

        (application as MasterApplication).service.phoneNumCheck(
            phoneNum
        ).enqueue(object : Callback<Verification>{
            override fun onFailure(call: Call<Verification>, t: Throwable) {
                checkSumPhone=false
                Toast.makeText(activity, "인증번호 보내기 실패!", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","번호인증 실패,${call.execute().message().toString()} ,${t.message.toString()}")
            }

            override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                if(response.isSuccessful){
                    checkSumPhone=true
                    Toast.makeText(activity, "인증번호 보내기 성공!", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 성공, ${response.message()}, ${response.body()?.message}")
                }else{
                    Toast.makeText(activity, "이미 가입된 휴대폰 번호입니다", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 err, ${response.errorBody()}, ${response.headers()}, ${response.body()?.message}")
                }
            }
        })
    }
    //인증번호 확인
    fun CheckVerification(activity: Activity){
        val phoneNum = editPhoneNumber.text.toString()
        val verificationNum = editSignNumber.text.toString()

        val checkBody= "$phoneNum/$verificationNum"

        (application as MasterApplication).service.checkVeri(
            checkBody
        ).enqueue(object : Callback<Verification>{
            override fun onFailure(call: Call<Verification>, t: Throwable) {
                Toast.makeText(activity, "인증 실패", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","인증번호 확인 실패, ${call.toString()}, ${t.message.toString()}")
                checkSumPhoneVerifiy=false
            }

            override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                Toast.makeText(activity, "인증 성공", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","인증번호 확인 성공, ${call.toString()}, ${response.body().toString()}")
                checkSumPhoneVerifiy=true
            }
        })
    }
    //가입
    private fun register(activity: Activity){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        val passwordCheck = editCheckPassword.text.toString()
        val phoneNumber = editPhoneNumber.text.toString()
        //lateinit var agreement : Array<TermsAgreementDto>
        val agreement = arrayOf(
            TermsAgreementDto(1,true),
            TermsAgreementDto(2,true),
            TermsAgreementDto(3,true)
        )
        //약관동의 ( 1,2번은 필수, 3번은 선택 )
        //allCheckBtn
//        if(allCheckBtn.isChecked){
//            agreement[0]= TermsAgreementDto(1,true)
//            agreement[1]=TermsAgreementDto(2,true)
//            agreement[2]=TermsAgreementDto(3,true)
//        }else{
//            sign_button.setBackgroundResource(R.drawable.buttonbackground_false)
//            sign_button.isClickable=false
//            return
//        }
        val user = Sign(email,password,passwordCheck,phoneNumber,agreement)

        (application as MasterApplication).service.register(
            user
        ).enqueue(object : Callback<Verification>{
            override fun onFailure(call: Call<Verification>, t: Throwable) {
                Toast.makeText(activity, """회원가입에 실패했습니다
                    |${call }|${t.message}
                """.trimMargin(), Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","fail, ${call.toString()}, ${t.message}")
            }
            override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                Toast.makeText(activity,  response.body().toString(), Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","Success, ${call.toString()}, ${response.body().toString()}")
                val token = response.headers().get("X-AUTH-TOKEN").toString()
                    saveUserToken(token, activity)
                    (application as MasterApplication).createRetrofit()
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
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm != null) imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    // 토큰 받아서 SharedPreference에 저장
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.apply()
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
}