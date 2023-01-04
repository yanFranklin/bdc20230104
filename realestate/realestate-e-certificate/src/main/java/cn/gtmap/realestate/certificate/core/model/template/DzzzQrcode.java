package cn.gtmap.realestate.certificate.core.model.template;

import cn.gtmap.realestate.certificate.core.model.abstraction.Qrcode;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.ZxingQrcode;

import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzQrcode implements Serializable{
    private static final long serialVersionUID = 5789741282079947137L;
    // 二维码类型
    @XmlAttribute
    private String type;
    // 二维码内容
    @XmlList
    private List<String> content;
    // 二维码宽度
    private int width;
    // 二维码高度
    private int height;
    // 二维码图上宽度
    private float fitWidth;
    // 二维码图上高度
    private float fitHeight;
    // 二维码横坐标
    private float x;
    // 二维码纵坐标
    private float y;
    // 二维码格式
    private String format;
    // 二维码logo路径
    private String logoPath;
    // 二维码logo宽度
    private int logoWidth;
    // 二维码logo高度
    private int logoHeight;

    public void writeQrcode(Document document, Object o) throws IOException, DocumentException {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        if (null != bdcDzzzZzxx) {
            if (CollectionUtils.isNotEmpty(content)) {
                StringBuilder qrcodeValue = new StringBuilder();
                for (int i = 0; i<content.size();i++) {
                    String value = (String) PublicUtil.getFieldValueByName(content.get(i),bdcDzzzZzxx);
                    if (i == 0) {
                        qrcodeValue.append(value);
                    } else {
                        qrcodeValue.append("&").append(value);
                    }
                }
                if (StringUtils.isNotBlank(qrcodeValue.toString())) {
                    ZxingQrcode zxingQrcode = new ZxingQrcode(qrcodeValue.toString());
                    zxingQrcode.setWidth(width);
                    zxingQrcode.setHeight(height);
                    BufferedImage bufferedImage = null;
                    if (StringUtils.equals(Constants.QRCODE_TYPE_LOGO,type)) {
                        zxingQrcode.setLogoHeight(logoHeight);
                        zxingQrcode.setLogoWidth(logoWidth);
                        zxingQrcode.setLogoData(PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE + BdcDzzzPdfUtil.DZZZ_DWDM + File.separator + logoPath));
                        bufferedImage = zxingQrcode.createQrCodeLogo();
                    } else if (StringUtils.equals(Constants.QRCODE_TYPE,type)) {
                        bufferedImage = zxingQrcode.createQrCode();
                    }

                    if (null != bufferedImage) {
                        byte[] out = Qrcode.writeToStream(bufferedImage, Qrcode.QRCODE_FORMAT_PNG);
                        if (null != out) {
                            Image codeQrImage = Image.getInstance(out);
                            codeQrImage.scaleToFit(fitWidth, fitHeight);
                            codeQrImage.setAbsolutePosition(x, y);
                            document.add(codeQrImage);
                        }

                        bdcDzzzZzxx.setEwmnr(qrcodeValue.toString());
                    }
                }
            }
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
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
        return "DzzzQrcode [content=" + content
                + ", width=" + width
                + ", height=" + height
                + ", x=" + x
                + ", y=" + y
                + ", format=" + format
                + ", logoPath=" + logoPath
                + ", logoWidth=" + logoWidth
                + ", logoHeight=" + logoHeight
                + ", fitWidth=" + fitWidth
                + ", fitHeight=" + fitHeight +"]";
    }
}
