package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.WatermarkText;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.model.template.DzzzWatermark;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzWaterMarkService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0 2019/1/22
 * @description 请求记录
 */
@Service
public class BdcDzzzWaterMarkServiceImpl implements BdcDzzzWaterMarkService {

    @Override
    public byte[] addWatermarkZzzx(byte[] pdf, String zzlxdm, String zzbgyy) {
        WatermarkText watermark = new WatermarkText();
        if (StringUtils.equals(Constants.BDC_ZZLXDM_ZM, zzlxdm)) {
            watermark.setRotation(BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ROTATION);
            if (StringUtils.equals(Constants.DZZZ_ZZZT_ZX_ZCZX, zzbgyy)) {
                watermark.setContent(BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_TEXT);
            } else if (StringUtils.equals(Constants.DZZZ_ZZZT_ZX_YCZX, zzbgyy)) {
                watermark.setContent(BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_TEXT_YC);
            }
            return watermark.addWatermark(pdf, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_ODDTEXTY
                    ,BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTX,BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_EVENTEXTY
                    ,BdcDzzzPdfUtil.DZZZ_ZMS_ZZZXWATERMARK_YINTERVAL);
        } else if (StringUtils.equals(Constants.BDC_ZZLXDM_ZS, zzlxdm)) {
            watermark.setRotation(BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ROTATION);
            if (StringUtils.equals(Constants.DZZZ_ZZZT_ZX_ZCZX, zzbgyy)) {
                watermark.setContent(BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_TEXT);
            } else if (StringUtils.equals(Constants.DZZZ_ZZZT_ZX_YCZX, zzbgyy)) {
                watermark.setContent(BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_TEXT_YC);
            }
            return watermark.addWatermark(pdf,BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTX, BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_ODDTEXTY
            ,BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTX,BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_EVENTEXTY
                    ,BdcDzzzPdfUtil.DZZZ_ZS_ZZZXWATERMARK_YINTERVAL);
        } else {
            return null;
        }
    }

    @Override
    public byte[] addWatermarkZzzx(byte[] pdf, DzzzPdf dzzzPdf, String zzbgyy) {
        byte[] waterArr = null;
        DzzzWatermark water = getDzzzWatermarkByType(dzzzPdf, zzbgyy);
        if (null != water) {
            waterArr = water.addWatermark(pdf);
        }
        return waterArr;
    }

    @Override
    public byte[] addWatermarkZzzx(byte[] pdf, DzzzPdf dzzzPdf, String zzbgyy, Object o) {
        byte[] waterArr = null;
        DzzzWatermark water = getDzzzWatermarkByType(dzzzPdf, zzbgyy);
        if (null != water) {
            waterArr = water.addWatermark(pdf, o);
        }
        return waterArr;
    }

    private DzzzWatermark getDzzzWatermarkByType(DzzzPdf dzzzPdf, String zzbgyy){
        DzzzWatermark dzzzWatermark = new DzzzWatermark();
        List<DzzzWatermark> watermarks = dzzzPdf.getWaterMark();
        if (CollectionUtils.isNotEmpty(watermarks)) {
            for (DzzzWatermark water : watermarks) {
                if (StringUtils.equals(water.getType(), zzbgyy)) {
                    dzzzWatermark = water;
                    break;
                }
            }
        }
        return dzzzWatermark;
    }

}
