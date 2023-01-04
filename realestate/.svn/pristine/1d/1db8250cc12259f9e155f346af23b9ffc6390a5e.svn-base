package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AccessStatsDto;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDyzmTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZhcxDyzmTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhcxTjRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/7/17
 * @description 综合查询打印证明统计
 */
@RestController
@Api(tags = "综合查询打印证明统计")
public class BdcZhcxTjRestController extends BaseController implements BdcZhcxTjRestService {

    @Autowired
    private LogMessageClient logMessageClient;

    /**
     * @return BdcZhcxDyzmTjDTO 综合查询打印证明统计
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合查询打印证明统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("综合查询打印证明统计")
    public BdcZhcxDyzmTjDTO zhcxDyzmTj(@RequestBody BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO) {

        // 统计维度默认部门
        if(StringUtils.isBlank(bdcZhcxDyzmTjQO.getTjwd())){
            bdcZhcxDyzmTjQO.setTjwd("organization");
        }

        // 获取不同证明对应的统计数据
        List<String> printTypes = CollectionUtils.isNotEmpty(bdcZhcxDyzmTjQO.getPrintTypes()) ? bdcZhcxDyzmTjQO.getPrintTypes() : Lists.newArrayList();
        List<AccessStatsDto> accessStatsDtoList = new LinkedList<>();
        for(String printType : printTypes){
            AccessStatsDto accessStatsDto = this.statistic(bdcZhcxDyzmTjQO, printType);
            accessStatsDtoList.add(accessStatsDto);
        }

        if(CollectionUtils.isEmpty(accessStatsDtoList)){
            return null;
        }

        // 过滤出所有的机构或者人员名称
        LinkedHashSet<String> statisticKeySet = new LinkedHashSet<>();
        for(AccessStatsDto item : accessStatsDtoList){
            if(null != item && MapUtils.isNotEmpty(item.getDetails())){
                statisticKeySet.addAll(item.getDetails().keySet());
            }
        }

        // 筛选匹配每个证明对应所有机构、人员数量
        Map<String, List<Integer>> valueMap = new HashMap<>();
        for(int i = 0; i < printTypes.size(); i++){
            // 每个证明数据
            AccessStatsDto accessStatsDto = accessStatsDtoList.get(i);

            List<Integer> valueList = new ArrayList<>(10);
            for(String orgUserName : statisticKeySet){
                if(null == accessStatsDto){
                    valueList.add(0);
                    continue;
                }

                Map<String, Integer> details = accessStatsDto.getDetails();
                Integer val = details.get(orgUserName);
                valueList.add(null == val ? 0 : val);
            }
            valueMap.put(printTypes.get(i), valueList);
        }

        BdcZhcxDyzmTjDTO bdcZhcxDyzmTjDTO = new BdcZhcxDyzmTjDTO();
        bdcZhcxDyzmTjDTO.setKeySet(statisticKeySet);
        bdcZhcxDyzmTjDTO.setValueMap(valueMap);
        return bdcZhcxDyzmTjDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZhcxDyzmTjQO 查询条件
     * @param zmtype 证明类型
     * @description  统计指定证明类型打印数量
     */
    private AccessStatsDto statistic(BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO, String zmtype){

        List<QueryLogCondition> conditions = new ArrayList<>();
        // 指定查询某个证明日志
        QueryLogCondition condition = new QueryLogCondition();
        condition.setKey("printType");
        condition.setValue(zmtype);
        condition.setType("equal");
        conditions.add(condition);


        // 指定时间区间
        Long beginTime = null, endTime = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getKssj())){
                beginTime = simpleDateFormat.parse(bdcZhcxDyzmTjQO.getKssj()).getTime();
            }
            if(StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getJzsj())){
                endTime = simpleDateFormat.parse(bdcZhcxDyzmTjQO.getJzsj()).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        AccessStatsDto allaccessStatsDto =new AccessStatsDto();
        int total =0;
        Map<String, Integer> details =new HashMap<>();
        if(StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getBmmc())) {
            String[] bmmcArray = bdcZhcxDyzmTjQO.getBmmc().split(",");
            for(String bmmc:bmmcArray) {
                if (conditions.size() > 1) {
                    conditions.remove(1);
                }
                QueryLogCondition bmCondition = new QueryLogCondition();
                bmCondition.setKey(CommonConstantUtils.ORGANIZATION);
                bmCondition.setValue(bmmc);
                bmCondition.setType("equal");
                conditions.add(1, bmCondition);
                // 先查询指定证明对应所有部门或者人员的打印数量
                AccessStatsDto accessStatsDto = logMessageClient.statisticLog(bdcZhcxDyzmTjQO.getTjwd(), "PRINTLOG", null, beginTime, endTime,conditions);
                LOGGER.info("综合查询打印证明统计,查询条件：{},查询结果：{}",JSONObject.toJSONString(conditions),accessStatsDto != null?JSONObject.toJSONString(accessStatsDto):"");

                if(accessStatsDto != null && MapUtils.isNotEmpty(accessStatsDto.getDetails())){
                    for (Map.Entry<String, Integer> entry : accessStatsDto.getDetails().entrySet()) {
                        //累加
                        if(details.containsKey(entry.getKey())){
                            details.put(entry.getKey(),entry.getValue()+details.get(entry.getKey()));
                        }else{
                            details.put(entry.getKey(),entry.getValue());
                        }
                    }
                    total+=accessStatsDto.getTotal();
                }
            }
            allaccessStatsDto.setTotal(total);
            allaccessStatsDto.setDetails(details);
        }else{
            // 先查询指定证明对应所有部门或者人员的打印数量
            allaccessStatsDto = logMessageClient.statisticLog(bdcZhcxDyzmTjQO.getTjwd(), "PRINTLOG", null, beginTime, endTime,conditions);

        }
        if(null == allaccessStatsDto || MapUtils.isEmpty(allaccessStatsDto.getDetails())){
            return null;
        }



        // 再根据指定的结构、人员筛选结果
        /// 统计维度：机构
        if(StringUtils.equals("organization", bdcZhcxDyzmTjQO.getTjwd()) && StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getBmmc())){
            String[] bmmcArray = bdcZhcxDyzmTjQO.getBmmc().split(",");

            Iterator<Map.Entry<String, Integer>> iterator = allaccessStatsDto.getDetails().entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                // 如果不是指定要查询的机构则删除
                if(!StringToolUtils.existItemEquals(entry.getKey(), bmmcArray)){
                    iterator.remove();
                }
            }
        }

        /// 统计维度：人员
        if(StringUtils.equals("alias", bdcZhcxDyzmTjQO.getTjwd()) && StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getRymc())){
            String[] rymcArray = bdcZhcxDyzmTjQO.getRymc().split(",");

            Iterator<Map.Entry<String, Integer>> iterator = allaccessStatsDto.getDetails().entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                // 如果不是指定要查询的人员则删除
                if(!StringToolUtils.existItemEquals(entry.getKey(), rymcArray)){
                    iterator.remove();
                }
            }
        }

        return allaccessStatsDto;
    }

}
