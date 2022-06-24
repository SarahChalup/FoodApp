package com.example.foodapp.db


import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(atribute: Any?) : String{
        if(atribute == null)
            return ""
        return atribute as String
    }
    @TypeConverter
    fun fromStringToAny (atribute: String?): Any{
        if(atribute == null)
            return ""
        return atribute
    }
}