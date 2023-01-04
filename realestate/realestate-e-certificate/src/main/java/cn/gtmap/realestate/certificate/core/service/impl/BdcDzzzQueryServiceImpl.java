package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzYwxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestData;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQueryService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxYsjService;
import cn.gtmap.realestate.certificate.util.CommonUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import cn.gtmap.realestate.certificate.util.SM4Util;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/21
 * @description 电子证照共享接口实现
 */
@Service
public class BdcDzzzQueryServiceImpl implements BdcDzzzQueryService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzQueryServiceImpl.class);
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzYwxxMapper bdcDzzzYwxxMapper;
    @Autowired
    private BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;

    /**
     * @param jsonString
     * @return 需要返回的电子证照标识信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照检索接口业务处理接口
     */
    @Override
    public DzzzResponseModel zzjs(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        DzzzQueryRequestModel dzzzQueryRequestModel = JSON.parseObject(jsonString, DzzzQueryRequestModel.class);

        DzzzQueryRequestData data = dzzzQueryRequestModel.getData();
        if (data != null) {
            List<String> check = new ArrayList<>(4);
            String zzlxdm = data.getZzlxdm();
            String czztdm = data.getCzztdm();
            if (StringUtils.isBlank(czztdm)) {
                check.add("czztdm is required fields cannot be empty or null");
            }
            if (StringUtils.isBlank(zzlxdm)) {
                check.add("zzlxdm is required fields cannot be empty or null");
            }
            if (CollectionUtils.isNotEmpty(check)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), check);
            }
            Map<String, Object> param = new HashMap<>(8);
            if (StringUtils.isNotBlank(data.getCzzt())) {
                param.put("czzt", data.getCzzt());
            }
            if (StringUtils.isNotBlank(czztdm)) {
                //加密证件号
                param.put("czztdm", SM4Util.encryptData_ECB(czztdm));
            }
            if (StringUtils.isNotBlank(data.getCzztdmlx())) {
                param.put("czztdmlx", data.getCzztdmlx());
            }
            if (StringUtils.isNotBlank(data.getCzztdmlxdm())) {
                param.put("czztdmlxdm", data.getCzztdmlxdm());
            }
            if (StringUtils.isNotBlank(zzlxdm)) {
                param.put("zzlxdm", zzlxdm);
            }
            if (StringUtils.isNotBlank(data.getDwdm())) {
                param.put("dwdmArr", data.getDwdm().split(","));
            }
            if (Objects.nonNull(data.getZzzt())) {
                param.put("zzzt", data.getZzzt());
            }
            return getQueryResponseData(param);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
    }


    @Override
    public DzzzResponseModel zzysj(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        DzzzQueryRequestModel dzzzQueryRequestModel = JSON.parseObject(jsonString, DzzzQueryRequestModel.class);
        DzzzQueryRequestData data = dzzzQueryRequestModel.getData();

        if (null != data) {
            String zzbs = data.getZzbs();
            if (StringUtils.isBlank(zzbs)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                        , "zzbs is required fields cannot be empty or null");
            }

            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(zzbs);
            if (null != bdcDzzzZzxxDO) {
                BdcDzzzZzxxYsj bdcDzzzZzxxYsj = bdcDzzzZzxxYsjService.getYsjByZzxxDo(bdcDzzzZzxxDO);
                //关键字解密
                if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getCZZTDM())) {
                    bdcDzzzZzxxYsj.setCZZTDM(SM4Util.decryptData_ECB(bdcDzzzZzxxYsj.getCZZTDM()));
                }

                Map<String, Object> result = new HashMap<>(4);
                //使用JSON串表示各元数据项段名，若无内容，默认返回全部元数据
                if (StringUtils.isNotEmpty(data.getMeladataRange())) {
                    String[] dataRangeArr = data.getMeladataRange().split(",");
                    Map<String, Object> responseDataMap = new HashMap<>();
                    for (String dataRange : dataRangeArr) {
                        Field filed = PublicUtil.getDeclaredFieldByName(dataRange, bdcDzzzZzxxYsj);
                        if (null == filed) {
                            result.put("info", dataRange);
                            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), result);
                        }
                        Object value = PublicUtil.getFieldValue(filed, bdcDzzzZzxxYsj);
                        if (value instanceof Date) {
                            responseDataMap.put(dataRange, ((Date) value).getTime());
                        } else {
                            responseDataMap.put(dataRange, value);
                        }
                    }

                    result.put("zzzmxx", responseDataMap);
                } else {
                    result.put("zzzmxx", bdcDzzzZzxxYsj);
                }

                //响应成功
                return bdcDzzzService.dzzzResponseSuccess(result);
            } else {
                //异常信息代码说明：--3：未获取到信息
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
            }
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
    }

    @Override
    public DzzzResponseModel zzcx(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        DzzzQueryRequestModel dzzzQueryRequestModel = JSON.parseObject(jsonString, DzzzQueryRequestModel.class);

        DzzzQueryRequestData data = dzzzQueryRequestModel.getData();
        if (data != null) {
            Map<String, Object> check = new HashMap<>(2);
            String zzlxdm = data.getZzlxdm();
            String bdcqzh = data.getBdcqzh();
            if (StringUtils.isBlank(bdcqzh)) {
                check.put("info", "bdcqzh is required fields cannot be empty or null");
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), check);
            }
            Map<String, Object> param = new HashMap<>(4);
            if (StringUtils.isNotBlank(zzlxdm)) {
                param.put("zzlxdm", zzlxdm);
            }
            if (StringUtils.isNotBlank(data.getBdcqzh())) {
                param.put("bdcqzh", data.getBdcqzh());
            }
            if (StringUtils.isNotBlank(data.getDwdm())) {
                param.put("dwdmArr", data.getDwdm().split(","));
            }
            return getQueryResponseData(param);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
    }

    private DzzzResponseModel getQueryResponseData(Map<String, Object> param) {
        List<BdcDzzzZzxxDO> bdcDzzzZzxxDOList = bdcDzzzZzxxMapper.getBdcDzzzZzxxDoListByMap(param);
        logger.info("查询到的bdcDzzzZzxxDO数量为："+bdcDzzzZzxxDOList.size());
        bdcDzzzZzxxDOList.forEach(bdcDzzzZzxxDO -> logger.info("zzzt为：" + bdcDzzzZzxxDO.getZzzt()));
        if (CollectionUtils.isNotEmpty(bdcDzzzZzxxDOList)) {
            List<Map<String, Object>> dzzzQueryResponseDataList = new ArrayList<>();
            for (BdcDzzzZzxxDO bdcDzzzZzxxDO : bdcDzzzZzxxDOList) {
                if (bdcDzzzZzxxDO != null) {
                    Map<String, Object> result = new HashMap<>(8);
                    result.put("zzbs", bdcDzzzZzxxDO.getZzbs());
                    result.put("zzmc", bdcDzzzZzxxDO.getZzmc());
                    result.put("zzzt", bdcDzzzZzxxDO.getZzzt());
                    result.put("bdcqzh", bdcDzzzZzxxDO.getBdcqzh());
                    String ywh = getYwh(bdcDzzzZzxxDO.getZzid());
                    if (StringUtils.isNotBlank(ywh)) {
                        result.put("ywh", ywh);
                    }
                    dzzzQueryResponseDataList.add(result);
                }
            }
            //响应成功
            return bdcDzzzService.dzzzResponseSuccess(dzzzQueryResponseDataList);
        } else {
            //异常信息代码说明--3：未检索到记录
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
        }
    }

    private String getYwh(String zzid){
        StringBuilder ywh = new StringBuilder();
        List<BdcDzzzYwxxDo> ywxxList = bdcDzzzYwxxMapper.queryYwxxByZzid(zzid);
        if (CollectionUtils.isNotEmpty(ywxxList)) {
            for (BdcDzzzYwxxDo bdcDzzzYwxxDo : ywxxList) {
                ywhIsEmpty(ywh, bdcDzzzYwxxDo.getYwh());
            }
        }
        return ywh.toString();
    }

    public void ywhIsEmpty(StringBuilder ywh, String str){
        if (StringUtils.isEmpty(ywh.toString())) {
            ywh.append(str);
        } else {
            ywh.append(",").append(str);
        }
    }

    @Override
    public DzzzResponseModel zzcxParamCheck(String jsonString) {
        DzzzQueryRequestData data = JSON.parseObject(jsonString, DzzzQueryRequestModel.class).getData();
        if (null == data) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "data is required fields cannot be empty or null");
        }

        List<String> check = new ArrayList<>(4);
        String bdcqzh = data.getBdcqzh();
        if (StringUtils.isBlank(bdcqzh)) {
            check.add("bdcqzh is required fields cannot be empty or null");
        }
        if (CollectionUtils.isNotEmpty(check)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), check);
        }

        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getCode(), null);
    }

    @Override
    public DzzzResponseModel zzjsParamCheck(String jsonString) {
        DzzzQueryRequestData data = JSON.parseObject(jsonString, DzzzQueryRequestModel.class).getData();
        if (null == data) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "data is required fields cannot be empty or null");
        }

        List<String> check = new ArrayList<>(4);
        String zzlxdm = data.getZzlxdm();
        String czztdm = data.getCzztdm();
        if (StringUtils.isBlank(czztdm)) {
            check.add("czztdm is required fields cannot be empty or null");
        }
        if (StringUtils.isBlank(zzlxdm)) {
            check.add("zzlxdm is required fields cannot be empty or null");
        }
        if (CollectionUtils.isNotEmpty(check)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), check);
        }

        if (!CommonUtil.indexOfStrs(Constants.BDC_ZZLXDM,zzlxdm)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "zzlxdm is not standard");
        }
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getCode(), null);
    }
}
