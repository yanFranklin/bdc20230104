package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlDyaqFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/27
 * @description 一窗页面表单权利信息请求处理
 */
@RestController
@RequestMapping("/rest/v1.0/ycym")
public class YcymQlxxController {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理抵押权服务
     */
    @Autowired
    BdcSlDyaqFeignService bdcSlDyaqFeignService;

    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询受理抵押信息
     */
    @GetMapping("/qlxx/dyaq/{xmid}")
    public BdcSlDyaqDO queryBdcSlDyaq(@PathVariable("xmid") String xmid) {
        return bdcSlDyaqFeignService.queryBdcSlDyaqByXmid(xmid);

    }

    /**
     * @param json 受理抵押表单json数据
     * @return 更新记录条数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理抵押信息
     */
    @PutMapping(value = "/qlxx/dyaq")
    public int updateSlDyaq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return bdcSlEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcSlDyaqDO.class.getName());
    }
}
