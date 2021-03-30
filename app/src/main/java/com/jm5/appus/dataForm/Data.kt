package com.jm5.appus.dataForm

import java.io.Serializable

data class Data (
        val id : Int,
        val title : String,
        val price : Int,
        val Images : List<Images>
):Serializable