package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.config.accept.MrsfxxConfig;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxmJsQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxxRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxhzVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxmxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import com.google.common.base.Preconditions;
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

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费信息rest服务
 */
@RestController
@Api(tags = "不动产受理收费信息服务")
public class BdcSlSfxxRestController extends BaseController implements BdcSlSfxxRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理收费信息数据服务
     */
    @Autowired
    private BdcSlSfxxService bdcSlSfxxService;
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产收费服务
     */
    @Autowired
    private BdcSfService bdcSfService;

    @Autowired
    MrsfxxConfig mrsfxxConfig;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID获取不动产受理收费信息", notes = "根据收费信息ID获取不动产受理收费信息服务")
    @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")
    public BdcSlSfxxDO queryBdcSlSfxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取不动产受理收费信息（不包含已作废的数据）", notes = "根据工作流实例ID获取不动产受理收费信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取不动产受理收费信息（包含作废的数据）", notes = "根据工作流实例ID获取不动产受理收费信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidBhzf(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSfxxService.listBdcSlSfxxByGzlslidBhzf(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据不动产收费信息DO获取不动产受理收费信息", notes = "根据不动产收费信息DO获取不动产受理收费信息")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "收费信息DO", required = true, paramType = "body")
    public List<BdcSlSfxxDO> queryBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxDO);
    }

    /**
     * @param pageable
     * @param bdcSlSfxxQOJSON
     * @return BdcSlSfxxDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询 收费信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页获取不动产受理收费信息", notes = "分页获取不动产受理收费信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcSlSfxxQOJSON", value = "查询参数JSON", required = false, paramType = "query")})
    public Page<BdcSlSfxxVO> listBdcSlSfxxByPage(Pageable pageable, @RequestParam(name = "bdcSlSfxxQOJSON", required = false) String bdcSlSfxxQOJSON) {
        return bdcSlSfxxService.listBdcSlSfxxByPage(pageable, bdcSlSfxxQOJSON);
    }

    /**
     * @param bdcSlSfxxQO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询 收费信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页获取不动产受理收费信息", notes = "分页获取不动产受理收费信息")
    @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息QO", required = true, paramType = "body")
    public List<BdcSlSfxxVO> listBdcSlSfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.listBdcSlSfxx(bdcSlSfxxQO);
    }

	/**
	 * @param bdcSlSfxxQO@return
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 查询 银行收费信息
	 */
	@Override
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "获取不动产受理银行收费信息", notes = "获取不动产受理银行收费信息")
	@ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息QO", required = true, paramType = "body")
	public List<BdcSlSfxxVO> listBdcSlSfxxYh(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
		return bdcSlSfxxService.listBdcSlSfxxYh(bdcSlSfxxQO);
	}

	@Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取不动产受理收费信息", notes = "根据项目ID获取不动产受理收费信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxxDO> listBdcSlSfxxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理收费信息", notes = "新增不动产受理收费信息服务")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")
    public BdcSlSfxxDO insertBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSlSfxxService.insertBdcSlSfxx(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收费信息", notes = "更新不动产受理收费信息服务")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")
    public Integer updateBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据 sfxxid 更新不动产收费信息和关联的收费项目", notes = "根据 sfxxid 更新不动产收费信息和关联的收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO", paramType="body"),
    })
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")
    public void updateBdcSlSfxxIdWithSfxm(@RequestParam(name="sfxxid") String sfxxid, @RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
	    this.bdcSlSfxxService.updateBdcSlSfxxIdWithSfxm(sfxxid, bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存或更新不动产受理收费信息", notes = "保存或更新不动产受理收费信息服务")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增或更新不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")
    public Integer saveOrUpdateBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSlSfxxService.saveOrUpdateBdcSlSfxx(bdcSlSfxxDO);
    }

    /**
     * @param bdcSlSfxxDOList
     * @return Integer
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新收费信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理收费信息", notes = "更新不动产受理收费信息服务")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "新增不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")
    public void updateBdcSlSfxxList(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList) {
       this.bdcSlSfxxService.batchUpdateBdcSlSfxx(bdcSlSfxxDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据收费信息ID删除不动产受理收费信息", notes = "根据收费信息ID删除不动产受理收费信息服务")
    @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSfxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcSlSfxxService.deleteBdcSlSfxxBySfxxid(sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理收费信息", notes = "根据基本信息ID删除不动产受理收费信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlSfxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSfxxService.deleteBdcSlSfxxByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "初始化收费信息", notes = "初始化收费信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
    })
    public BdcSlSfxxDO cshSfxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("必要参数为空！");
        }
        return bdcSfService.cshSfxx(gzlslid, xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "初始化非登记流程收费信息", notes = "初始化非登记流程收费信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public BdcSlSfxxDO cshFdjlcSfxx(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("必要参数为空！");
        }
        return bdcSfService.cshFdjlcSfxx(gzlslid);
    }


    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "组织页面收费信息内容", notes = "组织页面收费信息内容服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, dataType = "String", paramType = "query")})
    public BdcSlSfxxDTO generateSfxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid) {
        return bdcSfService.generateSfxx(gzlslid, xmid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容(南通)
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "组织页面收费信息内容", notes = "组织页面收费信息内容服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, dataType = "String", paramType = "query")})
    public BdcSlSfxxDTO generateSfxxNt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid) {
        return bdcSfService.generateSfxxNt(gzlslid, xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询收费信息(包含作废)", notes = "查询收费信息(包含作废)")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, dataType = "String", paramType = "query")})
    public List<BdcSlSfxxDO> queryBdcSlSfxxBhzf(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid) {
        return bdcSfService.queryBdcSlSfxxBhzf(gzlslid, xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收费状态", notes = "更新收费状态服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfxxDO", value = "不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO")})
    public Boolean gxSfxxSfzt(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {

        return bdcSfService.gxSfxxSfzt(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收费信息缴费书编码", notes = "更新收费信息缴费书编码服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "query")})
    public Boolean gxSfxxJfsbm(@RequestParam(name = "sfxxid") String sfxxid,@RequestParam(name = "qlrlb") String qlrlb,@RequestParam(name = "gzlslid") String gzlslid) {
        return bdcSfService.gxSfxxJfsbm(sfxxid, qlrlb, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用非税接口推送收费信息并生成缴费书编码信息", notes = "调用非税接口推送收费信息并生成缴费书编码信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "query")})
    public void createTaxNotice(@RequestParam(name = "sfxxid") String sfxxid,@RequestParam(name = "qlrlb") String qlrlb,@RequestParam(name = "gzlslid") String gzlslid) {
         bdcSfService.createTaxNotice(sfxxid, qlrlb, gzlslid);
    }

	@Override
	public Object getSfxmmc() {
		return bdcSfService.listSfxmmc();
	}

    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidHjfk(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlSfxxService.listBdcSlSfxxByGzlslidHjFk(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "组织一窗流程页面收费基本信息内容", notes = "组织一窗流程页面收费基本信息内容服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = false, dataType = "String", paramType = "query")})
    public BdcSlSfxxDTO generateYcslSfxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid){
        return bdcSfService.generateYcslSfxx(gzlslid, xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID推送待缴费信息", notes = "根据收费信息ID推送待缴费信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", dataType = "String", paramType = "path")})
    public BdcTsdjfxxResponseDTO tsdjfxx(@PathVariable(value = "sfxxid") String sfxxid){
        return bdcSfService.tsdjfxx(sfxxid);

    }




    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID查询收费信息并推送待缴费信息", notes = "根据收费信息ID查询收费信息并推送待缴费信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", dataType = "String", paramType = "path")})
    public BdcTsdjfxxResponseDTO cxtsdjfxx(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(name = "tsly") String tsly,
                                           @RequestParam(name = "pjdm", required = false) String pjdm, @RequestParam(name = "sftdsyj", required = false) boolean sftdsyj) {
        return bdcSfService.cxtsdjfxx(sfxxid, tsly, pjdm, sftdsyj);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "如皋市根据收费信息ID查询收费信息并推送待缴费信息", notes = "如皋市根据收费信息ID查询收费信息并推送待缴费信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "用户编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
    })
    public CommonResponse rgtsdjfxx(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(name = "qlrlb") String qlrlb,
                                    @RequestParam(name="currentUserName", required = false) String currentUserName,
                                    @RequestParam(name = "gzlslid") String gzlslid) {
        return bdcSfService.rgtsdjfxx(sfxxid, currentUserName, qlrlb, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID查询收费信息并作废待缴费信息", notes = "根据收费信息ID查询收费信息并作废待缴费信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfxxDO", value = "不动产受理收费信息DO", dataType = "BdcSlSfxxDO", paramType = "body")})
    public CommonResponse zfdjfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSfService.zfdjfxx(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成电子发票", notes = "生成电子发票")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", dataType = "String", paramType = "path")})
    public Object hkdzfpxx(@PathVariable(value = "sfxxid")String sfxxid){
        return bdcSfService.hkdzfpxx(sfxxid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "下载电子发票", notes = "下载电子发票")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", dataType = "String", paramType = "path")})
    public Object getDzfpxx(@PathVariable(value = "sfxxid")String sfxxid){
        return bdcSfService.getDzfpxx(sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "第三方根据外网受理编号，获取非税电子发票信息", notes = "第三方根据外网受理编号，获取非税电子发票信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDsfSfxxDTO", value = "第三方收费信息", dataType = "bdcDsfSfxxDTO", paramType = "body")})
    public CommonResponse dsfDzfpxxxz(@RequestBody BdcDsfSfxxDTO bdcDsfSfxxDTO) {
        return bdcSfService.dsfDzfpxxxz(bdcDsfSfxxDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号和项目ID推送待缴费信息外网生成支付订单", notes = "根据受理编号和项目ID推送待缴费信息外网生成支付订单服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "path")})
    public BdcTsdjfxxResponseDTO tsdjfxxBySlbh(@RequestParam(name = "slbh") String slbh, @RequestParam(name = "xmid") String xmid){
        return bdcSfService.tsdjfxxBySlbh(slbh,xmid);

    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/23 10:54
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例id查询收费项目是否点击保存按钮", notes = "根据工作流实例id查询收费项目是否点击保存按钮服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "path")})
    public Integer queryBczt(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcSfService.queryBczt(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据xmid和申请人类别提供缴费缴税信息", notes = "根据xmid和申请人类别提供缴费缴税信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sqrlb", value = "申请人类别", dataType = "String", paramType = "query")})
    public BdcSfSsxxDTO queryBdcSfSsxxDTO(@RequestBody BdcSfSsxxQO bdcSfSsxxQO){
        return bdcSfService.queryBdcSfSsxxDTO(bdcSfSsxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结单号提供缴费缴税信息", notes = "根据月结单号提供缴费缴税信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSfSsxxQO", value = "收费收税查询对象", dataType = "BdcSfSsxxQO", paramType = "body")})
    public List<BdcSfSsxxDTO> listYjBdcSfSsxxDTO(@RequestBody BdcSfSsxxQO bdcSfSsxxQO){
        return bdcSfService.listYjBdcSfSsxxDTO(bdcSfSsxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收费收税状态", notes = "更新收费收税状态服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSfSsxxDTO", value = "不动产收费收税信息", required = true, dataType = "BdcSfSsxxDTO")})
    public void updateSfSszt(@RequestBody BdcSfSsxxDTO bdcSfSsxxDTO){
        bdcSfService.updateSfSszt(bdcSfSsxxDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return boolean
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 回传收费收税状态（缴费状态）给大云
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "回传收费收税状态（缴费状态）给大云", notes = "回传收费收税状态（缴费状态）给大云")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public boolean saveSfssxxZtToProcess(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSfService.saveSfssxxZtToProcess(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "判断当前流程是否属于线上缴费", notes = "判断当前流程是否属于线上缴费")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public boolean checkIsXsjf(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcSfService.checkIsXsjf(gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收费收税银行缴库入库状态", notes = "更新收费收税银行缴库入库状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询条件", required = true, dataType = "String", paramType = "query")})
    public void updateSlSfSsxxYhJkrkzt(@RequestBody BdcYhjkrkztDTO bdcYhjkrkztDTO){
        bdcSfService.updateSlSfSsxxYhJkrkzt(bdcYhjkrkztDTO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "判断当前流程缴费缴库情况", notes = "判断当前流程是否线上缴费，是否已缴库")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Integer checkJfqk(@PathVariable(value = "gzlslid") String gzlslid){
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"缺失工作流实例ID!");
        return bdcSfService.checkjfqk(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "判断当前流程收费情况", notes = "判断当前流程是否已缴费")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Integer checkSfqk(@PathVariable(value = "gzlslid") String gzlslid){
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"缺失工作流实例ID!");
        return bdcSfService.checkSfqk(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return: Integer {@code 0} 未上传凭证 {@code 1} 已上传凭证
     * @description 验证线下缴费是否已上传税费缴纳凭证
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证线下缴费是否已上传税费缴纳凭证", notes = "{@code 0} 未上传凭证 {@code 1} 已上传凭证 {@code 2} 线上缴费，不做验证")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public Integer checkXxJfPz(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSfService.checkXxJfPz(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费信息退款申请", notes = "收费信息退款申请")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public BdcSlSfxxDO sfxxTksq(@RequestParam(name = "gzlslid") String gzlslid){
        return bdcSfService.sfxxTksq(gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费信息退款状态查询", notes = "收费信息退款状态查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public BdcSlSfxxDO querySfxxTkqk(@RequestParam(name = "gzlslid") String gzlslid){
        return bdcSfService.queryTkqk(gzlslid);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动修改收费状态为已核验
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "转发自动修改收费状态为已核验", notes = "转发自动修改收费状态为已核验")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void autoUpdateSfztYhy(@PathVariable(name = "processInsId") String gzlslid){
        bdcSfService.autoUpdateSfztYhy(gzlslid);
    }

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例ID删除不动产受理收费信息和收费项目数据
     * @date : 2020/5/9 10:31
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流实例ID删除不动产受理收费信息和收费项目数据", notes = "根据工作流实例ID删除不动产受理收费信息和收费项目数据服务")
    @ApiImplicitParam(name = "bdcSlSfxxDO", value = "不动产受理收费信息", required = true, dataType = "BdcSlSfxxDO", paramType = "body")
    public void deleteSfxmAndSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        bdcSfService.deleteSfxx(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通知政融平台POS缴费成功", notes = "通知政融平台POS缴费成功")
    @ApiImplicitParams({@ApiImplicitParam(name = "posJfDTO", value = "POS缴费结果信息", required = true, dataType = "PosJfDTO")})
    public Object tzzrpt(@RequestBody PosJfDTO posJfDTO) {
        return bdcSfService.tzzrpt(posJfDTO);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）", notes = "根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "项目ID集合", required = true, dataType = "List<String>", paramType = "body")})
    public Map<String, Object> queryHjGroupByXmids(@RequestBody List<String> xmids) {
        return bdcSlSfxxService.queryHjGroupByXmids(xmids);
    }

    /**
     * 提供自助查询机查询收费信息接口（盐城）
     *
     * @param zzcxjSfxxDTO 收费信息查询接口
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    public List<BdcSlSfxxZzcxjResponseDTO> zzcxjQuerySfxx(@RequestBody ZzcxjSfxxDTO zzcxjSfxxDTO) {
        return this.bdcSlSfxxService.zzcxjQuerySfxx(zzcxjSfxxDTO);
    }

    /**
     * @param bdcSlSfxxQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息
     * @date : 2020/11/27 15:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "发票查询收费信息", notes = "发票查询收费信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "不动产受理收费信息QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    })
    public List<BdcSlSfxxDO> listFpcxSfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.listFpcxSfxx(bdcSlSfxxQO);
    }

    /**
     * @param bdcSlSfxxQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息-批量查询返回收费信息和收费项目信息
     * @date : 2020/11/27 15:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量查询返回收费信息和收费项目信息", notes = "批量查询返回收费信息和收费项目信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "不动产受理收费信息QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    })
    public List<BdcSfxxDTO> listFpcxSfxxSfxm(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.listFpcxSfxxPl(bdcSlSfxxQO);
    }

    /**
     * @param bdcSlSfxxQOJson 查询条件
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 登记缴费管理
     * @date : 2020/11/27 15:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "登记缴费管理", notes = "登记缴费管理台账信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQOJSON", value = "不动产受理收费信息QO", required = true, dataType = "String", paramType = "query")
    })
    public Page<BdcdjjfglVO> djjfgl(Pageable pageable,
                                    @RequestParam(name = "bdcSlSfxxQOJson", required = false) String bdcSlSfxxQOJson){
        return bdcSlSfxxService.djjfgl(pageable,bdcSlSfxxQOJson);
    }

    /**
     * @param jfList 查询条件
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 缴费
     * @date : 2020/11/27 15:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "缴费", notes = "缴费,更新收费状态,保存收费操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jfList", value = "不动产受理收费信息VO", required = true, dataType = "jfList", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "操作类型", required = true, dataType = "String", paramType = "query")

    })
    public void jfcz(@RequestBody List<BdcdjjfglVO> jfList,@RequestParam("type") String type){
        bdcSlSfxxService.jfcz(jfList,type);
    }

    /**
     * 非税开票
     *s
     * @param pageable 分页
     * @param gzlslid
     * @return
     */
    @Override
    public Page<BdcSlSfxxDO> fskp(Pageable pageable, String gzlslid) {
        return bdcSlSfxxService.fskp(pageable,gzlslid);
    }

    /**
     * （南通）汇总缴费数据
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxhzVO} 汇总缴费信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("（南通）汇总缴费数据")
    @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    public BdcSfxxhzVO hzjfsj(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.hzjfsj(bdcSlSfxxQO);
    }

    /**
     * 查询缴费数据明细
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxmxVO} 缴费数据明细
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("查询缴费数据明细")
    @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    public BdcSfxxmxVO mxjfsj(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.mxjfsj(bdcSlSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理收费操作人信息", notes = "新增不动产受理收费操作人信息")
    @ApiImplicitParam(name = "BdcSlSfxxCzrzDO", value = "新增不动产受理收费操作人信息", required = true, dataType = "BdcSlSfxxCzrzDO")
    @PostMapping("/realestate-accept/rest/v1.0/sfxxczr/")
    public BdcSlSfxxCzrzDO insertBdcSlSfxxCzrz(@RequestBody BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO) {
        return bdcSlSfxxService.insertBdcSlSfxxCzrz(bdcSlSfxxCzrzDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("查询收费缴款情况,并更新")
    @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    public BdcSlSfxxDO queryJkqkAndUpdate(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return this.bdcSfService.queryJkqkAndUpdate(bdcSlSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("查询非税缴款情况,并更新")
    @ApiImplicitParam(name = "bdcqzh", value = "不动产权证号", required = true, dataType = "String", paramType = "query")
    public CommonResponse queryFsjfqk(@RequestParam(name= "bdcqzh") String bdcqzh) {
        return bdcSfService.queryFsjfqk(bdcqzh);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "工作流事件，用于登簿时，更新收费信息表中的登簿时间", notes = "工作流事件，用于登簿时，更新收费信息表中的登簿时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void modifySlSfxxDbsj(@RequestParam(name="processInsId") String processInsId) {
        if(StringUtils.isNotBlank(processInsId)){
            this.bdcSlSfxxService.modifySlSfxxDbsj(processInsId);
        }
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 自动计算收费信息 合计和收费项目数据 ---南通逻辑
     * @date : 2021/7/28 9:39
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "自动计算收费信息合计和收费项目数据", notes = "自动计算收费信息合计和收费项目数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void autoCountSfxxNt(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcSfService.autoCountNtSfxx(gzlslid,"");
    }


    /**
     * @param gzlslid,djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取南通收费项目数量
     * @date : 2021/7/29 10:03
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询收费项目数量", notes = "查询收费项目数量服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = false, dataType = "String", paramType = "query")
    })
    public BdcSlSfxmSlDTO querySfxmSl(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(name = "djxl") String djxl) {
        return bdcSfService.queryNtSfxmSl(gzlslid,djxl);
    }


    /**
     * @param  qxdm 区县代码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取收费信息配置
     * @date : 2021/7/29 10:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据区县代码获取默认收费信配置", notes = "自动计算收费信息合计和收费项目数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qxdm", value = "区县代码", required = true, dataType = "String", paramType = "path")
    })
    public Map queryMrsfxxPz(@PathVariable(value = "qxdm") String qxdm) {
        if(StringUtils.isBlank(qxdm)) {
            throw new AppException("");
        }
        return mrsfxxConfig.getMrsfxxMap().get(qxdm);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询收费状态", notes = "查询收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = false, dataType = "String", paramType = "query")
    })
    public Integer querySfzt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "qlrlb") String qlrlb) {
        return this.bdcSfService.queryJkqkxx(gzlslid, qlrlb);
    }

    /**
     * @param pageable
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询推送收费信息管理台账数据
     * @date : 2021/9/13 14:34
     */
    @Override
    public Page<BdcdjjfglVO> listTssfglByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json) {
        return bdcSlSfxxService.listTssfByPage(pageable, json);
    }

    /**
     * @param bdcSlSfxxQO  不动产受理收费页面类
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询推送收费信息数据
     * @date : 2021/9/15 8:48
     */
    @Override
    public List<BdcdjjfglVO> listTssfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.listTssfxx(bdcSlSfxxQO);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 抵押批量推送收费信息
     * @date : 2021/9/15 9:08
     */
    @Override
    public BdcTsdjfxxResponseDTO cxtsDyaqSfxx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSfService.tsjfDyaqSfxx(gzlslid);
    }

    @Override
    public Integer querySfyjByJfrxm(@RequestParam(value = "jfrxm", required = false) String jfrxm, @RequestParam(value = "gzldyid", required = false) String gzldyid) {
        return bdcSfService.querySfyjByJfrxm(jfrxm, gzldyid);

    }

    /**
     * @param pageable
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询未缴费数据 fph 不为空，缴费书编码不为空且未缴费的数据
     * @date : 2021/9/13 14:34
     */
    @Override
    public Page<BdcSlWjfDTO> listSlWjfByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json) {
        return bdcSlSfxxService.listWjftzByPage(pageable, json);
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算未缴费金额
     * @date : 2021/11/4 23:12
     */
    @Override
    public Map countWjfJe(@RequestParam(value = "json", required = false) String json) {
        return bdcSlSfxxService.countWjfhj(json);
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收费状态
     * @date : 2021/11/5 10:59
     */
    @Override
    public List<BdcSlWjfDTO> listSlWjfxx(@RequestParam(value = "json", required = false) String json) {
        return bdcSlSfxxService.listWjfxxByPage(json);
    }

    @Override
    public void autoCountSfxxSfxm(@RequestParam("processInsId") String processInsId) throws Exception{
        bdcSfService.autoCountSfxxSfxm(processInsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "转发自动计算收费信息", notes = "转发自动计算收费信息")
    public void autoCountSfxxSfxmForZf(@RequestParam("processInsId") String processInsId) throws Exception{
        bdcSfService.autoCountSfxxSfxmForZf(processInsId);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理收费单页面收费项目自动计算信息", notes = "处理收费单页面收费项目自动计算信息")
    public List<BdcSlSfxmDO> countSfxm(@RequestBody BdcSfxmJsQO bdcSfxmJsQO) throws Exception{
        return bdcSfService.countSfxm(bdcSfxmJsQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "接收第三方收费信息保存并推送", notes = "接收第三方收费信息保存并推送")
    public CommonResponse jsSfxxSaveAndTs(@RequestBody BdcDsfSfxxDTO bdcDsfSfxxDTO){
        return bdcSfService.jsSfxxSaveAndTs(bdcDsfSfxxDTO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费模式是否区县模式", notes = "收费模式是否区县模式")
    public String nantongSfMode(@RequestParam("processInsId") String processInsId,@RequestParam("configName") String configName){
        return bdcSfService.nantongSfMode(processInsId, configName);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收费状态操作时间", notes = "更新收费状态操作时间")
    public void updateSlSfxxSfztczsj(@PathVariable(name = "processInsId")  String processInsId) {
        bdcSfService.updateSlSfxxSfztczsj(processInsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "月结银行更新收费状态操作时间", notes = "月结银行更新收费状态操作时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void yjyhUpdateSfztAndSfsj(@RequestParam(name = "processInsId") String processInsId) {
        bdcSlSfxxService.yjyhUpdateSfztAndSfsj(processInsId);
    }

    /**
     * @param bdcSlSfxmDOList
     * @param gzlslid
     * @param sfcxjs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算土地使用权交易服务费
     * @date : 2022/4/24 14:30
     */
    @Override
    public List<BdcSlSfxmDO> countTdsyqJyfwf(@RequestBody List<BdcSlSfxmDO> bdcSlSfxmDOList, @RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "sfcxjs") boolean sfcxjs) {
        return bdcSfService.countTdsyqJyfwf(bdcSlSfxmDOList, gzlslid, sfcxjs);
    }

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费信息
     * @date : 2022/8/1 10:15
     */
    @Override
    public List<BdcSlSfxxDO> listBdcSlSfxxBySlbh(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用互联网缴费状态查询信息，并更新缴费状态、完税状态", notes = "调用互联网缴费状态查询信息，并更新缴费状态、完税状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public void queryHlwJfzt(@RequestParam(name = "processInsId") String processInsId) {
        bdcSfService.queryHlwJfzt(processInsId);
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送税费
     * @date : 2022/9/27 10:50
     */
    @Override
    public Object bdcSftj(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                          @RequestParam(name = "qlrlb") String qlrlb, @RequestParam(name = "sfxxid", required = false) String sfxxid) {
        return bdcSfService.sftjxx(gzlslid, xmid, qlrlb, sfxxid);
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 更新不动产受理收费信息并且根据收费状态推送登记流程
     * @date : 2022/9/26
     */
    @Override
    public Integer updateBdcSlSfxxAndTsdjlc(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        return bdcSlSfxxService.updateBdcSlSfxxAndTsdjlc(bdcSlSfxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取不动产受理收费信息和收费项目信息", notes = "获取不动产受理收费信息和收费项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "不动产受理收费信息QO", required = true, dataType = "BdcSlSfxxQO", paramType = "body")
    })
    public List<BdcSfxxDTO> queryBdcSlSfxxAndSfxm(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return this.bdcSlSfxxService.queryBdcSlSfxxAndSfxm(bdcSlSfxxQO);
    }
}
