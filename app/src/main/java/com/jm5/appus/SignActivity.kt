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
import com.jm5.appus.dataForm.*
import com.jm5.appus.retrofit.MasterApplication
import kotlinx.android.synthetic.main.activity_sign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SignActivity : AppCompatActivity() {
    var checkSumEmail = false
    var checkSumPass1 = false
    var checkSumPass2 = false
    //test
    var checkSumPhone = false
    //test
    var checkSumPhoneVerifiy = false
    var checkSumForm1 = false
    var checkSumForm2 = false
    var checkSumForm3 = false
    var signCheck = false

    val terms1 = HashMap<String, Any>()
    val terms2 = HashMap<String, Any>()
    val terms3 = HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        backBtn.setOnClickListener { onBackPressed() }
        allCheckBtn.setOnClickListener { onCheckChanged(allCheckBtn) }
        sign_term1_checkbox.setOnClickListener { onCheckChanged(sign_term1_checkbox) }
        sign_term2_checkbox.setOnClickListener { onCheckChanged(sign_term2_checkbox) }
        sign_term3_checkbox.setOnClickListener { onCheckChanged(sign_term3_checkbox) }

        sign_term1_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            checkSumForm1 = b
            if(b) {
                terms1.clear()
                terms1.put("agreement", true)
                terms1.put("termsId", 1)
            }else{
                terms1.clear()
                terms1.put("agreement", false)
                terms1.put("termsId", 1)
            }
        }

        sign_term2_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            checkSumForm2 = b
            if(b) {
                terms2.clear()
                terms2.put("agreement", true)
                terms2.put("termsId", 2)
            }else{
                terms2.clear()
                terms2.put("agreement", false)
                terms2.put("termsId", 2)
            }
        }
        sign_term3_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            checkSumForm3 = b
            if(b) {
                terms3.clear()
                terms3.put("agreement", true)
                terms3.put("termsId", 3)
            }else{
                terms3.clear()
                terms3.put("agreement", false)
                terms3.put("termsId", 3)
            }
        }
        //동의 약관 로드
        loadTerms()
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
//        allCheckBtn.setOnCheckedChangeListener { p0, p1 ->
//            if(!p1){
//                checkSumForm=false
//                sign_button.isClickable=false
//                sign_button.setBackgroundResource(R.drawable.buttonbackground_false)
//            }else checkSumForm=true
//        }
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
                    checkSumPhone = true
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
                    checkSumPhoneVerifiy = true
                }else {
                    btnOk.isClickable=false
                    checkSumPhoneVerifiy=false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

//        if(checkSumEmail&&checkSumPass1&&checkSumPass2&&checkSumPhone&&checkSumPhoneVerifiy&&checkSumForm1&&checkSumForm2){
//                sign_button.setBackgroundResource(R.drawable.buttonbackground)
//                sign_button.isClickable=true
//                signCheck = true
//            }else{
//                sign_button.setBackgroundResource(R.drawable.buttonbackground_false)
//                sign_button.isClickable=false
//                signCheck = false
//        }

        sign_button.setOnClickListener {
            if(checkSumEmail&&checkSumPass1&&checkSumPass2&&checkSumPhone&&checkSumPhoneVerifiy&&checkSumForm1&&checkSumForm2)
                register(this)
            else Toast.makeText(this,"정보를 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }
    //번호 인증
    fun TakeNum(activity: Activity){
        val phoneNum = editPhoneNumber.text.toString()

        (application as MasterApplication).service.phoneNumCheck(
            phoneNum
        ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
//                checkSumPhone=false
                Toast.makeText(activity, "인증번호 보내기 실패!", Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","번호인증 실패,${t.localizedMessage} ,${t.message.toString()}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
//                    checkSumPhone=true
                    Toast.makeText(activity, "인증번호 보내기 성공!", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 성공, ${response.message()}, ${response.message()}")
                }else{
                    Toast.makeText(activity, "이미 가입된 휴대폰 번호입니다", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","번호인증 err, ${response.errorBody()}, ${response.headers()}, ${response.message()}")
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
                Log.e("Sign?>>","인증번호 확인 실패, ${t.message.toString()}")
//                checkSumPhoneVerifiy=false
            }

            override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "인증 성공", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","인증번호 확인 성공, ${response.body()?.message}")
//                    checkSumPhoneVerifiy=true
                }else{
                    Toast.makeText(activity, "인증 err", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>","인증번호 err, ${response.body()?.message}")
                }

            }
        })
    }
    //가입
    private fun register(activity: Activity){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        val passwordCheck = editCheckPassword.text.toString()
        val phoneNumber = editPhoneNumber.text.toString()

//        val terms1 = HashMap<String, Any>()
//        terms1.put("agreement", true)
//        terms1.put("termsId", 1)
//
//
//        val terms2 = HashMap<String, Any>()
//        terms2.put("agreement", true)
//        terms2.put("termsId", 2)
//
//
//        val terms3 = HashMap<String, Any>()
//        terms3.put("agreement", true)
//        terms3.put("termsId", 3)

        val agreement = listOf(
                terms1,terms2,terms3
        )
        val user = Sign(email,password,passwordCheck,phoneNumber,agreement)

        (application as MasterApplication).service.register(
//                email,password,passwordCheck,phoneNumber,agreement
            user
        ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, """회원가입에 실패했습니다
                   ${t.message}
                """.trimMargin(), Toast.LENGTH_LONG).show()
                Log.e("Sign?>>","fail, ${t.message}")
            }
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    Log.e("Sign?>>", "Success,  ${response.body().toString()}")
                    finish()
                }
                else{
                    Log.e("Sign?>>","err${response.code()}${response.body()}${response.message()}")
                    Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show()
//                    Log.e("Sign?>>",user.toString())
                }
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

    private fun onCheckChanged(compoundButton: CompoundButton) {
        when(compoundButton.id) {
            R.id.allCheckBtn -> {
                if (allCheckBtn.isChecked) {
                    sign_term1_checkbox.isChecked = true
                    sign_term2_checkbox.isChecked = true
                    sign_term3_checkbox.isChecked = true
                }else {
                    sign_term1_checkbox.isChecked = false
                    sign_term2_checkbox.isChecked = false
                    sign_term3_checkbox.isChecked = false
                }
            }
        }
    }
    //이용약관 데이터 로드
    private fun loadTerms(){
        (application as MasterApplication).service.loadTerms("SIGN_UP").enqueue(
                object : Callback<List<Terms_SignUp>>{
                    override fun onFailure(call: Call<List<Terms_SignUp>>, t: Throwable) {
                        Log.e("Terms_Load_fail>>",t.message.toString())
                    }

                    override fun onResponse(call: Call<List<Terms_SignUp>>, response: Response<List<Terms_SignUp>>) {
                        if(response.isSuccessful) {
                            var body = response.body()

                            sign_term1.text=body?.get(0)?.title+"\n"+body?.get(0)?.content
                            sign_term2.text=body?.get(1)?.title+"\n"+body?.get(1)?.content
                            sign_term3.text=body?.get(2)?.title+"\n"+body?.get(2)?.content
                        }
                        else{
                            Log.e("Terms_Load_ERR>>",response.raw().toString())
                        }
                    }
                }
        )
    }
}