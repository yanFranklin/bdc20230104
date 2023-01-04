package cn.gtmap.realestate.certificate.core.model.dzzzgl;

import cn.gtmap.realestate.certificate.core.model.abstraction.Qrcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/26
 */
public class ZxingQrcode extends Qrcode {

    private int width;
    private int height;

    @Override
    public BufferedImage createQrCode() {
        BufferedImage bufferedImage = null;
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            //设置UTF-8， 防止中文乱码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置二维码四周白色区域的大小
            hints.put(EncodeHintType.MARGIN, 0);
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            //设置二维码的容错性
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            bitMatrix = deleteWhite(bitMatrix);
            bufferedImage = toBufferedImageDelWhite(bitMatrix);
        } catch (Exception e) {
            logger.error("ZxingQrcode:createQrCode", e);
        }
        return bufferedImage;
    }

    @Override
    public BufferedImage createQrCodeLogo() throws IOException {
        return super.createQrCodeLogo();
    }

    /**
     * @param matrix
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 返回二维码
     */
    public BufferedImage toBufferedImage(BitMatrix matrix) {
        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * @param matrix
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 返回二维码 删除白边
     */
    public BufferedImage toBufferedImageDelWhite(BitMatrix matrix) {
        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < matrix.getWidth(); x++) {
            for (int y = 0; y < matrix.getHeight(); y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK
                        : WHITE);
            }
        }
        return image;
    }

    /**
     * @param matrix
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 去掉二维码白边
     */
    public BitMatrix deleteWhite(BitMatrix matrix) {
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
        return resMatrix;
    }

    public ZxingQrcode(String content) {
        this.width = Qrcode.DEFAULT_WIDTH;
        this.height = Qrcode.DEFAULT_HEIGHT;
        this.content = content;
    }

    public ZxingQrcode(String content, int width, int height) {
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
