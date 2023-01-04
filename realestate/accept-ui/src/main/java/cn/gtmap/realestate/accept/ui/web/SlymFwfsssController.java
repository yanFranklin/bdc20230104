package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/5
 * @description 受理页面房屋附属设施相关控制层
 */
@Controller
@RequestMapping("/slym/fwfsss")
public class SlymFwfsssController {
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;


    /**
     * @param xmid 项目id
     * @return 房屋附属设施集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目id获取房屋附属设施集合
     */
    @ResponseBody
    @GetMapping("/list/xm")
    public Object listFwfsssByXmid(String xmid) {
        List<BdcFwfsssDO> bdcFwfsssDOList =bdcQllxFeignService.listFwfsss(xmid);
        if(CollectionUtils.isEmpty(bdcFwfsssDOList)){
            bdcFwfsssDOList = Collections.emptyList();
        }
        return bdcFwfsssDOList;

    }

    /**
     * @param json 前台传输项目集合JSON
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新房屋附属设施
     */
    @ResponseBody
    @PatchMapping(value = "/list")
    public Integer updateBdcFwfsss(@RequestBody String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                count += bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcFwfsssDO.class.getName());
            }
        }
        return count;
    }




}
