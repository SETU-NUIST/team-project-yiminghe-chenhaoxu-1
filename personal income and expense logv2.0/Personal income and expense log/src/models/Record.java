package models;

import java.io.Serializable;

public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;
    private String type;
    private double amount;
    private String description;

    public Record(String date, String type, double amount, String description) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "日期: " + date + ", 类型: " + type + ", 金额: " + amount + ", 备注: " + description;
    }
}