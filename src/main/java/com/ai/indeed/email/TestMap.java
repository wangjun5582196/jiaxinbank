package com.ai.indeed.email;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Map<String, Date> map = new HashMap<>();

        Date startDate = simpleDateFormat.parse("20240312");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);

        map.put("start",calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        map.put("end",calendar.getTime());



        System.out.println(map);


        Date startDate1 = map.get("start");
        Date endDate = map.get("end");
        Integer nowDate = Integer.valueOf(simpleDateFormat.format(new Date()));
        Integer end = Integer.valueOf(simpleDateFormat.format(endDate));
        System.out.println(nowDate);
        System.out.println(end);
    }
}
