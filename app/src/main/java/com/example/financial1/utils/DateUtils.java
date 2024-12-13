package com.example.financial1.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/zz/yyyy"); // Định dạng yy/zz/tttt
        return dateFormat.format(date);
    }
}
