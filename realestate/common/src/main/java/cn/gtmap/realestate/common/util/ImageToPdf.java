package cn.gtmap.realestate.common.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @program: realestate
 * @description: 图片转pdf
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-21 17:17
 **/
public class ImageToPdf {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageToPdf.class);

    public static File Pdf(ArrayList<String> imageUrllist, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20); //new一个pdf文档
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); //pdf写入
            doc.open();//打开文档
            for (int i = 0; i < imageUrllist.size(); i++) {  //循环图片List，将图片加入到pdf中
                doc.newPage();  //在pdf创建一页
                Image png1 = Image.getInstance(imageUrllist.get(i)); //通过文件路径获取image
                float heigth = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent(heigth, width);
                //设置对齐方式--居中
                png1.setAlignment(Image.MIDDLE);
                //将图像缩放到某个百分比
                png1.scalePercent(percent);// 表示是原来图像的比例;
                doc.add(png1);
            }
            doc.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("文件未找到:{}", e);
        } catch (DocumentException e) {
            LOGGER.error("文件夹未找到:{}", e);
        } catch (IOException e) {
            LOGGER.error("IO异常:{}", e);
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        if (!mOutputPdfFile.exists()) {
            //deleteOnExit()会出现内存泄漏的情况
            mOutputPdfFile.delete();
            return null;
        }
        return mOutputPdfFile; //反回文件输出流
    }

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 240 / h * 100;
        } else {
            p2 = 160 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    public static void imgOfPdf(String filepath, HttpServletRequest request) {
        boolean result = false;
        try {
            ArrayList<String> imageUrllist = new ArrayList<String>(); //图片list集合
//                imageUrllist.add(request.getSession()
//                        .getServletContext().getRealPath("\\" + filepath));  //添加图片文件路径
            imageUrllist.add(filepath);
            String fles = filepath.substring(0, filepath.lastIndexOf("."));
            String pdfUrl = fles + ".pdf";  //输出pdf文件路径
            File file = ImageToPdf.Pdf(imageUrllist, pdfUrl);//生成pdf
            if (file != null) {
                file.createNewFile();
            }
        } catch (IOException e) {
            LOGGER.error("IO异常:{}", e);
        }
    }

    /**
     * 毫米转像素
     * @param mm
     * @return
     */
    public static float mmTopx(float mm){
        mm = (float) (mm *3.33) ;
        return mm ;
    }

}
