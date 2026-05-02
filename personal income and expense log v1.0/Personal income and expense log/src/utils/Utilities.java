package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static void printLine() {
        System.out.println("------------------------------");
    }
}