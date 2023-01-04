package cn.gtmap.realestate.certificate.core.model.template;


import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzText implements Serializable{
    private static final long serialVersionUID = -535231700125732879L;
    // 内容
    private String content;
    // 内容横坐标
    private float x;
    // 内容纵坐标
    private float y;
    // 校准方式
    private int alignment;
    // 旋转角度
    private float rotation;
    // 内容样式
    private DzzzFont font;

    public void showTextAlign(PdfContentByte cb, Object o) throws IOException, DocumentException {
        BaseFont baseFont = font.getBaseFont();
        cb.setFontAndSize(baseFont, font.getFontSize());
        cb.setColorFill(font.getBaseColor());
        String value = (String) PublicUtil.getDeclaredFieldValueByName(content,o);
        cb.showTextAligned(alignment, value
                , x, y, rotation);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public DzzzFont getFont() {
        return font;
    }

    public void setFont(DzzzFont font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "DzzzText [content=" + content
                + ", x=" + x
                + ", y=" + y
                + ", alignment=" + alignment
                + ", rotation=" + rotation
                + ", font=" + font.toString() + "]";
    }
}
