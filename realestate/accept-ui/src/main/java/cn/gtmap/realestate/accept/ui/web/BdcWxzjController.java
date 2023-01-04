package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlWxzjFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/22
 * @description 维修资金信息控制类
 */

@Controller
@RequestMapping("/wxzj")
public class BdcWxzjController {

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcSlWxzjFeignService bdcSlWxzjFeignService;

    /**
     * 查询维修资金收费项目信息
     *
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @ResponseBody
    @GetMapping("/queryWxjjxx")
    public Object queryWxjjxx(@RequestParam(value = "gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }
        BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
        bdcSlWxjjxxDO.setGzlslid(gzlslid);
        return this.bdcSlWxzjFeignService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);
    }

    /**
     * 维修资金查询接口，调用一卡清接口生成订单信息
     *
     * @param xmid 项目ID
     * @return 订单信息（订单编号、应收金额）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/wxzjcx")
    public Object wxzjcx(@RequestParam(value = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("未获取到项目ID信息");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("未获取到项目信息");
        }
        Map<String, Object> param = new HashMap<>(2);
        param.put("bdcdyh", bdcXmDOList.get(0).getBdcdyh());
        param.put("jzmj", bdcXmDOList.get(0).getDzwmj());
        return this.exchangeInterfaceFeignService.requestInterface("ykq_create_order", param);
    }

    /**
     * 生成维修资金收费项目信息
     *
     * @param bdcSlWxjjxxDO 不动产维修基金信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/scwxzjxx")
    public void saveWxzjxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        if(StringUtils.isAnyBlank(bdcSlWxjjxxDO.getGzlslid(), bdcSlWxjjxxDO.getXmid())){
            throw new MissingArgumentException("缺失参数项目ID或工作流实例ID");
        }
        this.bdcSlWxzjFeignService.generateWxzjXmxx(bdcSlWxjjxxDO);
    }

    /**
     * 通知维修资金
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return object 通知结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tzwxzj")
    public Object tzwxzj(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少参数工作流实例ID");
        }
        return bdcSlWxzjFeignService.noticeWxzj(xmid, gzlslid);
    }


    /**
     * 删除维修资金收费项目信息
     *
     * @param wxjjxxid 维修基金id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @ResponseBody
    @DeleteMapping("/deleteWxjjxxByWxjjxxid")
    public Object deleteWxjjxxByWxjjxxid(@RequestParam(value = "wxjjxxid") String wxjjxxid) {
        if(StringUtils.isBlank(wxjjxxid)){
            throw new MissingArgumentException("缺失参数维修基金信息id");
        }
        return this.bdcSlWxzjFeignService.deleteWxjjxxByWxjjxxid(wxjjxxid);
    }
}
