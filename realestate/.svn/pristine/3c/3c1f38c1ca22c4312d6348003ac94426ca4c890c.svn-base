package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzQqjlMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzQqjlDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.InterfacePermission;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQqjlService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0 2019/1/22
 * @description 请求记录
 */
@Service
public class BdcDzzzQqjlServiceImpl implements BdcDzzzQqjlService {
    @Resource
    private BdcDzzzQqjlMapper bdcDzzzQqjlMapper;
    @Autowired
    private InterfacePermission interfacePermission;


    @Override
    public void insertBdcDzzzQqjl(BdcDzzzTokenDo bdcDzzzTokenDo, String jkmc) {
        BdcDzzzQqjlDo bdcDzzzQqjlDo = new BdcDzzzQqjlDo();
        if (null != bdcDzzzTokenDo) {
            bdcDzzzQqjlDo.setQqmc(bdcDzzzTokenDo.getYymc());
            bdcDzzzQqjlDo.setQqbm(bdcDzzzTokenDo.getYybm());
            bdcDzzzQqjlDo.setQqid(UUIDGenerator.generate());
            bdcDzzzQqjlDo.setQqsj(DateUtil.now());
            bdcDzzzQqjlDo.setJkmc(jkmc);
            bdcDzzzQqjlMapper.insertBdcDzzzQqjl(bdcDzzzQqjlDo);
        }
    }


    @Override
    public String countRecordUse(Map map, String way) {
        StringBuilder result = new StringBuilder("{");
        if (StringUtils.equals("syCharMonth", way)) {
            String resultMonth = countRecordMonth(map);
            if (StringUtils.isNotBlank(resultMonth)) {
                countStrAppend(result, resultMonth);
            }
        } else if (StringUtils.equals("syCharYear", way)) {
            String resultYear = countRecordYear(map);
            if (StringUtils.isNotBlank(resultYear)) {
                countStrAppend(result, resultYear);
            }
        } else if (StringUtils.equals("syCharWeek", way)) {
            String resultWeek = countRecordWeek(map);
            if (StringUtils.isNotBlank(resultWeek)) {
                countStrAppend(result, resultWeek);
            }
        }

        result.append("}");

        return result.toString();
    }

    private StringBuilder countStrAppend(StringBuilder result, String append) {
        if (StringUtils.equals("{", result.toString())) {
            result.append(append);
        } else {
            result.append(",").append(append);
        }
        return result;
    }

    private void strAppend(StringBuilder xData, StringBuilder yData, List<Map> resultList, int i, String use, BigDecimal useCount) {
        if (i != resultList.size() - 1) {
            xData.append("\"").append(use).append("\"").append(",");
            yData.append(useCount).append(",");
        } else {
            xData.append("\"").append(use).append("\"");
            yData.append(useCount);
        }
    }

    @Override
    public String countDepart(Map map) {
        StringBuilder result = new StringBuilder();
        List<Map> resultList = bdcDzzzQqjlMapper.countDepart(map);
        if (CollectionUtils.isNotEmpty(resultList)) {
            StringBuilder xDataUseDepartment = new StringBuilder("[");
            StringBuilder yDataUseDepartment = new StringBuilder("[");
            for (int i = 0; i < resultList.size(); i++) {
                Map resultMap = resultList.get(i);
                String departMc = (String) resultMap.get("DEPART_MC");
                BigDecimal countUseDepartment = (BigDecimal) resultMap.get("COUNT_USE_DEPARTMENT");

                strAppend(xDataUseDepartment, yDataUseDepartment, resultList, i, departMc, countUseDepartment);
            }
            xDataUseDepartment.append("]");
            yDataUseDepartment.append("]");

            result.append("{")
                    .append("\"xDataUseDepartment\":").append(xDataUseDepartment).append(",")
                    .append("\"yDataUseDepartment\":").append(yDataUseDepartment)
                    .append("}");
        }

        return result.toString();
    }

    @Override
    public String countRecordMonth(Map map) {
        StringBuilder result = new StringBuilder();
        List<Map> resultListMonth = bdcDzzzQqjlMapper.countRecordMonth(map);
        if (CollectionUtils.isNotEmpty(resultListMonth)) {
            StringBuilder xDataMonth = new StringBuilder("[");
            StringBuilder yDataMonth = new StringBuilder("[");
            for (int i = 0; i < resultListMonth.size(); i++) {
                Map resultMap = resultListMonth.get(i);
                String useMonth = (String) resultMap.get("USE_MONTH");
                BigDecimal useCountMonth = (BigDecimal) resultMap.get("USE_COUNT");
                useCountMonth = null != useCountMonth ? useCountMonth : new BigDecimal(0);

                strAppend(xDataMonth, yDataMonth, resultListMonth, i, useMonth, useCountMonth);
            }
            xDataMonth.append("]");
            yDataMonth.append("]");

            result.append("\"xDataMonth\":").append(xDataMonth).append(",")
                    .append("\"yDataMonth\":").append(yDataMonth);
        }

        return result.toString();
    }

    @Override
    public String countRecordYear(Map map) {
        StringBuilder result = new StringBuilder();
        List<Map> resultListYear = bdcDzzzQqjlMapper.countRecordYear(map);
        if (CollectionUtils.isNotEmpty(resultListYear)) {
            StringBuilder xDataYear = new StringBuilder("[");
            StringBuilder yDataYear = new StringBuilder("[");
            for (int i = 0; i < resultListYear.size(); i++) {
                Map resultMap = resultListYear.get(i);
                String useYear = (String) resultMap.get("USE_YEAR");
                BigDecimal useCountYear = (BigDecimal) resultMap.get("USE_COUNT");
                useCountYear = null != useCountYear ? useCountYear : new BigDecimal(0);

                strAppend(xDataYear, yDataYear, resultListYear, i, useYear, useCountYear);
            }
            xDataYear.append("]");
            yDataYear.append("]");

            result.append("\"xDataYear\":").append(xDataYear).append(",")
                    .append("\"yDataYear\":").append(yDataYear);
        }
        return result.toString();
    }

