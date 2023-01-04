package cn.gtmap.realestate.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/8/20 8:40
 * @description
 */
public final class ReadFileUtils{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFileUtils.class);

    private static final Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");

    /**
     * 读取文件内容，并且存储到另一个文件中
     *
     * @param logFile
     * @param startTime
     * @param endTime
     * @param saveFile
     */
    public static void readAppointedLineNumber(File logFile,String startTime,String endTime,File saveFile,String app,HttpServletResponse response) throws ParseException, IOException{
        if (!logFile.exists()){
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileWriter fWriter = new FileWriter(saveFile,true);
        BufferedWriter out = null;

        Date date = new Date();
        date.setTime(Long.parseLong(startTime));
        String st = format.format(date);
        Date date2 = new Date();
        date2.setTime(Long.parseLong(endTime));
        Date date3 = new Date();
        String end = format.format(date);
        // 创建 Pattern 对象
//        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        // 现在创建 matcher 对象
        Matcher ma;
        boolean rightLine = false;
        boolean endLine = false;
        try{
            out = new BufferedWriter(fWriter);
            LineIterator iterator = FileUtils.lineIterator(logFile,"UTf-8");
            out.write("==============================" + app + "=============================\r\n");
            while (iterator.hasNext()){
                String line = iterator.nextLine();
                ma = pattern.matcher(line);
                if (!rightLine && ma.find()){
                    if ((date.getTime() - format.parse(ma.group(0)).getTime()) / 1000 < 30 || (format.parse(ma.group(0)).getTime() - date.getTime()) / 1000 > 30){
                        rightLine = true;
                    }

                }
                if (rightLine){
                    if (ma.find()){
                        // 如果下一行的时间比结束时间大，读取结束
                        if (format.parse(ma.group(0)).after(date2)){
                            break;
                        }
                    }
                }
                if (rightLine){
                    out.write(line + "\r\n");
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            out.flush();
            out.close();
        }
    }

    // 获取需要查询的日志名称
    public static String getLogFile(String timestamp) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(Long.parseLong(timestamp));
        if (!format.format(date).equals(format.format(new Date()))){
            return "-all-" + format.format(date) + ".0.log";
        }
        return "-all.log";
    }

    public static List<String> readLogFile(File file,String app,String start,String end){
        List<String> list = new ArrayList<>();
//        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        // 现在创建 matcher 对象
        Matcher ma;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean rightLine = false;
        Date date = new Date();
        date.setTime(Long.parseLong(start));
        String st = format.format(date);
        Date date2 = new Date();
        date2.setTime(Long.parseLong(end));
        Date date3 = new Date();
        try{
            LineIterator iterator = FileUtils.lineIterator(file,"UTf-8");
            list.add("==============================" + app + "=============================\r\n");
            while (iterator.hasNext()){
                String line = iterator.nextLine();
                ma = pattern.matcher(line);
                if (!rightLine && ma.find()){
                    if ((date.getTime() - format.parse(ma.group(0)).getTime()) / 1000 < 30 || (format.parse(ma.group(0)).getTime() - date.getTime()) / 1000 > 30){
                        rightLine = true;
                    }

                }
                if (rightLine){
                    if (ma.find()){
                        // 如果下一行的时间比结束时间大，读取结束
                        if (format.parse(ma.group(0)).after(date2)){
                            break;
                        }
                    }
                }
                if (rightLine){
                    list.add(line + "\r\n");
                }

            }
        }catch (Exception e){

        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    public static void printLog(){
        LOGGER.error("反向代理：获取当前代理app的路径" + System.getProperty("java.class.path"));
    }
}