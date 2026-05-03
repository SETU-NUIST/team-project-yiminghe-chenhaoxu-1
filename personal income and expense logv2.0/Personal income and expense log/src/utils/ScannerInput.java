package utils;

import java.util.Scanner;

public class ScannerInput {
    private static Scanner sc = new Scanner(System.in);

    public static int getInt(String tip) {
        System.out.print(tip);
        while (!sc.hasNextInt()) {
            System.out.print("请输入数字！重新输入：");
            sc.next();
        }
        return sc.nextInt();
    }

    public static int getPositiveInt(String tip) {
        int num;
        while (true) {
            num = getInt(tip);
            if (num > 0) {
                break;
            }
            System.out.println("必须大于0！请重新输入：");
        }
        return num;
    }

    public static String getString(String tip) {
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        System.out.print(tip);
        return sc.nextLine().trim();
    }

    public static String getDate(String tip) {
        String date;
        while (true) {
            date = getString(tip);
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                break;
            }
            System.out.println("格式错误！请按 yyyy-MM-dd 输入：");
        }
        return date;
    }

    public static double getDouble(String tip) {
        double money;
        while (true) {
            System.out.print(tip);
            while (!sc.hasNextDouble()) {
                System.out.print("请输入正确金额：");
                sc.next();
            }
            money = sc.nextDouble();
            if (money > 0) {
                break;
            }
            System.out.println("金额不能为负数！请重新输入：");
        }
        return money;
    }
}