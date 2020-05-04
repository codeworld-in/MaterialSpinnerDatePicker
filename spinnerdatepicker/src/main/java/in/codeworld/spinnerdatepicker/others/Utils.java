package in.codeworld.spinnerdatepicker.others;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_YEAR;

public class Utils {

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(DAY_OF_YEAR) > 365;
    }

    public static boolean isMonthContain31Days(int selectedMonthIndex) {
        switch (selectedMonthIndex) {
            case 1:
                return true;
            case 3:
                return true;
            case 5:
                return true;
            case 7:
                return true;
            case 8:
                return true;
            case 10:
                return true;
            case 12:
                return true;
            default:
                return false;
        }
    }
}
