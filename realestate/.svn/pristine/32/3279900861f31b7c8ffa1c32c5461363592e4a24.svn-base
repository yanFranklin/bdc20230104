package cn.gtmap.realestate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 图片增加图片水印
     * @param targetImagePath    原图片地址
     * @param waterMarkImagePath 水印图片地址
     * @param x X轴偏移量
     * @param y Y轴偏移量
     */
    public final static void addImageWaterMark( String targetImagePath, String waterMarkImagePath, int x, int y) {

        try {
            // 原文件
            BufferedImage targetImage = ImageIO.read(new File(targetImagePath));

            // 水印图片
            BufferedImage watermarkImage = ImageIO.read(new File(waterMarkImagePath));

            BufferedImage image = addWaterMark(targetImage, watermarkImage, x, y);

            try(FileOutputStream outputStream = new FileOutputStream(targetImagePath)){
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
//                encoder.encode(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage addWaterMark(BufferedImage targetImage, BufferedImage watermarkImage, int offectX, int OffectY){
        // 获取原图片大小
        int width = targetImage.getWidth(null);
        int height = targetImage.getHeight(null);

        // 获取水印图片大小
        int waterImgWidth = watermarkImage.getWidth(null);
        int waterImgHeight = watermarkImage.getHeight(null);

        // 创建新画布
        BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(targetImage, 0, 0, width, height, null);
        graphics.drawImage(watermarkImage, offectX, OffectY, waterImgWidth, waterImgHeight, null);
        graphics.dispose();
        return image;

    }

    /**
     * 图片右下角增加水印，返回 MultipartFile 对象
     * @param imageBase64  图片 base64 字符串
     * @param waterMarkImagePath 水印图片地址
     * @return MultipartFile Spring附件对象
     * @throws IOException
     */
    public final static MultipartFile addImageWaterMarkBottomRight(String imageBase64, String waterMarkImagePath) throws IOException {
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(imageBase64);

        // 原文件
        BufferedImage targetImage = ImageIO.read(multipartFile.getInputStream());
        int width = targetImage.getWidth(null);
        int height = targetImage.getHeight(null);

        // 水印图片
        BufferedImage watermarkImage = ImageIO.read(new File(waterMarkImagePath));
        int waterImgWidth = watermarkImage.getWidth(null);
        int waterImgHeight = watermarkImage.getHeight(null);

        // 设置水印图片的偏移量，水印添加到右下角
        int offset_x = width - waterImgWidth;
        int offset_y = height - waterImgHeight;
        offset_x  = offset_x > 0 ? offset_x : 0;
        offset_y  = offset_y > 0 ? offset_y : 0;

        // 构建水印画布
        BufferedImage image = addWaterMark(targetImage, watermarkImage, offset_x, offset_y);

        try(ByteArrayOutputStream os = new ByteArrayOutputStream();){
            ImageIO.write(image, "png", os);
            multipartFile = new BASE64DecodedMultipartFile(os.toByteArray(), imageBase64.split(",")[0]);
        } catch (IOException e) {
            LOGGER.error("生成水印图片异常", e);
        }
        return multipartFile;
    }

    /**
     * 对图片进行旋转
     *
     * @param src  被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension( src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src  被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
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
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

}
