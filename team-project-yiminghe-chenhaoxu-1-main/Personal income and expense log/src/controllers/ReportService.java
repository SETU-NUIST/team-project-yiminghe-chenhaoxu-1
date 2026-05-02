package controllers;

import models.Account;
import models.Transaction;
import utils.Utilities;
import models.Income;
import models.Expense;

public class ReportService {
    private Account account;

    public ReportService(Account account) {
        this.account = account;
    }

    public void generateSummaryReport() {
        Utilities.printLine();
        System.out.println("=== 收支总览报表 ===");
        if (account.getTransactions().isEmpty()) {
            System.out.println("暂无交易记录，报表无数据");
            Utilities.printLine();
            return;
        }

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : account.getTransactions()) {
            if (t instanceof Income) {
                totalIncome += t.getAmount();
            } else if (t instanceof Expense) {
                totalExpense += t.getAmount();
            }
        }

        System.out.println("总收入：" + totalIncome + " 元");
        System.out.println("总支出：" + totalExpense + " 元");
        System.out.println("当前余额：" + account.getBalance() + " 元");
        Utilities.printLine();
    }
}
