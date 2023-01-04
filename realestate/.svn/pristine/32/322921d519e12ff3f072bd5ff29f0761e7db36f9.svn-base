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

/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2019/1/22
 * @description 不动产电子证照PDF
 */
public class BdcDzzzZms extends AbstractBdcDzzz implements Serializable {
    private static final long serialVersionUID = -97062016422430746L;
    public static final byte[] ZMS_BAK_IMAGE = PublicUtil.getFileByte(Constants.RESOURCES_IMG + "不动产登记电子证明.jpg");
    private float urx;
    private float ury;
    private float yearX;
    private float monthX;
    private float dayX;
    private float yearAndMonthAndDayY;
    private float bhX;
    private float bhY;
    private float zmqlsxmcXqlrXywrXzlXbdcdyhX;
    private float zmqlsxmcY;
    private float qlrY;
    private float ywrY;
    private float zlY;
    private float bdcdyhY;
    private float sqsjcX;
    private float nfX;
    private float szsxqcX;
    private float zhlshX;
    private float sqsjcaAndNfAndSzsxqcAndZhlshY;
    private float qlqtzkX;
    private float qlqtzkY;
    private float fjX;
    private float fjY;
    private float bankGroundImageX;
    private float bankGroundImageY;

    @Override
    public byte[] create() {
        byte[] rssult = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Rectangle pageSize = new Rectangle(this.urx, this.ury);
            Document document = new Document(pageSize, 0, 0, 0, 0);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            if (null != writer) {
                PdfContentByte cb = writer.getDirectContent();

                // 加文字水印
                if (BdcDzzzPdfUtil.DZZZ_ZMS_IS_ADDWATERMARK_TEXT && StringUtils.isNotBlank(bdcDzzzZzxx.getJzjzzsy())) {
                    addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_ODDTEXTY
                            , BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_EVENTEXTY
                            , BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_YINTERVAL, bdcDzzzZzxx.getJzjzzsy(), BdcDzzzPdfUtil.DZZZ_ZMS_JZJWATERMARK_ROTATION);
                }

                BaseFont kaiti = BaseFont.createFont(FONT_IMAG_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(kaiti, FONT_SIZE_THIRTEEN);
                cb.setColorFill(BaseColor.BLACK);
                cb = setPdfYearAndMonthAndDay(cb, this.bdcDzzzZzxx.getYear(), this.bdcDzzzZzxx.getMonth(), this.bdcDzzzZzxx.getDay());
                cb = setPdfSqsjcaAndNfAndSzsxqcAndZhlshY(cb, this.bdcDzzzZzxx.getSqsjc(), this.bdcDzzzZzxx.getNf()
                        , this.bdcDzzzZzxx.getSzsxqc(), this.bdcDzzzZzxx.getZhlsh());
                String bdcdyh = this.bdcDzzzZzxx.getBdcdyh().substring(0, 6) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(6, 12) + "" + this.bdcDzzzZzxx.getBdcdyh().substring(12, 19) + " " + this.bdcDzzzZzxx.getBdcdyh().substring(19);
                cb = setPdfZmqlsxmcXqlrXywrXzlXbdcdyhX(cb, this.bdcDzzzZzxx.getZmqlsx(), this.bdcDzzzZzxx.getQlr()
                        , this.bdcDzzzZzxx.getYwr(), this.bdcDzzzZzxx.getZl(), bdcdyh);
                cb.endText();

                PdfPTable table = new PdfPTable(1);
                table.setTotalWidth(225);

                if (BdcDzzzPdfUtil.DZZZ_ZMS_QLQTZK_CREATE) {
                    Font font = new Font(kaiti, BdcDzzzPdfUtil.DZZZ_ZMS_QLQTZK_FONTSIZE, Font.NORMAL);
                    PdfPCell qt = new PdfPCell(new Phrase(this.bdcDzzzZzxx.getQt(), font));
                    qt.setFixedHeight(105);
                    qt.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    qt.setBorder(0);
                    table.addCell(qt);
                }

                if (BdcDzzzPdfUtil.DZZZ_ZMS_FJ_CREATE) {
                    Font font = new Font(kaiti, BdcDzzzPdfUtil.DZZZ_ZMS_FJ_FONTSIZE, Font.NORMAL);
                    PdfPCell fj = new PdfPCell(new Phrase(this.bdcDzzzZzxx.getFj(), font));
                    fj.setFixedHeight(108);
                    fj.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    fj.setBorder(0);
                    table.addCell(fj);
                }

                // 表格位置
                if (!BdcDzzzPdfUtil.DZZZ_ZMS_QLQTZK_CREATE && BdcDzzzPdfUtil.DZZZ_ZMS_FJ_CREATE) {
                    table.writeSelectedRows(0, -1, 540, 176, writer.getDirectContent());
                } else {
                    table.writeSelectedRows(0, -1, 540, 281, writer.getDirectContent());
                }

                // 添加背景图片
                Image bankGroundImage = Image.getInstance(ZMS_BAK_IMAGE);
                if (null != bankGroundImage) {
                    bankGroundImage.scaleAbsolute(this.bankGroundImageX, this.bankGroundImageY);
                    document.add(bankGroundImage);
                }

                // 添加二维码
                addQrode(document, qrcode.createQrCodeLogo());

                //证照如果是注销状态增加注销水印
                if (Constants.BDC_DZZZ_ZZZT_N.equals (this.bdcDzzzZzxx.getZzzt())) {
                    if (!StringUtils.equals(Constants.DZZZ_ZZZT_ZX_YCZX, this.bdcDzzzZzxx.getZzbgyy())) {
                        addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_YINTERVAL, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_TEXT
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ROTATION);
                    } else {
                        addWatermarkText(cb, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTY
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_YINTERVAL, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_TEXT_YC
                                , BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ROTATION);
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

    private PdfContentByte setPdfBh(String bh, PdfContentByte cb) {
        if (StringUtils.isNotBlank(bh)) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, bh, this.bhX, this.bhY, 0);
        }
        return cb;
    }

    private PdfContentByte setPdfZmqlsxmcXqlrXywrXzlXbdcdyhX(PdfContentByte cb, String zmqlsxmc, String qlr, String ywr, String zl, String bdcdyh) {
        if (StringUtils.isNotBlank(zmqlsxmc)) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, zmqlsxmc, this.zmqlsxmcXqlrXywrXzlXbdcdyhX, this.zmqlsxmcY, 0);
        }
        if (StringUtils.isNotBlank(qlr)) {
            int wordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_QLR_WORDNUM);
            int lineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_QLR_LINESPACING);
            cb = textSubstring2(qlr, cb, this.zmqlsxmcXqlrXywrXzlXbdcdyhX, this.qlrY, wordNum, lineSpacing);
        }
        if (StringUtils.isNotBlank(ywr)) {
            int wordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_YWR_WORDNUM);
            int lineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_YWR_LINESPACING);
            cb = textSubstring2(ywr, cb, this.zmqlsxmcXqlrXywrXzlXbdcdyhX, this.ywrY, wordNum, lineSpacing);
        }
        if (StringUtils.isNotBlank(zl)) {
            int wordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_ZL_WORDNUM);
            int lineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_ZL_LINESPACING);
            cb = textSubstring2(zl, cb, this.zmqlsxmcXqlrXywrXzlXbdcdyhX, this.zlY, wordNum, lineSpacing);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            int wordNum = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_BDCDYH_WORDNUM);
            int lineSpacing = Integer.valueOf(BdcDzzzPdfUtil.DZZZ_ZMS_BDCDYH_LINESPACING);
            cb = textSubstring2(bdcdyh, cb, this.zmqlsxmcXqlrXywrXzlXbdcdyhX, this.bdcdyhY, wordNum, lineSpacing);
        }

        return cb;
    }

    private PdfContentByte setPdfSqsjcaAndNfAndSzsxqcAndZhlshY(PdfContentByte cb, String sqsjc, String nf, String szsxqc, String zhlsh) {
        if (StringUtils.isNotBlank(sqsjc) && StringUtils.isNotBlank(nf) && StringUtils.isNotBlank(szsxqc) && StringUtils.isNotBlank(zhlsh)) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, sqsjc, this.sqsjcX, this.sqsjcaAndNfAndSzsxqcAndZhlshY, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nf, this.nfX, this.sqsjcaAndNfAndSzsxqcAndZhlshY, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, szsxqc, this.szsxqcX, this.sqsjcaAndNfAndSzsxqcAndZhlshY, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, zhlsh, this.zhlshX, this.sqsjcaAndNfAndSzsxqcAndZhlshY, 0);
        }
        return cb;
    }

    private PdfContentByte setPdfYearAndMonthAndDay(PdfContentByte cb, String year, String month, String day) {
        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day)) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, year, this.yearX, this.yearAndMonthAndDayY, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, month, this.monthX, this.yearAndMonthAndDayY, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, day, this.dayX, this.yearAndMonthAndDayY, 0);
        }
        return cb;
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

    public float getYearX() {
        return yearX;
    }

    public void setYearX(float yearX) {
        this.yearX = yearX;
    }

    public float getMonthX() {
        return monthX;
    }

    public void setMonthX(float monthX) {
        this.monthX = monthX;
    }

    public float getDayX() {
        return dayX;
    }

    public void setDayX(float dayX) {
        this.dayX = dayX;
    }

    public float getYearAndMonthAndDayY() {
        return yearAndMonthAndDayY;
    }

    public void setYearAndMonthAndDayY(float yearAndMonthAndDayY) {
        this.yearAndMonthAndDayY = yearAndMonthAndDayY;
    }

    public float getBhX() {
        return bhX;
    }

    public void setBhX(float bhX) {
        this.bhX = bhX;
    }

    public float getBhY() {
        return bhY;
    }

    public void setBhY(float bhY) {
        this.bhY = bhY;
    }

    public float getZmqlsxmcXqlrXywrXzlXbdcdyhX() {
        return zmqlsxmcXqlrXywrXzlXbdcdyhX;
    }

    public void setZmqlsxmcXqlrXywrXzlXbdcdyhX(float zmqlsxmcXqlrXywrXzlXbdcdyhX) {
        this.zmqlsxmcXqlrXywrXzlXbdcdyhX = zmqlsxmcXqlrXywrXzlXbdcdyhX;
    }

    public float getZmqlsxmcY() {
        return zmqlsxmcY;
    }

    public void setZmqlsxmcY(float zmqlsxmcY) {
        this.zmqlsxmcY = zmqlsxmcY;
    }

    public float getQlrY() {
        return qlrY;
    }

    public void setQlrY(float qlrY) {
        this.qlrY = qlrY;
    }

    public float getYwrY() {
        return ywrY;
    }

    public void setYwrY(float ywrY) {
        this.ywrY = ywrY;
    }

    public float getZlY() {
        return zlY;
    }

    public void setZlY(float zlY) {
        this.zlY = zlY;
    }

    public float getBdcdyhY() {
        return bdcdyhY;
    }

    public void setBdcdyhY(float bdcdyhY) {
        this.bdcdyhY = bdcdyhY;
    }

    public float getSqsjcX() {
        return sqsjcX;
    }

    public void setSqsjcX(float sqsjcX) {
        this.sqsjcX = sqsjcX;
    }

    public float getNfX() {
        return nfX;
    }

    public void setNfX(float nfX) {
        this.nfX = nfX;
    }

    public float getSzsxqcX() {
        return szsxqcX;
    }

    public void setSzsxqcX(float szsxqcX) {
        this.szsxqcX = szsxqcX;
    }

    public float getZhlshX() {
        return zhlshX;
    }

    public void setZhlshX(float zhlshX) {
        this.zhlshX = zhlshX;
    }

    public float getSqsjcaAndNfAndSzsxqcAndZhlshY() {
        return sqsjcaAndNfAndSzsxqcAndZhlshY;
    }

    public void setSqsjcaAndNfAndSzsxqcAndZhlshY(float sqsjcaAndNfAndSzsxqcAndZhlshY) {
        this.sqsjcaAndNfAndSzsxqcAndZhlshY = sqsjcaAndNfAndSzsxqcAndZhlshY;
    }

    public float getQlqtzkX() {
        return qlqtzkX;
    }

    public void setQlqtzkX(float qlqtzkX) {
        this.qlqtzkX = qlqtzkX;
    }

    public float getQlqtzkY() {
        return qlqtzkY;
    }

    public void setQlqtzkY(float qlqtzkY) {
        this.qlqtzkY = qlqtzkY;
    }

    public float getFjX() {
        return fjX;
    }

    public void setFjX(float fjX) {
        this.fjX = fjX;
    }

    public float getFjY() {
        return fjY;
    }

    public void setFjY(float fjY) {
        this.fjY = fjY;
    }

    public float getBankGroundImageX() {
        return bankGroundImageX;
    }

    public void setBankGroundImageX(float bankGroundImageX) {
        this.bankGroundImageX = bankGroundImageX;
    }

    public float getBankGroundImageY() {
        return bankGroundImageY;
    }

    public void setBankGroundImageY(float bankGroundImageY) {
        this.bankGroundImageY = bankGroundImageY;
    }

    public BdcDzzzZms() {
        super();
    }
}
