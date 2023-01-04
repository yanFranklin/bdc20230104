package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.bdcdsflog.BdcDsfLogMapper;
import cn.gtmap.realestate.exchange.service.inf.log.BdcDsfLogService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BdcDsfLogServiceImpl implements BdcDsfLogService {

    protected static Logger LOGGER = LoggerFactory.getLogger(BdcDsfLogServiceImpl.class);

    @Autowired
    private Repo repository;

    @Autowired
    private BdcDsfLogMapper bdcDsfLogMapper;

    @Resource(name = "bdcDsfLogInAllServiceImpl")
    private BdcDsfLogInAllServiceImpl bdcDsfLogInAllService;

    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;


    /**
     * @param pageable
     * @param paramMap
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public Object listBdcDsfRzByPage(Pageable pageable, Map<String,Object> paramMap) {
        if (paramMap.containsKey("jkid")) {
            CommonResponse<BdcDwJkDO> jkid = bdcDsfLogInAllService.getInterfaceLogMode((String) paramMap.get("jkid"));
            if (!jkid.isSuccess()) {
                LOGGER.error("获取接口信息异常:{}", paramMap.get("jkid"));
                return null;
            }
            if (0 != jkid.getData().getJklx()) {
                paramMap.put("bdcdz", jkid.getData().getJkdz());
            } else {
                paramMap.put("jkmc", jkid.getData().getJkmc());
                paramMap.put("bdcdz", jkid.getData().getJkdz());
            }
        }
        //将部门标识和接口名称转换
        if(paramMap.containsKey("gxbmbz")){
            paramMap.put("gxbmbz",Arrays.asList(paramMap.get("gxbmbz").toString().split(",")));
        }
        if(paramMap.containsKey("jkmc")){
            paramMap.put("jkmc",Arrays.asList(paramMap.get("jkmc").toString().split(",")));
        }
        String czr = MapUtils.getString(paramMap,"czr");
        String bmid = MapUtils.getString(paramMap,"bmid");
        if (StringUtils.isBlank(czr) && StringUtils.isNotBlank(bmid)){
            List<UserDto> users = new ArrayList<>();
            String[] bmidArray = bmid.split(CommonConstantUtils.ZF_YW_DH);
            for (int i = 0; i < bmidArray.length; i++) {
                List<UserDto> tempUsers = organizationManagerClient.listUsersByOrg(bmidArray[i]);
                if(CollectionUtils.isNotEmpty(tempUsers)){
                    users.addAll(tempUsers);
                }
            }
            if(CollectionUtils.isNotEmpty(users)){
                String orgUserName = users
                        .stream()
                        .map(UserDto::getUsername)
                        .collect(Collectors.joining(","));
                if(StringUtils.isNotBlank(orgUserName)){
                    paramMap.put("czr",orgUserName);
                }
            }
        }
        return repository.selectPaging("listBdcDsfLogByPageOrder", paramMap, pageable);
    }

    /**
     *
     * @return
     * seriesData: [
                {value: 335, name: '互联网'},
                {value: 310, name: '税务'}
            ]
        legendData: ['互联网', '税务' ]
     */
    @Override
    public JSONObject countBdcDsfLogByGxbmbz(Map paramMap) {
        JSONObject result = new JSONObject();
        List<Map> mapList = bdcDsfLogMapper.countBdcDsfLogByGxbmbz(paramMap);
        if (CollectionUtils.isNotEmpty(mapList)) {
            JSONArray seriesData = new JSONArray();
            JSONArray legendSet = new JSONArray();
            for (Map map : mapList) {
                JSONObject serieData = new JSONObject();
                serieData.put("name",map.get("GXBMBZ"));
                serieData.put("value",map.get("ZS"));
                legendSet.add((String) map.get("GXBMBZ"));
                seriesData.add(serieData);
            }
            result.put("seriesData", seriesData);
            result.put("legendData", legendSet);
        }
        return result;
    }

    /**
     *
     * @return
     * legendData: ['互联网', '税务' ]
     * xAxisData: ['02-29', '03-07', '03-14', '03-21', '03-28', '04-04', '04-11']
     * seriesData: [
                        {
                        name: '互联网',
                        type: 'line',
                        stack: '总量',
                        data: [120, 132, 101, 134, 90, 230, 210]
                        },
                        {
                        name: '税务',
                        type: 'line',
                        stack: '总量',
                        data: [220, 182, 191, 234, 290, 330, 310]
                        }
                    ]
        result: [
                    {
                    WEEK: '02-29',
                    MC: '互联网',
                    JKZS: '35'
                },
                {
                WEEK: '02-29',
                MC: '税务',
                JKZS: '2'
                }
                ...
            ]
     */
    @Override
    public JSONObject countBdcDsfLogXtdyqs(Map paramMap) {
        JSONObject result = new JSONObject();
        List<Map> mapList = new ArrayList<>(5);

        //循环获取7个周期的数据
        for (int i = 0; i <7; i++) {
            paramMap.put("sysQssj",i);
            paramMap.put("sysJssj",i + 1);
            List<Map> mapListWeek1 = bdcDsfLogMapper.countBdcDsfLogWeek(paramMap);
            if (CollectionUtils.isNotEmpty(mapListWeek1)) {
                mapList.addAll(mapListWeek1);
            }
        }

        Map<String,String> xAxisMap = bdcDsfLogMapper.countWeek();
        if (CollectionUtils.isNotEmpty(mapList)) {
            List<String> legendData = new ArrayList<>();
            JSONArray seriesData = new JSONArray();
            String[] xAxisData = new String[7];
            //循环出legendData
            for (Map map : mapList) {
                legendData.add((String)map.get("GXBMBZ"));
            }
            //循环出xAxisData
            for (int i = 0; i < xAxisMap.size(); i++) {
                xAxisData[i] = (xAxisMap.get("WEEK" + (i + 1)));
            }

            //循环 过滤出共享部门对应每个周的值 seriesData
            if (CollectionUtils.isNotEmpty(legendData)) {
                for (String mc : legendData) {
                    JSONObject serieData = new JSONObject();
                    serieData.put("name",mc);
                    serieData.put("type","line");
                    //serieData.put("stack","总量");
                    JSONArray serieDatas = new JSONArray();
                    //得到seriesData 中对应时间段的数组
                    for(String week : xAxisData){
                        boolean jkzsFlag = false;
                        for (Map map : mapList) {
                            if (StringUtils.equals(mc, (String)map.get("GXBMBZ")) && StringUtils.equals(week, (String)map.get("WEEK"))) {
                                serieDatas.add(map.get("JKZS"));
                                jkzsFlag = true;
                                break;
                            }
                        }
                        if (!jkzsFlag) {
                            serieDatas.add(0);
                        }
                    }
                    serieData.put("data",serieDatas);
                    seriesData.add(serieData);
                }
            }

            result.put("legendData",legendData);
            result.put("xAxisData",xAxisData);
            result.put("seriesData",seriesData);
        }
        return result;
    }

    @Override
    public JSONObject countGxtDymx(Map paramMap) {
        JSONObject result = new JSONObject();
        List<Map> mapList = bdcDsfLogMapper.countGxtMx(paramMap);
        if (CollectionUtils.isNotEmpty(mapList)) {
            List<String> yAxisData = new ArrayList<>();
            JSONArray seriesDataCg = new JSONArray();
            JSONArray seriesDataSb = new JSONArray();
            for (Map map : mapList) {
                yAxisData.add((String)map.get("MC"));
                seriesDataCg.add(map.get("CGZS"));
                seriesDataSb.add(map.get("SBZS"));
            }
            result.put("yAxisData",yAxisData);
            result.put("seriesDataCg",seriesDataCg);
            result.put("seriesDataSb",seriesDataSb);
            result.put("tableData",mapList);
        }
        return result;
    }

    @Override
    public Object countZj(Map paramMap) {
        return bdcDsfLogMapper.countBdcDsfLogZj(paramMap);
    }

    @Override
    public Object countMx(Map paramMap) {
        return bdcDsfLogMapper.countBdcDsfLogByGxbmbz(paramMap);
    }

}
