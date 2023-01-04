package cn.gtmap.realestate.certificate.core.model.abstraction;


import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/26 二维码
 */
public abstract class Qrcode {
    public final Logger logger = LoggerFactory.getLogger(Qrcode.class);
    // 二维码颜色
    public static final int BLACK = 0xFF000000;
    // 二维码颜色
    public static final int WHITE = 0xFFFFFFFF;
    // 二维码格式
    public static final String QRCODE_FORMAT_PNG = "png";
    // 二维码默认宽度
    public static final int DEFAULT_WIDTH = 500;
    // 二维码默认高度
    public static final int DEFAULT_HEIGHT = 500;
    // logo
    public byte[] logoData = PublicUtil.getFileByte(Constants.RESOURCES_IMG + "qrcode_logo_200.png");
    // logo默认宽度
    public int logoWidth = 200;
    // logo默认高度
    public int logoHeight = 200;
    // 二维码横坐标
    protected float qrcodeAbsoluteX;
    // 二维码纵坐标
    protected float qrcodeAbsoluteY;
    // 二维码内容
    protected String content;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 生成二维码（无logo）
     */
    public BufferedImage createQrCode(){
        return null;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 生成二维码（有logo）
     */
    public BufferedImage createQrCodeLogo() throws IOException{
        BufferedImage matrixImage = this.createQrCode();
        // 读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        // 读取Logo图片
        Image logo = ImageIO.read(new ByteArrayInputStream(logoData));

        //开始绘制图片
        g2.drawImage(logo, (matrixWidth - this.logoWidth) / 2 + 2, (matrixHeigh - this.logoHeight) / 2 + 1, this.logoWidth, this.logoHeight, null);

        g2.dispose();
        return matrixImage;
    }

    public static byte[] writeToStream(BufferedImage bufferedImage, String format){
        byte[] qrcodeByte = null;
        if (null != bufferedImage) {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
                bufferedImage.flush();
                if (ImageIO.write(bufferedImage, format, out)) {
                    qrcodeByte = out.toByteArray();
                }
            } catch (IOException e) {

            }
        }
        return qrcodeByte;
    }

    public float getQrcodeAbsoluteX() {
        return qrcodeAbsoluteX;
    }

    public void setQrcodeAbsoluteX(float qrcodeAbsoluteX) {
        this.qrcodeAbsoluteX = qrcodeAbsoluteX;
    }

    public float getQrcodeAbsoluteY() {
        return qrcodeAbsoluteY;
    }

    public void setQrcodeAbsoluteY(float qrcodeAbsoluteY) {
        this.qrcodeAbsoluteY = qrcodeAbsoluteY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public byte[] getLogoData() {
        return logoData;
    }

    public void setLogoData(byte[] logoData) {
        this.logoData = logoData;
    }
}
