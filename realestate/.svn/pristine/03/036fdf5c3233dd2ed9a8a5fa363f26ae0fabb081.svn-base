package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.ZdJtcyService;
import cn.gtmap.realestate.common.core.domain.building.ZdJtcyDO;
import cn.gtmap.realestate.common.core.service.rest.building.ZdJtcyRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description
 */
@RestController
@Api(tags = "宗地家庭成员rest服务")
public class ZdJtcyRestController implements ZdJtcyRestService {

    @Autowired
    private ZdJtcyService zdJtcyService;

    @Override
    @ApiOperation(value = "根据地籍号查询宗地建设用地使用表")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ZdJtcyDO> listZdJtcyByDjh(@PathVariable(name = "djh") String djh){
        return zdJtcyService.listZdJtcyByDjh(djh);

    }
}
