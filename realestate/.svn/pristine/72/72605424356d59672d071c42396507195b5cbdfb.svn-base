package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSzgzlTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZsTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcSzgzlTjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZszmCxRestService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsTjVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsmxTjVO;
import cn.gtmap.realestate.inquiry.service.BdcZszmCxService;
import cn.gtmap.realestate.inquiry.service.ZipKinInfromationService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/15
 * @description  查询子系统：不动产证书证明查询
 */
@RestController
@Api(tags = "不动产证书证明查询服务接口")
public class BdcZszmCxRestController implements BdcZszmCxRestService{
    @Autowired
    private BdcZszmCxService bdcZszmCxService;

    @Autowired
    private ZipKinInfromationService service;

    /**
     * 综合查询
     * <p>
     *  说明：综合查询日志保存
     *  1、页面上会单独请求保存到数据库
     *  2、后台AOP：cn.gtmap.realestate.inquiry.core.aop.InquiryAspect#saveZhcxLog 拦截保存到ES
     *  3、当前方法设置不在LogApiAspect中保存，因为要在上面这个AOP中处理业务数据，另外有些查询不是页面过来调用，拦截当前方法可能拦截不到
     * </p>
     *
     * @param pageable      分页对象
     * @param zszmParamJson 查询条件
     * @return {Page} 证书证明查询分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "证书证明分页查询", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zszmParamJson", value = "证书证明查询分页参数JSON", required = false, paramType = "query")})
    public Page<BdcZszmDTO> listBdcZszm(Pageable pageable, @RequestParam(name = "zszmParamJson", required = false) String zszmParamJson) {
        return bdcZszmCxService.listBdcZszm(pageable, JSON.parseObject(zszmParamJson, BdcZszmQO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID集合参数
     * @return {List} 综合查询附加展示信息
     * @description 根据XMID查询综合查询附加显示信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据XMID查询综合查询附加显示信息")
    @ApiImplicitParam(name = "xmidList", value = "项目ID集合参数", required = true, paramType = "body")
    public List<BdcZhcxDTO> listZhcxFjxx(@RequestBody List<String> xmidList) {
        return bdcZszmCxService.listZhcxFjxx(xmidList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @return {List} 不动产单元号集合
     * @description 获取证书证明关联的不动产单元号集合
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取证书证明关联的不动产单元号集合")
    @ApiImplicitParam(name = "zsid", value = "证书ID", required = true, paramType = "path")
    public List<String> listBdcZszmBdcdyh(@PathVariable("zsid") String zsid) {
        return bdcZszmCxService.listBdcZszmBdcdyh(zsid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable      分页对象
     * @param zszmParamJson 查询条件
     * @return {Page} 项目信息分页数据
     * @description 根据证书ID获取关联的项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据证书ID获取关联的项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zszmParamJson", value = "项目信息查询分页参数JSON", required = false, paramType = "query")})
    public Page<BdcXmDO> listBdcZszmXmxx(Pageable pageable, @RequestParam(name = "zszmParamJson", required = false) String zszmParamJson) {
        return bdcZszmCxService.listBdcZszmXmxx(pageable, JSON.parseObject(zszmParamJson, BdcZszmQO.class));
    }

    /**
     * @param zstjParamJson
     * @return {List} 各部门证书统计数量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 证书统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("各部门证书统计数量")
    @ApiImplicitParam(name = "zstjParamJson", value = "", required = true, paramType = "query")
    public List<BdcZsTjVO> listBdcZsTj(@RequestParam(name = "zstjParamJson", required = false) String zstjParamJson) {
        return bdcZszmCxService.listBdcZsTj(JSON.parseObject(zstjParamJson, BdcZsTjQO.class));
    }

    /**
     * @param zstjParamJson
     * @return {List} 证书明细
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 证书打印明细
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("证书打印明细")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zstjParamJson", value = "", required = true, paramType = "query")})
    public List<BdcZsmxTjVO> listBdcZsmxTj(Pageable pageable, @RequestParam(name = "zstjParamJson", required = false) String zstjParamJson) {
        return bdcZszmCxService.listBdcZsmxTj(pageable, JSON.parseObject(zstjParamJson, BdcZsTjQO.class));
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("验证不动产单元是否存在办理其他登记")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", required = true, dataType = "BdcGzYzQO", paramType = "body")})
    public List<Map<String,Object>> checkBdcdyhGz(@RequestBody BdcGzYzQO bdcGzYzQO) {
        return this.bdcZszmCxService.checkBdcdyhGz(bdcGzYzQO);
    }

    /**
     * 判断fdcq表是否有值》1，以此区别是一证多房还是在建工程抵押
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询fdcq表是否存在产权数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, paramType = "body")
    public Integer getFdcqCount(@RequestBody String gzlslid) {
        return bdcZszmCxService.getFdcqCount(gzlslid);
    }

    /**
     * 判断抵押权表是否值》1
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询抵押表是否存在数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, paramType = "body")
    public Integer getDyaqCount(@RequestBody String gzlslid) {
        return bdcZszmCxService.getDyaqCount(gzlslid);
    }

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param pageable
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("证书证明统计查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zszmCountJson", value = "证书证明统计分页参数JSON", required = false, paramType = "query")})
    public Page<BdcZsTjDTO> getZszmCount(Pageable pageable, String zszmCountJson) {
        return bdcZszmCxService.getZszmCount(pageable, JSON.parseObject(zszmCountJson, BdcZsTjQO.class));
    }

    @Override
    public List<BdcZsTjDTO> getZszmCountExcel(String zszmCountJson) {
        return bdcZszmCxService.getZszmCountExcel(JSON.parseObject(zszmCountJson, BdcZsTjQO.class));
    }

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("证书证明统计查询-list")
    @ApiImplicitParams(@ApiImplicitParam(name = "zszmCountJson", value = "证书证明统计分页参数JSON", required = false, paramType = "query"))
    public List<BdcZsTjDTO> getZszmCountList(String zszmCountJson) {
        return bdcZszmCxService.getZszmCountList(JSON.parseObject(zszmCountJson, BdcZsTjQO.class));
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据权利人名称查询权利人证件号为空或证件号不为18位与15位的数量")
    @ApiImplicitParam(name = "qlrmc", value = "权利人名称集合", required = true, paramType = "body")
    public Integer getQlrZjhNullCount(@RequestBody List<String> qlrmc) {
        return bdcZszmCxService.getQlrZjhNullCount(qlrmc);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(" 获取缮证工作量统计")
    @ApiImplicitParam(name = "bdcSzgzlTjQO", value = "缮证工作量统计查询QO", required = true, paramType = "body")
    public List<BdcSzgzlTjDTO> getSzgzl(@RequestBody BdcSzgzlTjQO bdcSzgzlTjQO) {
        return bdcZszmCxService.getSzgzl(bdcSzgzlTjQO);
    }
}
