package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.dto.BdcPortalPzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.portal.ui.config.YwzyParamsConfig;
import cn.gtmap.realestate.portal.ui.service.impl.workflow.BdcWorkFlowAbstactServiceImpl_nantong;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/02/25
 * @description 登陆用户信息请求处理控制器，包括：基本资料、修改密码、退出
 */
@RestController
@RequestMapping("/rest/v1.0/config")
@Api(tags = "配置相关接口")
public class BdcConfigController extends BaseController {
    /**
     * 全限定类名
     */
    private static final String CLASS_NAME = BdcConfigController.class.getName();
    /**
     * 日志服务
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcConfigController.class);
    @Autowired
    YwzyParamsConfig ywzyParamsConfig;

    @Autowired
    ProcessDefinitionClient processDefinitionClient;

    @Autowired
    BdcWorkFlowAbstactServiceImpl_nantong bdcWorkFlowAbstactService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;
    /**
     * 项目列表台账是否显示删除按钮
     */
    @Value("${config.xm.delete: true}")
    private String xmdelete;
    /**
     * 流程办理页面是否显示审核按钮
     */
    @Value("${config.xm.checkback_processdefkey: hide}")
    private String checkProcessKey;

    /**
     * 网上业务显示流程名称
     */
    @Value("#{'${config.wsyw.gzldyids:}'.split(',')}")
    private List<String> wsywProcessKey;

    @Value("#{'${zrzy.xck.bdmc:}'.split(',')}")
    private List<String> bdcm;
    /**
     * 档案交接工作流实例ID
     */
    @Value("${config.dajj.gzldyid:}")
    private String dajjProcessKey;

    /**
     * 档案交接列表查询条件，流程名称排除的流程名称
     */
    @Value("${config.dajj.ylcmcNot:}")
    private String dajjCxtjExcludeProcessKey;

    /**
     * portal页面待办量跳转页面，默认待办任务
     */
    @Value("${config.dbltzym:todo}")
    private String dbltzym;

    /**
     * @description  是否显示原权利附件按钮
     */
    @Value("${yql.xsfj:false}")
    private Boolean xsyqlfj;

    /**
     * 控制是否打开离线提示
     */
    @Value("${timer.switch:false}")
    private Boolean tiemrSwitch;
    /**
     * 控制离线提示扫描间隔
     */
    @Value("${timer.time:60000}")
    private Long time;

    /**
     * 控制ofd文件是否在线预览
     */
    @Value("${ofd.sfzxyl:}")
    private Boolean ofdSfzxyl;

    /**
     * 【连云港】系统主页 “办理情况”区块是否配置专项业务办理量，默认false
     */
    @Value("${zxywbll: false}")
    private boolean zxywbll;

    /**
     * 【连云港】在配置了zxywbll: true时生效，表明专项业务办理量区块显示的专项业务的流程id，默认为空，需要配置
     */
    @Value("${lcid:}")
    private String lcid;

