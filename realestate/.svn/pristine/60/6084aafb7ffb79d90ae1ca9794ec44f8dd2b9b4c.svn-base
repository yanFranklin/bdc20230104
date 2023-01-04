package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.gtc.workflow.domain.statistics.OrgTaskStatisticsInfo;
import cn.gtmap.gtc.workflow.domain.statistics.UserTaskStatisticsInfo;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDyzmTjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZhcxDyzmTjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGzlTjFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhcxTjFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskStatisticsClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.inquiry.ui.util.GzltjCommonUtils;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import cn.gtmap.realestate.inquiry.ui.web.rest.config.ZtreeController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: <a href="@mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version: V1.0, 2019.03.12
 * @description: 工作量统计前端控制层, 主要是调用平台接口获取数据并组织
 */
@RestController
@RequestMapping("/gzltj")
@ConfigurationProperties(prefix = "gzltj")
public class BdcGzltjController extends BaseController {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String USERNAME = "username";
    private static final String USER_ALIAS = "userAlias";
    private static final String REAL_COUNT = "realCount";
    private static final String ORG_ID = "orgId";
    private static final String ORG_NAME = "orgName";
    private static final String PROCESS_NAME = "processName";
    private static final String PROCESS_KEY = "procDefKey";
    private static final String TASK_NAME = "taskName";
    private static final String ENCODING = "utf-8";
    private static final int MAX_COUNT = 9999;

    private static final String QTDESC = "其他";
    private static final String HJDESC = "合计";
    //private static final String  DATE_PREFIX = " 00:00:00";
    //private static final String  DATE_SUFFFIX = " 23:59:59";
    private static final String  EXPORTALL = "exportAll";
    private static final String  PRINT = "print";
    private static final String ERROR_INTERFACE = "调用平台接口出错，错误原因：{}";
    @Autowired
    private BdcGzlTjFeignService bdcGzlTjFeignService;
    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private TaskStatisticsClientMatcher taskClient;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcZhcxTjFeignService bdcZhcxTjFeignService;
    @Autowired
    private ExcelController excelController;

    @Value("${gzltj.jdcstjfs:realCount}")
    private String realCount;

    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    @Autowired
    ZtreeController ztreeController;

    //要统计的节点和节点柱子颜色
    private Map<String, String> jdmc;

    public Map<String, String> getJdmc() {
        return jdmc;
    }

