package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.StatisticsProcessClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.StatisticsProcDto;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.service.BdcJbxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DoubleUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/21
 * @description ??????????????????
 */
@Service
public class BdcJbxxServiceImpl implements BdcJbxxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJbxxServiceImpl.class);
    @Autowired
    BdcBhService bdcBhService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxServic;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    StatisticsProcessClient statisticsProcessClient;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Value("${slbhscfs.version:}")
    private String slbhgs;

    /**
     * ???????????????
     */
    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????1????????????????????? 2???????????????????????????
     */
    @Value("${djjg.sjly:1}")
    private String djjgSjly;

    @Value("${cjyz.lwsh:false}")
    private Boolean cjyzlwsh;


    /**
     * @param bdcSlCshDTO ??????????????????DTO
     * @return ???????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????
     */
    @Override
    public BdcSlJbxxDO insertBdcSlJbxx(BdcSlCshDTO bdcSlCshDTO) {
        if (StringUtils.isBlank(bdcSlCshDTO.getJbxxid()) || StringUtils.isBlank(bdcSlCshDTO.getGzlslid())) {
            throw new MissingArgumentException("jbxxid,gzlslid");
        }
        UserDto userDto;
        if (StringUtils.isNotBlank(bdcSlCshDTO.getCzrid())) {
            userDto = userManagerUtils.getUserByUserid(bdcSlCshDTO.getCzrid());
        } else {
            userDto = userManagerUtils.getCurrentUser();
        }
        if (null == userDto) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        if(StringUtils.isNoneBlank(bdcSlCshDTO.getGzldyid(),bdcSlCshDTO.getGzldymc()) &&bdcSlCshDTO.getSlsj() != null){
            bdcSlJbxxDO.setSlsj(bdcSlCshDTO.getSlsj());
            bdcSlJbxxDO.setLcmc(bdcSlCshDTO.getGzldymc());
            bdcSlJbxxDO.setGzldyid(bdcSlCshDTO.getGzldyid());
        }else{
            //??????????????????
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcSlCshDTO.getGzlslid());
            if (processInstanceData == null) {
                throw new AppException("?????????????????????????????????,???????????????ID:" + bdcSlCshDTO.getGzlslid());
            }
            bdcSlJbxxDO.setSlsj(processInstanceData.getStartTime());
            bdcSlJbxxDO.setLcmc(processInstanceData.getProcessDefinitionName());
            bdcSlJbxxDO.setGzldyid(processInstanceData.getProcessDefinitionKey());
        }
        bdcSlJbxxDO.setJbxxid(bdcSlCshDTO.getJbxxid());
        bdcSlJbxxDO.setGzlslid(bdcSlCshDTO.getGzlslid());
        bdcSlJbxxDO.setSlr(userDto.getAlias());
        bdcSlJbxxDO.setSlrid(userDto.getId());

        bdcSlJbxxDO.setZl(bdcSlCshDTO.getZl());
        bdcSlJbxxDO.setSqrxm(bdcSlCshDTO.getQlrmc());
        String slbh = bdcSlCshDTO.getSlbh();
        if (StringUtils.isBlank(slbh)) {
            slbh = bdcBhService.queryBh(Constants.BH_SL, "");
        }
        if (StringUtils.equals(slbhgs, Constants.VERSION_NT) && !slbh.contains("YTH") && !BdcdyhToolUtils.isContainSlbhTzm(slbh)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                String bdcdyh = bdcSlXmDOList.get(0).getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh)) {
                    slbh = bdcBhService.queryTzmByBdcdyh(bdcdyh) + slbh;
                }
            }
        }
        bdcSlJbxxDO.setSlbh(slbh);
        if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
            OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
            if (organizationDto != null) {
                //????????????
                //??????????????????????????????????????????????????????9???????????????????????????
                String qxdm = "";
                if (StringUtils.isNotBlank(organizationDto.getRegionCode()) && StringUtils.length(organizationDto.getRegionCode()) >= 6) {
                    qxdm = organizationDto.getRegionCode().substring(0, 6);
                } else {
                    qxdm = organizationDto.getRegionCode();
                }
                if (StringUtils.isNotBlank(bdcSlCshDTO.getQxdm())) {
                    bdcSlJbxxDO.setQxdm(bdcSlCshDTO.getQxdm());
                } else {
                    bdcSlJbxxDO.setQxdm(qxdm);
                }
                bdcSlJbxxDO.setDjjg(queryDjjgByUser(userDto));
                bdcSlJbxxDO.setDjbmdm(organizationDto.getCode());
            }
        }
        LOGGER.info("???????????????ID:{}???????????????{},???????????????{}", bdcSlJbxxDO.getGzlslid(),JSONObject.toJSONString(userDto),bdcSlJbxxDO.getQxdm());
        StatisticsProcDto statisticsProcDto = statisticsProcessClient.queryProcessStaticsInfo(bdcSlCshDTO.getGzlslid());
        if (statisticsProcDto != null && statisticsProcDto.getProcDueLimitDouble() != null) {
            bdcSlJbxxDO.setCnqx(DoubleUtils.getString(statisticsProcDto.getProcDueLimitDouble()));
        }
        if (StringUtils.isNotBlank(bdcSlCshDTO.getDjxl())) {
            //??????????????????
            BdcDjxlDjyyQO bdcDjxlDjyyQO = new BdcDjxlDjyyQO();
            bdcDjxlDjyyQO.setDjxl(bdcSlCshDTO.getDjxl());
            List<BdcDjxlDjyyGxDO> djxlDjyyGxDOList = bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
            if (CollectionUtils.isNotEmpty(djxlDjyyGxDOList)) {
                for (BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO : djxlDjyyGxDOList) {
                    bdcSlJbxxDO.setDjyy(bdcDjxlDjyyGxDO.getDjyy());
                    if (CommonConstantUtils.SF_S_DM.equals(bdcDjxlDjyyGxDO.getSfmr())) {
                        break;
                    }
                }
            }
        }
        if (bdcSlCshDTO.getZdjbdb()) {
            bdcSlJbxxDO.setZdjbdb(CommonConstantUtils.SF_S_DM);
        } else {
            bdcSlJbxxDO.setZdjbdb(CommonConstantUtils.SF_F_DM);
        }
        bdcSlJbxxDO = bdcSlJbxxServic.insertBdcSlJbxx(bdcSlJbxxDO);


        return bdcSlJbxxDO;
    }

    @Override
    public BdcSlJbxxDO saveGzlwBdcSlJbxx(BdcSlCshDTO bdcSlCshDTO) {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        if (Boolean.TRUE.equals(cjyzlwsh)) {
            //  //????????????????????????????????????,???????????????????????????????????????
            if (StringUtils.isBlank(bdcSlCshDTO.getSlbh()) || StringUtils.isBlank(bdcSlCshDTO.getGzldyid()) || StringUtils.isBlank(bdcSlCshDTO.getJbxxid())) {
                throw new MissingArgumentException("slbh,gzldyid,jbxxid");
            }
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (null == userDto) {
                throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
            }
            bdcSlJbxxDO.setJbxxid(bdcSlCshDTO.getJbxxid());
            bdcSlJbxxDO.setSlrid(userDto.getId());
            bdcSlJbxxDO.setGzldyid(bdcSlCshDTO.getGzldyid());
            bdcSlJbxxDO.setSlbh(bdcSlCshDTO.getSlbh());
            BdcSlJbxxDO jbxxDO = bdcSlJbxxServic.queryBdcSlJbxxByJbxxid(bdcSlCshDTO.getJbxxid());
            if (jbxxDO != null) {
                bdcSlJbxxServic.updateBdcSlJbxx(bdcSlJbxxDO);
            } else {
                bdcSlJbxxDO = bdcSlJbxxServic.insertBdcSlJbxx(bdcSlJbxxDO);
            }
        }
        return bdcSlJbxxDO;

    }

    /**
     * @param slbh ???????????????????????????
     * @return ?????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @Transactional
    @Override
    public String dealSameSlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            LOGGER.error("??????????????????");
            return null;
        }
        String newslbh = "";
        LOGGER.info("????????????????????????:{}", slbh);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //??????gzlslid??????
            Map<String, List<BdcXmDO>> xmMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getGzlslid));
            if (MapUtils.isNotEmpty(xmMap) && xmMap.size() > 1) {
                int i = 0;
                //????????????,??????????????????,??????????????????
                for (Map.Entry<String, List<BdcXmDO>> entry : xmMap.entrySet()) {
                    if (i != 0) {
                        String gzlslid = entry.getKey();
                        List<BdcXmDO> xmDOList = entry.getValue();
                        if (CollectionUtils.isNotEmpty(xmDOList) && xmDOList.get(0).getSlsj() != null) {
                            newslbh = bdcBhService.queryBhBySlsj(Constants.BH_SL, "", xmDOList.get(0).getSlsj());
                            LOGGER.info("????????????????????????:{}?????????????????????:{}", slbh, newslbh);
                            //??????bdc_xm slbh
                            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("slbh", newslbh);
                            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
                            Map<String, Object> map = new HashMap<>();
                            map.put("gzlslid", gzlslid);
                            bdcDjxxUpdateQO.setWhereMap(map);
                            int result = bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
                            LOGGER.info("??????bdc_xm?????????????????????????????????ID:{},??????????????????{},???????????????{}", gzlslid, newslbh, result);
                            //??????bdc_sl_jbxx slbh
                            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxServic.queryBdcSlJbxxByGzlslid(gzlslid);
                            if (bdcSlJbxxDO != null) {
                                bdcSlJbxxDO.setSlbh(newslbh);
                                result = bdcSlJbxxServic.updateBdcSlJbxx(bdcSlJbxxDO);
                            }
                            LOGGER.info("??????bdc_sl_jbxx?????????????????????????????????ID:{},??????????????????{},???????????????{}", gzlslid, newslbh, result);
                            //??????
                            try {
                                Map<String, Object> processInsExtendDto = new HashMap<>();
                                processInsExtendDto.put("PROC_INS_ID", gzlslid);
                                processInsExtendDto.put("SLBH", newslbh);
                                bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
                                LOGGER.info("????????????,???????????????ID:{},??????????????????{}", gzlslid, newslbh);
                            } catch (Exception e) {
                                LOGGER.error("???????????????????????????gzlslid={},????????????slbh={}", gzlslid, newslbh);
                            }

                        }
                    }
                    i++;
                }


            } else {
                throw new AppException("?????????????????????");
            }

        }
        return newslbh;

    }

    /**
     * @return ??????????????????????????????id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????????????????id
     */
    @Override
    public String spfGzldyid() {
        if (CollectionUtils.isNotEmpty(spfdyidList)) {
            return String.join(",", spfdyidList);
        }
        return "";
    }

    @Override
    public String queryDjjgByUser(UserDto userDto) {
        if (userDto != null) {
            if (StringUtils.equals("2", djjgSjly)) {
                //??????????????????
                List<OrganizationDto> organizationDtos = userManagerUtils.listUserParentOrgs(userDto.getUsername());
                if (CollectionUtils.isNotEmpty(organizationDtos)) {
                    return organizationDtos.get(0).getName();
                } else if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                    //?????????????????????????????????
                    return userDto.getOrgRecordList().get(0).getName();
                }
            } else if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                //????????????
                return userDto.getOrgRecordList().get(0).getName();
            }
        }
        return "";

    }
}
