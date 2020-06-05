package util;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;

public class DateUtil {

    public static Date getCurrentDate() {
        long millis = System.currentTimeMillis();
        return new java.sql.Date(millis);
    }

    public static Time getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        return new Time(currentTime.getTime());
    }

    public static Date convertStrToDate(String dateStr) throws Exception {
        LocalDate localDate = LocalDate.parse(dateStr);
        if (dateStr.split("-")[0].equals("0000"))
            throw new Exception();
        return Date.valueOf(localDate);
    }

    public static Date addOneMonth(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr);
        return Date.valueOf(localDate.plusMonths(1));
    }
}
