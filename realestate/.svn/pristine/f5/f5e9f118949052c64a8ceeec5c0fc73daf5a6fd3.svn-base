package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYgRestService;
import cn.gtmap.realestate.inquiry.service.BdcYgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-11
 * @description 不动产登记异议信息查询
 */
@RestController
public class BdcYgRestController implements BdcYgRestService {

    @Autowired
    private BdcYgService bdcYgService;

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询预告信息
     */
    @Override
    public List<BdcYgDO> listBdcYgByBdcdyhs(@RequestBody List<String> bdcdyhList) {
        return bdcYgService.listBdcYyByBdcdyhs(bdcdyhList);
    }
    /**
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @param djh 地籍号
     * @description 根据地籍号查询预告
     */
    @Override
    public List<BdcYgDO> listBdcYgByDjh(@RequestParam(value = "djh") String djh) {
        return bdcYgService.listBdcYgByDjh(djh);
    }
}
