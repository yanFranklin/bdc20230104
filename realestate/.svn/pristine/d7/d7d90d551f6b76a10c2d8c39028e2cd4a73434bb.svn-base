package cn.gtmap.realestate.inquiry.util;


import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
 * @version 1.0  2018/12/10.
 * @description
 */
public class FileUtils {

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
     * 拷贝完
     * @param source
     * @param dest
     */
    public static void copyFileUsingFileStreams(File source, File dest){
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(input != null){
                    input.close();
                }
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据文件路径获取base64编码字符串，不用换行
     * @Date 2022/5/28 13:56
     **/
    public static String getPDFBinary(String path) {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;
        File file = null;
        try {
            file = new File(path);
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
            String pdfBasse64 = Base64.encodeBase64String(bytes);
            return pdfBasse64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.delete();
                }
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
