package controllers;

import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class TransactionService {
    private final Account account;
    private final CategoryService categoryService;

    public TransactionService(Account account, CategoryService categoryService) {
        this.account = account;
        this.categoryService = categoryService;
    }

    public void addIncome() {
        Utilities.printLine();
        System.out.println("=== 添加收入记录 ===");

        System.out.print("请输入收入金额：");
        double amount = ScannerInput.getDouble("请输入收入金额：");

        System.out.print("请输入备注：");
        String desc = ScannerInput.getString("请输入备注：");

        Category category = categoryService.selectCategory("income");

        String date = Utilities.getCurrentDate();
        int id = account.getTransactions().size() + 1;

        Income income = new Income(id, amount, date, desc, category);
        account.addTransaction(income);

        System.out.println("✅ 收入添加成功！");
        Utilities.printLine();
    }

    public void addExpense() {
        Utilities.printLine();
        System.out.println("=== 添加支出记录 ===");

        System.out.print("请输入支出金额：");
        double amount = ScannerInput.getDouble("请输入支出金额：");

        System.out.print("请输入备注：");
        String desc = ScannerInput.getString("请输入备注：");

        Category category = categoryService.selectCategory("expense");

        String date = Utilities.getCurrentDate();
        int id = account.getTransactions().size() + 1;

        Expense expense = new Expense(id, amount, date, desc, category);
        account.addTransaction(expense);

        System.out.println("本次添加的支付详情：" + expense);
        System.out.println("交易类型：" + expense.getType());

        System.out.println("✅ 支出添加成功！");
        Utilities.printLine();
    }

    public void showAllTransactions() {
        Utilities.printLine();
        System.out.println("=== 所有交易记录 ===");

        if (account.getTransactions().isEmpty()) {
            System.out.println("暂无交易记录");
        } else {
            for (Transaction t : account.getTransactions()) {
                // 调用所有getter和getType()
                System.out.println("交易类型：" + t.getType());
                System.out.println("ID：" + t.getId());
                System.out.println("日期：" + t.getDate());
                System.out.println("金额：" + t.getAmount());
                System.out.println("备注：" + t.getDescription());
                System.out.println("分类：" + t.getCategory());
                System.out.println("------------------------");
            }
        }
        Utilities.printLine();
    }

    public void deleteTransaction() {
        Utilities.printLine();
        System.out.println("===== 删除交易记录 =====");

        int id = ScannerInput.getInt("请输入要删除的记录ID：");
        boolean isFound = false;

        for (int i = 0; i < account.getTransactions().size(); i++) {
            Transaction t = account.getTransactions().get(i);
            if (t.getId() == id) {
                account.getTransactions().remove(i);
                System.out.println("删除成功！");
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            System.out.println("未找到该记录！");
        }

        Utilities.printLine();
    }

    public void updateTransaction() {
        Utilities.printLine();
        System.out.println("===== 修改交易记录 =====");

        int id = ScannerInput.getInt("请输入要修改的记录ID：");
        Transaction target = null;

        for (Transaction t : account.getTransactions()) {
            if (t.getId() == id) {
                target = t;
                break;
            }
        }

        if (target == null) {
            System.out.println("未找到该记录！");
            Utilities.printLine();
            return;
        }

        System.out.println("1. 修改金额");
        System.out.println("2. 修改备注");
        System.out.println("3. 修改分类");
        int opt = ScannerInput.getInt("请选择：");

        if (opt == 1) {
            double newAmount = ScannerInput.getDouble("请输入新金额：");
            target.setAmount(newAmount);
            System.out.println("金额修改成功！");
        } else if (opt == 2) {
            String newDesc = ScannerInput.getString("请输入新备注：");
            target.setDescription(newDesc);
            System.out.println("备注修改成功！");
        } else if (opt == 3) {
            String newCate = ScannerInput.getString("请输入新分类名称：");
            target.getCategory().setName(newCate);
            System.out.println("分类修改成功！");
        } else {
            System.out.println("输入错误！");
        }

        Utilities.printLine();
    }

    public void filterByDate() {
        Utilities.printLine();
        System.out.println("===== 按日期筛选 =====");

        String date = ScannerInput.getString("请输入日期（如 2026-04-23）：");
        System.out.println("\n筛选结果：");

        boolean hasRecord = false;
        for (Transaction t : account.getTransactions()) {
            if (t.getDate().contains(date)) {
                String type = (t instanceof Income) ? "收入" : "支出";
                System.out.println("ID:" + t.getId() + " " + type
                        + " 金额:" + t.getAmount()
                        + " 日期:" + t.getDate()
                        + " 分类:" + t.getCategory().getName()
                        + " 备注:" + t.getDescription());
                hasRecord = true;
            }
        }

        if (!hasRecord) {
            System.out.println("该日期暂无记录");
        }

        Utilities.printLine();
    }

    public void filterByCategory() {
        Utilities.printLine();
        System.out.println("===== 按分类筛选 =====");

        String cateName = ScannerInput.getString("请输入分类名称：");
        System.out.println("\n筛选结果：");

        boolean hasRecord = false;
        for (Transaction t : account.getTransactions()) {
            if (t.getCategory().getName().equals(cateName)) {
                String type = (t instanceof Income) ? "收入" : "支出";
                System.out.println("ID:" + t.getId() + " " + type
                        + " 金额:" + t.getAmount()
                        + " 日期:" + t.getDate()
                        + " 分类:" + t.getCategory().getName()
                        + " 备注:" + t.getDescription());
                hasRecord = true;
            }
        }

        if (!hasRecord) {
            System.out.println("该分类暂无记录");
        }

        Utilities.printLine();
    }



}
