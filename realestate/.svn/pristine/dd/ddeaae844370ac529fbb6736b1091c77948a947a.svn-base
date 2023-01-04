package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYyQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYyXxCxRestService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import cn.gtmap.realestate.inquiry.service.BdcYyXxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-11
 * @description 不动产登记异议信息查询
 */
@RestController
@Api(tags = "不动产登记异议信息查询")
public class BdcYyXxCxRestController implements BdcYyXxCxRestService {
    @Autowired
    private BdcYyXxService bdcYyXxService;

    /**
     * @param pageable
     * @param bdcYyQOJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询不动产异议信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "异议查询#YYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcYyQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcYyVO> listBdcYyByPage(Pageable pageable, @RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson) {
        return bdcYyXxService.listBdcYyxxByPage(pageable, JSONObject.parseObject(bdcYyQOJson, BdcYyQO.class));
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "常州，异议查询分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcYyQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcYyVO> listBdcYyByPageCz(Pageable pageable, @RequestParam(name = "bdcYyQOJson", required = false)String bdcYyQOJson) {
        return bdcYyXxService.listBdcYyxxByPageCz(pageable, JSONObject.parseObject(bdcYyQOJson, BdcYyQO.class));
    }

    /**
     * @param bdcYyQOJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产异议信息
     */
    @Override
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcYyQOJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List listBdcYy(@RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson) {
        return bdcYyXxService.listBdcYyxx(JSONObject.parseObject(bdcYyQOJson, BdcYyQO.class));
    }

    @Override
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcYyQOJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List listBdcYyCz(@RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson) {
        return bdcYyXxService.listBdcYyxxCz(JSONObject.parseObject(bdcYyQOJson, BdcYyQO.class));
    }

    /**
     * @param bdcYyDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新 异议
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新 异议")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYyDOList", value = "异议信息", paramType = "body", dataType = "BdcYyDO")})
    public int updateBdcYy(@RequestBody List<BdcYyDO> bdcYyDOList) {
        return bdcYyXxService.updateBdcYy(bdcYyDOList);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询异议
     */
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List listBdcYyByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcYyXxService.listBdcYyByXmid(xmid);
    }

    @Override
    public List listBdcYyByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh) {
        return bdcYyXxService.listBdcYyByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @Override
    public List<BdcYyDO> listBdcYyByBdcdyhs(@RequestBody List<String> bdcdyhList) {
        return bdcYyXxService.listBdcYyByBdcdyhs(bdcdyhList);
    }
}
