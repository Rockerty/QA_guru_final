package testdata;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// Метод берет сокращение названия месяца на англ. и переводит его в числовой формат. feb -> 02
public class TestDataHelper {

    public static String getMonthNumber(String monthName) {
        Month month = Month.from(
                DateTimeFormatter.ofPattern("MMM")
                        .withLocale(Locale.ENGLISH)
                        .parse(monthName)
        );
        return String.format("%02d", month.getValue());
    }
}