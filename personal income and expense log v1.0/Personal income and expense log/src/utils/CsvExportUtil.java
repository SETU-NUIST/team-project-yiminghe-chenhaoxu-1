package utils;

import models.Record;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExportUtil {

    public static void exportToCsv(List<Record> records, String fileName) {
        if (records.isEmpty()) {
            System.out.println("⚠️ 没有记录可以导出");
            return;
        }

        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("日期,类型,金额,备注\n");

            for (Record r : records) {
                writer.write(r.getDate() + ","
                        + r.getType() + ","
                        + r.getAmount() + ","
                        + r.getDescription() + "\n");
            }

            System.out.println("✅ 数据已导出到：" + fileName);
        } catch (IOException e) {
            System.out.println("❌ 导出失败：" + e.getMessage());
        }
    }
}

