package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaspfQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface BdcHtbaRestService {



    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 合同备案分页查询
     */
    @PostMapping(value = "/realestate-etl/rest/v1.0/query/htba/page")
    Page<HtbaspfQO> listHtbaByPageJson(Pageable pageable, @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 注销合同，更新数据
     */
    @PostMapping(value = "/realestate-etl/rest/v1.0/update/htzt")
    Integer updateHtzt(@RequestParam(name = "baid") String baid, @RequestParam(name = "tfyy") String tfyy);

}