    /**
     * 获取业务转移配置信息
     *
     * @return {YwzyParamsConfig} 业务配置对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/ywzy")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取业务转移配置信息")
    public Object queryYwzyConfig() {
        if (MapUtils.isNotEmpty(ywzyParamsConfig.getParamMap())) {
            Map<String, Map<String, String>> paramMap = ywzyParamsConfig.getParamMap();
            UserDto currentUser = userManagerUtils.getCurrentUser();
            List<OrganizationDto> orgRecordList = currentUser.getOrgRecordList();
            for (OrganizationDto organizationDto : orgRecordList) {
                String code = organizationDto.getCode();
                Map<String, String> ywzy = paramMap.get(code);
                if (MapUtils.isNotEmpty(ywzy)) {
                    Map<String, String> param = Maps.newHashMap();
                    for (Map.Entry<String, String> entry : ywzy.entrySet()) {
                        String value = entry.getValue();
                        String[] strLen = StringUtils.split(value, ",");
                        if (strLen.length > 1) {
                            Random random = new Random();
                            param.put(entry.getKey(), strLen[random.nextInt(strLen.length)]);
                        }
                    }
                    LOGGER.debug("{}: 获取业务转移配置信息 :{}", CLASS_NAME, param);
                    return param;
                }
            }
        }
        return null;
    }

    @GetMapping("/xm/delete")
    public String queryXmDeleteButton() {
        return xmdelete;
    }

    @GetMapping("/xm/check/processkey")
    public String queryCheckProcessDefKey() {
        return checkProcessKey;
    }

    /**
     * 获取盐城网上业务流程
     *
     * @return List<ProcessDefData>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/wsyw/process")
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getAllProcessDefData() {
        List<ProcessDefData> allProcessDefData = processDefinitionClient.getAllProcessDefData();
        return allProcessDefData.stream().filter(processDefData -> wsywProcessKey.contains(processDefData.getProcessDefKey())).collect(Collectors.toList());
    }

    /**
     * 判断当前流程抵押权人是否接入互联网+<br>
     * 南通
     *
     * @return java.lang.String
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @GetMapping("/rl/sfjrhlw")
    public String listAotuEndProcess(@RequestParam String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("判断银行是否接入互联网+");
        }
        return bdcWorkFlowAbstactService.sfJrHlw(taskId);
    }

    /**
     * 获取预约分中心字典项
     *
     * @param
     * @return
     * @Date 2021/3/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/zd/yyfzx")
    public List<Map> yyfzx() {
        Map<String,List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<Map> yyfzx = new ArrayList<>();
        if (null != zdMap) {
            return yyfzx = zdMap.get("yyfzx");
        }
        return yyfzx;
    }

    /**
     * @param zdNames 逗号隔开的字典名称
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/mul")
    @ResponseBody
    public Map mulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            return getZdList(arr);
        }
        return new HashMap(0);
    }

    public Map getZdList(String[] zdNames) {
        Map<String, List<Map>> resultMap = new HashMap<>(zdNames.length);
        if (ArrayUtils.isNotEmpty(zdNames)) {
            for (String zdName : zdNames) {
                resultMap.put(zdName, bdcZdFeignService.queryBdcZd(zdName));
            }
        }
        return resultMap;
    }

    /**
     * 获取档案交接工作流定义ID
     * @return 档案交接工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/dajj/processkey")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取业务转移配置信息")
    public Map queryDajjProcessKey() {
        Map<String, String> configMap = new HashMap<>(2);
        configMap.put("processDefName", dajjProcessKey);
        configMap.put("ylcmc_not", dajjCxtjExcludeProcessKey);
        return configMap;
    }

    @GetMapping("/pz")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取配置信息")
    public Object getDbltz() {
        BdcPortalPzDTO portalPzDTO = new BdcPortalPzDTO();
        portalPzDTO.setDbltzym(dbltzym);
        portalPzDTO.setOfdSfzxyl(ofdSfzxyl);
        if(zxywbll){
            portalPzDTO.setZxywbll(true);
            portalPzDTO.setLcid(lcid);
        }
        return portalPzDTO;
    }

    /**
     * 获取档案交接工作流定义ID
     * @return 档案交接工作流定义ID
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    @GetMapping("/newpage/pzxx")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取newPage页面配置信息")
    public Object getNewpagePzxx(){
        Map<String,Object> resultMap = new HashMap<>();
//        xsyqlfj = true;
        resultMap.put("xsyqlfj",xsyqlfj);
        return resultMap;
    }

    @GetMapping("/getTimerConfig")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"), @ApiResponse(code = 500,message = "请求出错")})
    @ApiOperation("获取定时器开关")
    public Object getTimerSwich(){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("switch",tiemrSwitch);
        returnMap.put("time",time);
        return returnMap;
    }

    /**
     * 获取自然资源页面配置项
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    @GetMapping("/zrzy/pzxx")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取自然资源newPage页面配置信息")
    public Object getZyzyPz(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("bdmc",bdcm);
        return resultMap;
    }
}
