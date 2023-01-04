package cn.gtmap.realestate.certificate.core.service.impl.validator;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzCheckInfoService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzValidateService;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/6
 */
public class BdcDzzzZzxxInsertServiceImpl implements BdcDzzzValidateService {

    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Resource
    private BdcDzzzCheckInfoService bdcDzzzCheckInfoService;

    /**
     * <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     *
     * @param dzzzValidate
     * @return
     * @description 电子证照信息已入库
     */
    @Override
    public Map<String, Object> validate(DzzzValidate dzzzValidate) {
        Map<String, Object> resultMap = new HashMap<>(10);
        BdcDzzzZzxx bdcDzzzZzxx = null;
        List<String> resultList = null;
        if (null != dzzzValidate) {
            bdcDzzzZzxx = dzzzValidate.getBdcDzzzZzxx();
            resultList = dzzzValidate.getResultList();
        }
        if (null != bdcDzzzZzxx && StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh()) && StringUtils.isNotEmpty(bdcDzzzZzxx.getYwh())) {
            Map<String, String> paramMap = new HashMap<>(4);
            paramMap.put("ywh", bdcDzzzZzxx.getYwh());
            paramMap.put("bdcqzh", bdcDzzzZzxx.getBdcqzh());
            //此处不进行验证非正常注销的证书
            List<BdcDzzzZzxxDO> bdcDzzzZzxxDOList = bdcDzzzZzxxMapper.getBdcDzzzZzxxDoByMap(paramMap);
            if (CollectionUtils.isNotEmpty(bdcDzzzZzxxDOList)) {
                for (BdcDzzzZzxxDO bdcDzzzZzxxDO : bdcDzzzZzxxDOList) {
                    if (null != bdcDzzzZzxxDO && !StringUtils.equals(Constants.DZZZ_ZZZT_ZX_YCZX, bdcDzzzZzxxDO.getZzbgyy())) {
                        resultMap = bdcDzzzCheckInfoService.getResultMap(resultMap, resultList, bdcDzzzZzxxDO);
                        break;
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public String getCheckCode() {
        return ResponseEnum.CHECK_DZZZ_INSERT.getCode();
    }

    @Override
    public String getDescription() {
        return ResponseEnum.CHECK_DZZZ_INSERT.getMsg();
    }

}
