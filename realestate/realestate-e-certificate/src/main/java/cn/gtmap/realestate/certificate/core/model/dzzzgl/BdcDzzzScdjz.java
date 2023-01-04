package cn.gtmap.realestate.certificate.core.model.dzzzgl;

import cn.gtmap.realestate.certificate.core.model.abstraction.AbstractBdcDzzz;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/21
 */
public class BdcDzzzScdjz extends AbstractBdcDzzz implements Serializable {

    private static final long serialVersionUID = -968566076960326358L;

    @Override
    public byte[] create(){

        byte[] rssult = null;
        Rectangle pageSize = new Rectangle(PageSize.A4);
        Document document = new Document(pageSize);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            //文本字体
            BaseFont bf = BaseFont.createFont(FONT_IMAG_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            if (null != writer) {
                PdfContentByte cb = writer.getDirectContent();

                // 加文字水印
                if (BdcDzzzPdfUtil.DZZZ_SCDJZ_IS_ADDWATERMARK_TEXT) {
                    addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ODDTEXTY
                            ,BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_EVENTEXTY
                            ,BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_YINTERVAL,bdcDzzzZzxx.getJzjzzsy(),BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ROTATION);
                }


                cb.beginText();
                cb.setFontAndSize(bf, FONT_SIZE_TEN);
                cb.setColorFill(BaseColor.BLACK);

                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getBdcqzh(), (float) 345.0, (float) 700.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getQlr(), (float) 167.0, (float) 670.0, 0);
                int zlWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_ZL_WORDNUM);
                int zlLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_ZL_LINESPACING);
                cb = textSubstring(this.bdcDzzzZzxx.getZl(), cb, (float) 167.0, (float) 640.0, zlWordNum, zlLineSpacing);
                String bdcdyh = this.bdcDzzzZzxx.getBdcdyh().substring(0, 6) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(6, 12) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(12, 19) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(19);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, bdcdyh, (float) 167.0, (float) 610.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getQllx(), (float) 167.0, (float) 580.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getQlxz(), (float) 167.0, (float) 548.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getYt(), (float) 167.0, (float) 518.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getMj(), (float) 167.0, (float) 488.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getSyqx(), (float) 167.0, (float) 455.0, 0);
                int qlqtzkWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_QLQTZK_WORDNUM);
                int qlqtzkLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_QLQTZK_LINESPACING);
                cb = textSubstring(this.bdcDzzzZzxx.getQlqtzk(), cb, 167.0, 420.0, qlqtzkWordNum, qlqtzkLineSpacing, "\\n");
                int fjWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_FJ_WORDNUM);
                int fjLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_SCDJZ_FJ_LINESPACING);
                cb = textSubstring(this.bdcDzzzZzxx.getFj(), cb, 167.0, 330.0, fjWordNum, fjLineSpacing, "\\n");
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, PublicUtil.convertDateToStr(this.bdcDzzzZzxx.getZzbfrq()), (float) 407.0, (float) 131.0, 0);

                cb.endText();
                //添加图片
                Image image = Image.getInstance(PublicUtil.getFileByte(Constants.RESOURCES_IMG + "首次登记证模板正面.jpg"));
                image.scaleAbsolute((float) 530, (float) 740);
                document.add(image);

            }

            if (null != baos) {
                rssult = baos.toByteArray();
            }
        } catch (DocumentException e) {
            logger.error("DocumentException:生成首次登记证",e);
        } catch (IOException e) {
            logger.error("IOException:生成首次登记证",e);
        } finally {
            //关闭流
            document.close();
        }

        return rssult;
    }

    public BdcDzzzScdjz(BdcDzzzZzxx bdcDzzzZzxx) {
        super();
        this.bdcDzzzZzxx = bdcDzzzZzxx;
    }

    public BdcDzzzScdjz() {
    }

}
