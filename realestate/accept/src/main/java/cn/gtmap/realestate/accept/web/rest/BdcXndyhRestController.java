package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcXndyhService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcXndyhRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/28
 * @description 虚拟单元号服务
 */
@RestController
@Api(tags = "虚拟单元号服务")
public class BdcXndyhRestController extends BaseController implements BdcXndyhRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 虚拟单元号服务
     */
    @Autowired
    private BdcXndyhService bdcXndyhService;

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @return 不动产受理项目前台
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 判断虚拟单元号
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "判断虚拟单元号", notes = "判断虚拟单元号服务")
    @ApiImplicitParam(name = "bdcCshSlxmDTO", value = "不动产初始化受理项目", required = true, dataType = "BdcCshSlxmDTO")
    public List<BdcSlYwxxDTO> listYzXndyh(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = null;
        if (bdcCshSlxmDTO != null) {
            bdcSlYwxxDTOList = bdcXndyhService.listYzXndyh(bdcCshSlxmDTO.getBdcSlYwxxDTOList());
        }
        return bdcSlYwxxDTOList;
    }
}
