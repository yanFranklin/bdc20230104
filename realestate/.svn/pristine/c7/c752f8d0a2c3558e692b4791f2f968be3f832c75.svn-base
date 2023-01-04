package cn.gtmap.realestate.certificate.core.model.dzzzgl;

import cn.gtmap.realestate.certificate.core.model.abstraction.AbstractBdcDzzz;
import cn.gtmap.realestate.certificate.core.model.abstraction.Watermark;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/10 文字水印
 */
public class WatermarkText extends Watermark {

    private BaseColor color = new BaseColor(176, 116, 127);
    private float fontSize = 15;
    private BaseFont font;
    private PdfContentByte waterContent;
    // 水印内容
    private String content;
    // 旋转角度
    private float rotation = 45;

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description  证照状态注销时体现注销水印 三行两列
     */
    @Override
    public boolean addWatermark(float oddTextX, float oddTextY
            ,float evenTextX,float evenTextY,int yInterval) throws DocumentException, IOException {
        float oX = oddTextX;
        float oY = oddTextY;
        float eX = evenTextX;
        float eY = evenTextY;
        int yI = yInterval;
        waterContent.beginText();
        waterContent.setColorFill(color);// 文字水印 颜色
        font = BaseFont.createFont(AbstractBdcDzzz.FONT_IMAG_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 文字水印 字体及字号
        waterContent.setFontAndSize(font, fontSize);
        for (int i = 1; i <= 6; i++) {
            if ((i & 1) == 1) {
                waterContent.showTextAligned(Element.ALIGN_CENTER, content, oX, oY, rotation);
                oY = oY - yI;
            } else {
                waterContent.showTextAligned(Element.ALIGN_CENTER, content, eX, eY, rotation);
                eY = eY - yI;
            }
        }
        waterContent.endText();
        return true;
    }

    /**
     * @param pdf
     * @return
     * @description 电子证照增加注销水印
     */
    public byte[] addWatermark(byte[] pdf,float oddTextX, float oddTextY
            ,float evenTextX,float evenTextY,int yInterval) {
        byte[] waterMarkByte = null;
        try (ByteArrayOutputStream waterMark = new ByteArrayOutputStream()) {
            PdfReader reader = new PdfReader(pdf);
            PdfStamper stamper = new PdfStamper(reader, waterMark);
            int total =reader.getNumberOfPages();
            for(int i =1 ; i <= total; i++){
                waterContent = stamper.getOverContent(i);
                addWatermark(oddTextX, oddTextY
                , evenTextX, evenTextY, yInterval);
            }
            stamper.close();
            waterMarkByte = waterMark.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return waterMarkByte;
    }

    public BaseColor getColor() {
        return color;
    }

    public void setColor(BaseColor color) {
        this.color = color;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public BaseFont getFont() {
        return font;
    }

    public void setFont(BaseFont font) {
        this.font = font;
    }

    public PdfContentByte getWaterContent() {
        return waterContent;
    }

    public void setWaterContent(PdfContentByte waterContent) {
        this.waterContent = waterContent;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
