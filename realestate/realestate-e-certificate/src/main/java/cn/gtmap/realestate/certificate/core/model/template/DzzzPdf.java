package cn.gtmap.realestate.certificate.core.model.template;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.collections.CollectionUtils;

import javax.xml.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlRootElement(name = "DzzzPdf")
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzPdf implements Serializable{
    private static final long serialVersionUID = 1621981356600772204L;
    // 页面大小右上角x坐标
    @XmlAttribute
    private float urx;
    // 页面大小右上角y坐标
    @XmlAttribute
    private float ury;
    // 页
    private List<DzzzPdfPage> page;
    // 水印
    @XmlElementWrapper(name = "waterMarks")
    private List<DzzzWatermark> waterMark;

    private DzzzSignPosition sign;

    public byte[] createPdf(BdcDzzzZzxx bdcDzzzZzxx){
        byte[] rssult = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Rectangle pageSize = new Rectangle(urx, ury);
            Document document = new Document(pageSize, 0, 0, 0, 0);
            //创建一个DOC文档（根据指定的路径）
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            if (CollectionUtils.isNotEmpty(page)) {

                for (int i = 0; i < page.size(); i++) {
                    DzzzPdfPage pdfPage = page.get(i);

                    // 添加新页面
                    if (i != 0) {
                        document.newPage();
                    }

                    // 添加内容
                    List<DzzzText> texts = pdfPage.getText();
                    if (CollectionUtils.isNotEmpty(texts)) {
                        cb.beginText();
                        for (DzzzText text : texts) {
                            text.showTextAlign(cb, bdcDzzzZzxx);
                        }
                        cb.endText();
                    }

                    // 添加表格
                    List<DzzzTable> tables = pdfPage.getTable();
                    if (CollectionUtils.isNotEmpty(tables)) {
                        for (DzzzTable t : tables) {
                            t.writeTable(writer, bdcDzzzZzxx);
                        }
                    }

                    // 添加图片
                    List<DzzzImage> dzzzImages = pdfPage.getImg();
                    if (CollectionUtils.isNotEmpty(dzzzImages)) {
                        for (DzzzImage img : dzzzImages) {
                            img.addImage(document, bdcDzzzZzxx);
                        }
                    }

                    // 添加二维码
                    DzzzQrcode dzzzQrcode = pdfPage.getQrcode();
                    if (null != dzzzQrcode) {
                        dzzzQrcode.writeQrcode(document, bdcDzzzZzxx);
                    }
                }
            }

            document.close();
            if (null != baos) {
                rssult = baos.toByteArray();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rssult;
    }

    public float getUrx() {
        return urx;
    }

    public void setUrx(float urx) {
        this.urx = urx;
    }

    public float getUry() {
        return ury;
    }

    public void setUry(float ury) {
        this.ury = ury;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<DzzzPdfPage> getPage() {
        return page;
    }

    public void setPage(List<DzzzPdfPage> page) {
        this.page = page;
    }

    public List<DzzzWatermark> getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(List<DzzzWatermark> waterMark) {
        this.waterMark = waterMark;
    }

    public DzzzSignPosition getSign() {
        return sign;
    }

    public void setSign(DzzzSignPosition sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "DzzzPdf [page=" + page
                + ", urx=" + urx
                + ", ury=" + ury
                + ", waterMark=" + waterMark + "]";
    }
}
