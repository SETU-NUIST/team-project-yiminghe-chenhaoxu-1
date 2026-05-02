package models;

import java.util.ArrayList;
import java.util.List;
public class Account {
    private double balance;
    private List<Transaction> transactions;
    private List<Category> categories;

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.categories = new ArrayList<>();
        initDefaultCategories();
    }

    private void initDefaultCategories() {
        categories.add(new Category(1, "工资", "income"));
        categories.add(new Category(2, "奖金", "income"));
        categories.add(new Category(3, "餐饮", "expense"));
        categories.add(new Category(4, "交通", "expense"));
        categories.add(new Category(5, "娱乐", "expense"));
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction instanceof Income) {
            balance += transaction.getAmount();
        } else if (transaction instanceof Expense) {
            balance -= transaction.getAmount();
        }
    }

    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }
    public List<Category> getCategories() { return categories; }
}