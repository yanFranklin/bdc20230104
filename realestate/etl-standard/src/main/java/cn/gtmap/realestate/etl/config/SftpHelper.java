package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.etl.core.pool.SftpPool;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

// sftp辅助类
public class SftpHelper {

    private static final Logger logger = LoggerFactory.getLogger(SftpHelper.class);

    private SftpPool pool;

    public SftpHelper(SftpPool pool) {
        this.pool = pool;
    }

    /**
     * 下载文件 TODO 有问题
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @return 文件字节数组
     */
    public byte[] download(String dir, String name) {
        ChannelSftp sftp = pool.borrowObject();
        try {
            sftp.cd(dir);
            InputStream in = sftp.get(name);
            return Base64Utils.getByteByIn(in);
        } catch (SftpException e) {
            throw new RuntimeException("sftp下载文件出错", e);
        } finally {
            pool.returnObject(sftp);
        }
    }
    private boolean checkFileExist(String localPath) {
        File file = new File(localPath);
        return file.exists();
    }

    public void downloadFile(String dir, String name, String localFile) throws Exception {
        logger.info("sftp download File remotePath :" + dir + File.separator + name + " to localPath : " + localFile + " !");
        ChannelSftp sftp = pool.borrowObject();
        OutputStream output = null;
        File file = null;
        try {
            file = new File(localFile);
            if (!checkFileExist(localFile)) {
                file.createNewFile();
            }
            output = new FileOutputStream(file);
            sftp.cd(dir);
            sftp.get(name, output);
        } catch (Exception e) {
            logger.error("Download file error", e);
            throw new Exception("Download file error.");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    throw new Exception("Close stream error.");
                }
            }

        }
    }

    public void downloadFile(String dir, String name, OutputStream output) throws Exception {
        logger.info("sftp download File remotePath :" + dir + File.separator + name );
        ChannelSftp sftp = pool.borrowObject();
        try{
            sftp.cd(dir);
            // 判断文件是否存在
            InputStream inputStream = sftp.get(name);
            if(null == inputStream){
                name = name.toLowerCase();
            }
        }catch(Exception e){
            logger.info("file not exists");
            name = name.toLowerCase();
        }

        // 下载文件
        try {
            logger.info("实际下载操作sftp download File remotePath :" + dir + File.separator + name);
            sftp.get(name, output);
        } catch (Exception e) {
            logger.error("Download file error", e);
            throw new Exception("Download file error.");
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                    pool.returnObject(sftp);
                } catch (IOException e) {
                    throw new Exception("Close stream error.");
                }
            }

        }
    }

    /**
     * 上传文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @param in   输入流
     */
    public void upload(String dir, String name, InputStream in) {
        ChannelSftp sftp = pool.borrowObject();
        try {
            mkdirs(sftp, dir);
            sftp.cd(dir);
            sftp.put(in, name);
        } catch (SftpException e) {
            throw new RuntimeException("sftp上传文件出错", e);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 删除文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     */
    public void delete(String dir, String name) {
        ChannelSftp sftp = pool.borrowObject();
        try {
            sftp.cd(dir);
            sftp.rm(name);
        } catch (SftpException e) {
            throw new RuntimeException("sftp删除文件出错", e);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 递归创建多级目录
     *
     * @param dir 多级目录
     */
    private void mkdirs(ChannelSftp sftp, String dir) {
        String[] folders = dir.split("/");
        try {
            sftp.cd("/");
            for (String folder : folders) {
                if (folder.length() > 0) {
                    try {
                        sftp.cd(folder);
                    } catch (Exception e) {
                        sftp.mkdir(folder);
                        sftp.cd(folder);
                    }
                }
            }
        } catch (SftpException e) {
            throw new RuntimeException("sftp创建目录出错", e);
        }
    }

}