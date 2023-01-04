package cn.gtmap.realestate.inquiry.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;

import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcGgxxRestService;
import cn.gtmap.realestate.inquiry.service.changzhou.BdcGgxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/07/21/14:09
 * @Description:
 */
@RestController
public class BdcGgxxRestController implements BdcGgxxRestService {

    @Autowired
    private BdcGgxxService bdcGgxxService;
    @Override
    public int pushBdcGgxxToGgtz(String processInsId) {
        return 0;
    }

    @Override
    public int updateBdcGgxx(BdcGgDO bdcGgDO) {
        return 0;
    }


    @Override
    public BdcGgDO queryBdcGgxx(@RequestParam(value="id") String ggid) {
        BdcGgDO bdcGgDO = bdcGgxxService.queryBdcGgxx(ggid);
        return bdcGgDO;
    }
}
