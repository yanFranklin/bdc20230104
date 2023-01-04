package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.exchange.core.vo.BdcDsfRzTjYsVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-24
 * @description 颜色配置相关服务
 */
@Service
public class BdcDsfRzYsPzServiceImpl {

    // 统计页面 默认颜色配置
    public final static String DEFAULT_YS = "#36b654,#7ebbfc,#2e7ce8,#43c9cb,#e68787,#e13147,#f9d98e,#ca7fff,#7ad6d3,#9a3d6f,#f7bc25,#f27519,#658090,#aec847,#869065";

    // 在redis中存储的 日志统计 共享部门颜色 的key
    public final static String REDIS_KEY = "bdcDsfGxbmYs";

    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.vo.BdcDsfRzTjYsVO>
     * @description 获取配置的颜色
     */
    public List<BdcDsfRzTjYsVO> getYsPzList(){
        List<BdcDsfRzTjYsVO> ysVOS = new ArrayList<>();
        // 读取配置的颜色 键值对
        Map<String,String> yspzMap = getYsPz();
        if(MapUtils.isNotEmpty(yspzMap)){
            List<Map> gxbmList = bdcZdFeignService.queryBdcZd("gxbmbz");
            for(Map gxbm : gxbmList){
                String dm = MapUtils.getString(gxbm,"DM");
                BdcDsfRzTjYsVO vo = new BdcDsfRzTjYsVO(dm,MapUtils.getString(gxbm,"MC")
                        ,MapUtils.getString(yspzMap,dm));
                ysVOS.add(vo);
            }
        }
        return ysVOS;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @description 获取颜色配置 如果没有配置过，则初始化默认配置
     */
    private Map<String,String> getYsPz(){
        Map<String,String> yspzMap;
        String pzYs = bdcRedisFeignService.getStringValue(REDIS_KEY);
        if(StringUtils.isBlank(pzYs)){
            // 初始化默认配置
            yspzMap = setDefYs();
        }else{
            yspzMap = JSONObject.parseObject(pzYs,Map.class);
        }
        return yspzMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>dsf
     * @param pzJson
     * @return void
     * @description  保存颜色配置
     */
    public void saveYspz(String pzJson){
        List<BdcDsfRzTjYsVO> yspzList = JSONObject.parseArray(pzJson,BdcDsfRzTjYsVO.class);
        JSONObject gxbmYsMap = new JSONObject();
        if(CollectionUtils.isNotEmpty(yspzList)){
            for(BdcDsfRzTjYsVO vo : yspzList){
                gxbmYsMap.put(vo.getName(),vo.getColor());
            }
            bdcRedisFeignService.addStringValue(REDIS_KEY, gxbmYsMap.toJSONString());
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @description 初始化默认配置
     */
    private Map<String,String> setDefYs(){
        Map<String,String> yspzMap = new HashMap<>();
        List<Map> gxbmList = bdcZdFeignService.queryBdcZd("gxbmbz");
        if(CollectionUtils.isNotEmpty(gxbmList)){
            // 默认颜色数组
            List<String> defYsList = Arrays.asList(DEFAULT_YS.split(","));
            for(int i = 0 ;i < gxbmList.size();i++){
                Map gxbm = gxbmList.get(i);
                yspzMap.put(MapUtils.getString(gxbm,"DM")
                        , i < defYsList.size() ? defYsList.get(i) : defYsList.get(i-defYsList.size()));
            }
            bdcRedisFeignService.addStringValue(REDIS_KEY, JSONObject.toJSONString(yspzMap));
        }
        return yspzMap;
    }
}
