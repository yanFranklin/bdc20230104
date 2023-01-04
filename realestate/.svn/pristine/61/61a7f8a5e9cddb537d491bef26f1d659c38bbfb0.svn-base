package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxsqsDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxsqsQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCxsqsRestService;
import cn.gtmap.realestate.inquiry.service.BdcCxsqsService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/22
 * @description 查询申请书restController
 */
@RestController
@Api(tags = "不动产查询申请书服务接口")
public class BdcCxsqsRestController implements BdcCxsqsRestService {
    @Autowired
    BdcCxsqsService bdcCxsqsService;

    /**
     * @param bdcCxsqsDTO 查询申请书DTO对象
     * @return BdcCxsqsDTO 查询信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存不动产查询申请书
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存查询申请书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCxsqsDTO", value = "查询申请书DTO对象", required = true, paramType = "body")})
    public BdcCxsqsDTO saveOrUpdateBdcCxsqs(@RequestBody BdcCxsqsDTO bdcCxsqsDTO) {
        return bdcCxsqsService.saveOrUpdateBdcCxsqs(bdcCxsqsDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询申请书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCxsqsQO", value = "查询申请书QO对象", required = true, paramType = "body")})
    public List<BdcCxsqsDTO> queryBdcCxSqs(@RequestBody BdcCxsqsQO bdcCxsqsQO) {
        return bdcCxsqsService.queryBdcCxSqs(bdcCxsqsQO);
    }

    @Override
    public Page<BdcCxsqsDO> listBdcCxsqsPage(Pageable pageable, @RequestParam(name = "cxsqsParamJson", required = false) String cxsqsParamJson) {
        return bdcCxsqsService.listBdcCxsqsPage(pageable, JSON.parseObject(cxsqsParamJson, BdcCxsqsQO.class));
    }
}
