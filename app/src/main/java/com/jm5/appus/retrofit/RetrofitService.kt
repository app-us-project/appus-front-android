package com.jm5.appus.retrofit

import com.jm5.appus.dataForm.JWT
import com.jm5.appus.dataForm.Login
import com.jm5.appus.dataForm.Sign
import com.jm5.appus.dataForm.Verification
import retrofit2.http.*
import retrofit2.Call
import kotlin.String as String1

interface RetrofitService {
    //회원가입
    @Headers("content-type: application/json")
    @POST("/users/sign-up")
    fun register(
        @Body user: Sign
    ):Call<Verification>

    //휴대폰 인증번호
    @Headers("content-type: application/json")
    @POST("/users/auth-code/sending/{phoneNumber}")
    fun phoneNumCheck(
        @Path("phoneNumber",encoded = true) body : String1
    ):Call<Void>

    //휴대폰 인증번호 확인
    @Headers("content-type: application/json")
    @POST("/users/auth-code/{phoneNumberAndVerify}")
    fun checkVeri(
        @Path("phoneNumberAndVerify",encoded = true) body: String1
    ):Call<Void>

    //로그인
    @Headers("content-type: application/json")
    @POST("/users/login")
    fun login(
        @Body login : Login
    ):Call<JWT>


//    @Headers("content-type: application/json")
//    @POST("/users/login")
//    fun requestLogin(
//        @Field("email"):String,
//        @Field("password"):String
//    )



//    // 닉네임 중복 확인 GET
//    @Headers("content-type: application/json")
//    @POST("users")
//    fun getNicknameIsExist(
//        //@Query("success") nickname: String
//        @Body nickname : HashMap<String, String>
//    ): Call<NickName>
//
//    // 로그인 POST
//    @Headers("content-type: application/json")
//    @POST("login")
//    fun login(
//        @Body params: HashMap<String, String>
//    ): Call<Login>
}