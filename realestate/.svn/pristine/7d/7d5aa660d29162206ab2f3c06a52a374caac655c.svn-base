package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.ObjectUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/25
 * @description 廉防系统独立字典服务（需要将登记字典数据同步，廉防读取自身字典表）
 */
@RestController
@RequestMapping("/rest/v1.0/bdcZd")
public class LfZdRestController {
    private static Logger logger = LoggerFactory.getLogger(LfZdRestController.class);

    @Autowired
    private EntityMapper entityMapper;
    /**
     * 行政区划服务
     */
    @Autowired
    private RegionManagerClient regionManagerClient;

    /**
     * 全部字典项
     */
    public static final Map<String, List<Map>> zdMap = new ConcurrentHashMap<>(128);

    /**
     * 获取所有字典项
     * @return {Map} 字典值集合
     */
    @GetMapping("/list")
    public Map<String, List<Map>> listBdcZd() {
        // 不用common包公共字典逻辑，因为廉防只用很少的字典表，免去同步表或排除无用表等麻烦
        this.getZdValue("zslx", new BdcZdZslxDO());
        this.getZdValue("zssyqk", new BdcZdZssyqkDO());
        this.getZdValue("zjlx", new BdcZdZjlxDO());
        this.getZdValue("ycbjyy", new BdcZdYcbjyyDO());
        this.getZdValue("yzhzfyy", new BdcZdYzhzfyyDO());

        zdMap.put("xzqh", listXzqh());
        return zdMap;
    }

    /**
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     * @version 2021/09/8
     * @description 获取行政区划
     */
    @GetMapping("/xzqh")
    public List<Map> listXzqh() {
        List<Map> result = new ArrayList<>();
        List<RegionDto> regionDtolist = new ArrayList<>();
        // 获取省级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(1));
        // 获取市级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(2));
        // 获取县级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(3));

        if(CollectionUtils.isNotEmpty(regionDtolist)){
            for(RegionDto regionDto : regionDtolist){
                Map<String, Object> region = new HashMap<>(3);
                region.put("qhdm", regionDto.getCode());
                region.put("qhmc", regionDto.getName());
                region.put("qhjb", regionDto.getLevel());
                result.add(region);
            }
        }
        return result;
    }

    /**
     * 查询指定字典值
     * @param zdlx 字典类型
     * @param zdType 字典对象
     */
    private <T> void getZdValue(String zdlx, T zdType) {
        try {
            if(!zdMap.containsKey(zdlx)) {
                List zdList = entityMapper.select(zdType.getClass().newInstance());

                if(CollectionUtils.isNotEmpty(zdList)) {
                    List<Map> zdValueList = new ArrayList<>();
                    for(Object item : zdList) {
                        Map map = new HashMap();
                        map.put("DM", ObjectUtils.getClassValue(item, "dm"));
                        map.put("MC", ObjectUtils.getClassValue(item, "mc"));
                        zdValueList.add(map);
                    }
                    zdMap.put(zdlx, zdValueList);
                }
            }
        } catch (Exception e) {
            logger.error("获取字典{}失败", zdlx, e);
        }
    }
}
