package com.jm5.appus.dataForm

import java.io.Serializable

data class Terms_SignUp (
    var id : Int,
    var title : String?, //"서비스 이용 약관 (필수)",
    var content : String, //"서비스 이용 약관을 동의합니다.",
    var required : Boolean//true
):Serializable