package models;

public abstract class Transaction {
    protected int id;
    protected double amount;
    protected String date;
    protected String description;
    protected Category category;

    public Transaction(int id, double amount, String date, String description, Category category) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public abstract String getType();

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ID:" + id + " | 日期:" + date + " | 金额:" + amount + " | 分类:" + category.getName() + " | 备注:" + description;
    }
}