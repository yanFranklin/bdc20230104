package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.ZipKinDO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinInformationDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipkinExportDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.CollectorFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.ZipKinFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.inquiry.core.mapper.ZipKinMapper;
import cn.gtmap.realestate.inquiry.service.ZipKinInfromationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/06/30
 * @description
 */
@Service
public class ZipKinInformationServiceImpl implements ZipKinInfromationService {

    @Autowired
    private ZipKinMapper zipKinMapper;

    @Autowired
    private CollectorFeignService collectorFeignService;

    @Autowired
    private EntityMapper entityMapper;

    /**获取链路数量，默认500条*/
    @Value("${ZipKin.limit:500}")
    private Integer limit;

    private static final String METHOD = "method";

    private static final String CLASS = "class";

    private static final String DETAIL_CLASS = "mvc.controller.class";

    private static final String DETAIL_METHOD = "mvc.controller.method";

    public static List<String> serviceList = new ArrayList<>();

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public List<ZipKinInformationDTO> insertZipKinMsg() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date nowDate = calendar.getTime();
        long nowTimestamp = nowDate.getTime();
        calendar.add(Calendar.HOUR,-4);
        Date fourHourAgo = calendar.getTime();
        //获取4小时前的时间戳
        long fourHourAgoTimestamp = fourHourAgo.getTime();
        for (String serviceName : serviceList){
            long lookback = nowTimestamp - fourHourAgoTimestamp;
            List<List<Map<String,Object>>> zipKinInformationList = collectorFeignService.getTraces(serviceName,"all",
                    "",null,null, nowTimestamp,lookback,limit);
            List<ZipKinInformationDTO> zipKinInforResult = new ArrayList<>();
            for (int i=0;i<zipKinInformationList.size();i++){
                ZipKinInformationDTO dto = new ZipKinInformationDTO();
                if (zipKinInformationList.get(i).size()>1){
                    //获取总耗时
                    LongSummaryStatistics longSummaryStatistics = zipKinInformationList.get(i).stream()
                            .collect(Collectors.summarizingLong(o-> MapUtils.getLong(o,"duration",0L)));
                    Long sum = longSummaryStatistics.getSum();
                    dto.setSfczdgfw(true);
                    dto.setSumDuration(sum);
                    dto.setTraceId(zipKinInformationList.get(i).get(0).get("traceId").toString());
                    zipKinInforResult.add(dto);
                    continue;
                }
                dto.setTraceId(MapUtils.getString(zipKinInformationList.get(i).get(0),"traceId"));
                dto.setId(MapUtils.getString(zipKinInformationList.get(i).get(0),"id"));
                dto.setName(MapUtils.getString(zipKinInformationList.get(i).get(0),"name"));
                dto.setTimestamp(MapUtils.getDouble(zipKinInformationList.get(i).get(0),"timestamp"));
                dto.setSumDuration(MapUtils.getLong(zipKinInformationList.get(i).get(0),"duration",0L));
                dto.setDuration(MapUtils.getLong(zipKinInformationList.get(i).get(0),"duration",0L));
                dto.setBinaryAnnotations((List<Map<String, Object>>) zipKinInformationList.get(i).get(0).get("binaryAnnotations"));
                dto.setSfczdgfw(false);
                zipKinInforResult.add(dto);
            }
            //根据总耗时进行排序
            zipKinInforResult = zipKinInforResult.stream().sorted(Comparator.comparing(ZipKinInformationDTO::getSumDuration).reversed()).collect(Collectors.toList());
            //处理排序后数据并插入表中
            this.processZipkinDatas(zipKinInforResult,serviceName);
        }
        return null;
    }

    public void processZipkinDatas(List<ZipKinInformationDTO> zipKinInforResult,String serviceName){
        List<ZipKinDO> zipKinDOS = new ArrayList<>();
        int flagNum = 0;
        for (ZipKinInformationDTO zipKinDTO : zipKinInforResult){
            ZipKinDO zipKinDO = new ZipKinDO();
            if (zipKinDTO.getSfczdgfw()){
                List<Map<String,Object>> detailList = collectorFeignService.getDetailMsg(zipKinDTO.getTraceId());
                detailList = detailList.stream().sorted(Comparator.comparing(map -> MapUtils.getLong((Map) map,"duration",0L)).reversed()).collect(Collectors.toList());
                List<Map<String,Object>> annotationsList = (List<Map<String, Object>>) detailList.get(0).get("binaryAnnotations");
                String name = MapUtils.getString(detailList.get(0),"name","");
                String methodName = "";
                String className = "";
                String path = "";
                for (Map<String,Object> map : annotationsList){
                    if (DETAIL_CLASS.equals(MapUtils.getString(map,"key"))){
                        className = MapUtils.getString(map,"value");
                    }
                    if (DETAIL_METHOD.equals(MapUtils.getString(map,"key"))){
                        methodName = MapUtils.getString(map,"value");
                    }
                    if ("http.url".equals(MapUtils.getString(map,"key",""))){
                        path = MapUtils.getString(map,"value");
                    }
                }
                Integer d = (Integer) detailList.get(0).get("duration");
                zipKinDO.setSumDuration(zipKinDTO.getSumDuration());
                zipKinDO.setDuration(d.longValue());
                zipKinDO.setAppMethod(className+"."+methodName);
                zipKinDO.setId(zipKinDTO.getTraceId());
                zipKinDO.setAppService(serviceName);
                zipKinDO.setName(name);
                zipKinDO.setCreateDate(new Date());
                zipKinDO.setUrl(path);
                zipKinDOS.add(zipKinDO);
                if (++flagNum >= 100){
                    break;
                }
                continue;
            }
            zipKinDO.setDuration(zipKinDTO.getDuration());
            List<Map<String,Object>> methodList = zipKinDTO.getBinaryAnnotations().stream().filter(map -> MapUtils.getString(map,"key","").contains(METHOD)).collect(Collectors.toList());
            List<Map<String,Object>> classList = zipKinDTO.getBinaryAnnotations().stream().filter(map -> MapUtils.getString(map,"key","").contains(CLASS)).collect(Collectors.toList());
            List<Map<String,Object>> pathList = zipKinDTO.getBinaryAnnotations().stream().filter(map -> MapUtils.getString(map,"key","").contains("path")).collect(Collectors.toList());
            String method ="";
            String path = "";
            if (CollectionUtils.isNotEmpty(methodList) && CollectionUtils.isNotEmpty(classList)){
                method = classList.get(0).get("value").toString()+"."+methodList.get(0).get("value");
            }
            if (CollectionUtils.isNotEmpty(pathList)){
                path = pathList.get(0).get("value").toString();
            }
            zipKinDO.setAppMethod(method);
            zipKinDO.setName(zipKinDTO.getName());
            zipKinDO.setCreateDate(new Date());
            zipKinDO.setId(zipKinDTO.getTraceId());
            zipKinDO.setSumDuration(zipKinDTO.getDuration());
            zipKinDO.setAppService(serviceName);
            zipKinDO.setUrl(path);
            zipKinDOS.add(zipKinDO);
            if (++flagNum >= 100){
                break;
            }
        }
        entityMapper.insertBatchSelective(zipKinDOS);
    }

    /**
     * 导出最慢的10个服务
     */
    @Override
    public ZipkinExportDTO exportExcel(Integer num,String startDate,String endDate){
        ZipkinExportDTO zipkinExportDTO = new ZipkinExportDTO();
        if(StringUtils.isBlank(startDate)){
            startDate = null;
        }
        if (StringUtils.isBlank(endDate)){
            endDate = null;
        }
        List<ZipKinDTO> zipKinDTO = zipKinMapper.getTodayDatas(startDate,endDate);
        if (CollectionUtils.isEmpty(zipKinDTO)){
            return null;
        }
        //根据方法进行分组
        Map<String,List<ZipKinDTO>> zipkinMap = zipKinDTO.stream().collect(Collectors.groupingBy(ZipKinDTO::getAppMethod));
        List<ZipKinDTO> zipKinDTOList = new ArrayList<>();
        for (Map.Entry<String,List<ZipKinDTO>> entry:zipkinMap.entrySet()){
            ZipKinDTO exportDatas = new ZipKinDTO();
            LongSummaryStatistics collectDatas = entry.getValue().stream().collect(Collectors.summarizingLong(ZipKinDTO::getDuration));
            BigDecimal divisor = new BigDecimal(collectDatas.getSum());
            BigDecimal dividend = new BigDecimal(entry.getValue().size());
            Long duration = divisor.divide(dividend,4,BigDecimal.ROUND_HALF_UP).longValue();
            exportDatas.setAverageDuration(duration);
            exportDatas.setAppService(entry.getValue().get(0).getAppService());
            exportDatas.setAppMethod(entry.getKey());
            exportDatas.setName(entry.getValue().get(0).getName());
            exportDatas.setServiceCount(entry.getValue().size());
            zipKinDTOList.add(exportDatas);
        }
        //表头信息
        List<String> list =new ArrayList<>();
        list.add("应用");
        list.add("最耗时方法");
        list.add("耗时(ms)");
        list.add("路径(方法)");
        list.add("详细路径");
        list.add("重复数量");
        zipKinDTOList = zipKinDTOList.stream().sorted(Comparator.comparing(ZipKinDTO::getAverageDuration).reversed()).collect(Collectors.toList());
        //展示内容
        List<List<String>> valList = new ArrayList<>();
        for (int i = 0;i<num;i++){
            List<String> strList = new ArrayList<>();
            if (i>zipKinDTOList.size()-1){
                break;
            }
            strList.add(zipKinDTOList.get(i).getAppService());
            strList.add(zipKinDTOList.get(i).getAppMethod());
            strList.add(String.valueOf(zipKinDTOList.get(i).getAverageDuration()));
            strList.add(zipKinDTOList.get(i).getName());
            strList.add(zipKinDTOList.get(i).getUrl());
            strList.add(String.valueOf(zipKinDTOList.get(i).getServiceCount()));
            valList.add(strList);
        }
        String fileName = "耗时统计_"+ DateUtils.formateTime(new Date(),DateUtils.DATE_FORMAT_2);
        zipkinExportDTO.setFiledName(fileName);
        zipkinExportDTO.setColNameList(list);
        zipkinExportDTO.setValList(valList);
        return zipkinExportDTO;
    }


    @Scheduled(cron="59 59 7,11,15,19,23 * * ?")
    public void insertZipKinScheduled(){
        this.insertZipKinMsg();
    }

    @PostConstruct
    public void initServiceList(){
        serviceList.add("exchange-app");
        serviceList.add("accept-app");
        serviceList.add("accept-ui");
        serviceList.add("building-app");
        serviceList.add("building-ui");
        serviceList.add("certificate-app");
        serviceList.add("realestate-config");
        serviceList.add("engine-app");
        serviceList.add("init-app");
        serviceList.add("inquiry-app");
        serviceList.add("inquiry-ui");
        serviceList.add("portal-ui");
        serviceList.add("register-app");
        serviceList.add("register-ui-app");
        serviceList.add("etl-app");
    }
}
