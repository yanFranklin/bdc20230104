package cn.gtmap.realestate.certificate.core.model.template;

import cn.gtmap.realestate.certificate.core.model.abstraction.AbstractBdcDzzz;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzFont implements Serializable{
    private static final long serialVersionUID = -84652178244128365L;
    // 字体大小
    @XmlAttribute
    private float fontSize;
    // 字体颜色
    @XmlAttribute
    @XmlList
    private List<Integer> fontColor;
    // 字体样式
    @XmlAttribute
    private String fontStyle;

    public BaseColor getBaseColor(){
        return new BaseColor(fontColor.get(0), fontColor.get(1), fontColor.get(2));
    }

    public BaseFont getBaseFont() throws IOException, DocumentException {
        BaseFont font = null;
        switch (fontStyle) {
            case "楷体":
                font = BaseFont.createFont(AbstractBdcDzzz.FONT_IMAG_PATH
                        , BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                break;
            case "宋体":
                font = BaseFont.createFont(AbstractBdcDzzz.FONT_IMAG_PATH_SONGTI
                        , BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                break;
            default:
        }
        return font;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public List<Integer> getFontColor() {
        return fontColor;
    }

    public void setFontColor(List<Integer> fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    @Override
    public String toString() {
        return "DzzzFont [fontSize=" + fontSize
                + ", fontColor=" + fontColor
                + ", fontStyle=" + fontStyle + "]";
    }
}
