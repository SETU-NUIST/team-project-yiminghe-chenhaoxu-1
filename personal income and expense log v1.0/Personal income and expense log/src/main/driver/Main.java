package main.driver;

import controllers.CategoryService;
import controllers.ReportService;
import controllers.TransactionService;
import models.Account;
import models.Record;
import models.User;
import utils.FinanceManager;
import utils.ScannerInput;
import utils.StatisticsService;
import utils.UserService;
import utils.Utilities;
import utils.FileBackupUtil;
import utils.CsvExportUtil;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User loginUser = null;

        System.out.println("===== 个人收支记账本 =====");
        while (true) {
            System.out.println("\n1. 用户登录");
            System.out.println("2. 新用户注册");
            System.out.println("3. 退出系统");
            int op = ScannerInput.getInt("请选择操作：");

            if (op == 1) {
                String name = ScannerInput.getString("输入用户名：");
                String pwd = ScannerInput.getString("输入密码：");
                loginUser = userService.login(name, pwd);
                if (loginUser != null) {
                    System.out.println("登录成功！欢迎回来，" + loginUser.getUsername());
                    break;
                } else {
                    System.out.println("用户名或密码错误！");
                }
            } else if (op == 2) {
                String name = ScannerInput.getString("设置用户名：");
                String pwd = ScannerInput.getString("设置密码：");
                boolean res = userService.register(name, pwd);
                if (res) {
                    System.out.println("注册成功，请登录！");
                } else {
                    System.out.println("用户名已存在！");
                }
            } else if (op == 3) {
                System.out.println("退出程序");
                return;
            } else {
                System.out.println("无效选项");
            }
        }

        Account account = new Account();
        CategoryService categoryService = new CategoryService(account);
        TransactionService transactionService = new TransactionService(account, categoryService);
        ReportService reportService = new ReportService(account);

        FinanceManager financeManager = new FinanceManager(loginUser.getUsername());
        StatisticsService stat = new StatisticsService();

        boolean run = true;
        while (run) {
            System.out.println("\n===== 功能菜单 =====");
            System.out.println("1. 添加收入");
            System.out.println("2. 添加支出");
            System.out.println("3. 查看所有记录");
            System.out.println("4. 收支报表");
            System.out.println("5. 删除记录");
            System.out.println("6. 修改记录");
            System.out.println("7. 按日期筛选");
            System.out.println("8. 按分类筛选");
            System.out.println("9. 收支统计分析");
            System.out.println("10. 备份数据文件");
            System.out.println("11. 导出数据到CSV");
            System.out.println("12. 退出系统");

            int select = ScannerInput.getPositiveInt("请选择功能：");
            Utilities.printLine();

            switch (select) {
                case 1:
                    transactionService.addIncome();
                    String inDate = ScannerInput.getDate("输入日期(yyyy-MM-dd)：");
                    double inMoney = ScannerInput.getDouble("输入收入金额：");
                    String inDesc = ScannerInput.getString("输入备注：");
                    financeManager.addRecord(new Record(inDate, "收入", inMoney, inDesc));
                    System.out.println("✅ 收入记录保存成功！");
                    break;

                case 2:
                    transactionService.addExpense();
                    String outDate = ScannerInput.getDate("输入日期(yyyy-MM-dd)：");
                    double outMoney = ScannerInput.getDouble("输入支出金额：");
                    String outDesc = ScannerInput.getString("输入备注：");
                    financeManager.addRecord(new Record(outDate, "支出", outMoney, outDesc));
                    System.out.println("✅ 支出记录保存成功！");
                    break;

                case 3:
                    transactionService.showAllTransactions();
                    System.out.println("\n==== 持久化所有记录 ====");
                    for (Record r : financeManager.getAllRecords()) {
                        System.out.println(r);
                    }
                    break;

                case 4:
                    reportService.generateSummaryReport();
                    break;

                case 5:
                    int delIdx = ScannerInput.getPositiveInt("输入要删除的记录序号：") - 1;
                    if (financeManager.deleteRecord(delIdx)) {
                        System.out.println("✅ 删除成功！");
                    } else {
                        System.out.println("❌ 序号错误！");
                    }
                    transactionService.deleteTransaction();
                    break;

                case 6:
                    int editIdx = ScannerInput.getPositiveInt("输入要修改的记录序号：") - 1;
                    var list = financeManager.getAllRecords();
                    if (editIdx < 0 || editIdx >= list.size()) {
                        System.out.println("❌ 序号无效");
                        break;
                    }
                    Record old = list.get(editIdx);
                    String newD = ScannerInput.getDate("输入新日期：");
                    String newT = ScannerInput.getString("输入新类型(收入/支出)：");
                    double newM = ScannerInput.getDouble("输入新金额：");
                    String newDe = ScannerInput.getString("输入新备注：");

                    old.setDate(newD);
                    old.setType(newT);
                    old.setAmount(newM);
                    old.setDescription(newDe);
                    financeManager.updateRecord(editIdx, old);
                    System.out.println("✅ 修改完成！");
                    transactionService.updateTransaction();
                    break;

                case 7:
                    transactionService.filterByDate();
                    String qDate = ScannerInput.getDate("查询日期(yyyy-MM-dd)：");
                    var dateList = financeManager.getRecordsByDate(qDate);
                    if (dateList.isEmpty()) {
                        System.out.println("📅 该日期无记录");
                    } else {
                        for (int i = 0; i < dateList.size(); i++) {
                            System.out.println((i + 1) + ". " + dateList.get(i));
                        }
                    }
                    break;

                case 8:
                    transactionService.filterByCategory();
                    break;

                case 9:
                    var all = financeManager.getAllRecords();
                    System.out.println("\n==== 总体收支概况 ====");
                    System.out.println("总收入：" + stat.getTotalIncome(all));
                    System.out.println("总支出：" + stat.getTotalExpense(all));
                    System.out.println("总结余：" + stat.getBalance(all));

                    String ym = ScannerInput.getString("输入年月查询(yyyy-MM)：");
                    stat.showMonthStat(all, ym);
                    stat.showCategoryStat(all);
                    break;

                case 10:
                    String dataFileName = loginUser.getUsername() + "_record.xml";
                    FileBackupUtil.backupFile(dataFileName);
                    break;

                case 11:
                    List<Record> recordsToExport = financeManager.getAllRecords();
                    String exportFileName = loginUser.getUsername() + "_账单明细.csv";
                    CsvExportUtil.exportToCsv(recordsToExport, exportFileName);
                    break;

                case 12:
                    System.out.println("退出系统");
                    run = false;
                    break;

                case 13:
                    System.out.println("退出系统");
                    run = false;
                    break;

                default:
                    System.out.println("❌ 无效功能编号，请重新选择");
            }
        }
    }
}