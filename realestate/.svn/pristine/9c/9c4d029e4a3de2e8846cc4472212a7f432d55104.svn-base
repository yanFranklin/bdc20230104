package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.verify.DzzzVerifyRequestData;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.verify.DzzzVerifyRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28
 */
@Service
public class BdcDzzzVerifyServiceImpl implements BdcDzzzVerifyService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzVerifyServiceImpl.class);
    @Autowired
    private BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;

    @Override
    public DzzzResponseModel dzzzVerifyInfo(DzzzVerifyRequestModel dzzzVerifyRequestModel) {
        DzzzVerifyRequestData dataObject = dzzzVerifyRequestModel.getData();
        String zzzmxx = dataObject.getZzzmxx();
        if (StringUtils.isBlank(zzzmxx)) {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_XXBQ.getCode(), ResponseEnum.VERIFY_INFO_XXBQ.getMsg()));
        }
        BdcDzzzZzxx bdcDzzzZzxx = null;
        try {
            bdcDzzzZzxx = JSON.parseObject(zzzmxx, BdcDzzzZzxx.class);
            //解决传入json 不动产单元号可能存在空格问题
            if (bdcDzzzZzxx != null && StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                bdcDzzzZzxx.setBdcdyh(StringUtils.deleteWhitespace(bdcDzzzZzxx.getBdcdyh()));
            }
        } catch (JSONException e) {
            logger.error("BdcDzzzVerifyServiceImpl:dzzzVerifyInfo:JSONException", e);
        }
        if (null == bdcDzzzZzxx) {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
        }

        return verifyInfo(bdcDzzzZzxx);
    }

    @Override
    public DzzzResponseModel dzzzVerifyFile(DzzzVerifyRequestModel dzzzVerifyRequestModel) {
        DzzzVerifyRequestData dataObject = dzzzVerifyRequestModel.getData();
        String content = dataObject.getContent();
        String contentType = dataObject.getContentType();
        String bfjgxzqdm = dataObject.getBfjgxzqdm();
        List<String> check = new ArrayList<>(2);
        if (StringUtils.isBlank(content)) {
            check.add("content is required fields cannot be empty or null");
        }
        if (StringUtils.isBlank(contentType)) {
            check.add("contentType is required fields cannot be empty or null");
        } else {
            if (!StringUtils.equals(contentType, Constants.DZZZ_BASE64)) {
                check.add("contentType is not base64");
            }
        }
        if (StringUtils.isBlank(bfjgxzqdm)) {
            check.add("bfjgxzqdm is required fields cannot be empty or null");
        }
        if (CollectionUtils.isNotEmpty(check)) {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), JSON.toJSONString(check)));
        }


        return bdcDzzzSignConfigService.verifyFile(content, bfjgxzqdm, BdcDzzzPdfUtil.SIGN_COMPANY);
    }


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 证照信息验证逻辑
     * 第一步  先通过json 里zsid 或bdcqzh查证照库是否有数据  有继续  没有直接返回查无此信息
     * 第二 验证证书类型是否在规定内的三种  存在继续  不在信息错误，参数不一致
     * 第三 通过用户json 组织证照标识与数据库证照标识对比 是否一致  ,一致继续  ,不一致信息错误，参数不一致
     * 第四  验证扩展字段 是否一致（注意  只要配置了的字段 json 必须要有值，没有值会提示信息错误，参数不一致  后台会提示哪些字段不一致
     * 第五 验证传入证照状态是否和数据库一致 信息错误，参数不一致
     * 第六 验证证照有效期字段是否为空，为空返回信息错误 参数不一致
     * 第七 验证期限是否在有效期内 并且证照状态已注销  提示信息正确且未激活
     * 第八  验证不在有效期  但是证照状态为现势    信息正确且证照已失效
     * 第九  上边都符合 信息正确且有效
     */
    private DzzzResponseModel verifyInfo(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzZzxxDO bdcDzzzZzxxDO = null;
        if (null != bdcDzzzZzxx) {
            if (StringUtils.isBlank(bdcDzzzZzxx.getDwdm()) || StringUtils.isBlank(bdcDzzzZzxx.getZzbh())
                    || StringUtils.isBlank(bdcDzzzZzxx.getZzlxdm()) || StringUtils.isBlank(bdcDzzzZzxx.getZzbfjg())
                    || StringUtils.isBlank(bdcDzzzZzxx.getZzbfjgdm()) || null == bdcDzzzZzxx.getZzyxqjzrq()) {
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_XXBQ.getCode(), ResponseEnum.VERIFY_INFO_XXBQ.getMsg()));
            }

            if (StringUtils.isBlank(bdcDzzzZzxx.getZsid()) && StringUtils.isBlank(bdcDzzzZzxx.getBdcqzh())) {
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_XXBQ.getCode(), ResponseEnum.VERIFY_INFO_XXBQ.getMsg()));
            }

            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZsid())) {
                // 验证是否有此记录
                bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZsid(bdcDzzzZzxx.getZsid());
            } else if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbh()) && StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlxdm())) {
                Map<String, String> paramMap = new HashMap<>(4);
                paramMap.put("ywh", bdcDzzzZzxx.getYwh());
                paramMap.put("bdcqzh", bdcDzzzZzxx.getBdcqzh());
                bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByMap(paramMap);
            }
            if (null == bdcDzzzZzxxDO) {
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CWCXX.getCode(), ResponseEnum.VERIFY_INFO_CWCXX.getMsg()));
            }

            //验证证书类型必须在范围内
            if (null != bdcDzzzZzxx && !(StringUtils.equals(Constants.BDC_ZZLXDM_ZS, bdcDzzzZzxx.getZzlxdm()) || StringUtils.equals(Constants.BDC_ZZLXDM_ZM, bdcDzzzZzxx.getZzlxdm()))) {
                logger.info("证照信息验证，证书类型不在规定范围内[zzlxdm字段应该为1（不动产权电子证书）、2（不动产登记电子证明）两种]zzlxdm：{}，请求时间：{}", bdcDzzzZzxx.getZzlxdm(), DateUtil.now());
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
            }
            if (StringUtils.isEmpty(bdcDzzzZzxx.getZstype())) {
                bdcDzzzZzxx.setZstype(bdcDzzzZzxxService.getBdcDzzzZzxxZstypeByZzlxdm(bdcDzzzZzxx.getZzlxdm()));
            }

            // 验证证照标识是否一致
            String zzbsCre = bdcDzzzZzxxYsjService.encryptCalculateZzbs(bdcDzzzZzxx);
            if (!StringUtils.equals(zzbsCre, bdcDzzzZzxxDO.getZzbs())) {
                logger.info("证照信息验证，证照标识不一致：{}，请求时间：{}", bdcDzzzZzxxDO.getZzbs(), DateUtil.now());
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
            }
            // 验证配置扩展字段内容
            List<String> data = new ArrayList<>(10);
            BdcDzzzZzxx bdcDzzzZzxxCurrent = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, null);
            try {
                List<String> fieldList = ReadXmlPropsUtil.getDzzzCheckinfo(bdcDzzzZzxx.getBdcqzh());
                if (CollectionUtils.isNotEmpty(fieldList)) {
                    for (String fieldName : fieldList) {
                        Field field = bdcDzzzZzxx.getClass().getField(fieldName);
                        field.setAccessible(true);
                        Field fieldCurrent = bdcDzzzZzxxCurrent.getClass().getField(fieldName);
                        fieldCurrent.setAccessible(true);
                        if (field.getGenericType().toString().equals("class java.util.Date")) {
                            Date bdcDzzzZzxxFieldValue = (Date) field.get(bdcDzzzZzxx);
                            Date bdcDzzzZzxxCurrentFieldValue = (Date) fieldCurrent.get(bdcDzzzZzxxCurrent);
                            if (null != bdcDzzzZzxxFieldValue && null != bdcDzzzZzxxCurrentFieldValue && !DateUtils.isSameDay(bdcDzzzZzxxFieldValue, bdcDzzzZzxxCurrentFieldValue)) {
                                data.add(fieldName + "不一致");
                            } else if (null == bdcDzzzZzxxFieldValue && null != bdcDzzzZzxxCurrentFieldValue) {
                                logger.info("证照信息验证,请求JSON中不存在配置的{}扩展字段，请求时间：{}", fieldName, DateUtil.now());
                            } else if (null != bdcDzzzZzxxFieldValue && null == bdcDzzzZzxxCurrentFieldValue) {
                                data.add(fieldName + "不一致");
                            }
                            continue;
                        }
                        if (field.getGenericType().toString().equals("class java.lang.Integer")) {
                            Integer bdcDzzzZzxxFieldValue = (Integer) field.get(bdcDzzzZzxx);
                            Integer bdcDzzzZzxxCurrentFieldValue = (Integer) fieldCurrent.get(bdcDzzzZzxxCurrent);
                            if (null != bdcDzzzZzxxFieldValue && null != bdcDzzzZzxxCurrentFieldValue && !bdcDzzzZzxxFieldValue.equals(bdcDzzzZzxxCurrentFieldValue)) {
                                data.add(fieldName + "不一致");
                            } else if (null == bdcDzzzZzxxFieldValue && null != bdcDzzzZzxxCurrentFieldValue) {
                                logger.info("证照信息验证,请求JSON中不存在配置的{}扩展字段，请求时间：{}", fieldName, DateUtil.now());
                            } else if (null != bdcDzzzZzxxFieldValue && null == bdcDzzzZzxxCurrentFieldValue) {
                                data.add(fieldName + "不一致");
                            }
                            continue;
                        }
                        String bdcDzzzZzxxFieldValue = (String) field.get(bdcDzzzZzxx);
                        String bdcDzzzZzxxCurrentFieldValue = (String) fieldCurrent.get(bdcDzzzZzxxCurrent);
                        if (StringUtils.isNotEmpty(bdcDzzzZzxxFieldValue)) {
                            if (!StringUtils.equals(bdcDzzzZzxxFieldValue, bdcDzzzZzxxCurrentFieldValue)) {
                                data.add(fieldName + "不一致");
                            }
                        } else {
                            logger.info("证照信息验证,请求JSON中不存在配置的{}扩展字段，请求时间：{}", fieldName, DateUtil.now());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                logger.error("BdcDzzzVerifyServiceImpl:verifyInfo:IllegalAccessException", e);
            } catch (IllegalArgumentException e) {
                logger.error("BdcDzzzVerifyServiceImpl:verifyInfo:IllegalArgumentException", e);
            } catch (NoSuchFieldException e) {
                logger.error("BdcDzzzVerifyServiceImpl:verifyInfo:NoSuchFieldException", e);
            }

            if (CollectionUtils.isNotEmpty(data)) {
                logger.info("证照信息验证,配置扩展字段验证不一致：{}，请求时间：{}", data, DateUtil.now());
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
            }

            //验证传入证照状态是否和数据库一致
            if (null != bdcDzzzZzxx.getZzzt() && !bdcDzzzZzxxDO.getZzzt().equals(bdcDzzzZzxx.getZzzt())) {
                logger.info("证照信息验证,zzzt不一致：请求时间：{}", DateUtil.now());
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
            }

            if (null != bdcDzzzZzxxDO.getZzyxqjzrq() && null != bdcDzzzZzxx.getZzyxqjzrq()) {

                //验证传入证照有效期截止时间是否符合实际
                if (PublicUtil.convertStrTodate2("1980年1月1日").after(bdcDzzzZzxx.getZzyxqjzrq())) {
                    logger.info("证照信息验证,zzyxqjzrq不符合实际：请求时间：{}", DateUtil.now());
                    return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
                }

                // 信息正确且未激活
                if (!bdcDzzzZzxx.getZzyxqjzrq().after(bdcDzzzZzxxDO.getZzyxqjzrq())
                        && Constants.BDC_DZZZ_ZZZT_N.equals(bdcDzzzZzxxDO.getZzzt())) {
                    return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_ZQWJH.getCode(), ResponseEnum.VERIFY_INFO_ZQWJH.getMsg()));
                }

                // 信息正确且证照暂时失效
                if (bdcDzzzZzxx.getZzyxqjzrq().after(bdcDzzzZzxxDO.getZzyxqjzrq())
                        && Constants.BDC_DZZZ_ZZZT_Y.equals(bdcDzzzZzxxDO.getZzzt())) {
                    return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_ZQZSSX.getCode(), ResponseEnum.VERIFY_INFO_ZQZSSX.getMsg()));
                }

                // 信息正确且证照已失效
                if (Constants.BDC_DZZZ_ZZZT_N.equals(bdcDzzzZzxxDO.getZzzt())) {
                    return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_ZQYSX.getCode(), ResponseEnum.VERIFY_INFO_ZQYSX.getMsg()));
                }
            } else {
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
            }

            // 信息正确且有效
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_ZQYX.getCode(), ResponseEnum.VERIFY_INFO_ZQYX.getMsg()));
        } else {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_INFO_CSBYZ.getCode(), ResponseEnum.VERIFY_INFO_CSBYZ.getMsg()));
        }
    }
}
