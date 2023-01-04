package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.service.rest.etl.WwtsHtbaRestService;
import cn.gtmap.realestate.etl.service.HtbaService;
import cn.gtmap.realestate.etl.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 外网推送合同备案信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-04-13 15:42
 **/
@RestController
public class WwtsHtbaRestController extends BaseController implements WwtsHtbaRestService {

    @Autowired
    HtbaService htbaService;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网推送合同备案信息接口
     * @date : 2021/4/13 15:46
     */
    @Override
    public Object wwtsHtbaxx(@RequestBody HtbaxxDTO htbaxxDTO) {
        return htbaService.wwtsHtbaxx(htbaxxDTO);
    }

    /**
     * @param htbaxxDTO 撤销数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网撤销备案信息
     * @date : 2021/4/13 16:12
     */
    @Override
    public Object wwcxHtbaxx(@RequestBody HtbaxxDTO htbaxxDTO) {
        return htbaService.wwcxHtbaxx(htbaxxDTO);
    }
}
