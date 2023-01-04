package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zedeqiang.com">zedq</a>
 * @version 1.0
 * @date 2021/04/26 14:24
 */
public interface BdcYctbRestService {

    /**
     * 合肥_添加税务明细和缴费明细
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/yctb/add/tax/info/{gzlslid}")
    public YctbCommonResponse yctbAddTaxInfo(@PathVariable(value = "gzlslid") String gzlslid);


}
