package com.example.financial1.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    // Chuyển đổi từ Date sang Long (timestamp)
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    // Chuyển đổi từ Long (timestamp) sang Date
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
