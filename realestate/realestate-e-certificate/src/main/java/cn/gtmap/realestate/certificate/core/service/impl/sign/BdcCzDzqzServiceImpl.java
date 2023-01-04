package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzCheckService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.SM4Util;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 电子签章实现服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:10
 **/
@Service
public class BdcCzDzqzServiceImpl implements BdcDzqzService {
    private final Logger logger = LoggerFactory.getLogger(BdcCzDzqzServiceImpl.class);
    @Autowired
    BdcDzzzService bdcDzzzService;
    @Autowired
    BdcDzqzCheckService bdcDzqzCheckService;
    @Autowired
    BdcDzzzLshService bdcDzzzLshService;
    @Autowired
    BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    BdcDzzzCheckInfoService bdcDzzzCheckInfoService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;
    @Autowired
    private BdcDzzzZzxxCzztService bdcDzzzZzxxCzztService;
    @Autowired
    private BdcDzzzYwxxService bdcDzzzYwxxService;
    @Autowired
    private BdcDzzzMlxxService bdcDzzzMlxxService;
    @Autowired
    BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;

    /**
     * @param bdcDzzzZzxx
     * @param resultList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:10
     */
    @Override
    public DzzzResponseModel checkBdcDzzzQzxx(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList) {
        if (bdcDzzzZzxx != null) {
            //完善电子证照数据
            DzzzResponseModel checkModel = checkBdcDzzzQzxxCreate(bdcDzzzZzxx, resultList);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
                return checkModel;
            }
            bdcDzzzZzxx.setZzzt(Integer.valueOf(Constants.BDC_DZZZ_ZZZT_Y));
            bdcDzzzZzxx.setQzzt(Constants.DZZZ_QZZT_WQZ);
            bdcDzzzZzxx.setTbzt(Constants.DZZZ_TBZT_WTB);
            if (StringUtils.isBlank(bdcDzzzZzxx.getZzid())) {
                bdcDzzzZzxx.setZzid(UUIDGenerator.generate());
            }
            bdcDzzzZzxx.setCjsj(DateUtil.now());
            bdcDzzzZzxx.setZzlsh(bdcDzzzLshService.getBdcDzzzLsh(bdcDzzzZzxx.getZzlxdm(), bdcDzzzZzxx.getZzbfjgdm()));
            bdcDzzzZzxx.setZzbh(bdcDzzzZzxxService.getZzbhByDwdmAndLsh(bdcDzzzZzxx.getDwdm(), bdcDzzzZzxx.getZzlsh()));
            String zzbs = bdcDzzzZzxxYsjService.encryptCalculateZzbs(bdcDzzzZzxx);
//            String zzbs = "testZZBS";
            bdcDzzzZzxx.setZzbs(zzbs);
            bdcDzzzZzxx.setEwmnr(zzbs + "&" + bdcDzzzZzxx.getBdcdyh());
        } else {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    /**
     * @param bdcDzzzZzxx
     * @param resultList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:12
     */
    @Override
    public DzzzResponseModel checkBdcDzzzQzxxCreate(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList) {
        bdcDzzzZzxx = bdcDzzzZzxxService.getPerfectBdcDzzzZzxx(bdcDzzzZzxx);
        Map<String, Object> requiredMap = bdcDzqzCheckService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_REQUIRED.getCode(), null);
        if (MapUtils.isNotEmpty(requiredMap)) {
            logger.info("验证必填字段：{}，请求时间：{}", JSON.toJSONString(requiredMap), DateUtil.now());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_REQUIRED.getCode(), requiredMap);
        }
        Map<String, Object> parameterMap = bdcDzqzCheckService.check(bdcDzzzZzxx, ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        if (MapUtils.isNotEmpty(parameterMap)) {
            logger.info("参数合法验证：{}，请求时间：{}", JSON.toJSONString(parameterMap), DateUtil.now());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), parameterMap);
        }
        //验证是否已经生成证照
        Map<String, Object> createMap = bdcDzqzCheckService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_CREATE.getCode(), resultList);
        if (MapUtils.isNotEmpty(createMap)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_CREATE.getCode(), createMap);
        }
        Map<String, Object> insertMap = bdcDzqzCheckService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_INSERT.getCode(), resultList);
        if (MapUtils.isNotEmpty(insertMap)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_INSERT.getCode(), insertMap);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    /**
     * @param bdcDzzzZzxx@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 签章信息入库
     * @date : 2022/3/10 11:40
     */
    @Override
    public DzzzResponseModel insertBdcDzzzQzxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException {
        if (bdcDzzzZzxx != null) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.getBdcDzzzZzxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);

            //加密证件号
            String zjh_ecb = "";
            if (StringUtils.isNotBlank(bdcDzzzZzxxDO.getCzztdm())) {
                zjh_ecb = SM4Util.encryptData_ECB(bdcDzzzZzxxDO.getCzztdm());
                bdcDzzzZzxxDO.setCzztdm(zjh_ecb);
                int resultCzzt = bdcDzzzZzxxCzztService.insertBdcDzzzZzxxCzztDo(bdcDzzzZzxx);
                if (0 == resultCzzt) {
                    return bdcDzzzService.dzzzResponseFalse("持证主体信息入库失败！", null);
                }
            }
            int resultZzxx = bdcDzzzDzqzMapper.insertBdcDzzzQzxx(bdcDzzzZzxxDO);
            if (0 == resultZzxx) {
                return bdcDzzzService.dzzzResponseFalse("证照信息入库失败！", null);
            }
            int resultYwxx = bdcDzzzYwxxService.insertYwxx(bdcDzzzZzxx);
            if (0 == resultYwxx) {
                return bdcDzzzService.dzzzResponseFalse("业务信息入库失败！", null);
            }
            BdcDzzzMlxxDO bdcDzzzMlxxDO = bdcDzzzZzxxService.getBdcDzzzMlxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);
            bdcDzzzMlxxDO.setGdzt(0);
            bdcDzzzMlxxDO.setCzztdm(zjh_ecb);
            int resultMlxx = bdcDzzzMlxxService.insertBdcDzzzMlxx(bdcDzzzMlxxDO);
            if (0 == resultMlxx) {
                return bdcDzzzService.dzzzResponseFalse("目录信息入库失败！", null);
            }
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    /**
     * @param zzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据zzid 查询证照信息
     * @date : 2022/3/10 16:55
     */
    @Override
    public BdcDzzzZzxxDO queryBdcDzzzQzxx(String zzid) {
        return bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzid(zzid);
    }
}
