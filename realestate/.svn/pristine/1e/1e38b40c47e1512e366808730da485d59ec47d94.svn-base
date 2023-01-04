package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlXzxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXzxxPlDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXzxxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXzxxVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 流程修正rest服务
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-5-25 17:09
 **/
@RestController
@Api(tags = "流程修正接口服务")
public class BdcSlXzxxRestController extends BaseController implements BdcSlXzxxRestService {

    @Autowired
    BdcSlXzxxService bdcSlXzxxService;
    @Autowired
    Repo repo;

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需求流转信息
     * @date : 2021/5/25 17:54
     */
    @ApiOperation(value = "根据查询参数返回流程修正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlXzxxQO", value = "不动产需求流转信息封装查询对象", dataType = "bdcSlXzxxQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSlXzxxDO queryBdcSlXzxx(@RequestBody BdcSlXzxxQO bdcSlXzxxQO){
        if (!CheckParameter.checkAnyParameter(bdcSlXzxxQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ bdcSlXzxxQO.toString());
        }
        BdcSlXzxxDO bdcSlXzxxDO = new BdcSlXzxxDO();
        BeanUtils.copyProperties(bdcSlXzxxQO,bdcSlXzxxDO);
        return bdcSlXzxxService.queryBdcSlXzxx(bdcSlXzxxDO);
    }

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存流程修正信息
     * @date : 2021/2/23 17:54
     */
    @ApiOperation(value = "保存流程修正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlXzxxDO", value = "不动产需求流转信息封装对象", dataType = "bdcSlXzxxDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSlXzxxDO saveBdcSlXzxx(@RequestBody BdcSlXzxxDO bdcSlXzxxDO) {
        return bdcSlXzxxService.saveBdcSlXzxx(bdcSlXzxxDO);
    }

    /**
     * @param xzxxid
     * @param xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除需求流转信息
     * @date : 2021/2/23 17:54
     */
    @Override
    @ApiOperation(value = "删除不动产流程修正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xzxxid", value = "需求信息id",required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String",required = false, paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcSlXzxx(@RequestParam(value = "xzxxid", required = false) String xzxxid, @RequestParam(value = "xmid", required = false) String xmid) {
        return bdcSlXzxxService.deleteBdcSlXzxx(xzxxid,xmid);
    }


    @Override
    @ApiOperation(value = "用于删除流程时，同时删除流程修正")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID",required = false, dataType = "String", paramType = "query"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteBdcSlXzxxByGzlslid(@RequestParam(value = "processInsId") String processInsId) {
        bdcSlXzxxService.deleteBdcSlXzxxByGzlslid(processInsId);
    }

    @Override
    @ApiOperation(value = "用于创建修正流程时，插入数据到BDC_SL_XZXX")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcSlCshDTO", value = "", required = false, dataType = "BdcSlCshDTO", paramType = "query")
    })
    @ResponseStatus(HttpStatus.OK)
    public void cshBdcXzxx(@RequestParam(value = "processInsId", required = false) String processInsId, @RequestBody BdcSlCshDTO bdcSlCshDTO) {
        bdcSlXzxxService.cshBdcXzxx(processInsId, bdcSlCshDTO);
    }

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/9/27 19:22
     */
    @Override
    @ApiOperation(value = "根据查询参数返回流程修正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlXzxxQO", value = "不动产需求流转信息封装查询对象", dataType = "bdcSlXzxxQO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcSlXzxxDO> listBdcSlXzxx(@RequestBody BdcSlXzxxQO bdcSlXzxxQO) {
        return bdcSlXzxxService.listBdcSlXzxx(bdcSlXzxxQO);
    }

    /**
     * @param pageable
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询修正信息
     * @date : 2021/9/28 10:13
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询修正信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, paramType = "query")})
    public Page<BdcSlXzxxVO> listBdcSlxzxxByPage(Pageable pageable, @RequestParam(value = "gzlslid") String gzlslid) {
        Map<String, Object> map = new HashMap();
        map.put("gzlslid", gzlslid);
        return repo.selectPaging("listBdcSlxzxxByPage", map, pageable);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询批量修正信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, paramType = "query")})
    public Page<BdcSlXzxxPlDTO> listBdcSlxzxxByPagePl(Pageable pageable, @RequestParam(value = "gzlslids") String gzlslids) {
        Map<String, Object> map = new HashMap();
        map.put("gzlslids", gzlslids);
        return repo.selectPaging("listBdcSlxzxxPlByPage", map, pageable);
    }
}
