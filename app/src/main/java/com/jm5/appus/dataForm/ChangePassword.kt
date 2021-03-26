//비밀번호 변경
package com.jm5.appus.dataForm

import java.io.Serializable

data class ChangePassword (
        var email:String,
        var password: String,
        var passwordCheck: String,
        var phoneNumber:String
):Serializable