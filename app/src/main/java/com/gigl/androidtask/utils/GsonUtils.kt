package com.gigl.androidtask.utils

import com.google.gson.Gson

object GsonUtils {
    private var gson: Gson? = null
        get() {
            if (field == null) field = Gson()
            return field
        }

    @JvmStatic
    fun <T> fromJson(body: String?, classOfT: Class<T>?): T {
        return gson!!.fromJson(body, classOfT)
    }
}