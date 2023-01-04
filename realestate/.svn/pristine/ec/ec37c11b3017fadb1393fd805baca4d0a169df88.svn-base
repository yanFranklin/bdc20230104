package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.domain.statistics.UserTaskStatisticsTimeDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlqkDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.SlqktjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGzlTjFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskStatisticsClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.util.GzltjCommonUtils;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: <a href="@mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version: V1.0, 2019.03.12
 * @description: 工作量统计前端控制层, 主要是调用平台接口获取数据并组织
 */
@RestController
@RequestMapping("/slqktj")
public class BdcSlqktjController extends BaseController {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String USERNAME = "username";
    private static final String COUNT = "count";
    private static final String REAL_COUNT = "realCount";
    private static final String ORG_NAME = "orgName";
    private static final int MAX_COUNT = 9999;
    private static final String  EXPORTALL = "exportAll";
    private static final String ERROR_INTERFACE = "调用平台接口出错，错误原因：{}";
    @Autowired
    private BdcGzlTjFeignService bdcGzlTjFeignService;
    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private TaskStatisticsClientMatcher taskClient;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    /**
     * 字典服务
     */
    @Autowired
    private BdcZdController bdcZdController;
    @Autowired
    WorkFlowUtils workFlowUtils;

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.03.12 13:39
     * @author chenyucheng
     */
    @GetMapping(value = "/slry/count")
    public Object listSlryCount(SlqktjQO param) {
        List infoList = new ArrayList();
        List infoListTemp = new ArrayList();
        List<String> jdmcList = new ArrayList<>();
        if(param.getJdmc() != null){
            jdmcList = Arrays.asList(param.getJdmc().split(","));
        }else{
            jdmcList = new ArrayList<>();
            jdmcList.add("");
        }
        String jdmcTemp = "";
        for(int i=0;i<jdmcList.size();i++){
            jdmcTemp = jdmcList.get(i);
            try {
                if(StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj()) ){
                    long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                    long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(stTime,edTime,null,null,CommonConstantUtils.JDZDMAP.get
                            (jdmcTemp));
                }else if(!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())){
                    long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(null,edTime,null,null,CommonConstantUtils.JDZDMAP.get(jdmcTemp));
                }else if(StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())){
                    long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(stTime,null,null,null,CommonConstantUtils.JDZDMAP.get
                            (jdmcTemp));
                }else{
                    infoListTemp = taskClient.getUserStatisticsTime(null,null,null,null,CommonConstantUtils.JDZDMAP.get(jdmcTemp));
                }
                infoList.addAll(infoListTemp);
            } catch (Exception e) {
                LOGGER.error(ERROR_INTERFACE, e.getMessage());
            }
        }


        List<Map> resultList = new ArrayList<>();
        // 用于后续判断当前部门是否已经统计过
        Map<String,String> bmsMap = new HashMap<>();
        Map<String, Object> tempMap;
        String dimension = param.getDimension();
        for (int i = 0; i < infoList.size(); i++) {
            UserTaskStatisticsTimeDto tempObject = (UserTaskStatisticsTimeDto) infoList.get(i);
            // 分组的key，合肥项目组 - 32 中的 合肥项目组
            /**
             * 因为接口不支持流程部门入参，这里根据流程,部门内存过滤
             * 1.判空 2.比对。这里可以给大云提需求，入参加入bm和流程入参过滤
             * 第一次过滤 过滤不满足的条件的
             */
            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
            boolean orgNameFilterFlag =  StringUtils.isNotBlank(param.getDjjg())
                    && param.getDjjg().indexOf(tempObject.getOrgName().split(",")[0]) < 0;
            boolean slrmcFilterFlag =  StringUtils.isNotBlank(param.getSlrmc())
                    && param.getSlrmc().indexOf(tempObject.getUserAlias()) < 0;
            if(procKeyFilterFlag || orgNameFilterFlag || slrmcFilterFlag){
                continue;
            }
            String dimensionStr = tempObject.getUserAlias();
            if (!bmsMap.containsKey(dimensionStr) && StringUtils.isNotEmpty(dimensionStr)) {
                bmsMap.put(dimensionStr,dimensionStr);
                tempMap = Maps.newHashMap();
                tempMap.put(USERNAME, tempObject.getUsername());
                tempMap.put(ORG_NAME, tempObject.getUserAlias());
                tempMap.put("sum", 0L);
                tempMap.put("avg", tempObject.getAvg());
                tempMap.put(COUNT, 0);

                // 循环累积当前部门的realCount到hashMap中
                Iterator iterator = infoList.iterator();
                while (iterator.hasNext()) {
                    tempObject = (UserTaskStatisticsTimeDto) iterator.next();
                    /**
                     * 因为接口不支持流程部门入参，这里根据流程,部门内存过滤
                     * 1.判空 2.比对。这里可以给大云提需求，入参加入bm和流程入参过滤
                     * 第二次过滤，是为了去掉所有其他类型的为空的echart柱子
                     */
                     procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                            && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
                     orgNameFilterFlag =  StringUtils.isNotBlank(param.getDjjg())
                            && param.getDjjg().indexOf(tempObject.getOrgName().split(",")[0])<0;
                     slrmcFilterFlag =  StringUtils.isNotBlank(param.getSlrmc())
                            && param.getSlrmc().indexOf(tempObject.getUserAlias()) < 0;
                    if(procKeyFilterFlag || orgNameFilterFlag || slrmcFilterFlag){
                        continue;
                    }
                    // 统计维度
                    String dimensionEqualStr = tempObject.getUserAlias();

                    if (StringUtils.equals(dimensionStr, dimensionEqualStr)) {
                        tempMap.put(COUNT, (int) tempMap.get(COUNT) + tempObject.getRealCount());
                        tempMap.put("sum", (long) tempMap.get("sum") + tempObject.getSum());
                    }
                }
                resultList.add(tempMap);
            }
        }
        // 排序
        GzltjCommonUtils.compareByCols(dimension,resultList,ORG_NAME);
        return resultList;
    }

    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.06.6 13:39
     * @author chenyucheng
     */
    @GetMapping(value = "/slqk/count")
    public Object listSlqkCount(SlqktjQO param) {
        List list = bdcGzlTjFeignService.listSlqkCount(JSON.toJSONString(param));
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for(int i =0;i<list.size();i++){
            Map map = (Map)list.get(i);
            if(map.containsKey("DJXL")){
                map.put("DJXLMC",StringToolUtils.convertBeanPropertyValueOfZd(
                        Integer.parseInt(map.get("DJXL").toString()), zdMap.get(CommonConstantUtils.DJXL_ZD)));
            }
            if(map.containsKey("DJYY")){
                map.put("DJYYMC",map.get("DJYY"));
            }
        }
        return list;
    }


    /**
     * 统计所有部门的办件量信息
     *
     * @param param 包含起始时间、截至时间等，为一个qo对象
     * @return java.lang.Object 返回list，包含部门id和任务数量
     * @date 2019.06.6 13:39
     * @author chenyucheng
     */
    @GetMapping(value = "/slqk/mx")
    public Object listSlqkMx( @LayuiPageable Pageable pageable,SlqktjQO param) {
        Page<BdcSlqkDTO> page = bdcGzlTjFeignService.listSlqkMx(JSON.toJSONString(param),pageable);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for(int i =0;i<page.getContent().size();i++){
            BdcSlqkDTO dto = page.getContent().get(i);
            dto.setAjzt(StringToolUtils.convertBeanPropertyValueOfZd(
                        Integer.parseInt(dto.getAjzt()), zdMap.get(CommonConstantUtils.AJZT_ZD)));
        }
        return super.addLayUiCode(page);
    }



    /**
     * 取某一时段某一部门的工作明细
     *
     * @param param 开始时间/截止时间/bmid为须要的字段
     * @return java.lang.Object
     * @date 2019.03.13 10:07
     * @author chenyucheng
     */
    @GetMapping(value = "/slry/mx")
    public Object listGzltjBmMx(SlqktjQO param, @LayuiPageable Pageable pageable){
        String kssj = param.getKssj();
        String jzsj = param.getJzsj();
        List<UserTaskStatisticsTimeDto> resultList = new ArrayList<>();
        List infoList = new ArrayList();
        List infoListTemp = new ArrayList();
        List<String> jdmcList = new ArrayList<>();
        if(param.getJdmc() != null){
            jdmcList = Arrays.asList(param.getJdmc().split(","));
        }else{
            jdmcList = new ArrayList<>();
            jdmcList.add("");
        }
        String jdmc = "";
        for(int i=0;i<jdmcList.size();i++) {
            jdmc = jdmcList.get(i);
            try {
                if (StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())) {
                    long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                    long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(stTime, edTime, null, null, CommonConstantUtils.JDZDMAP.get(jdmc));
                } else if (!StringUtils.isNotEmpty(param.getKssj()) && StringUtils.isNotEmpty(param.getJzsj())) {
                    long edTime = DateUtils.parseDate(param.getJzsj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(null, edTime, null, null, CommonConstantUtils.JDZDMAP.get(jdmc));
                } else if (StringUtils.isNotEmpty(param.getKssj()) && !StringUtils.isNotEmpty(param.getJzsj())) {
                    long stTime = DateUtils.parseDate(param.getKssj(), DATE_FORMAT).getTime();
                    infoListTemp = taskClient.getUserStatisticsTime(stTime, null, null, null, CommonConstantUtils.JDZDMAP.get(jdmc));
                } else {
                    infoListTemp = taskClient.getUserStatisticsTime(null, null, null, null, CommonConstantUtils.JDZDMAP.get(jdmc));
                }
                infoList.addAll(infoListTemp);
            } catch (Exception e) {
                LOGGER.error(ERROR_INTERFACE, e.getMessage());
            }
        }
        Iterator iterator = infoList.iterator();
        UserTaskStatisticsTimeDto tempObject;

        while (iterator.hasNext()) {
            tempObject = (UserTaskStatisticsTimeDto) iterator.next();
            boolean procKeyFilterFlag = StringUtils.isNotBlank(param.getProcess())
                    && param.getProcess().indexOf(tempObject.getProcDefKey()) < 0;
            boolean orgNameFilterFlag =  StringUtils.isNotBlank(param.getDjjg())
                    && param.getDjjg().indexOf(tempObject.getOrgName().split(",")[0]) < 0;
            boolean slrmcFilterFlag =  StringUtils.isNotBlank(param.getSlrmc())
                    && param.getSlrmc().indexOf(tempObject.getUserAlias()) < 0;
            if(procKeyFilterFlag || orgNameFilterFlag || slrmcFilterFlag){
                continue;
            }
            if (StringUtils.isNotBlank(tempObject.getOrgName())) {
                if(StringUtils.isEmpty(param.getBmid())){
                    resultList.add(tempObject);
                }else{
                    if(param.getBmid().indexOf(tempObject.getOrgId().split(",")[0])>-1){
                        resultList.add(tempObject);
                    }
                }
            }
        }
        if(EXPORTALL.equals(param.getType())){//导出全部
            return super.addLayUiCode(new GTAutoConfiguration.DefaultPageImpl(resultList, 0, MAX_COUNT, resultList.size()));
        }

        // 排序
        String dimension = param.getDimension();
        GzltjCommonUtils.compareByCols(dimension, resultList,"");
        return super.addLayUiCode(PageUtils.listToPage(resultList, pageable));
    }



    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return {Map} 部门代码名称Map
     * @description 获取所有的组织机构信息
     */
    @GetMapping("/bm/select")
    public List<OrganizationDto> listOrgs() {
        return bdcZdController.listOrgs();
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return {Map} 部门代码名称Map
     * @description 获取所有用户信息
     */
    @GetMapping("/ry/select")
    public List<UserDto> listUsers() {
        List<UserDto> listUser = bdcZdController.listUsers();
        listUser = listUser.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))), ArrayList::new));
        return listUser;
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: java.lang.Object
     * @description 调用大运接口获取工作流定义名称
     */
    @GetMapping("/lc/select")
    public Object queryGzldymcs(){
        List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
        processDefDataList=processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState()==1
        ).collect(Collectors.toList());
        return processDefDataList;
    }

    /**
     * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return: java.lang.Object
     * @description 字典服务
     */
    @GetMapping("/djlxAndDjxl/select")
    public Object DjlxAndDjxl(){
        return  bdcZdFeignService.listBdcZd();
    }

    /**
     * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return: java.lang.Object
     * @description 字典服务
     */
    @GetMapping("/djyy/select")
    public Object djyyList(@RequestParam("djxl") String djxl){
        List<Map> list = bdcGzlTjFeignService.djyyList(djxl);
        List<Map> listResult = new ArrayList<>();
        List<String> listDis = new ArrayList<>();
        //去重
        if(CollectionUtils.isNotEmpty(list)){
            for(Map map:list){
                if(StringUtils.isNotBlank(map.get("DJYY").toString()) && !listDis.contains(map.get("DJYY").toString())){
                    listResult.add(map);
                    listDis.add(map.get("DJYY").toString());
                }
            }
        }
        return  listResult;
    }

}