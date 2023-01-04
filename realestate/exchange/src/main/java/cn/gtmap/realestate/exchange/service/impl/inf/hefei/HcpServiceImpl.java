package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.hcp.EvaluatedPersonInfo;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.dto.hefei.hcp.HcpHlwResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.hefei.hcp.HcpHlwResponseData;
import cn.gtmap.realestate.exchange.core.dto.hefei.hcp.HcpHlwResponseHead;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.hefei.HcpService;
import cn.gtmap.realestate.exchange.util.Base64Util;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.IPUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/07/21
 * @description 安徽好差评服务接口（合肥，舒城，蚌埠）
 */
@Service
public class HcpServiceImpl implements HcpService {
    protected static Logger LOGGER = LoggerFactory.getLogger(HcpServiceImpl.class);

    /**
     * 好差评系统字典项-申请人类型
     */
    private static final String HCP_ZD_SQRLX = "HCP_ZD_SQRLX";

    /**
     * 好差评系统字典项-申请人证件种类
     */
    private static final String HCP_ZD_SQRZJZL = "HCP_ZD_SQRZJZL";

    /**
     * 好差评系统字典项-申请人证件种类名称
     */
    private static final String HCP_ZD_SQRZJZLMC = "HCP_ZD_SQRZJZLMC";

    /**
     * 好差评系统字典项-办件状态dm
     */
    private static final String HCP_ZD_BJZTDM = "HCP_ZD_BJZTDM";

    /**
     * 好差评系统字典项-办件状态mc
     */
    private static final String HCP_ZD_BJZTMC = "HCP_ZD_BJZTMC";

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Value("${hcp.pjym.url:}")
    private String hcpPjymUrl;

    @Value("${hcp.gxzt.url:}")
    private String hcpGxztUrl;

    /**
     * 好差评讯飞的地址
     **/
    @Value("${hcp.xf.url:}")
    private String hcpXfUrl;

    /**
     * 好差评讯飞的msgCode配置
     **/
    @Value("${hcp.xf.msgcode:}")
    private String hcpXfMsgCode;

    /**
     * 好差评讯飞的msgType配置项
     **/
    @Value("${hcp.xf.msgtype:}")
    private String hcpXfMsgType;

    @Value("${hcp.source:501}")
    private String source;


