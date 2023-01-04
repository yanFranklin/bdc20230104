package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.HfJyztFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-23
 * @description 合肥版本 查询交易状态
 */
@RestController
@RequestMapping("/hf/jyzt")
@ConfigurationProperties(prefix = "lpbzt")
public class HfJyztController {

    /**
     * 配置信息
     */
    private  Map<String,String> pzMap = new HashMap();

    @Autowired
    private HfJyztFeignService hfJyztFeignService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ysfwbmList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @description 根据ysfwbm查询交易状态
     */
    @GetMapping("/byysfwbm")
    public List<HfJyztResponseDTO> queryJyztByYsfwbm(@RequestParam(name = "ysfwbmList") List<String> ysfwbmList,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        List<HfJyztResponseDTO> jyztList = hfJyztFeignService.queryJyztByYsfwbm(ysfwbmList,qjgldm);
        if(CollectionUtils.isNotEmpty(jyztList)){
            for(HfJyztResponseDTO hfJyztResponseDTO:jyztList){
                if(StringUtils.isNotBlank(hfJyztResponseDTO.getJyzt()) && pzMap.containsKey(hfJyztResponseDTO.getJyzt())){
                    hfJyztResponseDTO.setColor(pzMap.get(hfJyztResponseDTO.getJyzt()));
                }
            }
        }
        return jyztList;
    }

    public Map<String, String> getPzMap() {
        if(MapUtils.isNotEmpty(pzMap)){
            Map<String,String> map = new HashMap();
            for(String key:pzMap.keySet()){
                if(StringUtils.isNotBlank(key)){
                    String[] keys=key.split(",|，");
                    String value=pzMap.get(key);
                    for(String str:keys){
                        map.put(str,value);
                    }
                }
            }
            pzMap=map;
        }
        return pzMap;
    }
}