    @Override
    public String countRecordWeek(Map map) {
        StringBuilder result = new StringBuilder();

        List<Map> resultListDay = bdcDzzzQqjlMapper.countRecordWeek(map);
        if (CollectionUtils.isNotEmpty(resultListDay)) {
            StringBuilder xDataDay = new StringBuilder("[");
            StringBuilder yDataDay = new StringBuilder("[");
            for (int i = 0; i < resultListDay.size(); i++) {
                Map resultMap = resultListDay.get(i);
                String useDay = (String) resultMap.get("USE_WEEK");
                BigDecimal useCountDay = (BigDecimal) resultMap.get("USE_COUNT");
                useCountDay = null != useCountDay ? useCountDay : new BigDecimal(0);

                strAppend(xDataDay, yDataDay, resultListDay, i, useDay, useCountDay);
            }

            xDataDay.append("]");
            yDataDay.append("]");

            result.append("\"xDataDay\":").append(xDataDay).append(",")
                    .append("\"yDataDay\":").append(yDataDay);
        }
        return result.toString();
    }

    @Override
    public String countPortPercent(Map param) {
        JSONObject obj = new JSONObject();
        param.put("regionZzpdf", Constants.regionZzpdf);
        param.put("regionZzzx",Constants.regionZzzx);
        param.put("regionZzcx",Constants.regionZzcx);
        param.put("regionZzjs",Constants.regionZzjs);
        param.put("regionZzxxxz",Constants.regionZzxxxz);
        param.put("regionZzdzxz",Constants.regionZzdzxz);
        param.put("regionZzysj",Constants.regionZzysj);
        param.put("regionZzwjyz",Constants.regionZzwjyz);
        List<Map> mapList = bdcDzzzQqjlMapper.countPortPercent(param);
        Map<String, String> nameMap = interfacePermission.getRegionUseMap();
        if (CollectionUtils.isNotEmpty(mapList)) {
            JSONArray mc = new JSONArray();
            JSONObject select = new JSONObject();
            JSONArray seriesArray = new JSONArray();
            Map result = mapList.get(0);
            String[] regions = Constants.regions;
            if (null != result) {
                for (int i = 0; i < regions.length; i++) {
                    String value = String.valueOf(result.get(regions[i]));
                    String name = nameMap.get(regions[i]);
                    boolean selectType = true;
                    if (StringUtils.equals(Constants.DZZZ_NUMBER_ZERO, value)) {
                        selectType = false;
                    }
                    mc.add(name);
                    select.put(name, selectType);
                    JSONObject series = new JSONObject();
                    series.put("name",name);
                    series.put("value",value);
                    seriesArray.add(series);
                }
            }
            obj.put("legendData",mc);
            obj.put("selected",select);
            obj.put("seriesData",seriesArray);
        }
        return JSONObject.toJSONString(obj);
    }

    @Override
    public String countRegionUse(Map param) {
        JSONObject result = new JSONObject();
        param.put("regionZzpdf",Constants.regionZzpdf);
        param.put("regionZzzx",Constants.regionZzzx);
        param.put("regionZzcx",Constants.regionZzcx);
        param.put("regionZzjs",Constants.regionZzjs);
        param.put("regionZzxxxz",Constants.regionZzxxxz);
        param.put("regionZzdzxz",Constants.regionZzdzxz);
        param.put("regionZzysj",Constants.regionZzysj);
        param.put("regionZzwjyz",Constants.regionZzwjyz);
        String[] regions = Constants.regions;
        Map<String, String> nameMap = interfacePermission.getRegionUseMap();
        List<Map> resultList = bdcDzzzQqjlMapper.countRegionUse(param);
        if (CollectionUtils.isNotEmpty(resultList)) {
            JSONArray jksyYaxis = new JSONArray();
            JSONArray jksySeries = new JSONArray();
            JSONArray jksyLegendData = new JSONArray();
            for (String region : regions) {
                //boolean showType = false;
                //装载统计项名称
                String name = nameMap.get(region);
                jksyLegendData.add(name);
                //装载纵坐标所需数据
                JSONObject jksySerie = new JSONObject();
                jksySerie.put("name",name);
                jksySerie.put("type","bar");
                jksySerie.put("stack","总量");
                JSONArray jksySeriesData = new JSONArray();
                for (Map map : resultList) {
                    /*String value = String.valueOf(map.get(region));
                    if (StringUtils.equals(Constants.DZZZ_NUMBER_ZERO,value)) {
                        value = "";
                    }*/
                    jksySeriesData.add(map.get(region));
                }
                JSONObject jksySeriesLabel = new JSONObject();
                JSONObject jksySeriesNormal = new JSONObject();
                jksySeriesNormal.put("show",true);
                jksySeriesNormal.put("position","insideRight");
                jksySeriesLabel.put("normal",jksySeriesNormal);
                jksySerie.put("label",jksySeriesLabel);
                jksySerie.put("data",jksySeriesData);
                jksySeries.add(jksySerie);
            }
            //装载横坐标所需数据
            for (Map map : resultList) {
                jksyYaxis.add(map.get("MC"));
            }
            result.put("jksyLegendData",jksyLegendData);
            result.put("jksyYaxisData",jksyYaxis);
            result.put("jksySeriesData",jksySeries);
        }
        return JSONObject.toJSONString(result);
    }
}
