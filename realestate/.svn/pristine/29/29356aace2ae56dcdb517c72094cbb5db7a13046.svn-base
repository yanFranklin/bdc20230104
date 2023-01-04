package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcGzdjFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description 更正登记
 */
@RestController
@RequestMapping("/rest/v1.0/gzdj")
public class BdcGzdjController {

    @Autowired
    BdcGzdjFeignService bdcGzdjFeignService;

    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    /**
     * @param xmid 项目ID
     * @return 更正信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目ID查询更正信息
     */
    @GetMapping("/{xmid}")
    public List<BdcGzdjDO> listBdcGzdj(@PathVariable("xmid") String xmid) {
        return bdcGzdjFeignService.listBdcGzdjByXmid(xmid);
    }

    /**
     * @param json 更正信息json数据
     * @return 更新记录条数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新不动产更正信息
     */
    @PutMapping(value = "")
    public int updateBdcGzdj(@RequestBody String json) {
        if(StringUtils.isBlank(json)) {
            throw new AppException("更新更正信息内容为空");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcGzdjDO.class.getName());

    }
}