    public void setJdmc(Map<String, String> jdmc) {
        this.jdmc = jdmc;
    }

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.03.12 13:39
     * @author chenyucheng
     */
    @PostMapping(value = "/count")
    public Object listGzltjCount(@RequestBody GzltjQO param) {
        if(sfglbm && StringUtils.isBlank(param.getDjjg())){
            List<OrganizationDto> organizationDtos = ztreeController.queryGlOrgs(new BdcXtOrgQO());
            if (CollectionUtils.isNotEmpty(organizationDtos)){
                param.setDjjg(organizationDtos.stream().map(OrganizationDto::getName).collect(Collectors.joining(",")));
                param.setBmid(organizationDtos.stream().map(OrganizationDto::getId).collect(Collectors.joining(",")));
            }
        }
        List infoList = new ArrayList();
        List infoListtemp = new ArrayList();
        for(int i=0;i<param.getJdmc().split(",").length;i++){
           String jdmcTemp = param.getJdmc().split(",")[i];
           try {
               if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                   long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                   long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                   infoListtemp = taskClient.getStatisticsOfAllOrgs(stTime,edTime,null,false,null, CommonConstantUtils.JDZDMAP.get(jdmcTemp),param.getStatisticsType());
               }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                   long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                   infoListtemp = taskClient.getStatisticsOfAllOrgs(null,edTime,null,false,null,CommonConstantUtils.JDZDMAP.get(jdmcTemp),param.getStatisticsType());
               }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                   long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                   infoListtemp = taskClient.getStatisticsOfAllOrgs(stTime,null,null,false,null,CommonConstantUtils.JDZDMAP.get(jdmcTemp),param.getStatisticsType());
               }else{
                   infoListtemp = taskClient.getStatisticsOfAllOrgs(null,null,null,false,null,CommonConstantUtils.JDZDMAP.get(jdmcTemp),param.getStatisticsType());
               }
           } catch (Exception e) {
               LOGGER.error(ERROR_INTERFACE, e.getMessage());
           }
           infoList.addAll(infoListtemp);
        }

        List<Map> resultList = new ArrayList<>();
        Map<String,Map<String,Integer>> ycLcAndJdDataMap = new HashMap<>();

        // 用于后续判断当前部门是否已经统计过
        Map<String,String> bmsMap = new HashMap<>();
        Map<String, Object> tempMap;
        Map<String, String> process = new HashMap<>();
        Set<String> node = new HashSet<>();
        String dimension = param.getDimension();
        for (int i = 0; i < infoList.size(); i++) {
            OrgTaskStatisticsInfo tempObject = (OrgTaskStatisticsInfo) infoList.get(i);
            // 分组的key，合肥项目组 - 32 中的 合肥项目组
            /**
             * 因为接口不支持流程部门入参，这里根据流程,部门内存过滤
             * 1.判空 2.比对。这里可以给大云提需求，入参加入bm和流程入参过滤
             * 第一次过滤 过滤不满足的条件的
             */
            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
            boolean orgNameFilterFlag =  StringUtils.isNotBlank(param.getDjjg())
                    && param.getDjjg().indexOf(tempObject.getOrgName()) < 0;
            if(procKeyFilterFlag || orgNameFilterFlag){
                continue;
            }

            String dimensionStr = "";
            if( StringUtils.isBlank(dimension) || StringUtils.equals("wd-bm",dimension)){
                dimensionStr = tempObject.getOrgName();
            }else if(StringUtils.equals("wd-lc",dimension)){
                dimensionStr = tempObject.getProcDefName();
            }else if(StringUtils.equals("wd-jd",dimension)){
                dimensionStr = tempObject.getTaskName();
            }
            boolean flagKey = StringUtils.isNotBlank(tempObject.getProcDefKey());
            boolean flagName = StringUtils.isNotBlank(tempObject.getProcDefName());
            boolean flagTaskName = StringUtils.isNotBlank(tempObject.getTaskName());

            if(StringUtils.isNotBlank(tempObject.getProcDefName())){
                // 是否包含该流程的数据
                if(!ycLcAndJdDataMap.containsKey(tempObject.getProcDefName())){
                    Map<String,Integer> jdMap = new HashMap<>();
                    int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?tempObject.getRealCount():tempObject.getCount();
                    String taskName = tempObject.getTaskName();
                    if(jdmc != null && jdmc.containsKey(taskName)){
                        jdMap.put(taskName,realCountTemp);
                    }else{
                        jdMap.put(QTDESC,realCountTemp);
                    }
                    jdMap.put(HJDESC,realCountTemp);
                    ycLcAndJdDataMap.put(tempObject.getProcDefName(),jdMap);
                }else{
                    Map<String,Integer> jdMap = ycLcAndJdDataMap.get(tempObject.getProcDefName());
                    if(jdMap.containsKey(tempObject.getTaskName())){
                        int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?tempObject.getRealCount():tempObject.getCount();
                        jdMap.put(tempObject.getTaskName(),jdMap.get(tempObject.getTaskName())+realCountTemp);
                        jdMap.put(HJDESC,jdMap.get(HJDESC)+realCountTemp);

                    }else{
                        int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?tempObject.getRealCount():tempObject.getCount();
                        String taskName = tempObject.getTaskName();
                        if(jdmc != null && jdmc.containsKey(taskName)){
                            jdMap.put(taskName,realCountTemp);
                        }else{
                            if(jdMap.containsKey(QTDESC)){
                                jdMap.put(QTDESC,realCountTemp+jdMap.get(QTDESC));
                            }else{
                                jdMap.put(QTDESC,realCountTemp);
                            }
                        }
                        jdMap.put(HJDESC,jdMap.get(HJDESC)+realCountTemp);
                    }
                }
            }

            // 缓存流程
            if(flagKey && flagName && !process.containsKey(tempObject.getProcDefKey())){
                process.put(tempObject.getProcDefKey(),tempObject.getProcDefName());
            }
            // 缓存节点
            if(flagTaskName){
                node.add(tempObject.getTaskName());
            }
            if (!bmsMap.containsKey(dimensionStr) && StringUtils.isNotEmpty(dimensionStr)) {
                bmsMap.put(dimensionStr,dimensionStr);
                tempMap = Maps.newHashMap();
                tempMap.put(ORG_ID, tempObject.getOrgId());
                tempMap.put(ORG_NAME, dimensionStr);
                tempMap.put(REAL_COUNT, 0);
                // 循环累积当前部门的realCount到hashMap中
                Iterator iterator = infoList.iterator();
                while (iterator.hasNext()) {
                    tempObject = (OrgTaskStatisticsInfo) iterator.next();
                    /**
                     * 因为接口不支持流程部门入参，这里根据流程,部门内存过滤
                     * 1.判空 2.比对。这里可以给大云提需求，入参加入bm和流程入参过滤
                     * 第二次过滤，是为了去掉所有其他类型的为空的echart柱子
                     */
                     procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                            && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
                     orgNameFilterFlag =  StringUtils.isNotBlank(param.getDjjg())
                            && param.getDjjg().indexOf(tempObject.getOrgName())<0;
                    if(procKeyFilterFlag || orgNameFilterFlag){
                        continue;
                    }
                    // 统计维度
                    String dimensionEqualStr = "";
                    if( StringUtils.isBlank(dimension) || "wd-bm".equals(dimension)){
                        dimensionEqualStr = tempObject.getOrgName();
                    }else if("wd-lc".equals(dimension)){
                        dimensionEqualStr = tempObject.getProcDefName();
                    }else if("wd-jd".equals(dimension)){
                        dimensionEqualStr = tempObject.getTaskName();
                    }
                    if (StringUtils.equals(dimensionStr, dimensionEqualStr)) {
                        int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?tempObject.getRealCount():tempObject.getCount();
                        tempMap.put(REAL_COUNT, (int) tempMap.get(realCount) + realCountTemp);
                    }
                }
                resultList.add(tempMap);
            }
        }
        // 排序
        GzltjCommonUtils.compareByCols(dimension,resultList,ORG_NAME);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("map",process);// 流程
        resultMap.put("node",node);// 节点
        resultMap.put("list",resultList);// 工作量
        resultMap.put("ycLcAndJdData",ycLcAndJdDataMap);// 工作量
        resultMap.put("tjjdmc",jdmc);// 工作量
        return resultMap;
    }

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.06.6 13:39
     * @author chenyucheng
     */
    @PostMapping(value = "/countBjl")
    public Object listGzltjCountBjl(@RequestBody GzltjQO param) {
        Object obj = bdcGzlTjFeignService.listBdcGzltj(JSON.toJSONString(param));
        /**
         * 排序，排序之前先了解返回值的数据结构
         * [{slr:陈玉城,ajzt:1,count:10},{slr:陈玉城,ajzt:2,count:20}
         * {slr:刘德华,ajzt:1,count:22},{slr:刘德华,ajzt:2,count:12}]
         * 这种情况下 要按照slr的count总数排序
         */
        return BdcGzltjController.sortBjl((List)obj);
    }

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.06.6 13:39
     * @author chenyucheng
     */
    @GetMapping(value = "/bmry/countBjl")
    public Object listGzltjBmryCountBjl(GzltjQO param) {
        return bdcGzlTjFeignService.listBdcGzltjBmry(JSON.toJSONString(param));
    }

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.06.6 13:39
     * @author chenyucheng
     */
    @PostMapping(value = "/mxBjl")
    public Object listGzltjMxBjl(@RequestBody GzltjQO param) throws UnsupportedEncodingException {
        if(EXPORTALL.equals(param.getType())){// 导出全部
            return bdcGzlTjFeignService.listGzltjMxBjl(JSON.toJSONString(param));
        } else if(PRINT.equals(param.getType()) ){// 打印全部
            List list =  bdcGzlTjFeignService.listGzltjMxBjl(URLDecoder.decode(JSON.toJSONString(param),ENCODING));
            List<BdcDysjDTO> bdcDysjDTOList = GzltjCommonUtils.makePrintData(list,GzltjCommonUtils.MXBJL_MODEL);
            return bdcPrintFeignService.printDatas(bdcDysjDTOList);
        }else {
            return super.addLayUiCode(PageUtils.listToPage(bdcGzlTjFeignService.listGzltjMxBjl(JSON.toJSONString(param)),
                    new PageRequest(param.getPage()-1, param.getSize(), null)));
        }
    }

    /**
     * 收件量统计查询信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2022.3.1
     * @author wutao2
     */
    @GetMapping(value = "/mxSjl")
    public Object listGzltjMxSjl( @LayuiPageable Pageable pageable,GzltjQO param) {
        return super.addLayUiCode(PageUtils.listToPage(bdcGzlTjFeignService.listGzltjMxSjl(JSON.toJSONString(param)), pageable));
    }

    /**
     * 打印勾選辦件量
     * @param param
     * @return
     */
    @GetMapping(value = "/mxBjl/print")
    public Object listGzltjMxBjlPrint(GzltjQO param) throws UnsupportedEncodingException {
        List list =  JSON.parseArray(URLDecoder.decode(param.getPrintFilterJson(),ENCODING));
        List<BdcDysjDTO> bdcDysjDTOList = GzltjCommonUtils.makePrintData(list,GzltjCommonUtils.MXBJL_MODEL);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * 获取当前部门所有人员的办件量信息
     *
     * @param param 工作量统计qo对象
     * @return java.lang.Object
     * @date 2019.03.12 13:39
     * @author chenyucheng
     */
    @PostMapping(value = "/bmry/count")
    public List<Map> listGzltjBmCount(@RequestBody GzltjQO param) {
        List infoList = new ArrayList();
        List infoListtemp = new ArrayList();
        // 处理日期参数并调用接口获取结果
        String bmids = param.getBmid();
        String jdmcs = param.getJdmc();
        for(int i=0;i<bmids.split(",").length;i++){
            String bmid = bmids.split(",")[i];
            for(int j=0;j<jdmcs.split(",").length;j++){
                String jdmcT = jdmcs.split(",")[j];
                try {
                    if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                        long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                        long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                        infoListtemp = taskClient.getStatisticsByOrg(
                                stTime,edTime,
                                bmid, null, CommonConstantUtils.JDZDMAP.get(jdmcT),param.getStatisticsType());
                    }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                        long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                        infoListtemp = taskClient.getStatisticsByOrg(
                                null,edTime,
                                bmid, null, CommonConstantUtils.JDZDMAP.get(jdmcT),param.getStatisticsType());
                    }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                        long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                        infoListtemp = taskClient.getStatisticsByOrg(
                                stTime,null,
                                bmid, null, CommonConstantUtils.JDZDMAP.get(jdmcT),param.getStatisticsType());
                    }else{
                        infoListtemp = taskClient.getStatisticsByOrg(
                                null,null,
                                bmid, null, CommonConstantUtils.JDZDMAP.get(jdmcT),param.getStatisticsType());
                    }
                } catch (Exception e) {
                    LOGGER.error(ERROR_INTERFACE, e.getMessage());
                }
                infoList.addAll(infoListtemp);
            }
        }

        List<Map> resultList = Lists.newArrayList();
        StringBuilder rymcString = new StringBuilder();
        Map<String, Object> tempMap;
        // 循环统计
        for (int i = 0; i < infoList.size(); i++) {
            UserTaskStatisticsInfo tempObject = (UserTaskStatisticsInfo) infoList.get(i);
            /**
             * 因为接口不支持流程，人员入参，这里根据流程,部门内存过滤
             * 1.判空 2.比对。这里可以给大云提需求，入参加入人员和流程入参过滤
             */
            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess()) && StringUtils.isNotBlank(tempObject.getProcDefKey())
                    && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
            boolean orgNameSlrmcFlag =  StringUtils.isNotBlank(param.getSlrmc()) && StringUtils.isNotBlank(tempObject.getUserAlias())
                    && param.getSlrmc().indexOf(tempObject.getUserAlias()) <0;
            if(procKeyFilterFlag || orgNameSlrmcFlag){
                continue;
            }
            String username = tempObject.getUsername();
            // 根据流程，人员内存过滤
            if (StringUtils.indexOf(rymcString, username) < 0 && StringUtils.isNotEmpty(tempObject.getUserAlias())) {
                rymcString.append(username);
                tempMap = Maps.newHashMap();
                // 为了统一前端 都用orgname为key,这里放入org_name-username
                tempMap.put(USERNAME, tempObject.getUsername());
                tempMap.put(ORG_NAME, tempObject.getUserAlias());
                tempMap.put(REAL_COUNT, 0);
                Iterator iterator = infoList.iterator();
                while (iterator.hasNext()) {
                    tempObject = (UserTaskStatisticsInfo) iterator.next();
                    /**
                     * 因为接口不支持流程，人员入参，这里根据流程,部门内存过滤
                     * 1.判空 2.比对。这里可以给大云提需求，入参加入人员和流程入参过滤
                     */
                     procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())&& StringUtils.isNotBlank(tempObject.getProcDefKey())
                            && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
                     orgNameSlrmcFlag =  StringUtils.isNotBlank(param.getSlrmc()) && StringUtils.isNotBlank(tempObject.getUserAlias())
                            && param.getSlrmc().indexOf(tempObject.getUserAlias())<0;
                    if(procKeyFilterFlag || orgNameSlrmcFlag){
                        continue;
                    }
                    if (StringUtils.equals(username, tempObject.getUsername())) {
                        int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?tempObject.getRealCount():tempObject.getCount();
                        tempMap.put(REAL_COUNT, (int) tempMap.get(realCount) + realCountTemp);
                    }
                }
                resultList.add(tempMap);
            }
        }
        GzltjCommonUtils.compareByCols("wd-ry",resultList,"");
        return resultList;
    }

    /**
     * 取某一时段某一部门的工作明细
     *
     * @param param 开始时间/截止时间/bmid为须要的字段
     * @return java.lang.Object
     * @date 2019.03.13 10:07
     * @author chenyucheng
     */
    @PostMapping(value = "/bm/mx")
    public Object listGzltjBmMx(@RequestBody GzltjQO param) throws UnsupportedEncodingException {
        String kssj = param.getKssj();
        String jzsj = param.getJzsj();
        if(StringUtils.isNotEmpty(kssj)){
            param.setKssj(kssj.replace("AA"," "));
        }
        if(StringUtils.isNotEmpty(jzsj)){
            param.setJzsj(jzsj.replace("AA"," "));
        }
        List<OrgTaskStatisticsInfo> resultList = new ArrayList<>();
        List infoList = new ArrayList();
        try {
            if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsOfAllOrgs(stTime,edTime,null,false,null,CommonConstantUtils.JDZDMAP.get(param
                        .getJdmc()),param.getStatisticsType());
            }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsOfAllOrgs(null,edTime,null,false,null,CommonConstantUtils.JDZDMAP.get(param
                        .getJdmc()),param.getStatisticsType());
            }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsOfAllOrgs(stTime,null,null,false,null,CommonConstantUtils.JDZDMAP.get(param
                        .getJdmc()),param.getStatisticsType());
            }else{
                infoList = taskClient.getStatisticsOfAllOrgs(null,null,null,false,null,CommonConstantUtils.JDZDMAP.get(param
                        .getJdmc()),param.getStatisticsType());
            }
        } catch (Exception e) {
            LOGGER.error(ERROR_INTERFACE, e.getMessage());
        }

        Iterator iterator = infoList.iterator();
        OrgTaskStatisticsInfo tempObject;
        // 打印是的勾选过滤
        JSONArray ja = new JSONArray();
        if(StringUtils.isNotEmpty(param.getPrintFilterJson())){
            ja = JSON.parseArray(URLDecoder.decode(param.getPrintFilterJson(),ENCODING));
        }
        while (iterator.hasNext()) {
            tempObject = (OrgTaskStatisticsInfo) iterator.next();
            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
            boolean orgNameFilterFlag =  StringUtils.isNotBlank(param.getBmid())
                    && param.getBmid().indexOf(tempObject.getOrgId()) < 0;
            if(procKeyFilterFlag || orgNameFilterFlag){
                continue;
            }
            if (StringUtils.isNotBlank(tempObject.getOrgName())) {
                if(StringUtils.isEmpty(param.getBmid())){
                    resultList.add(tempObject);
                }else{
                    if(param.getBmid().indexOf(tempObject.getOrgId())>-1){
                        resultList.add(tempObject);
                    }
                }
            }
        }
        if(EXPORTALL.equals(param.getType())){//导出全部
            return super.addLayUiCode(new GTAutoConfiguration.DefaultPageImpl(resultList, 0, MAX_COUNT, resultList.size()));
        }
        if(param.getType().indexOf(PRINT)!=-1){//打印
            List<OrgTaskStatisticsInfo> list =  new GTAutoConfiguration.DefaultPageImpl(resultList, 0, MAX_COUNT, resultList.size())
                    .getContent();
            // 调用打印配置服务
            List<Map<String,String>> listMap = new ArrayList<>();
            Map<String,String> map;
            // json对象转成jsonstr 接口不支持，手动把数据组装成listmap
            for(int i=0;i<list.size();i++){
                Map filterMap = null;
                OrgTaskStatisticsInfo otsi = list.get(i);
                if(!CollectionUtils.isEmpty(ja)){// 打印勾选
                    // 打印勾选的，吧勾选的数据作为过滤参数，过滤勾选的数据
                    for(int j = 0;j<ja.size();j++){
                        filterMap = (Map)ja.get(j);
                        boolean orgIdFlag = filterMap.get(ORG_ID).equals(otsi.getOrgId());
                        boolean proKeyFlag = filterMap.get(PROCESS_KEY).equals(otsi.getProcDefKey());
                        boolean taskNameFlag = CommonConstantUtils.JDZDMAP.get(filterMap.get(TASK_NAME)).equals(otsi
                                .getTaskName());
                        if(orgIdFlag && proKeyFlag && taskNameFlag){
                            map = new HashMap<>();
                            map.put(ORG_NAME,otsi.getOrgName());
                            map.put(PROCESS_NAME,otsi.getProcDefName());
                            map.put(TASK_NAME,otsi.getTaskName());
                            int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?otsi.getRealCount():otsi.getCount();
                            map.put(REAL_COUNT,realCountTemp+"");
                            map.put(ORG_ID,i+"");
                            listMap.add(map);
                        }
                    }
                }else{
                    map = new HashMap<>();
                    map.put(ORG_NAME,otsi.getOrgName());
                    map.put(PROCESS_NAME,otsi.getProcDefName());
                    map.put(TASK_NAME,otsi.getTaskName());
                    int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?otsi.getRealCount():otsi.getCount();
                    map.put(REAL_COUNT,realCountTemp+"");
                    map.put(ORG_ID,i+"");
                    listMap.add(map);
                }
            }
            List<BdcDysjDTO> bdcDysjDTOList = GzltjCommonUtils.makePrintData(listMap,GzltjCommonUtils.BMMXGZL_MODEL);
            return bdcPrintFeignService.printDatas(bdcDysjDTOList);
        }
        // 排序
        String dimension = param.getDimension();
        GzltjCommonUtils.compareByCols(dimension, resultList,"");
        return super.addLayUiCode(PageUtils.listToPage(resultList, new PageRequest(param.getPage()-1, param.getSize(),null)));
    }

    /**
     * 取某一时段部门所有员工的工作记录
     *
     * @param param 开始时间/截止时间/ryid为须要的字段
     * @return java.lang.Object
     * @date 2019.03.13 10:05
     * @author chenyucheng
     */
    @PostMapping(value = "/ry/mx")
    public Object listGzltjBmryMx(@RequestBody GzltjQO param) throws UnsupportedEncodingException {
        String kssj = param.getKssj();
        String jzsj = param.getJzsj();
        param.setKssj(kssj.replace("AA"," "));
        param.setJzsj(jzsj.replace("AA"," "));
        List<Object> resultList = new ArrayList<>();
        List infoList = new ArrayList();
        try {
            String bmid = param.getBmid();
            if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(
                        stTime,edTime,
                        bmid, null, CommonConstantUtils.JDZDMAP.get(param.getJdmc()),param.getStatisticsType());
            }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(
                        null,edTime,
                        bmid, null, CommonConstantUtils.JDZDMAP.get(param.getJdmc()),param.getStatisticsType());
            }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(
                        stTime,null,
                        bmid, null, CommonConstantUtils.JDZDMAP.get(param.getJdmc()),param.getStatisticsType());
            }else{
                infoList = taskClient.getStatisticsByOrg(
                        null,null,
                        bmid, null, CommonConstantUtils.JDZDMAP.get(param.getJdmc()),param.getStatisticsType());
            }
        } catch (Exception e) {
            LOGGER.error(ERROR_INTERFACE, e.getMessage());
        }
        String ryid = param.getRyid();
        Iterator iterator = infoList.iterator();
        UserTaskStatisticsInfo tempObject;
        JSONArray ja = new JSONArray();
        if(StringUtils.isNotEmpty(param.getPrintFilterJson())){
            ja = JSON.parseArray(URLDecoder.decode(param.getPrintFilterJson(),ENCODING));
        }
        while (iterator.hasNext()) {
            tempObject = (UserTaskStatisticsInfo) iterator.next();

            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && URLDecoder.decode(param.getProcess(),ENCODING).indexOf(tempObject.getProcDefKey()) < 0;
            if(procKeyFilterFlag){
                continue;
            }
            if(StringUtils.isNotBlank(ryid)){
                String paramRyid = URLDecoder.decode(ryid,ENCODING);
                String resRyid = URLDecoder.decode(tempObject.getUsername(),ENCODING);
                if (StringUtils.isNotBlank(tempObject.getUsername())
                        && StringUtils.isBlank(ryid) || paramRyid.indexOf(resRyid) > -1) {
                    resultList.add(tempObject);
                }
            }else{
                resultList.add(tempObject);
            }

        }
        if(param.getType().indexOf(EXPORTALL)!=-1){//导出全部
            return super.addLayUiCode(new GTAutoConfiguration.DefaultPageImpl(resultList, 0, MAX_COUNT, resultList.size()));
        }
        if(param.getType().indexOf(PRINT)!=-1){//打印
            List<UserTaskStatisticsInfo> list =  new GTAutoConfiguration.DefaultPageImpl(resultList, 0, MAX_COUNT, resultList.size())
                    .getContent();
            List<Map<String,String>> listMap = new ArrayList<>();
            Map<String,String> map;
            for(int i=0;i<list.size();i++){
                Map filterMap = null;
                UserTaskStatisticsInfo otsi = list.get(i);
                if(!CollectionUtils.isEmpty(ja)){// 打印勾选
                    for(int j = 0;j<ja.size();j++){
                        filterMap = (Map)ja.get(j);
                        boolean orgIdFlag = filterMap.get(ORG_ID).equals(otsi.getOrgId());
                        boolean proKeyFlag = filterMap.get(PROCESS_KEY).equals(otsi.getProcDefKey());
                        boolean taskNameFlag = CommonConstantUtils.JDZDMAP.get(filterMap.get(TASK_NAME)).equals(otsi
                                .getTaskName());
                        boolean userNameFlag = filterMap.get(USERNAME).equals(otsi.getUsername());
                        if(orgIdFlag && proKeyFlag && taskNameFlag && userNameFlag){
                            map = new HashMap<>();
                            map.put(USERNAME,otsi.getUsername());
                            map.put(USER_ALIAS,otsi.getUserAlias());
                            map.put(ORG_NAME,otsi.getOrgName());
                            map.put(PROCESS_NAME,otsi.getProcDefName());
                            map.put(TASK_NAME,otsi.getTaskName());
                            int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?otsi.getRealCount():otsi.getCount();

                            map.put(REAL_COUNT,realCountTemp+"");
                            map.put(ORG_ID,i+"");
                            listMap.add(map);
                        }
                    }
                }else{
                    map = new HashMap<>();
                    map.put(USERNAME,otsi.getUsername());
                    map.put(USER_ALIAS,otsi.getUserAlias());
                    map.put(ORG_NAME,otsi.getOrgName());
                    map.put(PROCESS_NAME,otsi.getProcDefName());
                    map.put(TASK_NAME,otsi.getTaskName());
                    int realCountTemp = StringUtils.equals(REAL_COUNT,realCount)?otsi.getRealCount():otsi.getCount();

                    map.put(REAL_COUNT,realCountTemp+"");
                    map.put(ORG_ID,i+"");
                    listMap.add(map);
                }
            }
            List<BdcDysjDTO> bdcDysjDTOList = GzltjCommonUtils.makePrintData(listMap,GzltjCommonUtils.RYMXGZL_MODEL);
            return bdcPrintFeignService.printDatas(bdcDysjDTOList);
        }
        return super.addLayUiCode(PageUtils.listToPage(resultList, new PageRequest(param.getPage()-1, param.getSize(),null)));
    }

    /**
     * 部门人员明细
     * @param gzltjQO
     * @return
     */
    @GetMapping("/list/bmry")
    public List listBmryxx(GzltjQO gzltjQO) {
        List<Map> infoList = listGzltjBmCount(gzltjQO);
        if (CollectionUtils.isNotEmpty(infoList)) {
            List ryList = new ArrayList();
            JSONObject tempObject;
            Map tempMap;
            Iterator iterator = infoList.iterator();
            while (iterator.hasNext()) {
                tempMap = (Map) iterator.next();
                if (tempMap != null && tempMap.containsKey(USERNAME)
                        && StringUtils.isNotBlank(tempMap.get(USERNAME).toString())) {
                    tempObject = new JSONObject();
                    tempObject.put("DM", tempMap.get(USERNAME));
                    tempObject.put("MC", tempMap.get(USER_ALIAS));
                    ryList.add(tempObject);
                }
            }
            return ryList;
        }
        return new ArrayList();
    }

    /**
     * 通过部门id获取部门下的人员
     * @param orgId
     * @param pageable
     * @return
     */
    @GetMapping(value = "/bmusers")
    public Object listGzltjCount(String orgId,@LayuiPageable Pageable pageable) {
        if(StringUtils.isEmpty(orgId)){
            return new ArrayList();
        }
        List<UserDto> users = new ArrayList<>();
        if(orgId.indexOf(CommonConstantUtils.ZF_YW_DH) == -1){
            users = organizationManagerClient.listUsersByOrg(orgId);
        }else{
            for(int i = 0;i<orgId.split(CommonConstantUtils.ZF_YW_DH).length;i++){
                List<UserDto> tempUsers = organizationManagerClient.listUsersByOrg(orgId.split(CommonConstantUtils.ZF_YW_DH)[i]);
                if(CollectionUtils.isNotEmpty(tempUsers)){
                    users.addAll(tempUsers);
                }
            }
        }
        List ryList = new ArrayList();
        JSONObject tempObject;
        Iterator iterator = users.iterator();
        while (iterator.hasNext()) {
            UserDto dto = (UserDto)iterator.next();
            if (dto != null ) {
                tempObject = new JSONObject();
                tempObject.put("DM", dto.getUsername());
                tempObject.put("MC", dto.getAlias());
                ryList.add(tempObject);
            }
        }
        return ryList;
    }

    /**
     * 排序办件量
     * @param list
     */
    private static List sortBjl(List list){
        Map map = new HashMap();
        for(int i=0;i<list.size();i++){
            Map temp = (Map)list.get(i);
            String key = "SLR";
            if(temp.containsKey("GZLDYMC")){
                key = "GZLDYMC";
            }else if(temp.containsKey("DJJG")){
                key = "DJJG";
            }
            if(map.containsKey(temp.get(key))){
                int count = Integer.parseInt(map.get(temp.get(key)).toString());
                count += Integer.parseInt(temp.get("REALCOUNT").toString());
                map.put(temp.get(key),count);
            }else{
                map.put(temp.get(key),Integer.parseInt(temp.get("REALCOUNT").toString()));
            }
        }

        // 转换为list
        List<HashMap.Entry<String, Integer>> listRes = new ArrayList<HashMap.Entry<String, Integer>>(map.entrySet());
        // 对map进行排序
        listRes.sort(new Comparator<HashMap.Entry<String, Integer>>() {
            @Override
            public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List listData = new ArrayList();
        for(int i=0;i<listRes.size();i++){
            HashMap.Entry<String, Integer> entry = (HashMap.Entry<String, Integer>)(listRes.get(i));
            String key = entry.getKey();
            for(int j=0;j<list.size();j++){
                Map<String,Object> tempData = (Map)(list.get(j));
                String keyData = "";
                for(Object keyV : tempData.keySet()){
                    keyData = keyV.toString();
                    if(StringUtils.isNotBlank(key) && null != tempData.get(keyData) && key.equals(tempData.get(keyData).toString())){
                        listData.add(list.get(j));
                    }
                }

            }
        }
        return listData;
    }


    /**
     * 取某一时段部门所有员工的工作记录
     *
     * @param param 开始时间/截止时间
     * @return java.lang.Object
     * @date 2020.07.17 10:05
     * @author chenyucheng
     */
    @GetMapping(value = "/ry/mx/all")
    public Object listGzltjBmryAllMx(GzltjQO param, @LayuiPageable Pageable pageable, HttpServletResponse response) throws UnsupportedEncodingException {
        String kssj = param.getKssj();
        String jzsj = param.getJzsj();
        List<UserTaskStatisticsInfo> resultList = new ArrayList<>();
        List infoListTemp = new ArrayList();
        List infoList = new ArrayList();
        try {
            if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(stTime,edTime, "", null, "","RUN_STATISTIC");
                infoListTemp = taskClient.getStatisticsByOrg(stTime,edTime, "", null, "","END_STATISTIC");
            }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(null,edTime, "", null, "","RUN_STATISTIC");
                infoListTemp = taskClient.getStatisticsByOrg(null,edTime, "", null, "","END_STATISTIC");
            }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                infoList = taskClient.getStatisticsByOrg(stTime,null, "", null, "","RUN_STATISTIC");
                infoListTemp = taskClient.getStatisticsByOrg(stTime,null, "", null, "","END_STATISTIC");
            }else{
                infoList = taskClient.getStatisticsByOrg(null,null, "", null, "","RUN_STATISTIC");
                infoListTemp = taskClient.getStatisticsByOrg(null,null, "", null, "","END_STATISTIC");
            }
        } catch (Exception e) {
            LOGGER.error(ERROR_INTERFACE, e.getMessage());
        }
        infoList.addAll(infoListTemp);
        String ryid = param.getRyid();
        Iterator iterator = infoList.iterator();
        UserTaskStatisticsInfo tempObject;
        JSONArray ja = new JSONArray();
        if(StringUtils.isNotEmpty(param.getPrintFilterJson())){
            ja = JSON.parseArray(URLDecoder.decode(param.getPrintFilterJson(),ENCODING));
        }
        while (iterator.hasNext()) {
            tempObject = (UserTaskStatisticsInfo) iterator.next();

            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && URLDecoder.decode(param.getProcess(),ENCODING).indexOf(tempObject.getProcDefKey()) < 0;
            if(procKeyFilterFlag){
                continue;
            }
            String paramRyid = URLDecoder.decode(ryid,ENCODING);
            String resRyid = URLDecoder.decode(tempObject.getUsername(),ENCODING);
            if (StringUtils.isNotBlank(tempObject.getUsername())
                    && StringUtils.isBlank(ryid) || paramRyid.indexOf(resRyid) > -1) {
                resultList.add(tempObject);
            }
        }

        // 当没有条件 或者选择了查询出证 的条件是 需要把查询出证的数据增加进去
        if(StringUtils.isBlank(param.getProcess()) || param.getProcess().indexOf("cxcz") != -1){
            BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO = new BdcZhcxDyzmTjQO();
            bdcZhcxDyzmTjQO.setRymc(param.getSlrmc());
            bdcZhcxDyzmTjQO.setKssj(param.getKssj());
            bdcZhcxDyzmTjQO.setJzsj(param.getJzsj());
            bdcZhcxDyzmTjQO.setTjwd("alias");
            bdcZhcxDyzmTjQO.setPrintTypes(Arrays.asList(param.getPrintTypeList().split(",")));
            BdcZhcxDyzmTjDTO bdcZhcxDyzmTjDTO = bdcZhcxTjFeignService.zhcxDyzmTj(bdcZhcxDyzmTjQO);

            List<UserTaskStatisticsInfo> listZhcx = new ArrayList<>();

            if(null != bdcZhcxDyzmTjDTO && CollectionUtils.isNotEmpty(bdcZhcxDyzmTjDTO.getKeySet()) ){

                Map<String, List<Integer>> valueMap = bdcZhcxDyzmTjDTO.getValueMap();
                for(String key : valueMap.keySet()){
                    List<Integer> listCount = valueMap.get(key);
                    if(CollectionUtils.isNotEmpty(listCount)){
                        int count = 0;
                        for (String name : bdcZhcxDyzmTjDTO.getKeySet()) {
                            if(listCount.get(count) > 0){
                                UserTaskStatisticsInfo userTaskStatisticsInfo = new UserTaskStatisticsInfo();
                                userTaskStatisticsInfo.setUsername(name);
                                userTaskStatisticsInfo.setUserAlias(name);
                                userTaskStatisticsInfo.setTaskName("查询出证");
                                userTaskStatisticsInfo.setRealCount(listCount.get(count));
                                userTaskStatisticsInfo.setProcDefName(key);
                                listZhcx.add(userTaskStatisticsInfo);
                            }
                            count++;
                        }
                    }
                }
            }

            resultList.addAll(listZhcx);
        }

        // 先按人员 再按节点排序
        Comparator<UserTaskStatisticsInfo> byAlias = Comparator.comparing(UserTaskStatisticsInfo::getUserAlias);
        Comparator<UserTaskStatisticsInfo> byTaskName = Comparator.comparing(UserTaskStatisticsInfo::getTaskName).reversed();
        resultList.sort(byAlias.thenComparing(byTaskName));

        // 导出全部和查询公用一个，区分字段
        if(param.getType().indexOf(EXPORTALL) != -1){//导出全部
            return resultList;
        }

        return super.addLayUiCode(PageUtils.listToPage(resultList, pageable));
    }

}