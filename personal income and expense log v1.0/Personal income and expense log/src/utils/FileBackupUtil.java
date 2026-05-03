package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileBackupUtil {

    public static void backupFile(String originalFilePath) {
        File original = new File(originalFilePath);
        if (!original.exists()) {
            System.out.println("⚠️ 原文件不存在，无法备份");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        String backupFilePath = original.getName().replace(".xml", "")
                + "_backup_" + timeStamp + ".xml";
        File backup = new File(backupFilePath);

        try {
            Files.copy(original.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ 数据已备份到：" + backupFilePath);
        } catch (IOException e) {
            System.out.println("❌ 备份失败：" + e.getMessage());
        }
    }
}