package com.chillieman.chilliewallet.definitions.converter

import androidx.room.TypeConverter
import java.math.BigInteger

object BigIntegerConverters {
    @JvmStatic
    @TypeConverter
    fun toBigInteger(value: String?): BigInteger? {
        if (value == null) {
            throw IllegalArgumentException("Invalid BigInteger: $value")
        }

        return value.toBigIntegerOrNull()
    }

    @JvmStatic
    @TypeConverter
    fun fromBigInteger(value: BigInteger?): String? = value?.toString()
}