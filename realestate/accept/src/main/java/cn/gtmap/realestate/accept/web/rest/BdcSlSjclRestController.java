package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSjclService;
import cn.gtmap.realestate.accept.service.BdcSjclService;
import cn.gtmap.realestate.accept.service.BdcYqService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.SjclUploadDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymDchyDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSjclRestService;
import cn.gtmap.realestate.common.util.LogConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收件材料rest服务
 */
@RestController
@Api(tags = "不动产受理收件材料服务")
public class BdcSlSjclRestController extends BaseController implements BdcSlSjclRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理收件材料数据服务
     */
    @Autowired
    private BdcSlSjclService bdcSlSjclService;
    @Autowired
    private BdcSjclService bdcSjclService;
    @Autowired
    private BdcYqService bdcYqService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收件材料ID获取不动产受理收件材料", notes = "根据项目ID获取不动产受理收件材料服务")
    @ApiImplicitParam(name = "sjclid", value = "收件材料ID", required = true, dataType = "String", paramType = "path")
    public BdcSlSjclDO queryBdcSlSjclBySjclid(@PathVariable(value = "sjclid") String sjclid) {
        return bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "工作流实例ID获取不动产受理收件材料", notes = "工作流实例ID获取不动产受理收件材料服务")
    @ApiImplicitParam(name = "gzlslid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSjclDO> listBdcSlSjclByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "材料名称获取不动产受理收件材料", notes = "材料名称获取不动产受理收件材料服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "clmc", value = "材料名称", dataType = "String", paramType = "query")
    })
    public List<BdcSlSjclDO> listBdcSlSjclByClmc(@RequestParam(value = "gzlslid",required = false) String gzlslid,@RequestParam(value = "clmc") String clmc){
        return bdcSlSjclService.listBdcSlSjcl(gzlslid,clmc);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料页数", notes = "更新不动产受理收件材料页数服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query")
    })
    public Integer updateSjclYs(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid",required = false) String xmid) {
        return bdcSjclService.updateSjclYs(gzlslid, xmid);
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新组合流程收件材料页数
     * @date : 2021/12/20 13:47
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料页数", notes = "更新不动产受理收件材料页数服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query")
    })
    public Integer updateZhlcSjclYs(@RequestParam(value = "gzlslid") String gzlslid) {
        return bdcSjclService.updateZhlcSjclYs(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理收件材料", notes = "新增不动产受理收件材料服务服务")
    @ApiImplicitParam(name = "bdcSlSjclDO", value = "新增不动产受理收件材料", required = true, dataType = "BdcSlSjclDO")
    public BdcSlSjclDO insertBdcSlSjcl(@RequestBody BdcSlSjclDO bdcSlSjclDO) {
        return bdcSlSjclService.insertBdcSlSjcl(bdcSlSjclDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料", notes = "更新不动产受理收件材料服务")
    @ApiImplicitParam(name = "bdcSlSjclDO", value = "新增不动产受理收件材料", required = true, dataType = "BdcSlSjclDO")
    public Integer updateBdcSlSjcl(@RequestBody BdcSlSjclDO bdcSlSjclDO) {
        return bdcSlSjclService.updateBdcSlSjcl(bdcSlSjclDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据收件材料ID删除不动产受理收件材料", notes = "根据收件材料ID删除不动产受理收件材料服务")
    @ApiImplicitParam(name = "sjclid", value = "收件材料ID", required = true, dataType = "String", paramType = "path")
    public void deleteBdcSlSjclBySjclid(@PathVariable(value = "sjclid") String sjclid) {
        bdcSjclService.deleteSjcl(sjclid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除不动产受理收件材料
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据登记原因", notes = "根据工作流实例id删除不动产受理收件材料服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path")
    })
    public List<BdcSlSjclDO> reCreateSjcl(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSjclService.reCreateSjcl(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料", notes = "更新不动产受理收件材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "不动产受理收件材料Json", required = true, dataType = "String"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query")
    })
    public Integer updateBdcSlSjcl(@RequestBody String json) {
        return bdcSjclService.saveSjcl(json);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "修改收件材料顺序号", notes = "修改收件材料顺序号服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjclid", value = "收件材料ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "czlx", value = "操作类型", dataType = "String", paramType = "query")
    })
    public Integer changeSjclSxh(@PathVariable(value = "sjclid") String sjclid, @PathVariable(value = "czlx") String czlx) {
        return bdcSjclService.changeSjclSxh(sjclid, czlx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "提供收件材料名称服务", notes = "提供收件材料名称服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjclid", value = "收件材料ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "czlx", value = "操作类型", dataType = "String", paramType = "query")
    })
    public List<String> listSjclMc(String gzlslid, @RequestParam(value = "sjclIdsStr", required = false) String sjclIdsStr) {
        return bdcSjclService.listSjclMc(gzlslid, sjclIdsStr);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "提供验证收件材料份数页数是否为空服务", notes = "提供验证收件材料份数页数是否为空服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query")
    })
    public Boolean checkSjclYsFs(@PathVariable(value = "gzlslid")  String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数gzlslid");
        }
        return bdcSjclService.checkSjclYsFs(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "提供保存第三方收件材料服务", notes = "提供保存第三方收件材料服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcDsfSjclDTOList", value = "第三方收件材料", dataType = "BdcDsfSjclDTO", paramType = "query")
    })
    public void saveDsfSjcl(@PathVariable(value = "gzlslid")  String gzlslid,@RequestBody List<BdcDsfSjclDTO> bdcDsfSjclDTOList) throws IOException {
        bdcSjclService.saveDsfSjcl(gzlslid, bdcDsfSjclDTOList);

    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除流程附件，包括大云附件
     * @date : 2020/5/12 9:49
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流实例id删除流程附件", notes = "根据工作流实例id删除流程附件服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path")
    public void deleteAllSjcl(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcSjclService.deleteAllSjcl(gzlslid);
    }


    /**
     * @param slbh 受理编号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据受理编号查询不动产受理收件材料
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号查询流程附件", notes = "根据受理编号查询流程附件")
    @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "query")
    public List<BdcSlSjclDTO> listSjclBySlbh(@RequestParam(value = "slbh") String slbh) {
        return bdcSjclService.listSjclBySlbh(slbh);
    }

    /**
     * @param bdcSlSjclQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收件材料-查询条件包含等级小类
     * @date : 2021/3/25 14:47
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询收件材料-查询条件包含等级小类服务", notes = "查询收件材料-查询条件包含等级小类服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSjclQO", value = "查询条件", dataType = "BdcSlSjclQO", paramType = "query")
    })
    public List<BdcSlSjclDO> listBdcSlSjcl(@RequestBody BdcSlSjclQO bdcSlSjclQO) {
        return bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收件材料
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料组合流程", notes = "更新不动产受理组合流程收件材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "不动产受理收件材料Json", required = true, dataType = "String")
    })
    public Integer updateZhSlSjcl(@RequestBody String json) {
        return bdcSjclService.saveZhSjcl(json);
    }

    /**
     * @param sjclUploadDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/5 10:16
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收件材料组合流程", notes = "更新不动产受理组合流程收件材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjclUploadDTO", value = "不动产受理收件材料Json", required = true, dataType = "SjclUploadDTO")
    })
    public void createAndUploadFile(@RequestBody SjclUploadDTO sjclUploadDTO) throws IOException {
        bdcSjclService.createAndUploadFile(sjclUploadDTO);
    }

    /**
     * @param sjclJson@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料信息
     * @date : 2021/7/12 14:22
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "复制收件材料", notes = "复制收件材料服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjclJson", value = "不动产受理收件材料Json", required = true, dataType = "String")
    })
    public void copySjcl(@RequestBody String sjclJson) {
        bdcSjclService.copySjcl(sjclJson);
    }

    /**
     * @param sjclJson@author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 复制收件材料信息
     * @date : 2021/7/12 14:22
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "复制收件材料", notes = "复制收件材料服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sjclJson", value = "不动产受理收件材料Json", required = true, dataType = "String")
    })
    public void copyzhSjcl(@RequestBody String sjclJson) {
        bdcSjclService.copyzhSjcl(sjclJson);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产收件材料是否批注", notes = "更新不动产受理收件材料是否批注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query")
    })
    public Integer updateSjclSfpz(@RequestParam(value = "processInsId") String gzlslid) {
        return bdcSjclService.updateSjclSfpz(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "上传一体化平台审批附件", notes = "上传一体化平台审批附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query")
    })
    public void uploadYthSpfj(@RequestBody String json,@RequestParam(value = "processInsId") String gzlslid){
        bdcSjclService.uploadYthSpfj(json, gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送附件信息进行云签", notes = "推送附件信息进行云签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query")
    })
    public Object tsYqDzclxx(@RequestParam(value = "gzlslid") String gzlslid) {
       return this.bdcYqService.tsYqDzclxx(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件保存到附件材料
     * @date : 2022/11/25
     * @return object
     * @exception IOException
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "保存附件", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    public Boolean downloadWtcl(@RequestParam("gzlslid") String gzlslid) throws IOException {
        return bdcSjclService.downloadWtcl(gzlslid);
    }

    /**
     * @param slymDchyDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取多测合一附件
     * @date : 2022/12/20 8:45
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public void downDchyFj(@RequestBody SlymDchyDTO slymDchyDTO) {
        bdcSjclService.downDchyfj(slymDchyDTO);
    }
}
