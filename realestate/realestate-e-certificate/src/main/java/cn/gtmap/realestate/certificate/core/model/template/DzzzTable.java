package cn.gtmap.realestate.certificate.core.model.template;


import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
public class DzzzTable implements Serializable {
    private static final long serialVersionUID = -4000929033296475453L;
    // 表格内容
    private String content;
    // 表格宽度
    private float width;
    // 表格高度
    private float height;
    // 表格边框宽度
    private int border;
    // 表格边框颜色
    @XmlList
    private List<Integer> borderColor;
    // 表格内容水平对齐方式
    private int horizontalAlignment;
    // 表格内容垂直对齐方式
    private int verticalAlignment;
    // 表格横坐标
    private float x;
    // 表格纵坐标
    private float y;
    // 内容样式
    private DzzzFont font;

    public void writeTable(PdfWriter writer, Object o) throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(border);
        table.setTotalWidth(width);
        BaseFont kaiti = font.getBaseFont();
        Font f = new Font(kaiti, font.getFontSize(), Font.NORMAL);
        PdfPCell cell = null;
        if (StringUtils.equals(content, "bdcdyh")) {
            String bdcdyh = (String) PublicUtil.getDeclaredFieldValueByName(content, o);
            cell = new PdfPCell(new Phrase(bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12) + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19), f));
        } else {
            cell = new PdfPCell(new Phrase((String) PublicUtil.getDeclaredFieldValueByName(content, o), f));
        }
        cell.setFixedHeight(height);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        if (0 != border) {
            cell.setBorderColor(getBaseColor());
        } else {
            cell.setBorder(0);
        }
        table.addCell(cell);
        table.writeSelectedRows(0, -1, x, y, writer.getDirectContent());
    }

    public BaseColor getBaseColor() {
        return new BaseColor(borderColor.get(0), borderColor.get(1), borderColor.get(2));
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public List<Integer> getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(List<Integer> borderColor) {
        this.borderColor = borderColor;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(int verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DzzzFont getFont() {
        return font;
    }

    public void setFont(DzzzFont font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "DzzzTable [width=" + width
                + ", height=" + height
                + ", border=" + border
                + ", borderColor=" + borderColor
                + ", horizontalAlignment=" + horizontalAlignment
                + ", verticalAlignment=" + verticalAlignment
                + ", x=" + x
                + ", y=" + y
                + ", content=" + content
                + ", font=" + font + "]";
    }
}
