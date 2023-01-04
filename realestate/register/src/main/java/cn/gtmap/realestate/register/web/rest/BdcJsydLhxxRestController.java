package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmJsydlhxxGxDTO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcJsydLhxxRestService;
import cn.gtmap.realestate.register.service.BdcJsydLhxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化信息rest服务
 */
@RestController
@Api(tags = "建设用地量化信息服务")
public class BdcJsydLhxxRestController implements BdcJsydLhxxRestService {

    @Autowired
    private BdcJsydLhxxService bdcJsydLhxxService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询建设用地量化信息")
    public List<FwJsydzrzxxDO> listJsydLhxx(@PathVariable(name = "gzlslid") String gzlslid){
        return bdcJsydLhxxService.listJsydLhxx(gzlslid);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量新增建设用地量化关系信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXmJsydlhxxGxDTO", value = "不动产登记项目与建设用地量化信息关系DTO", required = true, dataType = "BdcXmJsydlhxxGxDTO", paramType = "body")})
    public void batchInsertJsydlhxxGx(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO) {
        this.bdcJsydLhxxService.batchInsertJsydlhxxGx(bdcXmJsydlhxxGxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID删除建设用地使用权量化关系信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public void deleteJsydlhxxGxByGzlslid(@PathVariable(value="gzlslid") String gzlslid) {
        this.bdcJsydLhxxService.deleteJsydlhxxGxByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新当前项目（现势）和原项目（历史）的量化抵押权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhDyaqZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhDyaqZt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新量化首登权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhsdqlzt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhsdqlzt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新 抵押权变更 的量化权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhDyaBgZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhDyaBgZt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新 抵押权 的量化权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhDyZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhDyZt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新 抵押注销流程 的量化权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhDyaZxZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhDyaZxZt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化生成量化信息附记内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public Object initLhxxFj(@RequestParam(name="processInsId") String processInsId) {
        return this.bdcJsydLhxxService.initLhxxFj(processInsId);
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "重新生成附记信息，并追加量化信息附记至附记信息中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "model", value = "量化附记模板", required = true, dataType = "String", paramType = "query")
    })
    public void generateLhFjxxAndModifyFj(@RequestParam(name="processInsId") String processInsId, @RequestParam(name="model") String model) {
        this.bdcJsydLhxxService.generateLhFjxxAndModifyFj(processInsId, model);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "将宗地逻辑幢信息生成PDF文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
    })
    public void generateLhLjzPdf(@RequestParam(name="gzlslid") String gzlslid) {
        this.bdcJsydLhxxService.generateLhLjzPdf(gzlslid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断当前宗地是否存在现势抵押")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lszd", value = "隶属宗地", required = true, dataType = "String", paramType = "query"),
    })
    public boolean checkXsDyaByLszd(@RequestParam(name = "lszd") String lszd) {
        return this.bdcJsydLhxxService.checkXsDyaByLszd(lszd);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "校验未勾选的逻辑幢，是否已预告或已首登")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXmJsydlhxxGxDTO", value = "不动产登记项目与建设用地量化信息关系DTO", required = true, dataType = "BdcXmJsydlhxxGxDTO", paramType = "body")
    })
    public String checkWgxLzjSfYygOrYsd(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO) {
        return this.bdcJsydLhxxService.checkWgxLzjSfYygOrYsd(bdcXmJsydlhxxGxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新 查封流程 的量化查封权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhCfZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhCfZt(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿时更新 注销查封流程 的量化查封权利状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void updateJsydLhcfzxZt(@RequestParam(name="processInsId") String processInsId) {
        this.bdcJsydLhxxService.updateJsydLhcfzxZt(processInsId);
    }
}
