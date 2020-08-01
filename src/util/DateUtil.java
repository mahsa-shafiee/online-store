package util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;

@Component
@Lazy
public class DateUtil {

    public Date getCurrentDate() {
        long millis = System.currentTimeMillis();
        return new Date(millis);
    }

    public Time getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        return new Time(currentTime.getTime());
    }

    public Date convertStrToDate(String dateStr) throws Exception {
        LocalDate localDate = LocalDate.parse(dateStr);
        if (dateStr.split("-")[0].equals("0000"))
            throw new Exception();
        return Date.valueOf(localDate);
    }

    public Date addOneMonth(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr);
        return Date.valueOf(localDate.plusMonths(1));
    }
}
