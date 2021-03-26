package com.jm5.appus.dataForm

import java.io.Serializable

data class Sign (
    var email : String,
    var password : String,
    var passwordCheck : String,
    var phoneNumber : String,
    var terms : List<HashMap<String,Any>>
):Serializable