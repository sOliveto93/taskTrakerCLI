import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.ENGLISH);

    public static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + dateStr);
            return null;
        }
    }

    public static String formaDate(Date date) {
        return date == null ? "null" : dateFormat.format(date);
    }
}
