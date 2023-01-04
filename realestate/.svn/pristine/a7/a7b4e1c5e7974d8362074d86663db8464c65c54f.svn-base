package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJcggService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJcggDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJcggRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理继承公告restcontroller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-01 16:04
 **/
@RestController
@Api(tags = "受理继承公告restcontroller")
public class BdcSlJcggRestController extends BaseController implements BdcSlJcggRestService {

    @Autowired
    BdcSlJcggService bdcSlJcggService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询继承公告信息
     * @date : 2021/12/1 16:08
     */
    @Override
    public List<BdcSlJcggDO> listBdcSlJcgg(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlJcggService.listBdcSlJcgg(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除继承公告信息
     * @date : 2021/12/1 16:08
     */
    @Override
    public void deleteBdcSlJcgg(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcSlJcggService.deleteBdcSlJcgg(gzlslid);
    }

    /**
     * @param bdcSlJcggDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存公告信息
     * @date : 2021/12/1 16:08
     */
    @Override
    public BdcSlJcggDO saveBdcSlJcgg(@RequestBody BdcSlJcggDO bdcSlJcggDO) {
        return bdcSlJcggService.saveBdcSlJcgg(bdcSlJcggDO);
    }
}
