package cn.gtmap.realestate.building.util;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/17
 * @description FTP下载公用类
 */

import cn.gtmap.realestate.building.config.ftp.FtpConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class FtpUtil {
    /**
     * 本地字符编码
     */
    private static volatile String LOCAL_CHARSET = "GBK";

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdtFtpConfig
     * @return org.apache.commons.net.ftp.FTPClient
     * @description 根据配置 获取 FTP链接
     */
    public static <T extends FtpConfig> FTPClient getFtpClient(T zdtFtpConfig) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(zdtFtpConfig.getIp(),
                zdtFtpConfig.getPort()!=null ? Integer.parseInt(zdtFtpConfig.getPort()):21);
        ftpClient.login(zdtFtpConfig.getUsername(), zdtFtpConfig.getPassword());
        // 登陆FTP服务器
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            logger.info("未连接到FTP，用户名或密码错误。");
            ftpClient.disconnect();
        } else {
            logger.info("FTP连接成功。");
        }
        return ftpClient;
    }

    /**
     * @param ftpHost     ip
     * @param ftpUserName 用户名
     * @param ftpPassword 密码
     * @param ftpPort     端口号 默认21
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取FtpClient类
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) throws IOException {
        FTPClient ftpClient = new FTPClient();
        //连接FTP服务器
        if (ftpPort == 0) {
            ftpClient.connect(ftpHost, 21);
        } else {
            ftpClient.connect(ftpHost, ftpPort);
        }
        ftpClient.login(ftpUserName, ftpPassword);
        // 登陆FTP服务器
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            logger.info("未连接到FTP，用户名或密码错误。");
            ftpClient.disconnect();
        } else {
            logger.info("FTP连接成功。");
        }
        return ftpClient;
    }

    /**
     * @param ftpPath  FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName 文件名称
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 及时获取响应报文 外层调用时，需要在 流关闭后 调用 ftpClient.completePendingCommand();
     */
    public static InputStream downloadFtpFile(FTPClient ftpClient, String ftpPath, String fileName) {
        InputStream in = null;
        try {
            getLocalCharSet(ftpClient);
            // FTP协议里面，规定文件名编码为iso-8859-1
            boolean boo = ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(LOCAL_CHARSET), FTP.DEFAULT_CONTROL_ENCODING));
            if (boo) {
                logger.info("FTP开始下载");
                in = ftpClient.retrieveFileStream(new String(fileName.getBytes(LOCAL_CHARSET), FTP.DEFAULT_CONTROL_ENCODING));
                logger.info("FTP下载成功");
            } else {
                logger.error("未能打开FTP目录,{}" , ftpPath );
            }
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件:", e);
        } catch (SocketException e) {
            logger.error("连接FTP失败:", e);
        } catch (IOException e) {
            logger.error("文件读取错误:", e);
        }
        return in;
    }



    /**
     * @param ftpClient
     * @param remoteUpLoadPath 上传的路径地址
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 创建对应目录
     */
    public static void createDirs(FTPClient ftpClient, String remoteUpLoadPath) {
        String[] dirs = remoteUpLoadPath.split("/");
        for (String dir : dirs) {
            try {
                ftpClient.makeDirectory(dir);
                ftpClient.changeWorkingDirectory(dir);
            } catch (IOException e) {
                logger.error("errorMsg:", e);
            }
        }
    }

    /**
     * @param ftpClient
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取本地编码
     */
    private static void getLocalCharSet(FTPClient ftpClient) {
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            ftpClient.enterLocalPassiveMode();// 设置被动模式
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
        } catch (IOException e) {
            logger.error("errorMsg:", e);
        }
    }

}