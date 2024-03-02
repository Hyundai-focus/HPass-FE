package com.hyundai.hpass.socialLogIn

import android.content.Context
import android.content.SharedPreferences
import com.hyundai.hpass.BuildConfig.PREF_NAME

/**
 *
 * @author 최현서
 *
 */
class PreferenceUtil(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    fun setString(key: String, defValue: String?){
        preferences.edit().putString(key, defValue).apply()
    }
    fun getString(key: String):String{
        return preferences.getString(key, null).toString()
    }
    fun clear(){
        preferences.edit().clear().commit()
    }
}