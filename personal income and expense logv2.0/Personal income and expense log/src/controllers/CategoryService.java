package controllers;

import models.Account;
import models.Category;
import utils.ScannerInput;
import utils.Utilities;

public class CategoryService {
    private Account account;

    public CategoryService(Account account) {
        this.account = account;
    }

    public void showAllCategories() {
        Utilities.printLine();
        System.out.println("=== 收支分类列表 ===");
        for (int i = 0; i < account.getCategories().size(); i++) {
            Category c = account.getCategories().get(i);
            System.out.println(c);
        }
        Utilities.printLine();
    }

    public Category selectCategory(String type) {
        showAllCategories();

        if (type.equals("income")) {
            System.out.print("请选择收入分类的ID：");
        } else {
            System.out.print("请选择支出分类的ID：");
        }
        int id = ScannerInput.getInt("请输入分类ID；"
                );

        Category result = null;
        for (int i = 0; i < account.getCategories().size(); i++) {
            Category c = account.getCategories().get(i);
            if (c.getId() == id && c.getType().equals(type)) {
                result = c;
                break;
            }
        }

        if (result == null) {
            System.out.println("输入的分类无效，使用默认分类");
            result = account.getCategories().get(0);
        }

        return result;
    }
}