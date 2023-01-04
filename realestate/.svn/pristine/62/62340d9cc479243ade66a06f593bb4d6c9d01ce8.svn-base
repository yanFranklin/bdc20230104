package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.qo.etl.HtbaspfQO;
import cn.gtmap.realestate.common.core.service.rest.etl.BdcHtbaRestService;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.etl.service.BdcHtbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0 2022/11/22
 * @description 非登记流程信息注销查询合同备案数据
 */

@RestController
public class BdcHtbaRestController implements BdcHtbaRestService {


    @Autowired
    private BdcHtbaService bdcHtbaService;

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 合同备案分页查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "合同备案分页查询",  action = LogActionConstans.QUERY)
    public Page<HtbaspfQO> listHtbaByPageJson(Pageable pageable, @RequestParam(name = "paramJson", required = false) String paramJson) {
        return bdcHtbaService.listHtbaByPageJson(pageable, paramJson);
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 注销合同，更新数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "注销合同，更新数据",  action = LogActionConstans.UPDATE)
    public Integer updateHtzt(@RequestParam(name = "baid") String baid, @RequestParam(name = "tfyy") String tfyy) {
        return bdcHtbaService.updateHtzt(baid, tfyy);
    }

}
