package cn.gtmap.realestate.inquiry.ui.web.rest.jsc;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.*;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.jsc.BdcXuanchengJscFeignService;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 宣城驾驶舱
 *
 * @author wangyinghao
 */
@RestController
@RequestMapping(value = "/rest/v1.0/jsc/xuancheng")
public class BdcXuanchengJscController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXuanchengJscController.class);

    @Autowired
    BdcXuanchengJscFeignService bdcXuanchengJscFeignService;

    @Autowired
    RedisUtils redisUtils;

    public static final String REDIS_KEY_PREFIX = "XuanchengJsc_";

    /**
     * 林权登记总面积
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/queryZmj")
    public Object queryJscDjZmj(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "DjZmj" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseArray(stringValue, Integer.class);
        }
        List<Integer> integers = bdcXuanchengJscFeignService.queryJscDjZmj(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(integers), 3600);
        return integers;
    }

    /**
     * 登记数量图表汇总
     *
     * @param info
     * @return
     */
    @PostMapping("/queryDjsl")
    public Object queryJscDjslList(@RequestBody JscCommomQO info) {
        String rediskey = REDIS_KEY_PREFIX + "Djsl" + info.getTimeFrame() + info.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseObject(stringValue);
        }
        Map<String, List<JscDjslDTO>> stringListMap = bdcXuanchengJscFeignService.queryJscDjslList(info);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(stringListMap), 3600);
        return stringListMap;
    }

    /**
     * 驾驶舱 概况
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/querySummary")
    public Object queryJscSummary(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "querySummary" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseArray(stringValue);
        }
        List<Integer> integers = bdcXuanchengJscFeignService.queryJscSummary(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(integers), 3600);
        return integers;
    }

    /**
     * 驾驶舱 林权权利
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/queryQl")
    public Object queryJscQl(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryQl" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseObject(stringValue);
        }
        Map<String, List<JscLqqlDTO>> stringListMap = bdcXuanchengJscFeignService.queryJscQl(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(stringListMap), 3600);
        return stringListMap;
    }

    /**
     * 驾驶舱 登记趋势
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/queryTrend")
    public Object queryJscTrend(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryTrend" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseObject(stringValue);
        }
        JscLqqsDTO jscLqqsDTO = bdcXuanchengJscFeignService.queryJscTrend(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(jscLqqsDTO), 3600);
        return jscLqqsDTO;
    }

    /**
     * 驾驶舱 交易情况（转移登记）
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/queryTransaction")
    public Object queryJscTransaction(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryTransaction" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseArray(stringValue);
        }
        List<JscLqjyDTO> jscLqjyDTOS = bdcXuanchengJscFeignService.queryJscTransaction(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(jscLqjyDTOS), 3600);
        return jscLqjyDTOS;
    }


    /**
     * 地图
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/queryQxdjsl")
    public Object queryJscMapData(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryQxdjsl" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseObject(stringValue);
        }
        Map<String, JscLqjyDTO> stringJscLqjyDTOMap = bdcXuanchengJscFeignService.queryJscDjslMap(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(stringJscLqjyDTOMap), 3600);
        return stringJscLqjyDTOMap;
    }


    /**
     * 历史遗留问题柱图
     *
     * @param JscCommomQO
     * @return
     */
    @PostMapping("/queryLsylwt")
    public Object queryLsylwt(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryLsylwt" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseObject(stringValue);
        }
        Map<String, List<JscLsylwtDTO>> stringListMap = bdcXuanchengJscFeignService.queryLsylwt(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(stringListMap), 3600);
        return stringListMap;
    }


    /**
     * 未解决问题概况区县占比图
     *
     * @param info
     * @return
     */
    @PostMapping("/queryLsylwtWjjQxzb")
    public Object queryLsylwtWjjQxzb(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryLsylwtWjjQxzb" + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseArray(stringValue);
        }
        List<JscLsylwtDTO> jscLsylwtDTOS = bdcXuanchengJscFeignService.queryLsylwtWjjQxzb(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(jscLsylwtDTOS), 3600);
        return jscLsylwtDTOS;
    }

    /**
     * 区县中未解决问题柱图（要按照问题类型进行补充）
     *
     * @param jscCommomQO@return
     */
    @PostMapping("/queryLsylwtQxWtlxzb")
    public Object queryLsylwtQxWtlxzb(@RequestBody JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "queryLsylwtQxWtlxzb"
                + jscCommomQO.getTimeFrame() + jscCommomQO.getSummaryDimension();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            //return JSON.parseArray(stringValue);
        }
        List<JscLsylwtDTO> jscLsylwtDTOS = bdcXuanchengJscFeignService.queryLsylwtQxWtlxzb(jscCommomQO);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(jscLsylwtDTOS), 3600);
        return jscLsylwtDTOS;
    }
}
