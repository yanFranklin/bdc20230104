package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzStorageService;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.*;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.JaxbUtil;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2019/1/22
 * @description 不动产电子证照PDF业务
 */
@Service
public class BdcDzzzZzxxPdfServiceImpl implements BdcDzzzZzxxPdfService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzxxPdfServiceImpl.class);
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    BdcDzzzStorageService bdcDzzzStorageService;

    @Override
    public byte[] createPdfBdcDzzzZms(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzZms bdcDzzzZms = new BdcDzzzZms();
        bdcDzzzZms.setBdcDzzzZzxx(bdcDzzzZzxx);
        bdcDzzzZms.setUrx((float) 842.0);
        bdcDzzzZms.setUry((float) 595.0);
        bdcDzzzZms.setYearX((float) 793.0 / 3);
        bdcDzzzZms.setMonthX((float) 960.0 / 3);
        bdcDzzzZms.setDayX((float) 1050.0 / 3);
        bdcDzzzZms.setYearAndMonthAndDayY((float) 440.0 / 3);
        bdcDzzzZms.setZmqlsxmcXqlrXywrXzlXbdcdyhX((float) 1620.0 / 3);
        bdcDzzzZms.setZmqlsxmcY((float) 1380.0 / 3);
        bdcDzzzZms.setQlrY((float) 1261.0 / 3);
        bdcDzzzZms.setYwrY((float) 1140.0 / 3);
        bdcDzzzZms.setZlY((float) 1010.0 / 3);
        bdcDzzzZms.setBdcdyhY((float) 895.0 / 3);
        bdcDzzzZms.setSqsjcX((float) 1323.0 / 3);
        bdcDzzzZms.setNfX((float) 1430.0 / 3);
        bdcDzzzZms.setSzsxqcX((float) 1570.0 / 3);
        bdcDzzzZms.setZhlshX((float) 2082.0 / 3);
        bdcDzzzZms.setSqsjcaAndNfAndSzsxqcAndZhlshY((float) 1507.0 / 3);
        bdcDzzzZms.setBankGroundImageX((float) 842.0);
        bdcDzzzZms.setBankGroundImageY((float) 610.0);

        ZxingQrcode zxingQrcode = new ZxingQrcode(bdcDzzzZzxx.getEwmnr());
        zxingQrcode.setQrcodeAbsoluteX(BdcDzzzPdfUtil.DZZZ_ZMS_QRCODE_X);
        zxingQrcode.setQrcodeAbsoluteY(BdcDzzzPdfUtil.DZZZ_ZMS_QRCODE_Y);
        zxingQrcode.setWidth(BdcDzzzPdfUtil.DZZZ_ZMS_QRCODE_WIDTH);
        zxingQrcode.setHeight(BdcDzzzPdfUtil.DZZZ_ZMS_QRCODE_HEIGHT);
        bdcDzzzZms.setQrcode(zxingQrcode);

        return bdcDzzzZms.create();
    }

    @Override
    public String sendFileCenter(String node_Id, String fileName, byte[] out) {
        fileName = fileName + Constants.BDC_DZZZ_FORMAT_PDF;
        return bdcDzzzFileCenterService.sendFileCenter(out, node_Id, fileName, Constants.BDC_DZZZ);
    }

    /**
     * @param node_Id
     * @param fileName
     * @param out
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上传大云文件中心
     * @date : 2022/3/10 11:27
     */
    @Override
    public String sendStorage(String nodeid, String fileName, byte[] out) {
        fileName = fileName + Constants.BDC_DZZZ_FORMAT_PDF;
        return bdcDzzzStorageService.sendFileCenter(out, nodeid, fileName, Constants.BDC_DZQZ);
    }

    @Override
    public DzzzPdf getDzzzPdf(String zzlxdm) {
        byte[] out = null;
        DzzzPdf dzzzPdf = null;
        if (StringUtils.equals(Constants.BDC_ZZLXDM_ZS, zzlxdm)) {
            out = PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE + BdcDzzzPdfUtil.DZZZ_DWDM + File.separator + "zs.xml");
        } else if (StringUtils.equals(Constants.BDC_ZZLXDM_ZM, zzlxdm)) {
            out = PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE + BdcDzzzPdfUtil.DZZZ_DWDM + File.separator + "zms.xml");
        }

        if (null != out) {
            dzzzPdf = (DzzzPdf) JaxbUtil.xmlTojava(DzzzPdf.class, out);
        }

        return dzzzPdf;
    }

    @Override
    public DzzzPdf getDzqzPdf(String zzlxdm,String dwdm) {
        byte[] out = null;
        DzzzPdf dzzzPdf = null;
        if (StringUtils.equals(Constants.BDC_ZZLXDM_ZS, zzlxdm)) {
            out = PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE  + dwdm + File.separator + "zs.xml");
        } else if (StringUtils.equals(Constants.BDC_ZZLXDM_ZM, zzlxdm)) {
            out = PublicUtil.getFileByte(Constants.RESOURCES_TEMPLATE + dwdm + File.separator + "zms.xml");
        }

        if (null != out) {
            dzzzPdf = (DzzzPdf) JaxbUtil.xmlTojava(DzzzPdf.class, out);
        }

        return dzzzPdf;
    }

    /*生成不动产证书pdf文件*/
    @Override
    public byte[] createPdfBdcDzzzZs(BdcDzzzZzxx bdcDzzzZzxx) {
        ZxingQrcode zxingQrcode = new ZxingQrcode(bdcDzzzZzxx.getEwmnr());
        zxingQrcode.setQrcodeAbsoluteX(BdcDzzzPdfUtil.DZZZ_ZS_QRCODE_X);
        zxingQrcode.setQrcodeAbsoluteY(BdcDzzzPdfUtil.DZZZ_ZS_QRCODE_Y);
        zxingQrcode.setWidth(BdcDzzzPdfUtil.DZZZ_ZS_QRCODE_WIDTH);
        zxingQrcode.setHeight(BdcDzzzPdfUtil.DZZZ_ZS_QRCODE_HEIGHT);

        BdcDzzzZs bdcDzzzZs = new BdcDzzzZs(bdcDzzzZzxx);
        bdcDzzzZs.setQrcode(zxingQrcode);

        return bdcDzzzZs.create();
    }

    @Override
    public DzzzResponseModel checkBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx) {
        //数据组织与数据验证
        List<String> resultList = new ArrayList<>(2);
        resultList.add("zzbh");
        resultList.add("zzbs");
        return bdcDzzzZzxxService.checkBdcDzzzZzxx(bdcDzzzZzxx, resultList);
    }

    @Override
    public DzzzResponseModel insertBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx) {
        DzzzResponseModel insertBdcDzzzZzxxModel = bdcDzzzZzxxService.insertBdcDzzzZzxx(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), insertBdcDzzzZzxxModel.getHead().getStatus())) {
            return insertBdcDzzzZzxxModel;
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("zzbs", bdcDzzzZzxx.getZzbs());
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), map);
    }

    @Override
    public byte[] createPdfBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx) {
        byte[] out = null;
        if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZS, bdcDzzzZzxx.getZstype())) {
            out = createPdfBdcDzzzZs(bdcDzzzZzxx);
        } else if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZMS, bdcDzzzZzxx.getZstype())) {
            out = createPdfBdcDzzzZms(bdcDzzzZzxx);
        }

        return out;
    }
}
