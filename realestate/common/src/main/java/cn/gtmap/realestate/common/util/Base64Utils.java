package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/19
 * @description
 */
public class Base64Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Utils.class);
    /*BASE64Encoder和BASE64Decoder这两个方法是sun公司的内部方法，并没有在java api中公开过，所以使用这些方法是不安全的，
     * 将来随时可能会从中去除，所以相应的应该使用替代的对象及方法，建议使用apache公司的API]
     */
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    /**
     * 将base64位码转换为文件
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            if(baseStrs.length > 1){
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] b = decoder.decodeBuffer(baseStrs[1]);
                for(int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                return new BASE64DecodedMultipartFile(b, baseStrs[0]);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    /**
     * @param bytes 字节信息
     * @return String base64字符串
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将字节base64加密
     */
    public static String encodeByteToBase64Str(byte[] bytes) {

        String base64Str = "";
        if (null != bytes) {
            base64Str = Base64.encodeBase64String(bytes);
        }
        return base64Str;
    }


    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param base64Str base64字符串
     * @return byte[]
     * @description 将base64字符串解密
     */
    public static byte[] decodeBase64StrToByte(String base64Str) {
        byte[] bytes = null;
        if (StringUtils.isNotBlank(base64Str)) {
            bytes = Base64.decodeBase64(base64Str);
        }
        return bytes;
    }

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param
     * @return
     * @description 将base64信息解密为文件
     */
    public static boolean decodeBase64StrToFile(String base64Str, String filePath) {
        byte[] bytes = decodeBase64StrToByte(base64Str);
        if (null != bytes) {
            String path = byteToFile(filePath, bytes);
            if (StringUtils.isNotBlank(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将字节信息转换为文件，返回文件路径
     */
    public static String byteToFile(String filePath, byte[] file) {
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), file);
            return filePath;
        } catch (IOException e) {
            LOGGER.error("byteToFile", e);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将base64信息，转换为jpg格式的图片文件流输出
     */
    public static void changeBase64ToImage(String imageStr, String filename, String formatName, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setDateHeader("expries", -1);
        //浏览器不缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
        ByteArrayInputStream bis = null;

        try {
            String[] baseStrs = imageStr.split(",");
            byte[] bytes = null;
            //有前缀名只取后面的base码
            if(baseStrs.length >1) {
                bytes = Base64Utils.decodeBase64StrToByte(baseStrs[1]);
            } else {
                bytes = Base64Utils.decodeBase64StrToByte(imageStr);
            }
            bis = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(bis);
            int width = (int) (image.getWidth(null) * 0.5);
            int height = (int) (image.getHeight(null) * 0.5);
            BufferedImage bufferImage= new BufferedImage (width,height,BufferedImage.TYPE_INT_RGB);
            bufferImage.getGraphics().drawImage(image,0,0,width,height,image.getGraphics().getColor(),null);//设置底色
            ImageIO.write(bufferImage, formatName, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("图像转换异常！", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭流异常", e);
                }
            }
        }
    }

    /**
     * 转换Base64加密的图片字符串为图片文件流
     * <p><code>scale</code>为图片缩放比例参数，例：缩放1/4大小 <code>scale = 0.25F</code></p>
     * @param imageStr     Base64加密图片字符串信息
     * @param filename     图片名称
     * @param formatName   图片格式
     * @param scale        缩放大小
     * @param response    HttpServletResponse
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    public static void changeBase64ToImageAndZoom(String imageStr, String filename, String formatName, float scale, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);

       final byte[] bytes = Base64Utils.decodeBase64StrToByte(imageStr);
        try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes)){
            BufferedImage image = ImageIO.read(bis);
            final int width = (int) (image.getWidth(null) * scale);
            final int height = (int) (image.getHeight(null) * scale);
            BufferedImage bufferImage= new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
            bufferImage.getGraphics().drawImage(image,0,0, width, height, image.getGraphics().getColor(),null);//设置底色
            ImageIO.write(bufferImage, formatName, response.getOutputStream());
        }catch (IOException e){
            LOGGER.error("图像转换异常！", e);
        }
    }

    public static void base64StringToPdf(String base64Content,String filePath) {

        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(base64Content);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //创建到指定文件的输出流
            File file = new File(filePath);
            fout = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);

            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
                fout.close();
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 网络图片转base64
     * @param url
     * @return base64
     */
    public static String encodeDzzzImageToBase64(URL url) {
        byte[] data = getFile(url.toString());
        if(data != null && data.length > 0){
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encodeBuffer(data).trim();//转换成base64串
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            return base64;
        }else{
            return "";
        }
    }

    /**
     * url转变为 MultipartFile对象
     * @param url
     * @param fileFormat
     * @return
     * @throws Exception
     */
    public static MultipartFile createFileItem(URL url, String fileFormat) throws Exception{
        FileItem item = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();

                FileItemFactory factory = new DiskFileItemFactory(16, null);
                String textFieldName = "uploadfile";
                String fileName = System.currentTimeMillis() + Math.random() + "." + fileFormat ;

                item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }finally {
            if(null != os){
                os.close();
            }
            if(null != is){
                is.close();
            }
        }

        return new CommonsMultipartFile(item);
    }


    /**
     * 获取文件流
     * @param url
     * @return
     */
    public static byte[] getFile(String url){
        URL urlConet = null;
        try {
            urlConet = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.error("解析url时，MalformedURLException异常！");
        }
        HttpURLConnection con = null;
        InputStream inStream = null;
        LOGGER.info("开始解析网络文件，当前时间：{}",System.currentTimeMillis());
        try {
            con = (HttpURLConnection)urlConet.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10 * 1000);
            inStream = con .getInputStream();    //通过输入流获取图片数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            byte[] data =  outStream.toByteArray();
            LOGGER.info("解析网络文件成功，当前时间：{}",System.currentTimeMillis());
            return data;
        } catch (IOException e) {
            LOGGER.info("解析网络文件失败，当前时间：{}",System.currentTimeMillis());
            LOGGER.error("通过输入流获取图片数据异常！{}",e);
        }finally {
            try {
                if(inStream != null){
                    inStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭输出流异常！");
            }
        }
       return null;
    }

    /**
     * 获取文件流
     * @param url
     * @return
     */
    public static byte[] testGetFile(String url){
        File file = new File("C:/Users/Administrator/Desktop/1.ofd");//待处理的图片
        InputStream is = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            is = new FileInputStream(file);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("C:/Users/Administrator/Desktop/2.ofd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //将字节写入文件
        try {
            fout.write(data);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return data;
    }


    public static String getPDFBinary(File file) {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;

        try {
            //建立读取文件的文件输出流
            fin = new FileInputStream(file);
            //在文件输出流上安装节点流（更大效率读取）
            bin = new BufferedInputStream(fin);
            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
            baos = new ByteArrayOutputStream();
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
            byte[] bytes = baos.toByteArray();
            //sun公司的API
            String pdfBasse64 = encoder.encodeBuffer(bytes).trim();
            return pdfBasse64;
            //apache公司的API
            //return Base64.encodeBase64String(bytes);
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
        return null;
    }

    /**
     * 文件转byte数组
     * @param file 文件
     * @return byte[]
     */
    public static byte[] getPDFByteArr(File file) {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;

        try {
            //建立读取文件的文件输出流
            fin = new FileInputStream(file);
            //在文件输出流上安装节点流（更大效率读取）
            bin = new BufferedInputStream(fin);
            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
            baos = new ByteArrayOutputStream();
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
            byte[] bytes = baos.toByteArray();
            //sun公司的API
            //String pdfBasse64 = encoder.encodeBuffer(bytes).trim();
            return bytes;
            //apache公司的API
            //return Base64.encodeBase64String(bytes);
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
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  将PDF文件地址转换为文件
     */
    public static MultipartFile changeUrltoMultipartFile(File pdfFile) throws IOException {
        String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(pdfBase64Str);
        if(null == multipartFile) {
            throw new AppException("保存PDF文件操作失败,原因: PDF文件转换Base64失败!");
        }
        return multipartFile;
    }

    public static byte[] getByteByIn(InputStream in){
        byte[] data = null;
        if(in !=null){
            // 读取图片字节数组
            try {
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static String blobToStr(Blob blob){
        String blobToStr = "";
        if(blob != null){
            try{
                int Bloblen = (int) blob.length();
                byte[] data = blob.getBytes(1,Bloblen);
                String type = bytesToHexString(data).toUpperCase();
                LOGGER.info("type:{}",type);
                // 判断是否是 WMF 格式
                if(type.startsWith("D7CDC")){
                    LOGGER.info("wmf格式");
                    data = WmfToJpgUtils.convert(data);
                }
                InputStream is = new ByteArrayInputStream(data);
                blobToStr = Base64Util.ImageToBase64ByIn(is);
                LOGGER.info("blobToStr: {}",blobToStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blobToStr;
    }

    /**
     * byte数组转换成16进制字符串
     * @param src add by sgh
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        if(src.length >= 128){
            for (int i = 0; i < 128; i++) {
                int v = src[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
        }
        return stringBuilder.toString();
    }


}