    /**
     * 调用好差评评价页面接口
     *
     * @param map 请求参数
     * @return 返回评价页面url
     */
    public Object organizeRequestUrl(Map map) {
        String gzlslid = MapUtils.getString(map, "gzlslid");
        String taskId = MapUtils.getString(map, "taskId");
        String userId = MapUtils.getString(map, "userId");
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(taskId)) {
            throw new AppException("调用好差评系统，缺少参数gzlslid或taskId");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        StringBuilder sb = new StringBuilder();
        sb.append("key=PJQ");
        sb.append("&evaluateChannel=PJQ");
        String projNo = "";
        String projName = "";
        String serviceCode = "";
        String serviceVersion = "";
        String serviceDepartment = "";
        String serviceDepartmentCode = "";
        // 办件申请人姓名/申请单位名
        String applicantName = "";
        // 办件申请人证件号码
        String applicantCardNumber = "";
        // 申请人联系方式
        String applicantPhone = "";
        // 办件状态dm,大云节点名称
        String handleStateCode = "";
        // 办件状态mc
        String handleStateName = "";
        String regionCode = "";
        String regionName = "";
        // 实施编码
        String ssqdBaseCode = "1";
        String serviceCategory = "";
        String infoTypeName = "";
        // 申请人类型
        String userProp = "";
        // 办件申请人证件类型
        String userPageType = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
            projNo = bdcXmDO.getZfxzspbh();
            regionCode = bdcXmDO.getQxdm() + "000000";
            List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
            regionName = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDO.getQxdm()), qxZdmap);
            // 权利人
            BdcQlrQO qlrQO = new BdcQlrQO();
            qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            qlrQO.setXmid(bdcXmDO.getXmid());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(qlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                applicantName = bdcQlrDO.getQlrmc();
                applicantCardNumber = bdcQlrDO.getZjh();
                applicantPhone = bdcQlrDO.getDh();
                userProp = convertBdcZdToDsf("hcp", HCP_ZD_SQRLX, String.valueOf(bdcQlrDO.getQlrlx()));
                userPageType = convertBdcZdToDsf("hcp", HCP_ZD_SQRZJZL, String.valueOf(bdcQlrDO.getZjzl()));
            }
            if (StringUtils.isNotBlank(bdcXmDO.getDjxl())) {
                String qjgldm = bdcXmFbDO.getQjgldm();
                List<BdcDjxlSbdjfkCsgxDO> sbdjfkCsgxDOList = bdcdjMapper.querySbdjfkList(bdcXmDO.getDjxl(), qjgldm);
                if (CollectionUtils.isNotEmpty(sbdjfkCsgxDOList)) {
                    BdcDjxlSbdjfkCsgxDO sbdjfkCsgxDO = sbdjfkCsgxDOList.get(0);
                    projName = sbdjfkCsgxDO.getServiceName();
                    serviceCode = sbdjfkCsgxDO.getServiceCode();
                    serviceVersion = sbdjfkCsgxDO.getServiceVersion();
                    serviceDepartment = sbdjfkCsgxDO.getDeptName();
                    serviceDepartmentCode = sbdjfkCsgxDO.getDeptId();
                    serviceCategory = sbdjfkCsgxDO.getInfoTypeCode();
                    infoTypeName = sbdjfkCsgxDO.getInfoType();
                    // 如果权籍管理代码有值，区划编码和名称根据qjgldm取值
                    if (StringUtils.isNotBlank(qjgldm)) {
                        regionCode = sbdjfkCsgxDO.getAreaCode();
                        List<Map> gjgldmZdmap = bdcZdFeignService.queryBdcZd("qjgldm");
                        String qhmc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(qjgldm), gjgldmZdmap);
                        regionName = qhmc;
                    }
                }
            }
        }
        // 获取当前节点名称
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if (Objects.nonNull(taskData)) {
            handleStateCode = convertBdcZdToDsf("hcp", HCP_ZD_BJZTDM, taskData.getTaskName());
            handleStateName = convertBdcZdToDsf("hcp", HCP_ZD_BJZTMC, taskData.getTaskName());
        }
        //参与该办件的窗口人员信息
        List<EvaluatedPersonInfo> personInfos = new ArrayList<>();
        EvaluatedPersonInfo personInfo = new EvaluatedPersonInfo();
        UserDto userDto = userManagerUtils.getUserByUserid(userId);
        if (userDto != null) {
            personInfo.setLoginName(userDto.getUsername());
            personInfo.setName(userDto.getAlias());
            personInfos.add(personInfo);
        }

        sb.append("&projNo=" + projNo);
        sb.append("&projName=" + projName);
        sb.append("&serviceName=" + projName);
        sb.append("&serviceCode=" + serviceCode);
        sb.append("&serviceVersion=" + serviceVersion);
        sb.append("&serviceDepartment=" + serviceDepartment);
        sb.append("&serviceDepartmentCode=" + serviceDepartmentCode);

        sb.append("&applicantName=" + applicantName);
        sb.append("&applyTime=" + DateUtils.formateTimeYmdhms(new Date()));
        sb.append("&applicantCardNumber=" + applicantCardNumber);
        sb.append("&applicantPhone=" + applicantPhone);
        sb.append("&handleStateCode=" + handleStateCode);
        sb.append("&handleStateName=" + handleStateName);
        sb.append("&regionCode=" + regionCode);
        sb.append("&regionName=" + regionName);
        sb.append("&ssqdBaseCode=" + ssqdBaseCode);
        // 必传source
        sb.append("&source=" + source);
        sb.append("&serviceCategory=" + serviceCategory);
        sb.append("&infoTypeName=" + infoTypeName);

        // 当前区划评价数据需要上报国家时为必填
        sb.append("&version=2");
        sb.append("&userProp=" + userProp);
        sb.append("&userPageType=" + userPageType);
        sb.append("&transactionTime=" + DateUtils.formateTimeYmdhms(new Date()));

        sb.append("&evaluatedPersonInfo=" + JSON.parseArray(JSON.toJSONString(personInfos)));
        sb.append("&htEvaluatedPersonInfo=" + JSON.parseArray(JSON.toJSONString(personInfos)));
        LOGGER.info("好差评评价页面组织参数：{}", hcpPjymUrl + sb.toString());
        //base64编码
        String param = Base64Util.str2Baes64Str(sb.toString());
        // 第二次base64编码
        String url = hcpPjymUrl + Base64Util.str2Baes64Str(param);
        LOGGER.info("好差评评价页面地址url：{}", url);
        Map<String, String> result = new HashMap<>();
        result.put("res", url);
        return result;
    }


    /**
     * @description: 蚌埠好差评，pad使用讯飞小程序，弹出评价页面
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/21 15:17
     * @param map
     * @return: java.lang.Object
     **/
    public Object sendHcpUrl(Map map) {
        Map<String, Object> param = new HashMap<>();
        String userId = MapUtils.getString(map, "userId");
        String username = "";
        Map<String, String> result = (Map<String, String>) organizeRequestUrl(map);
        String pjymUrl = result.get("res");
        if (StringUtils.isNotBlank(userId)) {
            UserDto userDto = userManagerUtils.getUserByUserid(userId);
            if (userDto != null) {
                username = userDto.getUsername();
            }
        }
        param.put("receiverUser", username);
        param.put("msgCode", hcpXfMsgCode);
        param.put("msgType", hcpXfMsgCode);
        param.put("data", pjymUrl);
        String response = "";
        JSONObject jsonObject = new JSONObject();
        try {
            response = httpClientUtils.sendPostRequest(hcpXfUrl, param, "蚌埠好差评请求讯飞");
            if (StringUtils.isNotBlank(response)) {
                jsonObject = JSON.parseObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @description: 宣城好差评，调用摩科打开网址接口
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/21 15:17
     * @param map
     * @return: java.lang.Object
     **/
    public Object sendXcHcpUrl(Map map) {
        Map<String, Object> param = new HashMap<>();
        Map<String, String> result = (Map<String, String>) organizeRequestUrl(map);
        String pjymUrl = result.get("res");
        param.put("sysIp", IPUtils.getLocalMac());
        param.put("key", pjymUrl);
        return  exchangeBeanRequestService.request("mk_dkwz",param);
    }

    /**
     * 字典项对照-不动产字典项转第三方字典
     *
     * @param value
     * @return
     */
    private String convertBdcZdToDsf(String dsfxtbs, String zdbs, String value) {
        // 查询第三方字典项对照关系
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
        bdcZdDsfzdgxDO.setBdczdz(value);
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        if (result != null) {
            return result.getDsfzdz();
        }
        return "";
    }


    public Object updateEvaluateStatus(String projNo) {
        if (StringUtils.isNotBlank(projNo)) {
            String url = hcpGxztUrl + "?projNo=" + projNo;
            LOGGER.info("updateEvaluateStatus请求参数.url:{}", url);
            String respons = "";
            try {
                respons = httpClientUtils.sendPostRequest(url, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("updateEvaluateStatus.respons:{}", respons);
            return respons;
        }
        return null;
    }

    /**
     * @param slbh
     * @description: 互联网查询不动产好差评相关信息
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/9 17:10
     * @return: BdcCommonResponse
     **/
    @Override
    public HcpHlwResponseDTO getHcpParamsBySlbh(String slbh) {
        HcpHlwResponseDTO hcpHlwResponseDTO = new HcpHlwResponseDTO();
        HcpHlwResponseHead failHead = new HcpHlwResponseHead();
        failHead.setReturncode("1000");
        failHead.setStatusCode("1000");
        if (StringUtils.isBlank(slbh)) {
            LOGGER.error("缺少必要参数受理编号");
            hcpHlwResponseDTO.setHead(failHead);
            return hcpHlwResponseDTO;
        }
        HcpHlwResponseData hcpHlwResponseData = new HcpHlwResponseData();
        hcpHlwResponseData.setNwslbh(slbh);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.error("项目不存在，受理编号：{}",slbh);
            hcpHlwResponseDTO.setHead(failHead);
            return hcpHlwResponseDTO;
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
        String regionCode = bdcXmDO.getQxdm() + "000000";
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
        String regionName = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDO.getQxdm()), qxZdmap);
        hcpHlwResponseData.setBjbh(bdcXmDO.getZfxzspbh());
        hcpHlwResponseData.setSsqyqhdm(regionCode);
        hcpHlwResponseData.setSsqyqhmc(regionName);
        // 权利人
        BdcQlrQO qlrQO = new BdcQlrQO();
        qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        qlrQO.setXmid(bdcXmDO.getXmid());
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(qlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            String userProp = convertBdcZdToDsf("hcp", HCP_ZD_SQRLX, String.valueOf(bdcQlrDO.getQlrlx()));
            String userPageType = convertBdcZdToDsf("hcp", HCP_ZD_SQRZJZL, String.valueOf(bdcQlrDO.getZjzl()));
            String zjxlmc= convertBdcZdToDsf("hcp", HCP_ZD_SQRZJZLMC, String.valueOf(bdcQlrDO.getZjzl()));
            hcpHlwResponseData.setSqrmc(bdcQlrDO.getQlrmc());
            hcpHlwResponseData.setSqrzjh(bdcQlrDO.getZjh());
            hcpHlwResponseData.setSqrdh(bdcQlrDO.getDh());
            hcpHlwResponseData.setBjsqrlx(userProp);
            hcpHlwResponseData.setBjsqrzjlxdm(userPageType);
            hcpHlwResponseData.setBjsqrzjlxmc(zjxlmc);
        }
        if (StringUtils.isNotBlank(bdcXmDO.getDjxl())) {
            String qjgldm = bdcXmFbDO.getQjgldm();
            List<BdcDjxlSbdjfkCsgxDO> sbdjfkCsgxDOList = bdcdjMapper.querySbdjfkList(bdcXmDO.getDjxl(),qjgldm);
            if (CollectionUtils.isNotEmpty(sbdjfkCsgxDOList)) {
                BdcDjxlSbdjfkCsgxDO sbdjfkCsgxDO = sbdjfkCsgxDOList.get(0);
                hcpHlwResponseData.setBjmc(sbdjfkCsgxDO.getServiceName());
                hcpHlwResponseData.setSxmc(sbdjfkCsgxDO.getServiceName());
                hcpHlwResponseData.setSxbm(sbdjfkCsgxDO.getServiceCode());
                hcpHlwResponseData.setSxbb(sbdjfkCsgxDO.getServiceVersion());
                hcpHlwResponseData.setSlbmmc(sbdjfkCsgxDO.getDeptName());
                hcpHlwResponseData.setSlbmbm(sbdjfkCsgxDO.getDeptId());
                hcpHlwResponseData.setBjlxbm(sbdjfkCsgxDO.getInfoTypeCode());
                hcpHlwResponseData.setBjlxmc(sbdjfkCsgxDO.getInfoType());
                // 如果权籍管理代码有值，区划编码和名称根据qjgldm取值
                if (StringUtils.isNotBlank(qjgldm)){
                    hcpHlwResponseData.setSsqyqhdm(sbdjfkCsgxDO.getAreaCode());
                    List<Map> gjgldmZdmap = bdcZdFeignService.queryBdcZd("qjgldm");
                    String qhmc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(qjgldm), gjgldmZdmap);
                    hcpHlwResponseData.setSsqyqhmc(qhmc);
                }

            }
        }
        // 获取当前节点名称
        List<TaskData> taskDataList = processTaskClient.processLastTasks(bdcXmDO.getGzlslid());
        if (CollectionUtils.isNotEmpty(taskDataList)) {
            TaskData taskData = taskDataList.get(0);
            String handleStateCode = convertBdcZdToDsf("hcp", HCP_ZD_BJZTDM, taskData.getTaskName());
            String handleStateName = convertBdcZdToDsf("hcp", HCP_ZD_BJZTMC, taskData.getTaskName());
            hcpHlwResponseData.setBjzt(handleStateCode);
            hcpHlwResponseData.setBjztmc(handleStateName);
        }
        //参与该办件的窗口人员信息
        UserDto userDto = userManagerUtils.getUserByUserid(bdcXmDO.getSlrid());
        if (userDto != null) {
            hcpHlwResponseData.setSlrxm(bdcXmDO.getSlr());
            hcpHlwResponseData.setSlryhm(userDto.getUsername());
        }
        HcpHlwResponseHead head = new HcpHlwResponseHead();
        head.setReturncode("0000");
        head.setStatusCode("0000");
        hcpHlwResponseDTO.setData(hcpHlwResponseData);
        hcpHlwResponseDTO.setHead(head);
        return hcpHlwResponseDTO;
    }



}
