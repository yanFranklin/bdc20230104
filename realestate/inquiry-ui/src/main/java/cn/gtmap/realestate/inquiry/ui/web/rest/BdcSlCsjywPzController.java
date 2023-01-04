package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlCsjPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理长三角业务配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 14:26
 **/
@RestController
@RequestMapping("/rest/v1.0/csjpz")
public class BdcSlCsjywPzController extends BaseController {
    @Autowired
    BdcSlCsjPzFeignService bdcSlCsjPzFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 加载配置数据
     * @date : 2022/5/10 14:36
     */
    @GetMapping("/{pzid}")
    public Object queryCsjpzxx(@PathVariable(value = "pzid") String pzid) {
        if (StringUtils.isNotBlank(pzid)) {
            BdcSlCsjpzDO bdcSlCsjpzQO = new BdcSlCsjpzDO();
            bdcSlCsjpzQO.setPzid(pzid);
            List<BdcSlCsjpzDO> bdcSlCsjpzDOList = bdcSlCsjPzFeignService.listCsjpz(bdcSlCsjpzQO);
            if (CollectionUtils.isNotEmpty(bdcSlCsjpzDOList)) {
                return bdcSlCsjpzDOList.get(0);
            }
        }
        return new BdcSlCsjpzDO();
    }

    /**
     * @param pzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除数据
     * @date : 2022/5/10 14:38
     */
    @DeleteMapping()
    public void deleteCsjpzxx(String pzid) {
        if (StringUtils.isNotBlank(pzid)) {
            bdcSlCsjPzFeignService.deleteCsjpz(pzid);
        }
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存配置信息
     * @date : 2022/5/10 14:39
     */
    @PostMapping()
    public Object saveCsjpzxx(@RequestBody String json) {
        if (StringUtils.isNotBlank(json)) {
            BdcSlCsjpzDO bdcSlCsjpzDO = JSON.parseObject(json, BdcSlCsjpzDO.class);
            if (StringUtils.isBlank(bdcSlCsjpzDO.getPzid())) {
                bdcSlCsjpzDO = bdcSlCsjPzFeignService.saveCsjpz(bdcSlCsjpzDO);
            } else {
                bdcSlEntityFeignService.updateByJsonEntity(json, bdcSlCsjpzDO.getClass().getName());
            }
            return bdcSlCsjpzDO;
        }
        return null;
    }
}
