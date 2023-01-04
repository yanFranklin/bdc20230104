package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzConfigurationVo;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzParameterVo;
import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:yuhao@gtmap.cn">yuhao</a>
 * @version 1.0, 2021/1/29
 * @description jsch相关工具类
 */
public class SshUtils {
    private static ChannelSftp channelSftp;
    private static ChannelExec channelExec;
    private static Session session = null;
    private static int timeout = 60000;
    /**
     * 创建JSch对象
     */
    private static final JSch jSch = new JSch();

    /**
     * 日志操作
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SshUtils.class);

    public SshUtils(){
    }

    /**
     * 连接jsch对象的方法
     */
    public static void connectJsch(BdcXzrzConfigurationVo sshConfiguration) throws JSchException {
        LOGGER.info("连接到主机："+sshConfiguration.getHost()+",用户名："+sshConfiguration.getUsername()+",密码："+sshConfiguration.getPassword()+",端口："+sshConfiguration.getPort());
        session = jSch.getSession(sshConfiguration.getUsername(),sshConfiguration.getHost(),Integer.parseInt(sshConfiguration.getPort()));//根据用户名，主机ip，和端口号获取一个session对象
        session.setPassword(sshConfiguration.getPassword());//设置密码
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking","no");
        session.setConfig(properties);//为session对象设置properties
        session.setTimeout(timeout);
        session.connect();//通过session建立连接
        LOGGER.info("连接成功");
    }

    /**
     * 日志下载的核心方法
     */
    public static boolean download(BdcXzrzParameterVo bdcXzrzParameterVo, HttpServletResponse servletResponse, boolean mark) throws JSchException, SftpException, IOException, ParseException{
        channelSftp = (ChannelSftp)session.openChannel("sftp");
        channelSftp.connect();
        Vector vector = null;
        String remoteContent = null;
        String remoteParentPath = bdcXzrzParameterVo.getPath();
        List<String> listApply = bdcXzrzParameterVo.getListApply();
        String applyName = bdcXzrzParameterVo.getApplyName();
        Date startTime = bdcXzrzParameterVo.getStartTime();
        Date endTime = bdcXzrzParameterVo.getEndTime();
        //文件list
        List<File> fileList = new ArrayList<>();
        for(int i = 0;i < listApply.size();i++) {
            String apply = listApply.get(i);
            int total = 0;
            Vector parentFile = getParentFile(remoteParentPath+listApply.get(i));
            String randomFileName = "";
            if(parentFile.size() != 0) {
                for(int m = 0;m < parentFile.size();m++) {
                    ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)parentFile.get(m);
                    SftpATTRS attrs = file.getAttrs();
                    boolean dir = attrs.isDir();
                    if(dir && !(file.getFilename().equals(".") || file.getFilename().equals(".."))) {
                        total++;
                        randomFileName = file.getFilename();
                    }
                }
                if(total == 1) {
                    //拼接路径
                    remoteContent = remoteParentPath+apply+"/"+randomFileName+"/logs/";
                }else {
                    remoteContent = remoteParentPath+apply+"/logs/";
                }
            }
            //得到对应的ui文件夹的名字，并且创建传过来的存储日志位置的ui文件夹的父文件夹
            String parentName = getCorrespondingFile(listApply.get(i), applyName);
            String destPath = applyName+parentName+"/";
            //ls获取文件名列表
            vector = channelSftp.ls(remoteContent);
            //使用迭代器遍历
            Iterator iterator = vector.iterator();
            while(iterator.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)iterator.next();
                //拿到文件名
                String filename = file.getFilename();
                String path = remoteContent+filename;
                int linuxTime = getLinuxTime(path);
                long time = linuxTime * 1000L;
                Date date = new Date(time);
                //得到当前文件名称时间字符串
                String fileTime = getTimeConvertString(date);
                Date stringDateFile = getStringConvertTime(fileTime);
                //判断文件名不是第一个.和第二个..并且文件的日期大于等于开始时间，小于等于结束时间的时候，进行io操作
                if(!(filename.equals(".") || filename.equals(".."))
                        && stringDateFile.getTime() >= startTime.getTime()
                        && stringDateFile.getTime() <= endTime.getTime()) {
                    InputStream inputStream = channelSftp.get(path);
                    File file1 = new File(destPath+filename);
                    FileOutputStream outputStream = new FileOutputStream(file1);
                    byte[] bytes = new byte[1024*10];
                    int len = 0;
                    while((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes,0,len);
                    }
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    //添加文件
                    fileList.add(file1);
                }
            }
            System.out.println(total);
        }
        if(fileList.size() == 0) {
            //删除创建的文件夹
            delCorrespondingFile(applyName);
            throw new AppException(0,"无对应日志下载");
        }else if(fileList.size() == 1) {
            //没有日志的处理
            if(mark) {
                //删除创建的文件夹
                delCorrespondingFile(applyName);
                return false;
            }
            //一个文件时,直接响应
            downSingle(servletResponse, fileList.get(0));
        }else {
            //没有日志的处理
            if(mark) {
                //删除创建的文件夹
                delCorrespondingFile(applyName);
                return false;
            }
            //当多个文件时，打包下载
            ZipUtil.zipDownload(servletResponse, "日志文件"+getDateConvertString(new Date())+".zip", fileList);
        }
        //删除创建的文件夹
        delCorrespondingFile(applyName);
        return true;
    }

    /**
     * 单个日志的处理
     */
    public static void downSingle(HttpServletResponse servletResponse, File file) throws IOException {
        servletResponse.setContentType("application/x-msdownload");
        //设置响应流中文件进行下载
        servletResponse.setHeader("Content-Disposition", "attachment;filename="+file.getName()+".text");
        ServletOutputStream os = servletResponse.getOutputStream();
        byte[] bytes = FileUtils.readFileToByteArray(file);
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * 获取远程文件的时间 (秒)
     */
    public static int getLinuxTime(String path) throws SftpException{
        SftpATTRS stat = channelSftp.stat(path);
        int mTime = stat.getMTime();
        return mTime;
    }

    /**
     * 获取log文件夹所有的父文件夹
     */
    public static Vector getParentFile(String remoteParentFile) throws JSchException, SftpException {
        channelSftp = (ChannelSftp)session.openChannel("sftp");
        channelSftp.connect();
        Vector vector = channelSftp.ls(remoteParentFile);
        return vector;
    }

    /**
     * 生成对应的文件夹存放日志
     */
    public static String getCorrespondingFile(String parentFileName,String location){
        String name = parentFileName;
        File file = new File(location+parentFileName);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdir();
        }
        file.mkdir();
        return name;
    }

    /**
     * 删除创建的对应文件夹
     */
    public static void delCorrespondingFile(String location) throws IOException {
        File file = new File(location);
        FileUtils.forceDelete(file);
    }

    /**
     * 时间转字符串
     */
    public static String getTimeConvertString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }

    /**
     * 字符串转时间
     */
    public static Date getStringConvertTime(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        return date;
    }

    /**
     * 下载日志文件的具体时间，避免重复
     */
    public static String getDateConvertString(Date date) {
        SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String time = sdf2.format(date);
        return time;
    }

    /**
     * 关闭资源
     */
    public static void close() {
        session.disconnect();
    }
}
