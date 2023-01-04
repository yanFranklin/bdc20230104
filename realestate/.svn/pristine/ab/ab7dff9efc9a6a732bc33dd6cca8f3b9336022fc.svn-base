package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.accept.core.service.BdcSfmService;
import cn.gtmap.realestate.accept.core.service.BdcSlCsjpzService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtQtdjYwDO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseFjDTO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.SfmZzResponse;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCommonFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BdcSfmServiceImpl implements BdcSfmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSfmServiceImpl.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;

    @Autowired
    BdcSlCsjpzService bdcSlCsjpzService;

    @Autowired
    ProcessInstanceClient processInstanceClient;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcCommonFeignService bdcCommonFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Value("${sfm.appkey:}")
    private String appkey;

    @Value("${sfm.appsecret:}")
    private String appsecret;

    @Value("${sfm.ouguid:}")
    private String ouguid;

    @Value("${sfm.itemOuName:}")
    private String itemOuName;

    @Value("${sfm.equipmentName:}")
    private String equipmentName;

    @Value("${sfm.equipmentCode:}")
    private String equipmentCode;

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码
     * @date : 2022/5/12 8:35
     */
    @Override
    public List<SfmLiscenseInfoDTO> queryCsjZzxxBySfmCx(CsjZzxxQO csjZzxxQO) {
        List<SfmLiscenseInfoDTO> sfmLiscenseInfo = new ArrayList<>();
        if (Objects.nonNull(csjZzxxQO) && StringUtils.isNotBlank(csjZzxxQO.getSfmnr())) {
            SfmZzResponse customBeanResponse = querySfmHa(csjZzxxQO);
            LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(customBeanResponse));
            if (Objects.nonNull(customBeanResponse) && Objects.nonNull(customBeanResponse.getCode()) && "000001".equals(customBeanResponse.getCode())) {
                List<SfmZzResponse.DataBean> customBeanList = customBeanResponse.getData();
                if (CollectionUtils.isNotEmpty(customBeanList)) {
                    for (SfmZzResponse.DataBean dataBean : customBeanList) {
                        SfmZzResponse.DataBean.LiscenseInfoBean liscenseInfo = dataBean.getLiscenseInfo();
                        if (Objects.nonNull(liscenseInfo) && StringUtils.isNotBlank(liscenseInfo.getHolder_identity_num())) {
                            SfmLiscenseInfoDTO sfmLiscenseInfoDTO = new SfmLiscenseInfoDTO();
                            BeanUtils.copyProperties(liscenseInfo, sfmLiscenseInfoDTO);
                            sfmLiscenseInfo.add(sfmLiscenseInfoDTO);
                        } else {
                            LOGGER.error("当前流程实例id{}查询苏服码信息返回值权利人信息缺失{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(liscenseInfo));
                        }
                    }
                }
            } else {
                LOGGER.error("当前流程实例id{}查询苏服码信息失败，返回结果code!=200，开始查询长三角证照信息", csjZzxxQO.getGzlslid());
            }

        }
        return sfmLiscenseInfo;
    }

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码
     * @date : 2022/5/12 8:35
     */
    @Override
    public List<SfmLiscenseFjDTO> queryCsjZzxxBySfmFj(CsjZzxxQO csjZzxxQO) {
        List<SfmLiscenseFjDTO> sfmLiscenseInfo = new ArrayList<>();
        if (Objects.nonNull(csjZzxxQO) && StringUtils.isNotBlank(csjZzxxQO.getSfmnr())) {
            SfmZzResponse customBeanResponse = querySfmHa(csjZzxxQO);
            LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(customBeanResponse));
            if (Objects.nonNull(customBeanResponse) && Objects.nonNull(customBeanResponse.getCode()) && "000001".equals(customBeanResponse.getCode())) {
                List<SfmZzResponse.DataBean> customBeanList = customBeanResponse.getData();
                if (CollectionUtils.isNotEmpty(customBeanList)) {
                    for (SfmZzResponse.DataBean dataBean : customBeanList) {
                        SfmZzResponse.DataBean.LiscenseInfoBean liscenseInfo = dataBean.getLiscenseInfo();
                        if (Objects.nonNull(liscenseInfo) && StringUtils.isNotBlank(liscenseInfo.getHolder_identity_num())) {
                            SfmLiscenseFjDTO sfmLiscenseInfoDTO = new SfmLiscenseFjDTO();
                            BeanUtils.copyProperties(dataBean, sfmLiscenseInfoDTO);
                            BeanUtils.copyProperties(liscenseInfo, sfmLiscenseInfoDTO);
                            sfmLiscenseInfo.add(sfmLiscenseInfoDTO);
                        } else {
                            LOGGER.error("当前流程实例id{}查询苏服码信息返回值权利人信息缺失{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(liscenseInfo));
                        }
                    }
                }
            } else {
                LOGGER.error("当前流程实例id{}查询苏服码信息失败，返回结果code!=200，开始查询长三角证照信息", csjZzxxQO.getGzlslid());
            }

        }
        return sfmLiscenseInfo;
    }

    private SfmZzResponse querySfmHa(CsjZzxxQO csjZzxxQO) {
        //获取token
        String token = querySfmHaToken(csjZzxxQO);
        //获取accesstoken
        String access_token = querySfmHaValidateToken(csjZzxxQO, token);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(access_token)) {
            return null;
        }
        //查询证照信息
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put("authtoken", access_token);
        paramMap.put("Authorization", "Bearer " + token);
        LOGGER.warn("当前流程实例id{}苏服码查询信息入参{}", csjZzxxQO.getGzlslid(), paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("csj_sfm_getZz", paramMap);
        if (Objects.nonNull(response)) {
            LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(response));
            return JSON.parseObject(JSON.toJSONString(response), SfmZzResponse.class);
        } else {
            return null;
        }
    }

    /**
     * 淮安查询苏服码Token
     *
     * @param csjZzxxQO
     * @return
     */
    private String querySfmHaToken(CsjZzxxQO csjZzxxQO) {
        //获取token
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put("client_id", appkey);
        paramMap.put("client_secret", appsecret);
        paramMap.put("grant_type", "client_credentials");
        paramMap.put("scope", "default");
        LOGGER.warn("当前流程实例id{}苏服码获取token入参{}", csjZzxxQO.getGzlslid(), paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("csj_sfm_getToken", paramMap);
        if (Objects.nonNull(response)) {
            LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(response));
            JSONObject data = JSON.parseObject(JSON.toJSONString(response));
            if (data.containsKey("code") && data.getString("code").equals("0")) {
                return null;
            }
            if (!data.containsKey("access_token")) {
                return null;
            }
            return data.getString("access_token");
        } else {
            return null;
        }
    }

    /**
     * 淮安查询苏服码validatetoken
     *
     * @param csjZzxxQO
     * @return
     */
    private String querySfmHaValidateToken(CsjZzxxQO csjZzxxQO, String accessToken) {
        BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(csjZzxxQO.getXmid());
        LOGGER.info("当前流程实例id{}苏服码查询信息项目信息{}", csjZzxxQO.getXmid(), bdcXmDO);
        if(Objects.isNull(bdcXmDO)){
            return null;
        }
        //获取token
        UserDto currentUser = userManagerUtils.getCurrentUser();
        LOGGER.info("当前流程实例id{}苏服码查询用户信息{}", csjZzxxQO.getXmid(), bdcXmDO);
        if (Objects.isNull(currentUser)) {
            return null;
        }
        BdcXtQtdjYwDO getxtpz = bdcCommonFeignService.getxtpz(csjZzxxQO.getGzlslid());
        LOGGER.info("当前流程实例id{}苏服码查询配置信息{}", csjZzxxQO.getXmid(), getxtpz);
        if (Objects.isNull(getxtpz)) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("qrcode", csjZzxxQO.getSfmnr());
        paramMap.put("casecode", getxtpz.getYwbm());
        paramMap.put("casename", getxtpz.getYwmc());
        paramMap.put("description", getxtpz.getYwmc());
        paramMap.put("items", csjZzxxQO.getZzlx());
        paramMap.put("num", bdcXmDO.getSlbh());
        //处理操作人信息
        Map<String, String> currentUserParamMap = new HashMap<>(4);
        currentUserParamMap.put("account", currentUser.getUsername());
        currentUserParamMap.put("name", currentUser.getAlias());
        currentUserParamMap.put("identity_num", currentUser.getIdCard());
        List<RoleDto> roleRecordList = currentUser.getRoleRecordList();
        if (CollectionUtils.isNotEmpty(roleRecordList)) {
            currentUserParamMap.put("role", roleRecordList.get(0).getAlias());
        }
        List<OrganizationDto> orgRecordList = currentUser.getOrgRecordList();
        if (CollectionUtils.isNotEmpty(orgRecordList)) {
            OrganizationDto organizationDto = orgRecordList.get(0);
            currentUserParamMap.put("division", organizationDto.getRegionName());
            currentUserParamMap.put("division_code", organizationDto.getRegionCode());
            currentUserParamMap.put("service_org_code", ouguid);
            currentUserParamMap.put("service_org", itemOuName);
        }
        paramMap.put("operator", currentUserParamMap);
        paramMap.put("equipmentName", equipmentName);
        paramMap.put("equipmentCode", equipmentCode);
        paramMap.put("caseid", getxtpz.getSsbbh());
        paramMap.put("ouguid", ouguid);
        paramMap.put("itemOuName", itemOuName);
        paramMap.put("Authorization", "Bearer " + accessToken);
        paramMap.put("qrctoken", Base64Utils.encodeByteToBase64Str(appkey.getBytes(StandardCharsets.UTF_8)));
        LOGGER.warn("当前流程实例id{}苏服码获取token入参{}", csjZzxxQO.getGzlslid(), paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("csj_sfm_getAccesstoken", paramMap);
        if (Objects.nonNull(response)) {
            LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(response));
            JSONObject data = JSON.parseObject(JSON.toJSONString(response));
            if (data.containsKey("code") && data.getString("code").equals("0")) {
                return null;
            }
            if (!data.containsKey("data")) {
                return null;
            }
            return data.getJSONObject("data").getString("validatetoken");
        } else {
            return null;
        }
    }
}
