package cn.gtmap.realestate.certificate.core.model.template;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/20
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzWatermark implements Serializable{
    private static final long serialVersionUID = -8264840525304936970L;
    @XmlTransient
    private final Logger logger = LoggerFactory.getLogger(DzzzWatermark.class);
    // 水印类型
    @XmlAttribute
    private String type;
    // 水印内容
    private String content;
    // 行数
    private Integer rows;
    // 列数
    private Integer columns;
    // 行高
    private Integer rowHeight;
    // 列宽
    private Integer colWidth;
    // 水印横坐标
    private float x;
    // 水印纵坐标
    private float y;
    // 旋转角度
    private float rotation;
    // 内容样式
    private DzzzFont font;

    public void addWatermark(PdfContentByte waterContent) throws DocumentException, IOException {
        waterContent.beginText();
        waterContent.setColorFill(font.getBaseColor());// 文字水印 颜色
        BaseFont f = font.getBaseFont();
        // 文字水印 字体及字号
        waterContent.setFontAndSize(f, font.getFontSize());
        float sX = x;
        float eY = y;
        // 循环行数
        for (int i = 1; i <= rows; i++) {
            // 若不是第一行 减去行高
            if (i != 1) {
                eY -= rowHeight;
            }
            // 循环列数
            for (int j = 1; j <= columns; j++) {
                // 若不是第一列 减去列宽
                if (j != 1) {
                    sX += colWidth;
                }
                waterContent.showTextAligned(Element.ALIGN_CENTER, content, sX, eY, rotation);
            }
            // 还原横坐标起始值
            sX = x;
        }
        waterContent.endText();
    }

    public byte[] addWatermark(byte[] pdf) {
        byte[] waterMarkByte = null;
        try (ByteArrayOutputStream waterMark = new ByteArrayOutputStream()) {
            PdfReader reader = new PdfReader(pdf);
            PdfStamper stamper = new PdfStamper(reader, waterMark);
            int total =reader.getNumberOfPages();
            for(int i =1 ; i <= total; i++){
                PdfContentByte waterContent = stamper.getOverContent(i);
                addWatermark(waterContent);
            }
            stamper.close();
            waterMarkByte = waterMark.toByteArray();
        } catch (IOException e) {
            logger.error("DzzzWatermark:addWatermark:IOException", e);
        } catch (DocumentException e) {
            logger.error("DzzzWatermark:addWatermark:DocumentException", e);
        }
        return waterMarkByte;
    }

    public byte[] addWatermark(byte[] pdf, Object o) {
        byte[] waterMarkByte = null;
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        if (null != bdcDzzzZzxx) {
            String jzjValue = (String) PublicUtil.getFieldValueByName(content, bdcDzzzZzxx);
            if (StringUtils.isNotBlank(jzjValue)) {
                this.content = jzjValue;
                waterMarkByte = addWatermark(pdf);
            }
        }

        return waterMarkByte;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
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

    public Integer getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Integer getColWidth() {
        return colWidth;
    }

    public void setColWidth(Integer colWidth) {
        this.colWidth = colWidth;
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

    @Override
    public String toString() {
        return "DzzzWatermark [type=" + type
                + ", content=" + content
                + ", rows=" + rows
                + ", columns=" + columns
                + ", rotation=" + rotation
                + ", font=" + font.toString()
                + ", rowHeight=" + rowHeight
                + ", colWidth=" + colWidth
                + ", x=" + x
                + ", y=" + y + "]";
    }
}
