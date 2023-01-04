package cn.gtmap.realestate.common.core.service.rest.pub;

import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/16
 * @description 查询预告信息
 */
public interface YgxxRestService {

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzmh 不动产权证明号
     * @param qlrmc 权利人名称
     * @param qlrzjh 权利人证件号
     * @return
     * @description 通过不动产权证明号、权利人名称、权利人证件号查询预告信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/ygxx/cqxx-bdcqzh-qlr")
    ResponseEntityDTO queryYgxxByBdcqzmhAndQlrxx(@RequestParam(value = "bdcqzmh",required = false) String bdcqzmh, @RequestParam(value = "qlrmc" ,required = false) String qlrmc, @RequestParam(value = "qlrzjh" ,required = false) String qlrzjh);

}
