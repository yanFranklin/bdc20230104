package cn.gtmap.realestate.certificate.core.service.impl.zzzx;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzZzzxConfigServiceImpl implements BdcDzzzZzzxConfigService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzzxConfigServiceImpl.class);
    private Map<String, BdcDzzzZzzxService> dzzzZzzxServiceMap;

    @Autowired
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzCheckInfoService bdcDzzzCheckInfoService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;

    @Override
    public DzzzResponseModel dzzzZzzx(Object o) {
        return getCreateDzzzService().dzzzZzzx(o);
    }

    @Override
    public BdcDzzzZzzxService getCreateDzzzService(String dwdm) {
        BdcDzzzZzzxService bdcDzzzZzzxService = dzzzZzzxServiceMap.get(dwdm);
        if (null == bdcDzzzZzzxService) {
            throw new NullPointerException("该单位代码" + BdcDzzzPdfUtil.DZZZ_DWDM + "没有对应的证照注销实现类！");
        }
        return bdcDzzzZzzxService;
    }

    @Override
    public DzzzResponseModel bdcDzzzZzzxBeforeCheck(BdcDzzzZzxx bdcDzzzZzxx) {
        if (StringUtils.isBlank(bdcDzzzZzxx.getZzbs())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "zzbs is required fields cannot be empty or null");
        }
        if (StringUtils.isBlank(bdcDzzzZzxx.getZzbgyy())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "zzbgyy is required fields cannot be empty or null");
        }
        //需要验证请求注销原因传参是否正确，符合字典表
        Map<String, Object> requiredMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, Constants.DZZZ_NUMBER_EIGHT, null);
        if (MapUtils.isNotEmpty(requiredMap)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "zzbgyy is not standard");
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public DzzzResponseModel bdcDzzzZzzxInfoCheck(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        if (null == bdcDzzzZzxxDO) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
        }
        if (2 == bdcDzzzZzxxDO.getZzzt()) {
            logger.info("证照已注销：请求时间：{}",  DateUtil.now());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CERTIFICATE_CANCELLED_ERROR.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public DzzzResponseModel updateDzzzZxxxInfo(String zzid, String zzbgyy, Date zzbgsj) {
        // 更新属性信息
        BdcDzzzZzxxDO bdcDzzz = new BdcDzzzZzxxDO();
        bdcDzzz.setZzid(zzid);
        bdcDzzz.setZzzt(Constants.BDC_DZZZ_ZZZT_N);
        bdcDzzz.setZzbgyy(zzbgyy);
        bdcDzzz.setZzbgsj(zzbgsj);
        int count = bdcDzzzZzxxMapper.updateBdcDzzzByZzid(bdcDzzz);
        if (count == 0) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.DATABASE_UPDATE.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    /**
     * @param zzid
     * @param zzbgyy
     * @param zzbgsj
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新签章注销信息
     * @date : 2022/3/10 21:23
     */
    @Override
    public DzzzResponseModel updateDzqzZxxxInfo(String zzid, String zzbgyy, Date zzbgsj, String zzwjlj) {
        // 更新属性信息
        BdcDzzzZzxxDO bdcDzzz = new BdcDzzzZzxxDO();
        bdcDzzz.setZzid(zzid);
        bdcDzzz.setZzzt(Constants.BDC_DZZZ_ZZZT_N);
        bdcDzzz.setZzbgyy(zzbgyy);
        bdcDzzz.setZzbgsj(zzbgsj);
        bdcDzzz.setZzqzlj(zzwjlj);
        int count = bdcDzzzDzqzMapper.updateBdcQzxxByZzid(bdcDzzz);
        if (count == 0) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.DATABASE_UPDATE.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    public BdcDzzzZzzxService getCreateDzzzService() {
        BdcDzzzZzzxService bdcDzzzZzzxService = dzzzZzzxServiceMap.get(BdcDzzzPdfUtil.DZZZ_DWDM);
        if (null == bdcDzzzZzzxService) {
            throw new NullPointerException("该单位代码" + BdcDzzzPdfUtil.DZZZ_DWDM + "没有对应的证照注销实现类！");
        }
        return bdcDzzzZzzxService;
    }

    public Map<String, BdcDzzzZzzxService> getDzzzZzzxServiceMap() {
        return dzzzZzzxServiceMap;
    }

    public void setDzzzZzzxServiceMap(Map<String, BdcDzzzZzzxService> dzzzZzzxServiceMap) {
        this.dzzzZzzxServiceMap = dzzzZzzxServiceMap;
    }
}
