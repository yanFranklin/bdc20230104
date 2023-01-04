package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/01
 * @description Zip文件处理工具
 */
@Component
public class ZipUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";
    /**
     * 字节缓冲区大小
     */
    private static final int BUFFER_SIZE = 1024;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    UserManagerUtils userManagerUtils;


    /**
     * 解压zip文件
     *
     * @param zipFilePath      zip压缩文件完整路径，例如 H:\\文件.zip
     * @param unZipFileDestDir zip文件解压后保存的目录
     * @param encoding         zip文件的编码
     * @return 返回 zip 压缩文件里的文件名的 list
     * @throws Exception
     */
    public static List<String> unZip(String zipFilePath, String unZipFileDestDir, String encoding) throws Exception {
        if (StringUtils.isBlank(zipFilePath)) {
            throw new AppException("解压异常，未指定目标zip文件！");
        }

        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new AppException("解压异常，目标zip文件不存在！");
        }

        // 如果目标保存目录未指定则解压到压缩文件所在目录
        if (StringUtils.isBlank(unZipFileDestDir)) {
            unZipFileDestDir = zipFile.getParent();
        }

        if (StringUtils.isBlank(encoding)) {
            encoding = Charset.defaultCharset().name();
        }

        // 添加目录分隔符号 H:\
        unZipFileDestDir = unZipFileDestDir.endsWith(File.separator) ? unZipFileDestDir : unZipFileDestDir + File.separator;

        ZipArchiveInputStream zipFileInputStream = null;
        try {
            zipFileInputStream = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE), encoding);

            ZipArchiveEntry entry;
            List<String> fileNames = new ArrayList<>();
            while ((entry = zipFileInputStream.getNextZipEntry()) != null) {
                fileNames.add(entry.getName().trim());
                File file = new File(StringUtils.deleteWhitespace(unZipFileDestDir), StringUtils.deleteWhitespace(entry.getName()));

                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(file);
                } else {
                    OutputStream outputStream = null;
                    try {
                        FileUtils.touch(file);
                        outputStream = new FileOutputStream(new File(StringUtils.deleteWhitespace(unZipFileDestDir), StringUtils.deleteWhitespace(entry.getName())));
                        IOUtils.copy(zipFileInputStream, outputStream);
                    } finally {
                        IOUtils.closeQuietly(outputStream);
                    }
                }
            }
            return fileNames;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(zipFileInputStream);
        }
    }

    /**
     * zip压缩文件
     *
     * @param sourceFolder 需压缩文件或者文件夹 路径
     * @param zipFilePath  压缩文件输出路径
     * @return {String} 压缩文件输出路径
     * @throws Exception
     */
    public static String zip(String sourceFolder, String zipFilePath) throws Exception {
        LOGGER.debug("开始压缩[{}]到[{}]", sourceFolder, zipFilePath);

        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);
        // 解决中文文件名乱码
        zos.setEncoding(CHINESE_CHARSET);

        try {
            File file = new File(sourceFolder);
            String basePath = file.isDirectory() ? file.getPath() : file.getParent();
            zipfile(file, basePath, zos);
            return zipFilePath;
        } catch (Exception e) {
            LOGGER.error("zip压缩[{}]异常", sourceFolder, e);
            throw e;
        } finally {
            zos.closeEntry();
            IOUtils.closeQuietly(zos);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(out);
            LOGGER.debug("压缩 [{}] 完成！", sourceFolder);
        }
    }

    /**
     * 递归压缩文件
     *
     * @param parentFile 父目录
     * @param basePath   当前层级压缩路径
     * @param zos        zip文件输出流
     * @throws Exception
     */
    public static void zipfile(File parentFile, String basePath, ZipOutputStream zos) throws Exception {
        File[] files;
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }

        String pathName;
        byte[] cache = new byte[BUFFER_SIZE];
        for (File file : files) {
            if (file.isDirectory()) {
                LOGGER.debug("目录：{}", file.getPath());

                basePath = basePath.replace('\\', '/');
                if (basePath.substring(basePath.length() - 1).equals("/")) {
                    pathName = file.getPath().substring(basePath.length()) + "/";
                } else {
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                }

                zos.putNextEntry(new ZipEntry(pathName));
                zipfile(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length());
                pathName = pathName.replace('\\', '/');
                if (pathName.substring(0, 1).equals("/")) {
                    pathName = pathName.substring(1);
                }

                LOGGER.debug("压缩：{}", pathName);

                InputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                zos.putNextEntry(new ZipEntry(pathName));

                try {
                    int byteLength;
                    while ((byteLength = bufferedInputStream.read(cache, 0, BUFFER_SIZE)) != -1) {
                        zos.write(cache, 0, byteLength);
                    }
                } finally {
                    bufferedInputStream.close();
                    fileInputStream.close();
                }
            }
        }
    }


    /**
     * 循环创建大云文件--在本地按照大云的目录结构将文件下载到本地
     *
     * @param currentPath
     * @param zipfile
     * @param notCreateEmptyFolder
     */
    public void createFiles(String currentPath,
                            List<String> zipfile,
                            Boolean notCreateEmptyFolder
    ) {
        if (CollectionUtils.isEmpty(zipfile)) {
            return;
        }
        for (String storageId : zipfile) {
            StorageDto storageDto = storageClient.findById(storageId);
            //如果是目录循环创建
            if (CommonConstantUtils.FILETYPE_ML.equals(storageDto.getType())) {
                List<StorageDto> storageDtos = storageClient.listAllSubsetStorages(storageId, "", 1, 1, 0, null);
                //如果创建空文件夹---空文件夹不管创建的结果
                File file = new File(currentPath + storageDto.getName());
                if (CollectionUtils.isEmpty(storageDtos) && notCreateEmptyFolder) {
                    continue;
                }
                file.mkdir();
                List<String> childrenZipfile = storageDtos
                        .stream().map(StorageDto::getId).collect(Collectors.toList());
                //非空文件夹
                createFiles(currentPath + storageDto.getName() + "/",
                        childrenZipfile,
                        notCreateEmptyFolder
                );
            } else {
                //如果是文件则下载到当前目录
                try {
                    MultipartDto downloadFile = storageClient.download(storageId);
                    File file = new File(currentPath + downloadFile.getName());
                    if (Objects.isNull(file)) {
                        file.createNewFile();
                    }
                    //写入文件内容
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(downloadFile.getData());
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 上传文件夹到大云
     *
     * @param parentId
     * @param filePath
     * @param gzlslid
     * @return
     */
    public void uploadFiles(String parentId, String filePath, String gzlslid) {
        //获取文件类型和文件内容
        try {
            File file = new File(filePath);
            //如果是目录就创建目录然后遍历子节点
            if (file.isDirectory()) {
                //创建目录
                StorageDto storageDto = uploadFile(parentId, filePath, gzlslid);
                File[] files = file.listFiles();
                for (File childFile : files) {
                    uploadFiles(storageDto.getId(), filePath + "/" + childFile.getName(), gzlslid);
                }
            } else {
                //如果是文件则直接上传
                uploadFile(parentId, filePath, gzlslid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传单个文件或者目录
     *
     * @param parentId
     * @param filePath
     * @param gzlslid
     * @return
     */
    public StorageDto uploadFile(String parentId, String filePath, String gzlslid) {
        StorageDto currentStorageDto = new StorageDto();
        if (StringUtils.isNotBlank(parentId)) {
            currentStorageDto = storageClient.findById(parentId);
        }
        //获取文件类型和文件内容
        try {
            Path path = new File(filePath).toPath();
            String mimeType = Files.probeContentType(path);
            File file = new File(filePath);
            if (file.isDirectory()) {
                return storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID,
                        gzlslid,
                        currentStorageDto.getId(),
                        file.getName(),
                        userManagerUtils.getCurrentUserName());
            } else {
                FileInputStream inputStream = new FileInputStream(file);
                long length = file.length();
                byte[] bytes = IOUtils.readFully(inputStream, Math.toIntExact(length));
                //文件上传
                MultipartDto multipartDto = new MultipartDto();
                multipartDto.setNodeId(currentStorageDto.getId());
                multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
                multipartDto.setData(bytes);
                multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                multipartDto.setContentType(mimeType);
                multipartDto.setSize(length);
                multipartDto.setOriginalFilename(file.getName());
                multipartDto.setName(file.getName());
                multipartDto.setSpaceCode(gzlslid);
                StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                inputStream.close();
                file.delete();
                return storageDto;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void deleteDir(File directory){
        //获取目录下所有文件和目录
        File[] files = directory.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                deleteDir(file);
            }else {
                file.delete();
            }
        }
        //最终把该目录也删除
        directory.delete();
    }
}
