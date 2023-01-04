package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzGdZgzRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzGdZgzService;
import cn.gtmap.realestate.engine.util.Constants;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/7 15:51
 * @description 规则系统固定子规则服务接口
 */
@RestController
@Api(tags = "规则系统固定子规则服务接口")
public class BdcGzGdZgzRestController implements BdcGzGdZgzRestService {
    @Autowired
    BdcGzGdZgzService bdcGzGdZgzService;

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zgzParamJson zgzParamJson
     * @return List<BdcGzZgzDO>
     * @description 查询固定子规则列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询固定子规则列表服务")
    @ApiImplicitParam(name = "zgzParamJson", value = "固定子规则查询参数Json", dataType = "BdcGzZgzQO", required = false, paramType = "query")
    public List<BdcGzZgzDO> listBdcGzGdZgz(String zgzParamJson) {
        return bdcGzGdZgzService.listBdcGzGdZgz(JSON.parseObject(zgzParamJson, BdcGzZgzQO.class));
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDTO bdcGzZgzDTO
     * @return String
     * @description 保存固定子规则信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("保存固定子规则信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzZgzDTO", value = "子规则信息实体", required = true, paramType = "body")})
    public String saveBdcGzGdZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO) {
        return bdcGzGdZgzService.saveBdcGzGdZgz(bdcGzZgzDTO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDOList bdcGzZgzDOList
     * @description 删除固定子规则
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("删除固定子规则服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzZgzDOList", value = "规则集合", dataType = "BdcGzZgzDO", required = true, paramType = "body")})
    public void deleteBdcGzGdZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOList) {
        bdcGzGdZgzService.deleteBdcGzZgz(bdcGzZgzDOList);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param gzid gzid
     * @return 固定子规则DTO
     */
    @Override
    @ApiOperation(value = "查询固定子规则DTO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzid", value = "规则id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcGzZgzDTO queryBdcGzGdZgzDTO(@PathVariable("gzid") String gzid) {
        return bdcGzGdZgzService.queryBdcGzGdZgzDTOByGzid(gzid);
    }
}
