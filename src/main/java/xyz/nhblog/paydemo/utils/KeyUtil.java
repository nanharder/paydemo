package xyz.nhblog.paydemo.utils;

import java.util.Calendar;
import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式： 时间 + 随机数
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(a);

    }

    public static String getMonthKey() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(calendar.get(Calendar.YEAR));
        sb.append(calendar.get(Calendar.MONTH));
        return sb.toString();
    }
}
