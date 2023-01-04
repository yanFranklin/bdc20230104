package cn.gtmap.realestate.certificate.core.model.abstraction;


import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.WatermarkText;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import cn.gtmap.realestate.certificate.util.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/14
 */
public abstract class AbstractBdcDzzz implements AbstractDzzz {
    public final Logger logger = LoggerFactory.getLogger(AbstractBdcDzzz.class);
    // 楷体
    public static final String FONT_IMAG_PATH = PublicUtil.getFilePath(Constants.RESOURCES_IMG + "simkai.ttf");
    // 宋体
    public static final String FONT_IMAG_PATH_SONGTI = PublicUtil.getFilePath(Constants.RESOURCES_IMG + "song98-1.ttf");

    public static final float FONT_SIZE_EIGHT = 9;
    public static final float FONT_SIZE_TEN = 10;
    public static final float FONT_SIZE_ELEVEN = 11;
    public static final float FONT_SIZE_TWELVE = 12;
    public static final float FONT_SIZE_THIRTEEN = 13;

    protected Qrcode qrcode;
    protected BdcDzzzZzxx bdcDzzzZzxx;

    protected abstract byte[] create();

    /**
     * @param str       需要处理字符串
     * @param cb        pdf
     * @param x         x坐标
     * @param y         y坐标
     * @param wordNum   每行显示字数
     * @param rowSpace  行间距
     * @param separator 分隔符
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description
     */

    protected PdfContentByte textSubstring(String str, PdfContentByte cb, double x, double y, int wordNum, int rowSpace, String separator) {
        if (StringUtils.isNotBlank(str)) {
            String[] strs = str.split(separator);
            int j = 0;
            for (int i = 0; i < strs.length; i++) {
                double strY = (y - j * rowSpace);
                while (strs[i].length() > wordNum) {
                    String newText = strs[i].substring(0, wordNum);
                    strs[i] = strs[i].substring(wordNum, strs[i].length());
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, newText, (float) x, (float) strY, 0);
                    j++;
                    strY = (y - j * rowSpace);
                }
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, strs[i], (float) x, (float) strY, 0);
                j++;
            }
        }
        return cb;
    }

    protected PdfContentByte textSubstring1(String str, PdfContentByte cb, double x, double y, int wordNum, int rowSpace, String separator) {
        try{
            if (StringUtils.isNotBlank(str)) {
                String[] strs = str.split(separator);
                int j = 0;
                for (int i = 0; i < strs.length; i++) {
                    double strY = (y - j * rowSpace);
                    int countTotal = StringUtil.countTotalGbk(strs[i]);
                    while (countTotal > wordNum) {
                        String newText = StringUtil.substringByByte(strs[i], wordNum, countTotal);
                        strs[i] = strs[i].substring(newText.length(), strs[i].length());
                        countTotal = StringUtil.countTotalGbk(strs[i]);
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, newText, (float) x, (float) strY, 0);
                        j++;
                        strY = (y - j * rowSpace);
                    }
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, strs[i], (float) x, (float) strY, 0);
                    j++;
                }
            }
        } catch (UnsupportedEncodingException e){
            logger.error("textSubstring1", e);
        }

        return cb;
    }


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param under
     * @param oddTextX 第一列起始横坐标
     * @param oddTextY 第一列起始纵坐标
     * @param evenTextX 第二列起始横坐标
     * @param evenTextY 第二列起始纵坐标
     * @param yInterval 行间距
     * @param content 水印内容
     * @param rotation 旋转角度
     * @return
     * @throws DocumentException
     * @throws IOException
     * @description 添加文字水印
     */
    protected Boolean addWatermarkText(PdfContentByte under, float oddTextX , float oddTextY, float evenTextX
                                               ,float evenTextY, int yInterval, String content, float rotation) throws DocumentException, IOException{
        WatermarkText watermark = new WatermarkText();
        watermark.setRotation(rotation);
        watermark.setWaterContent(under);
        watermark.setContent(content);
        return watermark.addWatermark(oddTextX, oddTextY, evenTextX, evenTextY, yInterval);
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param document
     * @throws DocumentException
     * @throws IOException
     * @description 添加二维码
     */
    protected void addQrode(Document document, BufferedImage bufferedImage) throws DocumentException,IOException{
        if (null != qrcode && StringUtils.isNotBlank(qrcode.getContent())) {
            byte[] out = Qrcode.writeToStream(bufferedImage, Qrcode.QRCODE_FORMAT_PNG);
            if (null != out) {
                Image codeQrImage = Image.getInstance(out);
                codeQrImage.scaleToFit(85, 85);
                codeQrImage.setAbsolutePosition(qrcode.getQrcodeAbsoluteX(), qrcode.getQrcodeAbsoluteY());
                document.add(codeQrImage);
            }
        }
    }

    public PdfContentByte textSubstring(String str, PdfContentByte cb, double x, double y, int wordNum, int lineSpacing) {
        if (StringUtils.isNotBlank(str)) {
            while (str.length() > wordNum) {
                y = y + 8;
                String zlNew = str.substring(0, wordNum);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, zlNew, (float) x, (float) y, 0);
                str = str.substring(wordNum);
                y = y - lineSpacing;
            }
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, str, (float) x, (float) y, 0);
        }

        return cb;
    }

    public PdfContentByte textSubstring2(String str, PdfContentByte cb, double x, double y, int wordNum, int lineSpacing) {
        try{
            if (StringUtils.isNotBlank(str)) {
                int countTotal = StringUtil.countTotalGbk(str);
                while (countTotal > wordNum) {
                    y = y + 8;
                    String zlNew = StringUtil.substringByByte(str, wordNum, countTotal);
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, zlNew, (float) x, (float) y, 0);
                    str = str.substring(zlNew.length());
                    countTotal = StringUtil.countTotalGbk(str);
                    y = y - lineSpacing;
                }
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, str, (float) x, (float) y, 0);
            }
        } catch (UnsupportedEncodingException e){
            logger.error("textSubstring2", e);
        }

        return cb;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 获取加注件水印信息
     */
    protected List<String> getWatermarkText() {
        List<String> textList = new ArrayList<>();
        if (null != this.bdcDzzzZzxx) {
            if (StringUtils.isNotEmpty(this.bdcDzzzZzxx.getJzjzzz())) {
                textList.add("此件由" + this.bdcDzzzZzxx.getJzjzzz() + "提供");
            }
            if (StringUtils.isNotEmpty(this.bdcDzzzZzxx.getJzjzzsy())) {
                textList.add("仅供办理" + this.bdcDzzzZzxx.getJzjzzsy() + "事项时使用");
            }
            if (null != this.bdcDzzzZzxx.getJzjyxqjzsj()) {
                textList.add("有效期至" + PublicUtil.convertDateToStr(this.bdcDzzzZzxx.getJzjyxqjzsj()));
            }
        }
        return textList;
    }

    public Qrcode getQrcode() {
        return qrcode;
    }

    public void setQrcode(Qrcode qrcode) {
        this.qrcode = qrcode;
    }

    public BdcDzzzZzxx getBdcDzzzZzxx() {
        return bdcDzzzZzxx;
    }

    public void setBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        this.bdcDzzzZzxx = bdcDzzzZzxx;
    }
}
