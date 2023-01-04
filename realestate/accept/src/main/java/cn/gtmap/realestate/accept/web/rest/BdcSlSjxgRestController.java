package cn.gtmap.realestate.accept.web.rest;


import cn.gtmap.realestate.accept.core.service.BdcSlSjxgService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjxgDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSjxgRestService;
import cn.gtmap.realestate.common.util.LogActionConstans;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @description 数据修改restController
 * @date : 2022/11/30
 */
@RestController
@Api(tags = "不动产数据修改服务")
public class BdcSlSjxgRestController extends BaseController implements BdcSlSjxgRestService {

    @Autowired
    BdcSlSjxgService bdcSlSjxgService;


    /**
     * @param gzlslid
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 查询数据修改数据DO
     * @date : 2022/11/30
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "查询数据修改DTO", action = LogActionConstans.QUERY)
    public BdcSlSjxgDO querySlSjxgDO(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSjxgService.querySlSjxgDO(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据工作流实例id删除数据修改数据
     * @date : 2022/11/30
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "根据工作流实例id删除数据修改数据", action = LogActionConstans.DELETE)
    public int deleteSlSjxg(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSjxgService.deleteSlSjxg(gzlslid);
    }

    /**
     * @param bdcSlSjxgsqDO@author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 新增数据修改数据
     * @date : 2022/11/30
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "新增数据修改数据", action = LogActionConstans.CREATE)
    public BdcSlSjxgDO saveSlSjxg(@RequestBody BdcSlSjxgDO bdcSlSjxgsqDO) {
        return bdcSlSjxgService.saveSlSjxg(bdcSlSjxgsqDO);
    }
}
