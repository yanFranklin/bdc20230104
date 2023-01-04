package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.service.SyncDzzzService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.accept.DzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.DzzzClxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.DzzzTokenResponseDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.SyncDzzzClxxDTO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/28
 * @description 电子证照同步服务
 */
@Service
public class SyncDzzzServiceImpl implements SyncDzzzService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncDzzzServiceImpl.class);

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  用户服务
      */
    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 电子证照使用形式
     */
    @Value("${dzzz.userform:1}")
    private String userform;

    /**
     * 电子证照材料级别
     */
    @Value("${dzzz.lvl:C}")
    private String lvl;

    /**
     * 电子证照使用范围
     */
    @Value("${dzzz.userrange:不动产登记}")
    private String userrange;

    /**
     * 电子证照返回材料数量
     */
    @Value("${dzzz.maxnum:3}")
    private String maxnum;

    /**
     * 电子证照材料类型编码
     */
    @Value("${dzzz.typeCode:}")
    private String typeCode;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步电子证照材料信息
     */
    @Override
    @Async
    public void syncDzzzClxx(List<BdcZsDO> bdcZsDOList, String slbh, String currentUserName){
        List<SyncDzzzClxxDTO> syncDzzzClxxDTOList =new ArrayList<>();
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            LOGGER.info("同步电子证照材料信息失败,证书信息：{}缺失数据",bdcZsDOList);
            return;
        }
        if (StringUtils.isBlank(currentUserName)) {
            currentUserName = userManagerUtils.getCurrentUserName();
        }

        UserDto userDto =null;
        if (StringUtils.isNotBlank(currentUserName)) {
            userDto = userManagerUtils.getUserByName(currentUserName);
        }
        for(BdcZsDO bdcZsDO:bdcZsDOList) {
            if(StringUtils.isNoneBlank(bdcZsDO.getZsid(),bdcZsDO.getZzid())) {
                //获取证书权利人
                Example example = new Example(BdcQlrDO.class);
                example.createCriteria().andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR)
                        .andEqualTo("zsid", bdcZsDO.getZsid());
                List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isEmpty(bdcQlrDOList)) {
                    LOGGER.info("同步电子证照材料信息失败,权利人信息为空:{}", bdcZsDO.getBdcqzh());
                    return;
                }
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                String token = getAhDzzzToken(bdcQlrDO);
                if (StringUtils.isBlank(token)) {
                    LOGGER.info("同步电子证照材料信息失败,获取token失败,{}", bdcZsDO.getBdcqzh());
                    return;
                }
                DzzzClxxResponseDTO dzzzClxxResponseDTO = getDzzzClxx(userDto, token, bdcZsDO);
                if (dzzzClxxResponseDTO == null || CollectionUtils.isEmpty(dzzzClxxResponseDTO.getData())) {
                    LOGGER.info("权利人：{}同步电子证照材料信息失败,获取材料信息为空,{}", bdcQlrDO.getQlrmc(), bdcZsDO.getBdcqzh());
                    return;
                }
                for (DzzzClxxDTO dzzzClxxDTO : dzzzClxxResponseDTO.getData()) {
                    if(StringUtils.isNotBlank(bdcZsDO.getZzid()) &&StringUtils.equals(bdcZsDO.getZzid(),dzzzClxxDTO.getId())){
                        SyncDzzzClxxDTO syncDzzzClxxDTO =new SyncDzzzClxxDTO();
                        syncDzzzClxxDTO.setDzzzClxxDTO(dzzzClxxDTO);
                        syncDzzzClxxDTO.setBdcZsDO(bdcZsDO);
                        syncDzzzClxxDTO.setSlbh(slbh);
                        syncDzzzClxxDTOList.add(syncDzzzClxxDTO);

                    }
                }
            }
        }
        if(CollectionUtils.isEmpty(syncDzzzClxxDTOList)) {
            LOGGER.info("slbh{}同步电子证照材料信息失败,获取材料信息为空" , slbh);
            return;
        }
        //同步证照库
        bdcDzzzFeignService.syncDzzzClxx(syncDzzzClxxDTOList);


    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取电子证照token
     */
    private String getAhDzzzToken(BdcQlrDO bdcQlrDO){
        if(bdcQlrDO != null) {
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("applyId", bdcQlrDO.getZjh());
            tokenMap.put("applyName", bdcQlrDO.getQlrmc());
            tokenMap.put("applyPhone", bdcQlrDO.getDh());
            tokenMap.put("isApp", false);
            Object tokenResponse = exchangeInterfaceFeignService.requestInterface("dzzz_token", tokenMap);
            if(tokenResponse != null) {
                DzzzTokenResponseDTO dzzzTokenResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(tokenResponse), DzzzTokenResponseDTO.class);
                LOGGER.info("电子证照token信息:{}", JSONObject.toJSONString(tokenResponse));
                if (dzzzTokenResponseDTO != null && StringUtils.equals(dzzzTokenResponseDTO.getFlag(), "200") && dzzzTokenResponseDTO.getData() != null) {
                    return dzzzTokenResponseDTO.getData().getToken();
                }
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取电子证照材料信息
     */
    private DzzzClxxResponseDTO getDzzzClxx(UserDto userDto,String token,BdcZsDO bdcZsDO){
        if(userDto != null) {
            Map<String, Object> clxxMap = new HashMap<>();
            clxxMap.put("personName", userDto.getAlias());
            clxxMap.put("personPhone", userDto.getMobile());
            clxxMap.put("typeCode", typeCode);
            clxxMap.put("userRange", userrange);
            clxxMap.put("useForm", userform);
            clxxMap.put("maxNum", maxnum);
            clxxMap.put("lvl", lvl);
            clxxMap.put("token", token);
            LOGGER.info("获取证照材料信息参数:{}", JSONObject.toJSONString(clxxMap, SerializerFeature.WRITE_MAP_NULL_FEATURES));
            Object clxxResponse = exchangeInterfaceFeignService.requestInterface("dzzz_zzclxx", clxxMap);
            if(clxxResponse != null) {
                LOGGER.info("{}获取到的材料信息数据:{}", bdcZsDO.getBdcqzh(), JSONObject.toJSONString(clxxResponse));
                return JSONObject.parseObject(JSONObject.toJSONString(clxxResponse), DzzzClxxResponseDTO.class);
            }else{
                LOGGER.info("{}获取到的材料信息数据为空:{}", bdcZsDO.getBdcqzh(), clxxResponse);
            }
        }
        return null;
    }
}
