package models;

public class Expense extends Transaction {
    public Expense(int id, double amount, String date, String description, Category category) {
        super(id, amount, date, description, category);
    }

    @Override
    public String getType() {
        return "expense";
    }

    @Override
    public String toString() {
        return "[支出] " + super.toString();
    }
}