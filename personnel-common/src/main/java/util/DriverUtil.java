package util;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DriverUtil {
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取硬盘的每个盘符
     */
    public static Map driver(){
        // 当前文件系统类
        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        long total = 0;
        long residue = 0;
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            total += fs[i].getTotalSpace();
            residue += fs[i].getFreeSpace();
        }
        Map map = new HashMap();
        map.put("total", FormetFileSize(total));
        map.put("residue", FormetFileSize(total - residue));

        int v = (int) ((new BigDecimal((float) residue / total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);

        map.put("ratio",(100 - v));

        return map;
    }

}
