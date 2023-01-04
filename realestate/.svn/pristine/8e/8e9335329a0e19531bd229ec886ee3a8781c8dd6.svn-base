package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcYjSfDdxxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/12
 * @description 不动产银行月结收费订单信息REST服务
 */
public interface BdcYjSfDdxxRestService {

    /**
     * 作废月结订单信息内容
     * @param yjdhList 月结订单号集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjsf/ddxx/zf")
    void zfYjddxx(@RequestBody List<String> yjdhList);

    /**
     * 更新月结订单状态
     * @param bdcYjSfDdxxDO 月结收费订单信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/realestate-accept/rest/v1.0/yjsf/ddzt")
    void modifyYjDdzt(@RequestBody BdcYjSfDdxxDO bdcYjSfDdxxDO);

    /**
     * 根据月结单号查询月结收费订单信息
     * @param yjdh 月结单号
     * @return 月结收费订单信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/yjsf/ddxx/{yjdh}")
    BdcYjSfDdxxDO queryBdcYjSfDdxxByYjdh(@PathVariable(name="yjdh") String yjdh);

    /**
     * 获取合一支付缴费途径默认配置
     * @return 配置内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/yjsf/ddxx/hyzfjftj/config")
    String getHyzfjftjConfig();

}



