package cn.gtmap.realestate.certificate.core.model.dzzzgl;


import cn.gtmap.realestate.certificate.core.model.abstraction.AbstractBdcDzzz;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/14
 */
public class BdcDzzzZs extends AbstractBdcDzzz implements Serializable {
    private static final long serialVersionUID = -4810389050075950657L;
    public static final byte[] ZS_BAK_IMAGE = PublicUtil.getFileByte(Constants.RESOURCES_IMG + "不动产登记电子证书.jpg");

    @Override
    public byte[] create() {
        byte[] rssult = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Rectangle pageSize = new Rectangle(2526 / 3, 1785 / 3);
            Document document = new Document(pageSize, 0, 0, 0, 0);
            BaseFont kaiti = BaseFont.createFont(FONT_IMAG_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            if (null != writer) {

                PdfContentByte cb = writer.getDirectContent();

                // 加文字水印
                if (BdcDzzzPdfUtil.DZZZ_ZS_IS_ADDWATERMARK_TEXT && StringUtils.isNotBlank(bdcDzzzZzxx.getJzjzzsy())) {
                    addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ODDTEXTY
                    ,BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_EVENTEXTY
                    ,BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_YINTERVAL,bdcDzzzZzxx.getJzjzzsy(),BdcDzzzPdfUtil.DZZZ_ZS_JZJWATERMARK_ROTATION);
                }

                cb.beginText();
                cb.setFontAndSize(kaiti, FONT_SIZE_THIRTEEN);
                cb.setColorFill(BaseColor.BLACK);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getYear(), (float) 267.0, (float) 158.3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getMonth(), (float) 315.0, (float) 158.3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getDay(), (float) 352.0, (float) 158.3, 0);

                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getSqsjc(), (float) 450.0, (float) 504.5, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getNf(), (float) 484.0, (float) 504.5, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getSzsxqc(), (float) 530.0, (float) 504.5, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getZhlsh(), (float) 695.0, (float) 504.5, 0);

                int qlrWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_QLR_WORDNUM);
                int qlrLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_QLR_LINESPACING);
                cb = textSubstring2(this.bdcDzzzZzxx.getQlr(), cb, (float) 537.5, (float) 468.5, qlrWordNum, qlrLineSpacing);

                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getGyqk(), (float) 537.5, (float) 440.0, 0);
                int zlWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_ZL_WORDNUM);
                int zlLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_ZL_LINESPACING);
                cb = textSubstring2(this.bdcDzzzZzxx.getZl(), cb, (float) 537.5, (float) 410.0, zlWordNum, zlLineSpacing);
                String bdcdyh = this.bdcDzzzZzxx.getBdcdyh().substring(0, 6) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(6, 12) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(12, 19) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(19);

                int bdcdyhWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_BDCDYH_WORDNUM);
                int bdcdyhLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_BDCDYH_LINESPACING);
                cb = textSubstring2(bdcdyh, cb, (float) 537.5, (float) 380.0, bdcdyhWordNum, bdcdyhLineSpacing);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getQllx(), (float) 537.5, (float) 352.0, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.bdcDzzzZzxx.getQlxz(), (float) 537.5, (float) 323.1, 0);
                int ytWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_YT_WORDNUM);
                int ytLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_YT_LINESPACING);
                cb = textSubstring2(this.bdcDzzzZzxx.getYt(), cb, (float) 537.5, (float) 293.1, ytWordNum, ytLineSpacing);

                int mjWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_MJ_WORDNUM);
                int mjLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_MJ_LINESPACING);
                cb = textSubstring2(this.bdcDzzzZzxx.getMj(), cb, (float) 537.5, (float) 263.5, mjWordNum, mjLineSpacing);

                int syqxWordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_SYQX_WORDNUM);
                int syqxLineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZS_SYQX_LINESPACING);
                cb = textSubstring2(this.bdcDzzzZzxx.getSyqx(), cb, (float) 537.5, (float) 233.0, syqxWordNum, syqxLineSpacing);
                cb.endText();

                PdfPTable table = new PdfPTable(1);
                table.setTotalWidth(227);
                if (BdcDzzzPdfUtil.DZZZ_ZS_QLQTZK_CREATE) {
                    Font font = new Font(kaiti, BdcDzzzPdfUtil.DZZZ_ZS_QLQTZK_FONTSIZE, Font.NORMAL);
                    PdfPCell qlqtzk = new PdfPCell(new Phrase(this.bdcDzzzZzxx.getQlqtzk(), font));
                    qlqtzk.setFixedHeight(72);
                    qlqtzk.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    qlqtzk.setBorder(0);
                    table.addCell(qlqtzk);
                }
                if (BdcDzzzPdfUtil.DZZZ_ZS_FJ_CREATE) {
                    Font font = new Font(kaiti, BdcDzzzPdfUtil.DZZZ_ZS_FJ_FONTSIZE, Font.NORMAL);
                    PdfPCell fj = new PdfPCell(new Phrase(this.bdcDzzzZzxx.getFj(), font));
                    fj.setFixedHeight(71);
                    fj.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    fj.setBorder(0);
                    table.addCell(fj);
                }
                // 表格位置
                if (!BdcDzzzPdfUtil.DZZZ_ZS_QLQTZK_CREATE && BdcDzzzPdfUtil.DZZZ_ZS_FJ_CREATE) {
                    table.writeSelectedRows(0, -1, 537, 152, writer.getDirectContent());
                } else {
                    table.writeSelectedRows(0, -1, 537, 224, writer.getDirectContent());
                }

                Image image1 = Image.getInstance(ZS_BAK_IMAGE);
                image1.scaleAbsolute((float) 842.0, (float) 595.0);
                document.add(image1);

                // 添加二维码
                addQrode(document, qrcode.createQrCodeLogo());

                //证照如果是注销状态增加注销水印
                if (Constants.BDC_DZZZ_ZZZT_N.equals(this.bdcDzzzZzxx.getZzzt())) {
                    if (!StringUtils.equals(Constants.DZZZ_ZZZT_ZX_YCZX,this.bdcDzzzZzxx.getZzbgyy())) {
                        addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_YINTERVAL, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_TEXT
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ROTATION);
                    }else {
                        addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_YINTERVAL, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_TEXT_YC
                                , BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ROTATION);
                    }
                }

                document.close();
                if (null != baos) {
                    rssult = baos.toByteArray();
                }
            }
        } catch (DocumentException e) {

        } catch (IOException e) {

        }

        return rssult;
    }

    public BdcDzzzZs(BdcDzzzZzxx bdcDzzzZzxx) {
        this.bdcDzzzZzxx = bdcDzzzZzxx;
    }

    public BdcDzzzZs() {
    }
}
