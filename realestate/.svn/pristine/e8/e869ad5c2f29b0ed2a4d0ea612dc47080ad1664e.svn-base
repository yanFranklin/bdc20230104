package cn.gtmap.realestate.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.swetake.util.Qrcode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/4/10,1.0
 * @description 生成二维码内容
 */
@Component
public class QRcodeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(QRcodeUtils.class);

    /**
     * 设置编码方式,默认是“ISO-8859-1”编码方式
     * 错误修正容量
     * L 1 水平 7%的字码可被修正
     * M 0 水平 15%的字码可被修正
     * Q 3 水平 25%的字码可被修正
     * H 2 水平 30%的字码可被修正
     */
    public static HashMap hints = new HashMap() {
        {
            put(EncodeHintType.CHARACTER_SET, "utf-8");
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            put(EncodeHintType.MARGIN, 0);
        }
    };

    /**
     * @param content  内容
     * @param imgPath  图片路径
     * @param response
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取二维码图片
     */
    public static void encoderQRCode(String content, String imgPath, HttpServletResponse response) {
        try {
            encoderCode(content, imgPath, BarcodeFormat.QR_CODE, response);
        } catch (Exception e) {
            LOGGER.error("二维码获取异常！", e);
        }

    }

    /**
     * @param content  内容
     * @param imgPath  图片路径
     * @param response
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取条码图片
     */
    public static void encoderCode(String content, String imgPath, BarcodeFormat barcodeFormat, HttpServletResponse response) {
        try {

            String s = URLDecoder.decode(content, "utf-8");
            byte[] contentBytes = s.getBytes(StandardCharsets.UTF_8);
            int picSize = 0;
            //图片大小设置 12*version + 67
            if (contentBytes.length < 152) {
                picSize = 151;
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            } else if (contentBytes.length <= 256) {
                picSize = 211;
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            } else {
                picSize = 259;
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            }

            creatRrCode(content,
                    picSize,
                    picSize, "jpg", imgPath, barcodeFormat, response);
        } catch (Exception e) {
            LOGGER.error("二维码获取异常！", e);
        }

    }


    public static void encoderQRCodeZx(String content, String imgPath, HttpServletResponse response) {
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');

            String s = URLDecoder.decode(content, "utf-8");
            byte[] contentBytes = s.getBytes(StandardCharsets.UTF_8);
            int picSize = 0;
            //图片大小设置 12*version + 67
            if (contentBytes.length < 152) {
                qrcodeHandler.setQrcodeVersion(8);
                picSize = 151;
            } else if (contentBytes.length <= 256) {
                qrcodeHandler.setQrcodeVersion(12);
                picSize = 211;
            } else {
                qrcodeHandler.setQrcodeVersion(16);
                picSize = 259;
            }


            BufferedImage bufImg = new BufferedImage(picSize, picSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, picSize, picSize);

            // 设定图像颜色> BLACK
            gs.setColor(Color.BLACK);

            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容> 二维码
            if (contentBytes.length > 0) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                LOGGER.info("QRCode content bytes length = "
                        + contentBytes.length + " not in [ 0,120 ]. ");
            }

            gs.dispose();
            bufImg.flush();

            // 生成二维码QRCode图片
            if (StringUtils.isNotBlank(imgPath)) {
                //生成下载图片
                File imgFile = new File(imgPath);
                ImageIO.write(bufImg, "jpg", imgFile);
            } else {
                //生成IO流
                ImageIO.write(bufImg, "jpg", response.getOutputStream());
            }
        } catch (Exception e) {
            LOGGER.error("二维码获取异常！", e);
        }

    }

    /**
     * 通过二维码内容生成图片 最终返回图片的base64
     *
     * @param content
     */
    public static String getBase64byEwmnr(String content) {
        ByteArrayOutputStream baos = null;
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            String s = URLDecoder.decode(content, "utf-8");
            byte[] contentBytes = s.getBytes("utf-8");
            int picSize = 0;
            if (contentBytes.length < 256) {
                qrcodeHandler.setQrcodeVersion(8);
                picSize = 148;
            }

            BufferedImage bufImg = new BufferedImage(picSize, picSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, picSize, picSize);

            // 设定图像颜色> BLACK
            gs.setColor(Color.BLACK);

            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容> 二维码
            if (contentBytes.length > 0) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                LOGGER.info("QRCode content bytes length = "
                        + contentBytes.length + " not in [ 0,120 ]. ");
            }

            gs.dispose();
            bufImg.flush();
            baos = new ByteArrayOutputStream();//io流

            ImageIO.write(bufImg, "jpg", baos);//写入流中


            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            //base64 += "data:image/jpg;base64,";
            //System.out.println("44444"+base64);
            return base64;
        } catch (Exception e) {
            LOGGER.error("二维码获取异常！", e);
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭流异常", e);
            }
        }
        return "";
    }

    /**
     * @param content
     * @param width
     * @param height
     * @param imgType
     * @param imgPath
     * @param response
     */
    public static void creatRrCode(String content,
                                   int width,
                                   int height,
                                   String imgType,
                                   String imgPath,
                                   BarcodeFormat barcodeFormat,
                                   HttpServletResponse response
    ) {
        //ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String binary = null;
            /* *
             * contents:条形码/二维码内容
             *  format：编码类型，如 条形码，二维码 等;BarcodeFormat.QR_CODE为二维条码编码格式
             *  width：码的宽度,单位为像素
             *  height：码的高度,单位为像素
             *  hints：码内容的编码类型
             *  BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
             *  BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码*/

            if (Objects.isNull(barcodeFormat)){
                barcodeFormat = BarcodeFormat.QR_CODE;
            }
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    content, barcodeFormat, width, height, hints);
            BufferedImage image = deleteWhite(bitMatrix);
//            if(width > 151){
//                image = deleteWhite(bitMatrix);
//            }else {
//                image = toBufferedImage(bitMatrix);
//            }
            image = zoomInImage(image, width, height);
            //转换成type格式的IO流
            if (StringUtils.isNotBlank(imgPath)) {
                File imgFile = new File(imgPath);
                ImageIO.write(image, imgType, imgFile);
            } else {
                ImageIO.write(image, imgType, response.getOutputStream());
            }
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不去除白边
     * image流数据处理
     * 0xFFFFFFFF 白色像素 ;0xFF000000 黑色像素
     * 像素生成模式 RGB
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 去除白边
     * 读取二维码区域重新生成一个BitMatrix
     */
    public static BufferedImage deleteWhite(BitMatrix matrix) {
        //排除0bit
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? BLACK
                        : WHITE);
            }
        }
        return image;
    }

    /**
     * 图片放大缩小
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
}
