package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcBzbZmRestService;
import cn.gtmap.realestate.inquiry.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.inquiry.service.BdcBzbZhcxZmService;
import cn.gtmap.realestate.inquiry.service.BdcZszmCxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/03/02
 * @description （标准版）综合查询证明相关处理逻辑请求
 */
@RestController
@Api(tags = "（标准版）不动产房产证明服务接口")
public class BdcBzbZmRestController implements BdcBzbZmRestService {
    @Autowired
    private BdcBzbZhcxZmService bdcBzbZhcxZmService;

    @Autowired
    private BdcZszmCxService bdcZszmCxService;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQO 权利人信息
     * @return {String} Redis key
     * @description （标准版）保存有房无房证明打印参数信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存有房无房证明打印参数信息")
    @ApiImplicitParam(name = "bdcQlrQO", value = "参数信息", required = true, paramType = "body")
    public String saveBzbYfwfzmXx(@RequestBody BdcQlrQO bdcQlrQO) {
        return bdcBzbZhcxZmService.saveBzbYfwfzmXx(bdcQlrQO);
    }

    /**
     * 验证权利人证件号是否必填
     * @param qlrmc 权利人名称
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("验证证件号是否必填")
    @ApiImplicitParam(name = "qlrmc",value = "权利人名称")
    public Boolean zjhYz(@RequestParam(value = "qlrmc") String qlrmc) {
        if (StringUtils.isBlank(qlrmc)) {
            throw new NullPointerException("权利人名称不可为空！");
        }
        return bdcZszmCxService.checkQlczjh(qlrmc);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 编号
     * @description 获取有房无房证明编号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取有房无房证明编号")
    public String getYfwfzmBh() {
        return bdcBzbZhcxZmService.getYfwfzmBh();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印房产证明的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印房产证明的XML数据")
    @ApiImplicitParam(name = "key", value = "缓存参数信息Redis Key", required = true, paramType = "path")
    public String getPrintXmlOfZfxx(@PathVariable("key") String key) {
        return bdcBzbZhcxZmService.getPrintXmlOfZfxx(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明参数信息到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存权属证明参数信息到redis")
    @ApiImplicitParam(name = "bdcQszmQOList", value = "参数信息", required = true, paramType = "body")
    public BdcQszmDyDTO saveBdcQszmParamToRedis(@RequestBody List<BdcQszmQO> bdcQszmQOList) {
        return bdcBzbZhcxZmService.saveBdcQszmParamToRedis(bdcQszmQOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印权属证明的XML数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "缓存参数信息Redis Key", required = true, paramType = "path"),
            @ApiImplicitParam(name = "bdclx", value = "不动产类型",  dataType = "String", paramType = "path")})
    public String getPrintXmlOfQszm(@PathVariable("key") String key, @PathVariable("bdclx") String bdclx) {
        return bdcBzbZhcxZmService.getPrintXmlOfQszm(key, bdclx);
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param  bdcdyxxDTOList 打印请求参数
     * @return {BdcdyxxDTO} 缓存的Redis KEY
     * @description 缓存权属证明参数信息到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存不动产单元查询参数信息到redis")
    @ApiImplicitParam(name = "bdcdyxxDTOList", value = "参数信息", required = true, paramType = "body")
    public String saveDjbdcdyhParamToRedis(@RequestBody List<BdcdyxxDTO> bdcdyxxDTOList) {
        return bdcBzbZhcxZmService.saveDjbdcdyhParamToRedis(bdcdyxxDTOList);
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印不动产单元查询的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印权属证明的XML数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "缓存参数信息Redis Key", required = true, paramType = "path"),
            @ApiImplicitParam(name = "bdclx", value = "不动产类型",  dataType = "String", paramType = "path")})
    public String getPrintXmlOfBdcdycx(@PathVariable("key") String key, @PathVariable("bdclx") String bdclx) {
        return bdcBzbZhcxZmService.getPrintXmlOfBdcdycx(key, bdclx);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权属证明查询号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取权属证明查询号")
    public String getQszmCxh() {
        return bdcBzbZhcxZmService.getQszmCxh();
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZszmDTOList 打印查档信息请求参数
     * @return {BdcZszmDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存查档信息参数信息到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存查档信息参数信息到redis")
    @ApiImplicitParam(name = "bdcZszmDTOList", value = "参数信息", required = true, paramType = "body")
    public Object saveBdcCdxxParamToRedis(@RequestBody List<BdcZszmDTO> bdcZszmDTOList) {
        return bdcBzbZhcxZmService.saveBdcCdxxParamToRedis(bdcZszmDTOList);
    }

    /**
     * 获取打印查档结果的XML数据
     *
     * @param key  redis key
     * @param dylx 打印类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印查档结果的XML数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "缓存参数信息Redis Key", required = true, paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "打印类型",  dataType = "String", paramType = "path")})
    public String getPrintXmlOfCdxxJg(@PathVariable("key") String key, @PathVariable("dylx") String dylx) {
        return bdcBzbZhcxZmService.getPrintXmlOfCdxxJg(key, dylx);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrQOList 权利人信息
     * @return {List} 房产信息
     * @description 查询权利人房产证明并生成PDF
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询权利人房产证明并生成PDF")
    @ApiImplicitParam(name = "qlrQOList", value = "权利人信息", required = true, paramType = "body")
    public List<BdcFczmPdfDTO> getFczmPdf(@RequestBody List<BdcQlrQO> qlrQOList) {
        return bdcBzbZhcxZmService.getFczmPdf(qlrQOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 权属证明参数
     * @return WwsqResponse 权属证明
     * @description 查询指定单元权属证明并生成PDF
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询指定单元权属证明并生成PDF")
    @ApiImplicitParam(name = "bdcQszmQOList", value = "权利人信息", required = true, paramType = "body")
    public List<BdcQszmPdfDTO> getQszmPdf(@RequestBody List<BdcQszmQO> bdcQszmQOList) {
        return bdcBzbZhcxZmService.getQszmPdf(bdcQszmQOList);
    }

    /**
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @return String 查询范围
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取查询范围")
    @GetMapping("/realestate-inquiry/rest/v1.0/zhcx/cxfw")
    public String getCxfw() {
        return bdcBzbZhcxZmService.getCxfw();
    }

}
