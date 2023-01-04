package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwJsydzrzxxService;
import cn.gtmap.realestate.building.core.service.ZdJsydsybService;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJsydsybDO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.service.rest.building.ZdJsydLhxxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description
 */
@RestController
@Api(tags = "宗地建设用地量化rest服务")
public class ZdJsydLhxxRestController implements ZdJsydLhxxRestService {

    @Autowired
    private ZdJsydsybService zdJsydsybService;

    @Autowired
    private FwJsydzrzxxService fwJsydzrzxxService;

    @Autowired
    private Repo repo;

    @Override
    @ApiOperation(value = "根据地籍号查询宗地建设用地使用表")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdJsydsybDO queryZdJsydsybByDjh(@PathVariable(name = "djh") String djh){
        return zdJsydsybService.queryZdJsydsybByDjh(djh);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据建设用地自然幢主键集合查询建设用地自然幢列表")
    public List<FwJsydzrzxxDO> listFwJsydzrzxx(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO){
        return fwJsydzrzxxService.listFwJsydzrzxx(fwJsydzrzxxQO);

    }

    @Override
    @ApiOperation(value = "分页查询建设用地自然幢列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数Json", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<FwJsydzrzxxDTO> listFwJsydzrzxxByPageJson(Pageable pageable,
                                                          @RequestParam(name = "paramJson", required = false) String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson, Map.class);
        }
        return fwJsydzrzxxService.listFwJsydzrzxxByPage(pageable, map);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据建设用地自然幢主键集合查询建设用地自然幢和状态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwJsydzrzxxQO", value = "建设用地自然幢信息查询QO", required = true, dataType = "FwJsydzrzxxQO", paramType = "body")})
    public List<FwJsydzrzxxDTO> listFwJsydzrzxxWithZt(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO) {
        return fwJsydzrzxxService.listFwJsydzrzxxWithZt(fwJsydzrzxxQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据建设用地自然幢主键集合更新量化抵押权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwJsydzrzxxQO", value = "建设用地自然幢信息查询QO", required = true, dataType = "FwJsydzrzxxQO", paramType = "body")})
    public void updateFwJsydzrzxxLhdycsPl(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO) {
        this.fwJsydzrzxxService.updateFwJsydzrzxxLhdycsPl(fwJsydzrzxxQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据建设用地自然幢信息查询QO条件，更新状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwJsydzrzxxQO", value = "建设用地自然幢信息查询QO", required = true, dataType = "FwJsydzrzxxQO", paramType = "body")})
    public void updateFwJsydzrzxxZtPl(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO) {
        this.fwJsydzrzxxService.updateFwJsydzrzxxZtPl(fwJsydzrzxxQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据建设用地自然幢信息查询QO条件，更新状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwJsydzrzxxDOList", value = "建设用地自然幢信息集合", required = true, dataType = "FwJsydzrzxxQO", paramType = "body")})
    public void updateFwJsydzrzxxPl(@RequestBody List<FwJsydzrzxxDO> fwJsydzrzxxDOList) {
        this.fwJsydzrzxxService.updateFwJsydzrzxxPl(fwJsydzrzxxDOList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询建设用地自然幢的量化抵押状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public Integer queryLhdyqlZtByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh) {
        return this.fwJsydzrzxxService.queryLhdyqlZtByBdcdyh(bdcdyh);
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询建设用地自然幢的量化首登状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public Integer queryLhsdqlZtByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh) {
        return this.fwJsydzrzxxService.queryLhsdqlZtByBdcdyh(bdcdyh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据fwDcbIndex查询建设用地自然幢的量化抵押状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwDcbIndex", value = "fwDcbIndex", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "djh", value = "djh", required = true, dataType = "String", paramType = "param")})
    public Integer queryLhdyqlZtByFwDcbIndexAndDjh(@RequestParam(name="fwDcbIndex") String fwDcbIndex, @RequestParam(name="djh") String djh) {
        return this.fwJsydzrzxxService.queryLhdyqlZtByFwDcbIndexAndDjh(fwDcbIndex, djh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据地籍号查询当前宗地是否存在量化抵押状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "djh", value = "djh", required = true, dataType = "String", paramType = "path")})
    public Integer queryZdLhxxByDjh(@PathVariable(name="djh") String djh) {
        return this.fwJsydzrzxxService.queryZdLhxxByDjh(djh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询建设用地自然幢的量化查封状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public Integer queryZdLhCfZtByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh) {
        return this.fwJsydzrzxxService.queryLhCfZtByBdcdyh(bdcdyh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询是否存在量化查封数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public Integer queryZdLhcfxxByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh) {
        return this.fwJsydzrzxxService.queryLhCfxxByBdcdyh(bdcdyh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号校验单元号所在宗地上逻辑幢的量化查封状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public Integer checkJsydLhcfztByLhCzrz(@PathVariable(name = "bdcdyh")String bdcdyh) {
        return this.fwJsydzrzxxService.checkJsydLhcfztByLhCzrz(bdcdyh);
    }
}
