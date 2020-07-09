//**************************DONE***************************
package com.upgrad.blog.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * TODO: 7.16. Implement a method with the following signature.
 * public static  String format(LocalDateTime localDateTime)
 * This method should convert the default date time to the human readable format[dd-MM-yyyy HH:mm:ss].
 */

public class DateTimeFormatter {
    public static  String format(LocalDateTime localDateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sdf.format(localDateTime);
        String time=sdf.toString();
        return time;
    }


}
