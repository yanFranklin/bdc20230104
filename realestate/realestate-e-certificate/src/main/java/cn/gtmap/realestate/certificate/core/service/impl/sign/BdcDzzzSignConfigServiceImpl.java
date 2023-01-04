package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 签章
 */
public class BdcDzzzSignConfigServiceImpl implements BdcDzzzSignConfigService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzSignConfigServiceImpl.class);
    private Map<String, BdcDzzzSignService> bdcDzzzSignMap;

    @Override
    public byte[] sign(Object o, byte[] out, String signCompany) {
        return getSignatureService(signCompany).signature(o, out);
    }

    @Override
    public DzzzResponseModel verifyFile(String content, String bfjgxzqdm, String signCompany) {
        return getSignatureService(signCompany).verifyFile(content, bfjgxzqdm);
    }

    @Override
    public void updateSignInfo(byte[] signArr, BdcDzzzZzxx bdcDzzzZzxx) {
        if (null != signArr) {
            if (StringUtils.isEmpty(bdcDzzzZzxx.getZzqzr())) {
                bdcDzzzZzxx.setZzqzr(bdcDzzzZzxx.getZzbfjg());
            }
            bdcDzzzZzxx.setZzqzsj(DateUtil.now());
            bdcDzzzZzxx.setQzzt(Constants.DZZZ_QZZT_YQZ);
        }
    }

    public BdcDzzzSignService getSignatureService(String signCompany) {
        BdcDzzzSignService bdcDzzzSignService = bdcDzzzSignMap.get(signCompany);
        if (null == bdcDzzzSignService) {
            throw new NullPointerException("该签章单位" + signCompany + "没有对应的实现类！");
        }
        return bdcDzzzSignService;
    }

    public Map<String, BdcDzzzSignService> getBdcDzzzSignMap() {
        return bdcDzzzSignMap;
    }

    public void setBdcDzzzSignMap(Map<String, BdcDzzzSignService> bdcDzzzSignMap) {
        this.bdcDzzzSignMap = bdcDzzzSignMap;
    }
}
