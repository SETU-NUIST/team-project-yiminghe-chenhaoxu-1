package models;

public class Income extends Transaction {
    public Income(int id, double amount, String date, String description, Category category) {
        super(id, amount, date, description, category);
    }

    @Override
    public String getType() {
        return "income";
    }

    @Override
    public String toString() {
        return "[收入] " + super.toString();
    }
}
