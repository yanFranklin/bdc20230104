package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.FwlxBgService;
import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.building.FwLxBgRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-12-04
 * @description 房屋类型变更服务
 */
@RestController
@Api(tags = "房屋类型变更服务")
public class FwLxBgRestController extends BaseController implements FwLxBgRestService {

    @Autowired
    private FwlxBgService fwlxBgService;

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwlxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @description 
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋类型变更")
    public void fwLxBg(@RequestBody FwlxBgRequestDTO fwlxBgRequestDTO) {
        BdcdyfwlxBgService service = fwlxBgService.getBean(fwlxBgRequestDTO);
        if(service != null){
            service.executeBg(fwlxBgRequestDTO);
        }else{
            throw new MissingArgumentException("fwlx");
        }
    }
}
