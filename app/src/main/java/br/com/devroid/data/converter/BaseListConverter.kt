package br.com.devroid.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class BaseListConverter<T> {

    private val type = object : TypeToken<List<T>>() {}.type

    @TypeConverter
    open fun fromDatabase(values: String?): List<T> = Gson().fromJson(values, type)

    @TypeConverter
    open fun toDatabase(elements: List<T>?): String = Gson().toJson(elements, type)
}