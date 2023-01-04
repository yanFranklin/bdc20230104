package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.inquiry.FccxDO;
import cn.gtmap.realestate.common.core.domain.inquiry.YhcxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxCqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcLogQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYhcxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcLogVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询 查询数据
 */
@RestController
@RequestMapping(value = "/rest/v1.0/yhcx")
public class BdcYhcxController extends BaseController {
    @Autowired
    private BdcYhcxFeignService bdcYhcxFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    /**
     * 用户服务
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 日志服务
     */
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    private LogMessageClient logMessageClient;


    /**
     * 获取机构配置信息
     *
     * @param bdcZsQO
     */
    @PostMapping("/search")
    public BdcYhcxDTO queryYhcx(@RequestBody BdcZsQO bdcZsQO) {
        BdcYhcxDTO bdcYhcxDTO = new BdcYhcxDTO();

        if (StringUtils.isBlank(bdcZsQO.getBdcqzh())) {
            throw new AppException("产权证号不能为空！");
        }
        List<BdcZsDO> bdcZsDOList = bdcYhcxFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            throw new AppException("查不到对应证书信息");
        }
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);

        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 组织银行查询数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        bdcYhcxDTO.setBdcqzh(bdcZsDO.getBdcqzh());
        bdcYhcxDTO.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get("gyfs")));
        bdcYhcxDTO.setFj(bdcZsDO.getFj());

        // 通过证书id查询项目
        List<BdcXmDO> bdcXmDOList = bdcYhcxFeignService.listBdcXmByZsid(bdcZsDO.getZsid());

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("查询不到对应的项目信息");
        }
        // 保存权利人
        bdcYhcxDTO.setQlr(bdcXmDOList.get(0).getQlr());
        // 保存权利性质
        if (bdcXmDOList.get(0).getQlxz() != null) {
            bdcYhcxDTO.setQlxz(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDOList.get(0).getQlxz()), zdMap.get("qlxz")));
        }
        if (bdcXmDOList.get(0).getQszt() == 2) {
            bdcYhcxDTO.setSfzx("是");
        } else {
            bdcYhcxDTO.setSfzx("否");
        }
        if (bdcXmDOList.get(0).getDjsj() != null) {
            bdcYhcxDTO.setDjsj(simpleDateFormat.format(bdcXmDOList.get(0).getDjsj()));
        }

        // 取产权信息
        List<String> xmidList = bdcXmDOList.stream().map(p -> p.getXmid()).collect(Collectors.toList());
        List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(xmidList);
        // 循环产权信息
        if (CollectionUtils.isEmpty(bdcQlList)) {
            throw new AppException("产权信息为空");
        }
        // 保存产权信息
        List<BdcYhcxCqDTO> bdcYhcxCqDTOList = new ArrayList<>();
        for (BdcQl bdcQl : bdcQlList) {
            BdcYhcxCqDTO bdcYhcxCqDTO = new BdcYhcxCqDTO();
            if (bdcQl instanceof BdcTdsyqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcTdsyqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcTdsyqDO) bdcQl).getZl());
            }
            if (bdcQl instanceof BdcJsydsyqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcJsydsyqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcJsydsyqDO) bdcQl).getZl());
                if (((BdcJsydsyqDO) bdcQl).getSyqjssj() != null) {
                    bdcYhcxDTO.setTdsyjssj(simpleDateFormat.format(((BdcJsydsyqDO) bdcQl).getSyqjssj()));
                }
            }
            if (bdcQl instanceof BdcFdcqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcFdcqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcFdcqDO) bdcQl).getZl());
                bdcYhcxCqDTO.setZcs(((BdcFdcqDO) bdcQl).getZcs());
                bdcYhcxCqDTO.setSzc(((BdcFdcqDO) bdcQl).getSzc());
                bdcYhcxCqDTO.setGhyt(StringToolUtils.convertBeanPropertyValueOfZd(((BdcFdcqDO) bdcQl).getGhyt(), zdMap.get("fwyt")));
                bdcYhcxCqDTO.setFwxz(StringToolUtils.convertBeanPropertyValueOfZd(((BdcFdcqDO) bdcQl).getFwxz(), zdMap.get("fwxz")));
                bdcYhcxCqDTO.setJzmj(((BdcFdcqDO) bdcQl).getJzmj());
                bdcYhcxCqDTO.setTdsyqmj(((BdcFdcqDO) bdcQl).getTdsyqmj());
                if (((BdcFdcqDO) bdcQl).getTdsyjssj() != null) {
                    bdcYhcxDTO.setTdsyjssj(simpleDateFormat.format(((BdcFdcqDO) bdcQl).getTdsyjssj()));
                }
            }
            if (bdcQl instanceof BdcFdcq3DO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcFdcq3DO) bdcQl).getBdcdyh());
            }
            if (bdcQl instanceof BdcGjzwsyqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcGjzwsyqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcGjzwsyqDO) bdcQl).getZl());
                if (((BdcGjzwsyqDO) bdcQl).getTdhysyjssj() != null) {
                    bdcYhcxDTO.setTdsyjssj(simpleDateFormat.format(((BdcGjzwsyqDO) bdcQl).getTdhysyjssj()));
                }
            }
            if (bdcQl instanceof BdcHysyqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcHysyqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcHysyqDO) bdcQl).getZl());
                if (((BdcHysyqDO) bdcQl).getSyqjssj() != null) {
                    bdcYhcxDTO.setTdsyjssj(simpleDateFormat.format(((BdcHysyqDO) bdcQl).getSyqjssj()));
                }
            }
            if (bdcQl instanceof BdcTdcbnydsyqDO) {
                bdcYhcxCqDTO.setBdcdyh(((BdcTdcbnydsyqDO) bdcQl).getBdcdyh());
                bdcYhcxCqDTO.setZl(((BdcTdcbnydsyqDO) bdcQl).getZl());
                if (((BdcTdcbnydsyqDO) bdcQl).getCbjssj() != null) {
                    bdcYhcxDTO.setTdsyjssj(simpleDateFormat.format(((BdcTdcbnydsyqDO) bdcQl).getCbjssj()));
                }
            }
            bdcYhcxCqDTOList.add(bdcYhcxCqDTO);
        }
        bdcYhcxDTO.setBdcYhcxCqDTOList(bdcYhcxCqDTOList);

        // 组合不动产单元号
        Set<String> bdcdyhSet = bdcXmDOList.stream().map(p -> p.getBdcdyh()).collect(Collectors.toSet());
        List<String> bdcdyhList = new ArrayList<>(bdcdyhSet);
        // 查询限制权利
        List<BdcYhcxDyaDTO> bdcDyaqDOList = bdcYhcxFeignService.listBdcDyaqByBdcdyh(bdcdyhList);
        List<BdcCfDO> bdcCfDOList = bdcYhcxFeignService.listBdcCfByBdcdyh(bdcdyhList);
        List<BdcYyDO> bdcYyDOList = bdcYhcxFeignService.listBdcYyByBdcdyh(bdcdyhList);
        List<String> sdxxList = bdcYhcxFeignService.listBdcSdxxByBdcdyh(bdcdyhList);

        // 配置限制权利信息
        if (CollectionUtils.isEmpty(bdcDyaqDOList)) {
            bdcDyaqDOList.add(new BdcYhcxDyaDTO());
        } else {
            for (BdcYhcxDyaDTO bdcYhcxDyaDTO : bdcDyaqDOList) {
                bdcYhcxDyaDTO.setDyfs(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcYhcxDyaDTO.getDyfs()), zdMap.get("dyfs")));
            }
        }
        // 查封信息
        if (CollectionUtils.isEmpty(bdcCfDOList)) {
            bdcCfDOList.add(new BdcCfDO());
        }
        bdcYhcxDTO.setBdcDyaqDOList(bdcDyaqDOList);
        bdcYhcxDTO.setBdcCfDOList(bdcCfDOList);
        // 异议信息
        if (CollectionUtils.isEmpty(bdcYyDOList)) {
            bdcYhcxDTO.setYyxx("无");
        } else {
            StringBuilder yyxxBuilder = new StringBuilder();
            for (BdcYyDO bdcYyDO : bdcYyDOList) {
                yyxxBuilder.append("于");
                if (bdcYyDO.getDjsj() != null) {
                    yyxxBuilder.append(simpleDateFormat.format(bdcYyDO.getDjsj()));
                }
                yyxxBuilder.append("被 异议，异议时间从");
                if (bdcYyDO.getYydjksrq() != null) {
                    yyxxBuilder.append(simpleDateFormat.format(bdcYyDO.getYydjksrq()));
                }
                yyxxBuilder.append(" 到 ");
                if (bdcYyDO.getYydjjsrq() != null) {
                    yyxxBuilder.append(simpleDateFormat.format(bdcYyDO.getYydjjsrq()));
                }
            }
            bdcYhcxDTO.setYyxx(yyxxBuilder.toString());
        }
        // 锁定信息
        if (CollectionUtils.isNotEmpty(sdxxList)) {
            StringBuilder sdxxBuilder = new StringBuilder();
            for (String sdxx : sdxxList) {
                sdxxBuilder.append(sdxx).append(" ");
            }
            bdcYhcxDTO.setSdxx(sdxxBuilder.toString());
        }

        return bdcYhcxDTO;
    }


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param logInfoMap 日志参数信息
     * @description  保存日志信息
     */
    @PostMapping(value = "/saveLog")
    public void saveLogInfo(@RequestBody Map<String, Object> logInfoMap) {
        if(MapUtils.isEmpty(logInfoMap) || StringUtils.isBlank(MapUtils.getString(logInfoMap, "logType"))){
            LOGGER.error("保存日志信息中止，因为日志参数信息为空");
            return;
        }

        // 获取用户名称、账号
        String userAlias = MapUtils.getString(logInfoMap, "userAlias");
        String userName = MapUtils.getString(logInfoMap, "userName");

        if(StringUtils.isBlank(userAlias) || StringUtils.isBlank(userName)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            userAlias = userDto == null ? "" : userDto.getAlias();
            logInfoMap.put(CommonConstantUtils.ALIAS, userAlias);

            userName = userDto == null ? "" : userDto.getUsername();
            logInfoMap.put("userName", userName);

            if(null != userDto){
                String org = userManagerUtils.getOrganizationByUserName(userDto.getUsername());
                logInfoMap.put(CommonConstantUtils.ORGANIZATION,org);
            }
        }

        try {
            String logType = MapUtils.getString(logInfoMap, "logType");
            zipkinAuditEventRepository.newSpanTag(new AuditEvent(userName, logType, logInfoMap), logType);
        }catch(Exception e){
            LOGGER.error("保存日志信息出错：{}", e.getMessage());
        }
    }


    /**
     * 统计银行查询log
     * @param pageable
     * @param bdcLogQO
     * @return
     */
    @PostMapping(value = "/countLog")
    public Object logListByPage(BdcLogQO bdcLogQO) {
        List<BdcLogVO> logList = Lists.newArrayList();
        Long begin = null;
        Long end = null;
        if(bdcLogQO.getBeginTime() != null) {
            begin = bdcLogQO.getBeginTime().getTime();
        }
        if(bdcLogQO.getEndTime() != null) {
            end = bdcLogQO.getEndTime().getTime();
        }
        if(StringUtils.isNotBlank(bdcLogQO.getAlias()) && StringUtils.isNotBlank(bdcLogQO.getOrganization())){
            // 两个都有值得情况下 只取用户名为条件
            bdcLogQO.setOrganization("");
        }

        Page<AuditLogDto> auditLogDtoPage = null;
        List list = new ArrayList();
        // 页面的部门和人是多选框，因为日志接口不支持多个参数，这里做循环查询
        String tjlx = CommonConstantUtils.ALIAS;
        if(StringUtils.isBlank(bdcLogQO.getAlias())){// 按部门统计
            tjlx = CommonConstantUtils.ORGANIZATION;
            String org = bdcLogQO.getOrganization();
            for(int i=0;i<org.split(",").length;i++){
                bdcLogQO.setOrganization(org.split(",")[i]);
                List<QueryLogCondition> conditionList = combineConditionList(bdcLogQO);
                auditLogDtoPage = logMessageClient.listLogs(0, 100000 , "YHCX",
                        "", null, begin, end, conditionList);
                List listT = auditLogDtoPage.getContent();
                list.addAll(listT);
            }
        }else{
            String alias = bdcLogQO.getAlias();
            for(int i=0;i<alias.split(",").length;i++){
                bdcLogQO.setAlias(alias.split(",")[i]);
                List<QueryLogCondition> conditionList = combineConditionList(bdcLogQO);
                auditLogDtoPage = logMessageClient.listLogs(0, 100000 , "YHCX",
                        "", null, begin, end, conditionList);
                List listT = auditLogDtoPage.getContent();
                list.addAll(listT);
            }
        }

        Map<String,Integer> map = new HashMap();

        if(null != auditLogDtoPage && CollectionUtils.isNotEmpty(list)){
            for(int i=0;i<list.size();i++){
                AuditLogDto log = (AuditLogDto)list.get(i);
                List<DataValue> dataValueList = log.getBinaryAnnotations();
                if(CollectionUtils.isNotEmpty(dataValueList)){
                    for(int j=0;j<dataValueList.size();j++){
                        if(tjlx.equals(dataValueList.get(j).getKey())){
                            if(map.containsKey(dataValueList.get(j).getValue())){
                                int countT = map.get(dataValueList.get(j).getValue())+1;
                                map.put(dataValueList.get(j).getValue(),countT);
                            }else{
                                map.put(dataValueList.get(j).getValue(),1);
                            }
                            break;
                        }
                    }
                }
            }
        }
        List<Map.Entry<String, Integer>> listt = new ArrayList<>(map.entrySet());
        Collections.sort(listt, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<String, Integer> returnMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : listt) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }


    /**
     * version 1.0
     * @description 组织日志查询参数条件
     * @param bdcLogQO 查询条件
     * @date 2020/5/12
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    private List<QueryLogCondition> combineConditionList(BdcLogQO bdcLogQO) {
        List<QueryLogCondition> conditionList = Lists.newArrayList();
        QueryLogCondition queryLogCondition = null;
        Object value = null;
        if(bdcLogQO != null) {
            Field[] fields = bdcLogQO.getClass().getDeclaredFields();
            // 默認模糊查詢
            String type = StringUtils.isNotBlank(bdcLogQO.getCxfs())? bdcLogQO.getCxfs():CommonConstantUtils.TYPE_LIKE;
            try {
                for(int i = 0; i < fields.length; i++) {
                    // 这些属性不放在本过滤条件 直接放在接口参数中
                    if(!StringUtils.equalsIgnoreCase("cxfs", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("beginTime", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("endTime", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("eventName", fields[i].getName())
                            && !StringUtils.equalsIgnoreCase("type", fields[i].getName())) {
                        fields[i].setAccessible(true);
                        value = fields[i].get(bdcLogQO);
                        if(value != null && StringUtils.isNotBlank(value.toString())) {
                            queryLogCondition = new QueryLogCondition();
                            if(StringUtils.equalsIgnoreCase(CommonConstantUtils.ALIAS, fields[i].getName())){
                                // alias可能是多个，这里要处理一下逻辑
                                queryLogCondition.setKey(CommonConstantUtils.ALIAS);
                            }else if(StringUtils.equalsIgnoreCase(CommonConstantUtils.ORGANIZATION, fields[i].getName())){
                                queryLogCondition.setKey(CommonConstantUtils.ORGANIZATION);

                            }else if(StringUtils.equalsIgnoreCase("cqzh", fields[i].getName())){
                                queryLogCondition.setKey(CommonConstantUtils.BDCQZH);
                            }
                            queryLogCondition.setValue(value.toString());
                            queryLogCondition.setType(type);
                            conditionList.add(queryLogCondition);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取值失败！", e);
            }
        }
        return conditionList;
    }

    /**
     * 解析银行查询的导入条件
     *
     * @param httpServletRequest 网页请求
     * @return list
     * @date 2020/05/13
     * @author chenyucheng
     */
    @PostMapping("/excel")
    public Object configFccxImportToExcel(MultipartHttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }
        InputStream fileIn = null;
        List dataList = new ArrayList();
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);
                // 创建实体类
                YhcxDO yhcxDO = new YhcxDO();
                List<YhcxDO> YhcxCxList = new ArrayList<YhcxDO>();
                Sheet dataSheet = workbook.getSheet(0);
                Field[] fieldExportDataList = YhcxDO.class.getDeclaredFields();
                // 遍历Excel
                for (int i = 1; i < dataSheet.getRows(); i++) {
                    Object dataObject = YhcxDO.class.newInstance();
                    for (int j = 0; j < fieldExportDataList.length; j++) {
                        fieldExportDataList[j].setAccessible(true);
                        String contentData = dataSheet.getCell(j, i).getContents();
                        // 判断属性的类型
                        fieldExportDataList[j].set(dataObject, contentData);
                    }
                    dataList.add(dataObject);
                }
                YhcxCxList = dataList;
            }
            return dataList;
        } catch (IOException | BiffException | IllegalAccessException | InstantiationException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
    }

}
