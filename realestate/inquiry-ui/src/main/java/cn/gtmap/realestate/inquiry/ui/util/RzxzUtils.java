package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.realestate.common.core.vo.inquiryui.BdcRzxzDO;
import cn.gtmap.realestate.common.core.vo.inquiryui.BdcRzxzVO;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzParameterVo;
import com.ctrip.framework.foundation.internals.Utils;
import com.jcraft.jsch.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author <a href="mailto:hongqin@gtmap.cn">yuhao</a>
 * @version 1.0, 2022/9/2
 * @description jsch相关工具类,连接远程主机，下载远程文件
 */
public class RzxzUtils extends HttpServlet {
    private static ChannelSftp channelSftp;
    private static Session session = null;
    private static int timeout = 60000;
    private static final JSch jSch = new JSch();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RzxzUtils.class);

    public RzxzUtils(){
    }

    /**
     * 连接jsch对象的方法
     * @param sshConfiguration 日志下载相关实体类，包含ip，端口号等信息
     * @throws JSchException
     */
    public static void connectJsch(BdcRzxzDO sshConfiguration) throws JSchException {
        LOGGER.info("连接到主机：{},用户名：{},密码：{},端口：{}",sshConfiguration.getHost(),sshConfiguration.getUsername(),sshConfiguration.getPassword(),sshConfiguration.getPort());
        // 根据用户名，主机ip，和端口号获取一个session对象
        session = jSch.getSession(sshConfiguration.getUsername(),sshConfiguration.getHost(),Integer.parseInt(sshConfiguration.getPort()));
        // 设置密码
        session.setPassword(sshConfiguration.getPassword());
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking","no");
        // 为session对象设置properties
        session.setConfig(properties);
        session.setTimeout(timeout);
        // 通过session建立连接
        session.connect();
        LOGGER.info("连接成功");
    }
    /**
     * 前台传过来的应用实例是否包含远程服务器对应的实例目录名称(包含的话下载)
     * @param logs 前台传过来需要下载日志的主机与服务
     * @param path 应用所在远程主机上的目录
     * @param applyName 主机名称
     */
    public static String getConfiguration(BdcRzxzVO logs, String path, String applyName) throws SftpException, JSchException, ParseException, IOException, ServletException,Exception{
        String path1 = "";
        // /home/realestate/hefei/ 得到所有log文件夹的父文件夹
        Vector parentFile = RzxzUtils.getParentFile(path);
        // 得到前台传过来的应用实例
        String[] uiapplyStr = logs.getUiapply().split(",");
        List<String> listUiapply = new ArrayList<>(Arrays.asList(uiapplyStr));
        // 用来存储符合条件的应用实例
        Map<String,String> listParentFile = new HashMap<>();
        if(parentFile.size() != 0) {
            for(int i = 0;i < parentFile.size();i++) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)parentFile.get(i);
                // 判断是否符合条件 传过来的应用实例是否对应远程服务器上的实例名称
                for (String uiapply : listUiapply) {
                    if(match(uiapply,file.getFilename())) {
                        // 符合条件就存储
                        listParentFile.put(uiapply,file.getFilename());
                    }
                }
            }
        }
        // 判断前台传过来的应用实例是否包含了listParentFile，包含就下载
        if(listParentFile.size()!=0) {
            BdcXzrzParameterVo parameterVo = new BdcXzrzParameterVo(path,listUiapply,applyName,RzxzUtils.getStringConvertTime(logs.getStart_time()),RzxzUtils.getStringConvertTime(logs.getEnd_time()));
            path1 = RzxzUtils.download(parameterVo,logs.getApply(),listParentFile);
        }
        return path1;
    }

    /**
     * 日志下载的核心方法
     * @param bdcXzrzParameterVo
     * @param applyname 具体服务器对应的应用名称
     * @param listParentFile<String,String> 前端从页面获取到具体应用服务名称与远程服务器中对应日志文件名称
     * @return
     * @throws Exception
     */
    public static String download(BdcXzrzParameterVo bdcXzrzParameterVo,String applyname, Map<String,String> listParentFile) throws Exception {
        Vector vector = null;
        String remoteContent = null;
        String remoteParentPath = bdcXzrzParameterVo.getPath();
        List<String> listApply = bdcXzrzParameterVo.getListApply();
        String applyName = bdcXzrzParameterVo.getApplyName();
        Date startTime = bdcXzrzParameterVo.getStartTime();
        Date endTime = bdcXzrzParameterVo.getEndTime();
        // 文件list
        List<File> fileList = new ArrayList<>();
        String destPath = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            for (int i = 0; i < listApply.size(); i++) {
                String apply = listApply.get(i);
                int total = 0;
                // 每个应用服务的目录下找到日志所在目录  remoteContent日志所在目录
                if (!Utils.isBlank(listParentFile.get(apply))) {
                    Vector parentFile = channelSftp.ls(remoteParentPath + "/" + listParentFile.get(apply));
                    String randomFileName = "";
                    if (parentFile.size() != 0) {
                        for (int m = 0; m < parentFile.size(); m++) {
                            ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) parentFile.get(m);
                            SftpATTRS attrs = file.getAttrs();
                            boolean dir = attrs.isDir();
                            if (dir && !(file.getFilename().equals(".") || file.getFilename().equals(".."))) {
                                total++;
                                randomFileName = file.getFilename();
                            }
                        }
                        if (total == 1) {
                            //拼接路径
                            remoteContent = remoteParentPath + listParentFile.get(apply) + "/" + randomFileName + "/logs/";
                        } else {
                            remoteContent = remoteParentPath + listParentFile.get(apply) + "/logs/";
                        }
                    }
                    LOGGER.info("日志下载：远程主机{}的日志具体地址{}", applyname, remoteContent);
                    // 得到对应的应用服务的名字，作为文件夹的名字，创建文件夹。路径为配置中的临时文件地址拼接主机ip
                    String parentName = getCorrespondingFile(listApply.get(i), applyName + "/" + applyname + "/");
                    destPath = applyName + "/" + applyname + "/" + parentName + "/";
                    // ls获取文件名列表
                    vector = channelSftp.ls(remoteContent);
                    // 使用迭代器遍历
                    Iterator iterator = vector.iterator();
                    // 遍历远程日志地址下符合时间要求的日志，下载到本地刚创建的文件夹中，并记录日志文件地址到fileList
                    while (iterator.hasNext()) {
                        ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
                        // 拿到文件名
                        String filename = file.getFilename();
                        String path = remoteContent + filename;
                        int linuxTime = getLinuxTime(path);
                        long time = linuxTime * 1000L;
                        Date date = new Date(time);
                        // 得到当前文件名称时间字符串
                        String fileTime = getTimeConvertString(date);
                        Date stringDateFile = getStringConvertTime(fileTime);
                        // 判断文件名不是第一个.和第二个..并且文件的日期大于等于开始时间，小于等于结束时间的时候，进行io操作
                        if (!(filename.equals(".") || filename.equals(".."))
                                && stringDateFile.getTime() >= startTime.getTime()
                                && stringDateFile.getTime() <= endTime.getTime()) {
                            File file1 = createFile(destPath + filename);
                            try (
                                    InputStream inputStream = channelSftp.get(path);
                                    FileOutputStream outputStream = new FileOutputStream(file1)
                            ) {
                                byte[] bytes = new byte[1024 * 10];
                                int len = 0;
                                while ((len = inputStream.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, len);
                                }
                            } catch (FileNotFoundException e) {
                                LOGGER.info("", e);
                            }
                            // 添加文件
                            fileList.add(file1);
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.info("远程连接过程中出现错误",e);
        }finally {
            channelSftp.exit();
        }
        LOGGER.info("日志下载：符合条件的日志列表数量{}",fileList.size());
        if (CollectionUtils.isEmpty(fileList)){
            // 删除创建的文件夹
            FileUtils.deleteDirectory(new File(applyName +"/"+ applyname + "/"));
            delfile(applyName);
            return "";
        }else {
            // 删除空文件夹
            List<File> list = visitAll(new File(applyName +"/" +applyname));
            removeNullFile(list);
            return applyName +"/" +applyname;
        }
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
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        Vector vector = channelSftp.ls(remoteParentFile);
        channelSftp.exit();
        return vector;
    }

    /**
     * 生成对应的文件夹存放日志
     */
    public static String getCorrespondingFile(String parentFileName,String location){
        String name = parentFileName;
        File file = new File(location+parentFileName);
        file.mkdirs();
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
    /**
     * 判断文件是否存在，不存在就创建
     * @param file
     */
    public static File createFile(String file) throws IOException {
        String strPath = file;
        File file1 = new File(strPath);
        File fileParent = file1.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        file1.createNewFile();
        return file1;
    }

    /**
     * 复制文件夹
     * @param sourcePath
     * @param newPath
     */
    public static void copyDir(String sourcePath,String newPath) {
        try {
            (new File(newPath)).mkdirs();
            // 与mkdir()都创建文件夹 ，mkdirs()如果父文件夹不存在也会创建
            File fileList = new File(sourcePath);
            String[] strName = fileList.list();
            //游标
            File temp = null;
            for (int i = 0; i < strName.length; i++) {
                // 如果源文件路径以分隔符File.separator /或者\结尾那就sourcePath
                if (sourcePath.endsWith(File.separator)) {
                    temp = new File(sourcePath + strName[i]);
                } else {
                    temp = new File(sourcePath + File.separator + strName[i]);
                }
                if (temp.isFile()) {
                    // 如果游标遇到文件
                    FileInputStream in = new FileInputStream(temp);
                    // 复制且改名
                    File file = new File(newPath + "/" + temp.getName().toString());
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024 * 8];
                    int length;
                    while ((length = in.read(buffer)) != -1) {

                        out.write(buffer, 0, length);
                    }
                    out.flush();
                    out.close();
                    in.close();
                }
                // 如果游标遇到文件夹
                if (temp.isDirectory()) {
                    copyDir(sourcePath + "/" + strName[i], newPath + "/" + strName[i]);
                }
            }
        } catch (Exception e) {
            LOGGER.info("日志下载：文件夹复制失败!");
        }
    }

    /**
     * 压缩zip,循环压缩子目录文件
     */
    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if(file.exists()){
            //处理文件夹
            if(file.isDirectory()){
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                if(files.length != 0)
                {
                    for(File f:files){
                        writeZip(f, parentPath, zos);
                    }
                }
                else
                {       //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    //创建压缩文件
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    //添加压缩文件
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    LOGGER.error("创建ZIP文件失败",e);
                } catch (IOException e) {
                    LOGGER.error("创建ZIP文件失败",e);
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                        LOGGER.error("创建ZIP文件失败",e);
                    }
                }
            }
        }
    }
    private static List<File> list = new ArrayList<File>();

    /**
     * 得到某一目录下的所有文件夹
     * @param root
     * @return
     */
    public static List<File> visitAll(File root)
    {
        File[] dirs = root.listFiles();
        if (dirs != null)
        {
            for (int i = 0; i < dirs.length; i++)
            {
                if (dirs[i].isDirectory())
                {
                    list.add(dirs[i]);
                }
                visitAll(dirs[i]);
            }
        }
        return list;
    }
    /**
     * 删除空的文件夹
     * @param list
     */
    public static void removeNullFile(List<File> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            File temp = list.get(i);
            // 是目录且为空
            if (temp.isDirectory() && Objects.requireNonNull(temp.listFiles()).length <= 0)
            {
                temp.delete();
            }
        }
    }

    /**
     * 删除空文件夹
     * @param path
     */
    public static void delfile(String path){
        File file = new File(path);
        File []list = file.listFiles();
        if(Objects.requireNonNull(list).length<=0){
            file.delete();
            String filename = file.getParent();
            File file1 = new File(filename);
            delfile(file1.getPath());
        }
    }
    /**
     * 前缀字符串匹配
     * @param uiapply 服务名称
     * @param filename 文件名称
     * @return
     */
    public static boolean match(String uiapply,String filename){
        String uiapply1 = uiapply.replaceAll("\\d+","")
                .replaceAll("-","")
                .replaceAll("\\.","")
                .replace("realestate","");
        String filename1=filename.replaceAll("\\d+","")
                .replaceAll("-","")
                .replaceAll("\\.","")
                .replace("realestate","");
        return filename1.equals(uiapply1);
    }
}
