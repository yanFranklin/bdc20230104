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
 * @description 基本信息服务
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
     * 商品房流程
     */
    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记结构数据来源：1：用户当前部门 2：用户部门所属父级
     */
    @Value("${djjg.sjly:1}")
    private String djjgSjly;

    @Value("${cjyz.lwsh:false}")
    private Boolean cjyzlwsh;


    /**
     * @param bdcSlCshDTO 受理基本信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
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
            //更新流程信息
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcSlCshDTO.getGzlslid());
            if (processInstanceData == null) {
                throw new AppException("未获取到对应的流程信息,工作流实例ID:" + bdcSlCshDTO.getGzlslid());
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
                //区县代码
                //海门配置地区代码会到下级乡镇，长度为9位，直接截取前六位
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
        LOGGER.info("工作流实例ID:{}用户信息：{},区县代码：{}", bdcSlJbxxDO.getGzlslid(),JSONObject.toJSONString(userDto),bdcSlJbxxDO.getQxdm());
        StatisticsProcDto statisticsProcDto = statisticsProcessClient.queryProcessStaticsInfo(bdcSlCshDTO.getGzlslid());
        if (statisticsProcDto != null && statisticsProcDto.getProcDueLimitDouble() != null) {
            bdcSlJbxxDO.setCnqx(DoubleUtils.getString(statisticsProcDto.getProcDueLimitDouble()));
        }
        if (StringUtils.isNotBlank(bdcSlCshDTO.getDjxl())) {
            //登记原因赋值
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
            //  //存储基本信息创建基本参数,用于后续审核通过后自动创建
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
     * @param slbh 需要处理的受理编号
     * @return 新生成受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理重复受理编号
     */
    @Transactional
    @Override
    public String dealSameSlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            LOGGER.error("受理编号为空");
            return null;
        }
        String newslbh = "";
        LOGGER.info("当前处理受理编号:{}", slbh);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //根据gzlslid分组
            Map<String, List<BdcXmDO>> xmMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getGzlslid));
            if (MapUtils.isNotEmpty(xmMap) && xmMap.size() > 1) {
                int i = 0;
                //存在多个,保留一条数据,处理其余数据
                for (Map.Entry<String, List<BdcXmDO>> entry : xmMap.entrySet()) {
                    if (i != 0) {
                        String gzlslid = entry.getKey();
                        List<BdcXmDO> xmDOList = entry.getValue();
                        if (CollectionUtils.isNotEmpty(xmDOList) && xmDOList.get(0).getSlsj() != null) {
                            newslbh = bdcBhService.queryBhBySlsj(Constants.BH_SL, "", xmDOList.get(0).getSlsj());
                            LOGGER.info("当前处理受理编号:{}替换的受理编号:{}", slbh, newslbh);
                            //更新bdc_xm slbh
                            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("slbh", newslbh);
                            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
                            Map<String, Object> map = new HashMap<>();
                            map.put("gzlslid", gzlslid);
                            bdcDjxxUpdateQO.setWhereMap(map);
                            int result = bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
                            LOGGER.info("更新bdc_xm表受理编号，工作流实例ID:{},新受理编号：{},更新数量：{}", gzlslid, newslbh, result);
                            //更新bdc_sl_jbxx slbh
                            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxServic.queryBdcSlJbxxByGzlslid(gzlslid);
                            if (bdcSlJbxxDO != null) {
                                bdcSlJbxxDO.setSlbh(newslbh);
                                result = bdcSlJbxxServic.updateBdcSlJbxx(bdcSlJbxxDO);
                            }
                            LOGGER.info("更新bdc_sl_jbxx表受理编号，工作流实例ID:{},新受理编号：{},更新数量：{}", gzlslid, newslbh, result);
                            //回写
                            try {
                                Map<String, Object> processInsExtendDto = new HashMap<>();
                                processInsExtendDto.put("PROC_INS_ID", gzlslid);
                                processInsExtendDto.put("SLBH", newslbh);
                                bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
                                LOGGER.info("回写大云,工作流实例ID:{},新受理编号：{}", gzlslid, newslbh);
                            } catch (Exception e) {
                                LOGGER.error("回写大云字段异常！gzlslid={},回写字段slbh={}", gzlslid, newslbh);
                            }

                        }
                    }
                    i++;
                }


            } else {
                throw new AppException("未发现重复数据");
            }

        }
        return newslbh;

    }

    /**
     * @return 获取配置的工作流定义id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取配置的工作流定义id
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
                //部门所属父级
                List<OrganizationDto> organizationDtos = userManagerUtils.listUserParentOrgs(userDto.getUsername());
                if (CollectionUtils.isNotEmpty(organizationDtos)) {
                    return organizationDtos.get(0).getName();
                } else if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                    //取不到父级，取当前部门
                    return userDto.getOrgRecordList().get(0).getName();
                }
            } else if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                //当前部门
                return userDto.getOrgRecordList().get(0).getName();
            }
        }
        return "";

    }
}
