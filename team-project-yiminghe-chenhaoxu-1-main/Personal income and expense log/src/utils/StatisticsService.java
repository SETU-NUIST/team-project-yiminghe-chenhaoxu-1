package utils;

import models.Record;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {

    public double getTotalIncome(List<Record> list) {
        double sum = 0;
        for(Record r : list){
            if(r.getType().equals("收入")){
                sum += r.getAmount();
            }
        }
        return sum;
    }

    public double getTotalExpense(List<Record> list) {
        double sum = 0;
        for(Record r : list){
            if(r.getType().equals("支出")){
                sum += r.getAmount();
            }
        }
        return sum;
    }

    public double getBalance(List<Record> list) {
        return getTotalIncome(list) - getTotalExpense(list);
    }

    public void showMonthStat(List<Record> list, String yearMonth) {
        double in = 0, out = 0;
        for(Record r : list){
            if(r.getDate().startsWith(yearMonth)){
                if(r.getType().equals("收入")){
                    in += r.getAmount();
                }else{
                    out += r.getAmount();
                }
            }
        }
        System.out.println("\n==== "+yearMonth+" 月度统计 ====");
        System.out.println("本月收入："+in);
        System.out.println("本月支出："+out);
        System.out.println("本月结余："+(in-out));
    }

    public void showCategoryStat(List<Record> list) {
        Map<String,Double> map = new HashMap<>();
        for(Record r : list){
            if(r.getType().equals("支出")){
                map.put(r.getDescription(), map.getOrDefault(r.getDescription(),0.0)+r.getAmount());
            }
        }
        System.out.println("\n==== 支出分类统计 ====");
        for(String key : map.keySet()){
            System.out.println(key + " : " + map.get(key) + " 元");
        }
    }
}