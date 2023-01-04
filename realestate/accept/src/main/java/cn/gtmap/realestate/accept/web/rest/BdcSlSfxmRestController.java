package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmPlxgDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmSlDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmsDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxmRestService;
import cn.gtmap.realestate.common.util.LogActionConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费项目rest服务
 */
@RestController
@Api(tags = "不动产受理收费项目rest服务")
public class BdcSlSfxmRestController extends BaseController implements BdcSlSfxmRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理收费项目数据服务
     */
    @Autowired
    private BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    private BdcSfService bdcSfService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费项目ID获取不动产受理收费项目", notes = "根据收费项目ID获取不动产受理收费项目服务")
    @ApiImplicitParam(name = "sfxmid", value = "收费项目ID", required = true, dataType = "String", paramType = "path")
    public BdcSlSfxmDO queryBdcSlSfxmBySfxmid(@PathVariable(value = "sfxmid") String sfxmid) {
        return bdcSlSfxmService.queryBdcSlSfxmBySfxmid(sfxmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取不动产受理收费项目", notes = "根据基本信息ID获取不动产受理收费项目服务")
    @ApiImplicitParam(name = "sfxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxmDO> listBdcSlSfxmBySfxxid(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "初始化不动产受理收费项目", notes = "初始化不动产受理收费项目服务")
    @ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxmDO> listCshBdcSlSfxm(@RequestBody List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList, @PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlSfxmService.listCshBdcSlSfxm(bdcSlSfxmPzDOList, sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理收费项目", notes = "新增不动产受理收费项目服务服务")
    @ApiImplicitParam(name = "bdcSlSfxmDO", value = "新增不动产受理收费项目", required = true, dataType = "BdcSlSfxmDO")
    public BdcSlSfxmDO insertBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO) {
        return bdcSlSfxmService.insertBdcSlSfxm(bdcSlSfxmDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收费项目", notes = "更新不动产受理收费项目服务")
    @ApiImplicitParam(name = "bdcSlSfxmDO", value = "不动产受理收费项目", required = true, dataType = "BdcSlSfxmDO")
    public Integer updateBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO) {
        return bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理收费项目", notes = "保存不动产受理收费项目服务")
    @ApiImplicitParam(name = "bdcSlSfxmDO", value = "不动产受理收费项目", required = true, dataType = "BdcSlSfxmDO")
    public Integer saveOrUpdateBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO) {
        return bdcSlSfxmService.saveOrUpdateBdcSlSfxm(bdcSlSfxmDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据收费项目ID删除不动产受理收费项目", notes = "根据收费项目ID删除不动产受理收费项目服务")
    @ApiImplicitParam(name = "sfxmid", value = "收费项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSfxmBySfxmid(@PathVariable(value = "sfxmid") String sfxmid) {
        return bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(sfxmid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理收费项目", notes = "根据基本信息ID删除不动产受理收费项目服务")
    @ApiImplicitParam(name = "sfxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSfxmBySfxxid(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlSfxmService.deleteBdcSlSfxmBySfxxid(sfxxid);
    }

    /**
     * @param processInsId 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发自动保存收费数据
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例id保存收费数据", notes = "根据工作流实例id保存收费数据服务")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "query")
    public void saveSfDataForZf(@RequestParam("processInsId") String processInsId) throws Exception {
        bdcSfService.autoCountSfxm(processInsId);
    }

    /**
     * @param bdcSlSfxmsDTO 收费项目信息值
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算登记费
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "计算登记费", notes = "计算登记费服务")
    @ApiImplicitParam(name = "bdcSlSfxmsDTO", value = "新增不动产受理收费项目", dataType = "BdcSlSfxmsDTO")
    public BdcSlSfxmDO countDjf(@RequestBody BdcSlSfxmsDTO bdcSlSfxmsDTO) {
        return bdcSfService.countDjf(bdcSlSfxmsDTO.getBdcSlSfxmDO(), bdcSlSfxmsDTO.getBdcXmDOList(), bdcSlSfxmsDTO.getSfzhlc(), bdcSlSfxmsDTO.getSfcxjs(), bdcSlSfxmsDTO.getVersion());
    }

    /**
     * @param bdcXmDOList 不动产项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取登记费数量（区分住宅和非住宅）
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "获取登记费数量", notes = "获取登记费数量服务")
    @ApiImplicitParam(name = "bdcXmDOList", value = "不动产受理项目", dataType = "List<BdcXmDO>")
    public BdcSlSfxmSlDTO getSfxmDjfSl(@RequestBody List<BdcXmDO> bdcXmDOList) {
        return bdcSfService.querySfxmDjfSl(bdcXmDOList);
    }

    /**
     * @param bdcSlSfxmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询获取收费项目
     * @date : 2020/11/30 11:31
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取登记费数量", notes = "获取登记费数量服务")
    @ApiImplicitParam(name = "bdcSlSfxmQO", value = "不动产受理项目", dataType = "BdcSlSfxmQO")
    public List<BdcSlSfxmDO> listFpcxSfxm(@RequestBody BdcSlSfxmQO bdcSlSfxmQO) {
        return bdcSlSfxmService.listSfxmBySfxmdm(bdcSlSfxmQO);
    }

    /**
     * @param bdcXmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 盐城请求查询登记费信息
     * @date : 2021/6/23 16:42
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "盐城请求查询登记费信息", notes = "盐城请求查询登记费信息服务")
    @ApiImplicitParam(name = "bdcXmDOList", value = "不动产项目", dataType = "List<BdcXmDO>")
    public BdcSlSfxmSlDTO queryYcDjfSl(@RequestBody List<BdcXmDO> bdcXmDOList) {
        return bdcSfService.queryYcDjfSl(bdcXmDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量修改收费项目内容", notes = "批量修改多个流程的收费项目信息,")
    @ApiImplicitParam(name = "bdcSlSfxmPlxgDTO", value = "不动产受理收费项目批量修改DTO", dataType = "BdcSlSfxmPlxgDTO")
    public void plxgSfxm(@RequestBody BdcSlSfxmPlxgDTO bdcSlSfxmPlxgDTO) {
        this.bdcSlSfxmService.plxgSfxm(bdcSlSfxmPlxgDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据登记原因、sfxxid获取不动产受理收费项目", notes = "根据登记原因、sfxxid获取不动产受理收费项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djyy", value = "登记原因", required = true, dataType = "String", paramType = "query")})
    public List<BdcSlSfxmDO> listBdcSlSfxmBySfxxidAndDjyy(@RequestParam("sfxxid") String sfxxid, @RequestParam("djyy")String djyy) {
        return bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(sfxxid,djyy);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "批量修改收费项目信息", notes = "批量修改收费项目信息")
    @ApiImplicitParam(name = "bdcSlSfxmDOList", value = "不动产受理收费项目项目", dataType = "List<bdcSlSfxmDOList>", paramType = "body")
    public void batchUpdateBdcSlSfxm(@RequestBody List<BdcSlSfxmDO> bdcSlSfxmDOList) {
        this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "计算土地交易服务费数据", action = LogActionConstans.UPDATE)
    public void countTdjyfwf(@RequestParam("sfxmid") String sfxmid, @RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid") String xmid) {
        bdcSfService.countTdjyfwf(sfxmid, gzlslid, xmid);
    }
}
