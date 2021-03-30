package com.jm5.appus.retrofit

import com.jm5.appus.dataForm.*
import retrofit2.http.*
import retrofit2.Call

interface RetrofitService {
    //전체 상품 로드
    @GET("/api/product/")
//    fun loadProducts():Call<Any>
    fun loadProducts(@Header("authentication") authentication : String?):Call<LoadProducts>

    //비밀번호찾기 휴대폰 인증번호
    @Headers("content-type: application/json")
    @POST("/users/password/search/auth-code/sending")
    fun searchPassword(
            @Body user: SearchPassword
    ):Call<Void>

    //비밀번호찾기 휴대폰 인증번호 확인
    @Headers("content-type: application/json")
    @POST("/users/password/search/auth-code/{phoneNumberAndVerify}")
    fun searchPasswordCheckVeri(
            @Path("phoneNumberAndVerify",encoded = true) body: String
    ):Call<Void>

    //비밀번호 변경
    @Headers("content-type: application/json")
    @POST("/users/password/change")
    fun changePassword(
            @Body user: ChangePassword
    ):Call<Void>
    //로그인
    @Headers("content-type: application/json")
    @POST("/users/login")
    fun login(
            @Body login : Login
    ):Call<JWT>
    //약관
    @Headers("content-type: application/json")
    @GET("/users/terms/sign-up")
    fun loadTerms(
            @Query("terms-type" )  termsType : String
    ):Call<List<Terms_SignUp>>
    //회원가입
//    @FormUrlEncoded
    @JvmSuppressWildcards
    @Headers("content-type: application/json")
    @POST("/users/sign-up")
    fun register(
            @Body body : Sign
    ):Call<Void>
//    @JvmSuppressWildcards
//    fun register(
//            @Field("email") email: String,
//            @Field("password") password: String,
//            @Field("passwordCheck") passwordCheck: String,
//            @Field("phoneNumber") phoneNumber: String,
//            @Field("terms") terms: List<HashMap<String, Any>>
//    ):Call<Void>

    //휴대폰 인증번호
    @Headers("content-type: application/json")
    @POST("/users/auth-code/sending/{phoneNumber}")
    fun phoneNumCheck(
        @Path("phoneNumber",encoded = true) body : String
    ):Call<Void>

    //휴대폰 인증번호 확인
    @Headers("content-type: application/json")
    @POST("/users/auth-code/{phoneNumberAndVerify}")
    fun checkVeri(
        @Path("phoneNumberAndVerify",encoded = true) body: String
    ):Call<Verification>
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