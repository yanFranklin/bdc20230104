package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcGgFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: realestate
 * @description: 非标准登记业务数据创建电子签章
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-24 09:56
 **/
@Service
public class BdcFdjywDzqzServiceImpl implements BdcFdjywDzqzService {
    private final Logger LOGGER = LoggerFactory.getLogger(BdcFdjywDzqzServiceImpl.class);

    @Autowired
    BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    BdcDzqzPdfService bdcDzqzPdfService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    BdcGgFeignService bdcGgFeignService;
    @Autowired
    BdcDzzzLshService bdcDzzzLshService;
    @Autowired
    BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;

    /**
     * @param o
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据推送的数据 可能只有pdf 相关信息，无证书信息，生成签章信息
     * @date : 2022/8/24 9:54
     */
    @Override
    public DzzzResponseModel createFdjDzqz(Object o) {
        LOGGER.warn("开始准备创建电子签章信息");
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx) o;
        bdcDzzzZzxx = bdcDzzzZzxxService.getPerfectBdcDzzzZzxx(bdcDzzzZzxx);
        bdcDzzzZzxx.setZzzt(Constants.BDC_DZZZ_ZZZT_Y);
        bdcDzzzZzxx.setQzzt(Constants.DZZZ_QZZT_WQZ);
        bdcDzzzZzxx.setTbzt(Constants.DZZZ_TBZT_WTB);
        if (StringUtils.isBlank(bdcDzzzZzxx.getZzid())) {
            bdcDzzzZzxx.setZzid(UUIDGenerator.generate());
        }
        bdcDzzzZzxx.setCjsj(DateUtil.now());
        bdcDzzzZzxx.setZzlsh(bdcDzzzLshService.getBdcDzzzLsh(bdcDzzzZzxx.getZzlxdm(), bdcDzzzZzxx.getZzbfjgdm()));
        bdcDzzzZzxx.setZzbh(bdcDzzzZzxxService.getZzbhByDwdmAndLsh(bdcDzzzZzxx.getDwdm(), bdcDzzzZzxx.getZzlsh()));
        String zzbs = bdcDzzzZzxx.getYwh() + UUIDGenerator.generate16();
//            String zzbs = "testZZBS";
        bdcDzzzZzxx.setZzbs(zzbs);
        bdcDzzzZzxx.setEwmnr(zzbs + "&" + bdcDzzzZzxx.getBdcdyh());
        // 生成pdf 文件上传大云文件中心

        // 上传文件中心
        String zzqzlj = bdcDzzzZzxxPdfService.sendStorage(bdcDzzzZzxx.getZzid(), bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA, bdcDzzzZzxx.getPdfByte());
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzqzlj(zzqzlj);
        LOGGER.info("模板定制接口生成pdf上传附件成功！生成的地址路径{}", zzqzlj);
        // 信息入库
        return bdcDzqzPdfService.insertBdcDzqz(bdcDzzzZzxx);
    }
}
