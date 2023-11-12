package com.sam.movieapp.data.sources.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromIdList(idList: List<Int>?): String? {
        if (idList == null) return null
        return Gson().toJson(idList)
    }

    @TypeConverter
    fun toIdList(source: String?) : List<Int>?{
        if (source == null) return null
        return Gson().fromJson(source, object : TypeToken<List<Int>>() {}.type)
    }

}