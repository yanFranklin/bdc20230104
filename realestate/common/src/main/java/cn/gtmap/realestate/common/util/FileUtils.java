package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/18
 * @description
 */
@Component
public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @params file 待上传文件
     * @param flagId 文件标识ID（PDF预览页面viewer.html页面生成的当前PDF标识，后台生成PDF时候将文件上传到大云，后续Adobe预览时候可以用这个标识寻找到大云文件）
     * @return {String} 大云storage存储ID
     * @description 上传打印PDF文件到大云storage （读取文件）
     */
    public void uploadPdfFileToStorage(File file, String flagId) {
        if(null == file || !file.exists()) {
            throw new MissingArgumentException("文件不存在，无法上传！");
        }

        try {
            byte[] fileBytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);
            StorageDto storageDto = storageClient.createRootFolder("BDCDJ_PDF_ID", "BDCDJ_PDF_SPACE", file.getName(), null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto("", storageDto, fileBytes, file.getName());
                storageDto = storageClient.multipartUpload(multipartDto);
            }
            redisUtils.addHashValue(CommonConstantUtils.REDIS_PDF_FILE, flagId, storageDto.getId(), 60 * 60 * 1000);
            LOGGER.info("缓存下载PDF文件，标识：{}，大云storageId:{}", flagId, storageDto.getId());
        } catch (Exception e) {
            LOGGER.error("文件上传异常!待上传文件路径：{}", file.getAbsolutePath(), e);
            throw new AppException("文件上传异常");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @params fileBytes 待上传文件字节数组
     * @param flagId 文件标识ID（PDF预览页面viewer.html页面生成的当前PDF标识，后台生成PDF时候将文件上传到大云，后续Adobe预览时候可以用这个标识寻找到大云文件）
     * @return {String} 大云storage存储ID
     * @description 上传打印PDF文件到大云storage (读取字节数组)
     */
    public void uploadPdfFileToStorage(byte[] fileBytes, String flagId) {
        if(null == fileBytes || 0 == fileBytes.length) {
            throw new MissingArgumentException("文件不存在，无法上传！");
        }

        try {
            StorageDto storageDto = storageClient.createRootFolder("BDCDJ_PDF_ID", "BDCDJ_PDF_SPACE", flagId, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto("", storageDto, fileBytes, flagId);
                storageDto = storageClient.multipartUpload(multipartDto);
            }
            redisUtils.addHashValue(CommonConstantUtils.REDIS_PDF_FILE, flagId, storageDto.getId(), 60 * 60 * 1000);
            LOGGER.info("缓存下载PDF文件，标识：{}，大云storageId:{}", flagId, storageDto.getId());
        } catch (Exception e) {
            LOGGER.error("文件上传异常!", e);
            throw new AppException("文件上传异常");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param fileFlagId 文件标识ID
     * @return {byte[]} 文件字节数组
     * @description 下载PDF打印文件内容
     */
    public byte[] getPdfStorageDownloadUrl(String fileFlagId) {
        if(StringUtils.isBlank(fileFlagId)) {
            throw new MissingArgumentException("指定文件标识不存在，无法获取大云文件信息！");
        }

        String storageId = redisUtils.getHashValue(CommonConstantUtils.REDIS_PDF_FILE, fileFlagId);
        LOGGER.info("待下载PDF文件，标识：{}，大云storageId:{}", fileFlagId, storageId);
        MultipartDto multipartDto = storageClient.download(storageId);
        return multipartDto.getData();
    }

    /**
     * 组织大云上传参数
     * @param currentUserName 当前用户名
     * @param fileByte        文件字节
     * @param fileName        文件名称
     * @return MultipartDto 大云上传参数
     */
    public MultipartDto getUploadParamDto(String currentUserName, StorageDto storageDto, byte[] fileByte, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(storageDto.getClientId());
        multipartDto.setData(fileByte);
        if (fileByte != null) {
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(storageDto.getName());
        }
        return multipartDto;
    }

    /**
     * @return void
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @params [file]
     * @description 递归删除该目录下所有目录和文件
     */
    public static void deleteDirectory(File file) {
        if (!file.exists()) {
            LOGGER.error("路径不存在！");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                } else {
                    deleteDirectory(f);
                    f.delete();
                }
            }
        }
    }

    public byte[] fileToByte(File file) {
        if (!file.exists()) {
            LOGGER.error("路径不存在！");
        }
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bout = null;
        try {
            //建立读取文件的文件输出流
            fin = new FileInputStream(file);
            //在文件输出流上安装节点流（更大效率读取）
            bin = new BufferedInputStream(fin);
            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
//            baos = new ByteArrayOutputStream();
            //创建一个新的缓冲输出流，以将数据写入指定的底层输出流
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }


    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param PdfFilePath 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static String pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            imgFolderPath = dstImgFolder;
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            PdfReader reader = new PdfReader(PdfFilePath);
            int pages = reader.getNumberOfPages();
            StringBuffer imgFilePath = new StringBuffer("");
            for (int i = 0; i < pages; i++) {
                String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append("_");
                imgFilePath.append(String.valueOf(i + 1));
                imgFilePath.append(".jpg");
                File dstFile = new File(imgFilePath.toString());
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, "jpg", dstFile);
            }
            file.delete();
            return parseImg2Base64(imgFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return "";
    }



    /**
     * 转换base
     * @param imgPath
     * @return
     */
    public static String parseImg2Base64(String imgPath){
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(imgPath);
        file.delete();
        return encode;
    }

    /**
     * 将图片旋转90度
     */
    public static BufferedImage rotateImage(BufferedImage bufferedImage, int angel) {
        if (bufferedImage == null) {
            return null;
        }

        if (angel < 0) {
            // 将负数角度，纠正为正数角度
            angel = angel + 360;
        }

        int imageWidth = bufferedImage.getWidth(null);
        int imageHeight = bufferedImage.getHeight(null);

        // 计算重新绘制图片的尺寸
        Rectangle rectangle = calculatorRotatedSize(new Rectangle(new Dimension(imageWidth, imageHeight)), angel);

        // 获取原始图片的透明度
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage newImage = null;
        newImage = new BufferedImage(rectangle.width, rectangle.height, type);
        Graphics2D graphics = newImage.createGraphics();

        // 平移位置
        graphics.translate((rectangle.width - imageWidth) / 2, (rectangle.height - imageHeight) / 2);

        // 旋转角度
        graphics.rotate(Math.toRadians(angel), imageWidth / 2, imageHeight / 2);

        // 绘图
        graphics.drawImage(bufferedImage, null, null);
        return newImage;
    }

    //计算旋转后的尺寸
    private static Rectangle calculatorRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));

        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;

        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }
}
