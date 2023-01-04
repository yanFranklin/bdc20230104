package cn.gtmap.realestate.certificate.core.model.template;

;
import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzImage implements Serializable{
    private static final long serialVersionUID = -8560454188565913142L;
    // 图片类型
    @XmlAttribute
    private Integer type;
    // 图片路径
    private String imgPath;
    // 图片base64
    private String imgBase64;
    // 图片绝对定位横坐标
    private float x;
    // 图片绝对定位纵坐标
    private float y;
    // 图片图上宽度
    private float fitWidth;
    // 图片图上高度
    private float fitHeight;

    public void addImage(Document document, Object o) throws IOException, DocumentException {
        switch (type) {
            case 1:
                addBakImage(document);
                break;
            case 2:
                addBase64Image(document, o);
                break;
            default:
        }
    }

    public void addBakImage(Document document) throws IOException, DocumentException {
        Image image1 = Image.getInstance(PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE + BdcDzzzPdfUtil.DZZZ_DWDM + File.separator + imgPath));
        image1.scaleAbsolute(x, y);
        document.add(image1);
    }

    public void addBase64Image(Document document, Object o) throws IOException, DocumentException {
        String base64StrValue = (String) PublicUtil.getDeclaredFieldValueByName(imgBase64,o);
        byte[] data = Base64Util.decodeBase64StrToByte(base64StrValue);
        if (null != data) {
            Image image1 = Image.getInstance(data);
            image1.setAbsolutePosition(x, y);
            image1.scaleToFit(fitWidth, fitHeight);
            document.add(image1);
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public float getFitWidth() {
        return fitWidth;
    }

    public void setFitWidth(float fitWidth) {
        this.fitWidth = fitWidth;
    }

    public float getFitHeight() {
        return fitHeight;
    }

    public void setFitHeight(float fitHeight) {
        this.fitHeight = fitHeight;
    }

    @Override
    public String toString() {
        return "DzzzBackgroundImg [imgPath=" + imgPath
                + ", x=" + x
                + ", y=" + y
                + ", type=" + type
                + ", imgBase64Value=" + imgBase64
                + ", fitWidth=" + fitWidth
                + ", fitHeight=" + fitHeight +"]";
    }
}
