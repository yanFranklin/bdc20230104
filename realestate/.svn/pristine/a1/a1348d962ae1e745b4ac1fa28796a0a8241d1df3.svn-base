package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.BdcdyxxService;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-05
 * @description
 */
@RestController
@Api(tags = "同步业务系统权籍不动产单元信息服务")
public class BdcdyxxRestController implements BdcdyxxRestService{

    @Autowired
    private BdcdyxxService bdcdyxxService;

    /**
     * @param bdcdyxxPlRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量更新不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新不动产单元信息")
    public void updateBdcdyxxPl(@RequestBody BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bdcdyxxService.updateBdcdyxxPl(bdcdyxxPlRequestDTO);
    }

    /**
     * @param bdcdyxxRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新不动产单元信息")
    public void updateBdcdyxx(@RequestBody BdcdyxxRequestDTO bdcdyxxRequestDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }
}
