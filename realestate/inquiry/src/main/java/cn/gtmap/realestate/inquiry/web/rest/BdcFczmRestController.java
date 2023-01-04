package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxCsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFczmQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcFczmRestService;
import cn.gtmap.realestate.inquiry.service.BdcFczmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description 不动产房产证明服务接口
 */
@RestController
@Api(tags = "不动产房产证明服务接口")
public class BdcFczmRestController implements BdcFczmRestService {
    /**
     * 房产证明打印
     */
    @Autowired
    private BdcFczmService bdcFczmService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxCsDTO 参数信息
     * @return {String} 缓存KEY值
     * @description 将住房查询证明请求的参数--权利人信息缓存到Redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("将住房查询证明请求的参数--权利人证件号缓存到Redis")
    @ApiImplicitParam(name = "bdcZfxxCsDTO", value = "参数信息", required = true, paramType = "body")
    public String saveZfxxQlrxxToRedis(@RequestBody BdcZfxxCsDTO bdcZfxxCsDTO) {
        return bdcFczmService.saveZfxxQlrxxToRedis(bdcZfxxCsDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印住房查询证明的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印住房查询证明的XML数据")
    @ApiImplicitParam(name = "ewmurl", value = "二维码请求地址", required = true, paramType = "path")
    public String getPrintXmlOfZfxx(@PathVariable("key") String key) {
        return bdcFczmService.getPrintXmlOfZfxx(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
     * @return {String} redis key
     * @description 缓存房产档案不动产单元号到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存房产档案不动产单元号,qszt,gzlslid到redis")
    @ApiImplicitParam(name = "list", value = "参数集合", required = true, paramType = "body")
    public String saveBdcFcdaBdcdyhToRedis(@RequestBody List<BdcFczmQO> list) {
        return bdcFczmService.saveBdcFcdaBdcdyhToRedis(list);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印房产档案的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印房产档案的XML数据")
    @ApiImplicitParam(name = "key", value = "redis key", required = true, paramType = "path")
    public String getPrintXmlOfFcda(@PathVariable("key")  String key) {
        return bdcFczmService.getPrintXmlOfFcda(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description 根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中")
    @ApiImplicitParam(name = "bdcZszmDTOList", value = "选中的记录数据", required = true, paramType = "body")
    public String getBdcdyhRedisKeyOfDyacfzm(@RequestBody List<BdcZszmDTO> bdcZszmDTOList) {
        return bdcFczmService.getBdcdyhRedisKeyOfDyacfzm(bdcZszmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description 获取抵押查封证明打印的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取抵押查封证明打印的XML数据")
    @ApiImplicitParam(name = "key", value = "不动产单元号对应Redis键", required = true, paramType = "path")
    public String getPrintXmlOfDyacfzm(@PathVariable("key") String key) {
        return bdcFczmService.getPrintXmlOfDyacfzm(key);
    }


    /**
     * 获取盐城打印清单的XML数据
     *
     * @param key reids缓存Key
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取盐城打印清单的XML数据")
    @ApiImplicitParams(@ApiImplicitParam(name = "key", value = "reids缓存Key", paramType = "path"))
    public String getPrintXmlOfDyqd(@PathVariable("key") String key) {
        return bdcFczmService.getPrintXmlOfDyqd(key);
    }

    /**
     * 缓存盐城综合查询打印有无土地信息证明参数
     * @param bdcQlrQO 权利人信息
     * @return BdcPrintParamQO 缓存参数等信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存盐城综合查询打印有无土地信息证明参数")
    @ApiImplicitParam(name = "bdcQlrQO", value = "权利人信息", required = true, paramType = "body")
    public BdcPrintParamQO saveYwtdxxzmParamToRedis(@RequestBody BdcQlrQO bdcQlrQO) {
        return bdcFczmService.saveYwtdxxzmParamToRedis(bdcQlrQO);
    }

    /**
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取盐城综合查询打印有无土地信息证明的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取盐城综合查询打印有无土地信息证明的XML数据")
    @ApiImplicitParams(@ApiImplicitParam(name = "key", value = "reids缓存Key", paramType = "path"))
    public String getPrintXmlOfYwtdxxzm(@PathVariable("key")String key) {
        return bdcFczmService.getPrintXmlOfYwtdxxzm(key);
    }
}
