package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcBjscDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcBjscTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BjsctjQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.Object2MapUtils;
import cn.gtmap.realestate.inquiry.core.cloudMapper.BdcCloudMapper;
import cn.gtmap.realestate.inquiry.service.BdcBjscTjService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/5/15
 * @description  查询子系统：办件时长统计
 */
@Service
public class BdcBjscTjServiceImpl implements BdcBjscTjService {

    @Autowired
    private BdcCloudMapper bdcCloudMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBjscTjServiceImpl.class);

    @Value("${bjsctjsjd:<3,3-5,>5}")
    private String period;
    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bjsctjQO 查询条件
     * @return 办件时长统计集合
     * @description 办件时长统计
     */
    @Override
    public List<Map> listBjscCount(BjsctjQO bjsctjQO) {
        setParamList(bjsctjQO);
        List<BdcBjscTjDTO> list = bdcCloudMapper.countBjsc(bjsctjQO);
        Map<String,BdcBjscDTO> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(list)){
            LOGGER.info("查询办件时长统计集合长度：{}",list.size());
            LOGGER.info("时间段配置为：{}",period);
            List<String> listPerid = Arrays.asList(period.split(","));
            if(CollectionUtils.isNotEmpty(listPerid)){
                for(BdcBjscTjDTO bdcBjscTjDTO : list){
                    String bmid = bdcBjscTjDTO.getBmid();
                    if(StringUtils.isBlank(bmid)){
                        continue;
                    }
                    if(!map.containsKey(bmid)){
                        BdcBjscDTO bdcBjscDTO = new BdcBjscDTO();
                        bdcBjscDTO.setBjywl(1);
                        bdcBjscDTO.setAvgBjsc(bdcBjscTjDTO.getBjsc());
                        bdcBjscDTO.setBjsc(bdcBjscTjDTO.getBjsc());
                        bdcBjscDTO.setBmmc(bdcBjscTjDTO.getBmid());
                        Map<String,Object> periodMap = new HashMap();
                        for(String per : listPerid){
                            // <3 类型
                            if(per.indexOf("<") != -1){//小于时间段
                                int lessCountDay = Integer.parseInt(per.substring(1,2));
                                if(bdcBjscTjDTO.getBjsc() <= lessCountDay) {
                                    periodMap.put(per + "_count", 1);
                                    break;
                                }
                            }

                            // 3-5类型
                            if(per.indexOf("-") != -1){
                                int lessCountDay = Integer.parseInt(per.split("-")[0]);
                                int moreCountDay = Integer.parseInt(per.split("-")[1]);
                                if(bdcBjscTjDTO.getBjsc() > lessCountDay && bdcBjscTjDTO.getBjsc() <= moreCountDay) {
                                    periodMap.put(per + "_count", 1);
                                    break;
                                }
                            }

                            // >5类型
                            if(per.indexOf(">") != -1){
                                int moreCountDay = Integer.parseInt(per.substring(1,2));
                                if(bdcBjscTjDTO.getBjsc() > moreCountDay) {
                                    periodMap.put(per + "_count", 1);
                                    break;
                                }
                            }
                        }
                        bdcBjscDTO.setPeriodCount(periodMap);
                        map.put(bmid,bdcBjscDTO);
                    }else{
                        BdcBjscDTO bdcBjscDTO = map.get(bmid);

                        Integer bjywl = bdcBjscDTO.getBjywl()+1;
                        float bjsc = bdcBjscDTO.getBjsc() + bdcBjscTjDTO.getBjsc();
                        float avgBjsc = bjsc/bjywl;
                        bdcBjscDTO.setBjywl(bjywl);
                        bdcBjscDTO.setAvgBjsc(avgBjsc);
                        bdcBjscDTO.setBjsc(bjsc);

                        Map<String,Object> periodMap = bdcBjscDTO.getPeriodCount();

                        for(String per : listPerid){
                            // <3 类型
                            if(per.indexOf("<") != -1){//小于时间段
                                int lessCountDay = Integer.parseInt(per.substring(1,2));
                                if(bdcBjscTjDTO.getBjsc() <= lessCountDay) {
                                    if(periodMap.containsKey(per + "_count")){
                                        int count = (Integer) periodMap.get(per + "_count")+1;
                                        periodMap.put(per + "_count", count);
                                    }else{
                                        periodMap.put(per + "_count", 1);
                                    }
                                    break;
                                }
                            }

                            // 3-5类型
                            if(per.indexOf("-") != -1){
                                int lessCountDay = Integer.parseInt(per.split("-")[0]);
                                int moreCountDay = Integer.parseInt(per.split("-")[1]);
                                if(bdcBjscTjDTO.getBjsc() > lessCountDay && bdcBjscTjDTO.getBjsc() <= moreCountDay) {
                                    if(periodMap.containsKey(per + "_count")){
                                        int count = (Integer)periodMap.get(per + "_count")+1;
                                        periodMap.put(per + "_count", count);
                                    }else{
                                        periodMap.put(per + "_count", 1);
                                    }
                                    break;
                                }
                            }

                            // >5类型
                            if(per.indexOf(">") != -1){
                                int moreCountDay = Integer.parseInt(per.substring(1,2));
                                if(bdcBjscTjDTO.getBjsc() > moreCountDay) {
                                    if(periodMap.containsKey(per + "_count")){
                                        int count = (Integer)periodMap.get(per + "_count")+1;
                                        periodMap.put(per + "_count", count);
                                    }else{
                                        periodMap.put(per + "_count", 1);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // 处理每个部门的 每个时段的占比
            if(MapUtils.isNotEmpty(map)){
                for(String key : map.keySet()){
                    BdcBjscDTO bdcBjscDTO = map.get(key);
                    Integer bjywl = bdcBjscDTO.getBjywl();
                    Map<String,Object> periodMap = bdcBjscDTO.getPeriodCount();
                    Map<String,Object> periodMapTemp = new HashMap<>();
                    for(String periodMapKey : periodMap.keySet()){
                        periodMapTemp.put(periodMapKey,periodMap.get(periodMapKey));
                        Integer count = (Integer)periodMap.get(periodMapKey);
                        double zb = ((double)count)/((double)bjywl)*100;
                        double factor = Math.pow(10, 1);
                        zb = Math.floor(zb * factor + 0.5) / factor;
                        periodMapTemp.put(periodMapKey.split("_")[0]+"_percent",zb+"%");
                    }

                    // 补全为空的
                    for(String per : listPerid){
                        if(!periodMapTemp.containsKey(per+"_count")){
                            periodMapTemp.put(per+"_count",0);
                            periodMapTemp.put(per+"_percent",0);
                        }
                    }
                    bdcBjscDTO.setPeriodCount(periodMapTemp);
                }
            }

        }
        // map转换成list
        return convertMapToListMap(map);
    }


    /**
     * 处理查询参数，转换成集合
     * @param bjsctjQO
     */
    private static void setParamList (BjsctjQO bjsctjQO){
        if(StringUtils.isNotBlank(bjsctjQO.getDjjg()) && bjsctjQO.getDjjg().split(",").length > 0){
            bjsctjQO.setDjjglist(Arrays.asList(bjsctjQO.getDjjg().split(",")));
        }else{
            bjsctjQO.setDjjglist(new ArrayList());
        }

        if(StringUtils.isNotBlank(bjsctjQO.getProcessname()) && bjsctjQO.getProcessname().split(",").length > 0){
            bjsctjQO.setProcessnamelist(Arrays.asList(bjsctjQO.getProcessname().split(",")));
        }else{
            bjsctjQO.setProcessnamelist(new ArrayList());
        }

        if(StringUtils.isNotBlank(bjsctjQO.getDjyy()) && bjsctjQO.getDjyy().split(",").length > 0){
            bjsctjQO.setDjyylist(Arrays.asList(bjsctjQO.getDjyy().split(",")));
        }else{
            bjsctjQO.setDjyylist(new ArrayList());
        }
        if(StringUtils.isNotBlank(bjsctjQO.getSply()) && bjsctjQO.getSply().split(",").length > 0){
            List<String> splylist = Arrays.asList(bjsctjQO.getSply().split(","));
            //如果有外网,则将外网详细都加上
            if (splylist.contains(CommonConstantUtils.SPLY_WWSQ.toString())) {
                for (Integer sply : CommonConstantUtils.SPLY_WWSQ_DETAIL) {
                    splylist.add(sply.toString());
                }
            }
            bjsctjQO.setSplylist(splylist);
        }else{
            bjsctjQO.setSplylist(new ArrayList());
        }
    }

    /**
     * 统计数据转列表数据
     * @param map
     * @return
     */
    private static List<Map> convertMapToListMap(Map<String,BdcBjscDTO> map){
        if(MapUtils.isNotEmpty(map)){
            List<Map> list = new ArrayList<>();
            for(String key : map.keySet()){
                Map bdcBjscDTOMap;
                BdcBjscDTO bdcBjscDTO =  map.get(key);
                bdcBjscDTOMap = Object2MapUtils.object2MapExceptNull(bdcBjscDTO);
                if(bdcBjscDTOMap.containsKey("periodCount")){
                    bdcBjscDTOMap.putAll((Map)bdcBjscDTOMap.get("periodCount"));
                }
                list.add(bdcBjscDTOMap);
            }
            return list;

        }
        return new ArrayList<>();
    }

}
