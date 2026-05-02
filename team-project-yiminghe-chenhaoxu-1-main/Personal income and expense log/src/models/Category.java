package models;

public class Category {
    private int id;
    private String name;
    private String type;

    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ". " + name + " (" + (type.equals("income") ? "收入" : "支出") + ")";
    }
}
