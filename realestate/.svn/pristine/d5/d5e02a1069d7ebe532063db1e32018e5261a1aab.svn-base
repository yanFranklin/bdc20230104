package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcNtFczmRestService;
import cn.gtmap.realestate.inquiry.service.BdcNtFczmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description (南通)不动产房产证明打印处理
 */
@RestController
@Api(tags = "(南通)不动产房产证明打印处理")
public class BdcNtFczmRestController implements BdcNtFczmRestService {
    /**
     * 南通房产证明打印
     */
    @Autowired
    private BdcNtFczmService bdcNtFczmService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO
     * @return {BdcFczmDTO} 房产证明信息
     * @description 保存（南通）房产证明信息至缓存
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存（南通）房产证明信息至缓存")
    @ApiImplicitParam(name = "bdcFczmDTO", value = "房产证明", required = true, paramType = "body")
    public String saveNtFcdaXx(@RequestBody BdcFczmDTO bdcFczmDTO) {
        return bdcNtFczmService.saveNtFcdaXx(bdcFczmDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTOList 房产证明信息
     * @return {String} redis key
     * @description 批量保存（南通）房产证明信息至缓存
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量保存（南通）房产证明信息至缓存")
    @ApiImplicitParam(name = "bdcFczmDTOList", value = "房产证明", required = true, paramType = "body")
    public String saveNtFcdaXxPl(@RequestBody List<BdcFczmDTO> bdcFczmDTOList) {
        return bdcNtFczmService.saveNtFcdaXxPl(bdcFczmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明打印信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取（南通）房产证明打印信息")
    @ApiImplicitParam(name = "key", value = "缓存数据的Redis Key", required = true, paramType = "path")
    public String getNtFczmXml(@PathVariable("key")String key) {
        return bdcNtFczmService.getNtFczmXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明批量打印信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取（南通）房产证明批量打印信息")
    @ApiImplicitParam(name = "key", value = "缓存数据的Redis Key", required = true, paramType = "path")
    public String getNtFczmPlXml(@PathVariable("key") String key) {
        return bdcNtFczmService.getNtFczmPlXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）有房无房证明打印信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取（南通）有房无房证明打印信息")
    @ApiImplicitParam(name = "key", value = "缓存数据的Redis Key", required = true, paramType = "path")
    public String getNtYfwfzmXml(@PathVariable("key")String key) {
        return bdcNtFczmService.getNtYfwfzmXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDyzmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description （南通）将要打印的抵押证明参数信息保存至Redis中
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("将要打印的抵押证明参数信息保存至Redis中")
    @ApiImplicitParam(name = "bdcDyzmDTOList", value = "要打印的不动产信息", required = true, paramType = "body")
    public BdcDyzmDyDTO saveDyzmCsxxToRedis(@RequestBody List<BdcDyzmDTO> bdcDyzmDTOList) {
        return bdcNtFczmService.saveDyzmCsxxToRedis(bdcDyzmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} 打印的数据内容
     * @description （南通）获取抵押证明打印对应的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取抵押证明打印对应的XML数据")
    @ApiImplicitParam(name = "key", value = "redis key", required = true, paramType = "path")
    public String getPrintXmlOfDyzm(@PathVariable("key") String key) {
        return bdcNtFczmService.getPrintXmlOfDyzm(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 房产出售时间
     * @description 获取房产出售时间
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取房产出售时间")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, paramType = "param")
    public String getFdcqCssj(@RequestParam("xmid") String xmid) {
        return bdcNtFczmService.getFdcqCssj(xmid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明不动产单元号到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存权属证明不动产单元号到redis")
    @ApiImplicitParam(name = "list", value = "打印请求参数", required = true, paramType = "body")
    public BdcQszmDyDTO saveNtFcdaBdcdyhToRedis(@RequestBody List<BdcQszmQO> list) {
        return bdcNtFczmService.saveNtFcdaBdcdyhToRedis(list);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印权属证明的XML数据")
    @ApiImplicitParam(name = "key", value = "redis key", required = true, paramType = "path")
    public String getPrintXmlOfQszm(@PathVariable("key") String key) {
        return bdcNtFczmService.getPrintXmlOfQszm(key);
    }

    /**
     * @param bdcPrintParamDTO 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 缓存打印利害关系人、律师查询参数到redis
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("缓存打印利害关系人、律师查询参数到redis")
    @ApiImplicitParam(name = "bdcPrintParamDTO", value = "打印请求参数", required = true, paramType = "body")
    public BdcPrintParamQO saveLhgxrLscxPrintParam(@RequestBody BdcPrintParamDTO bdcPrintParamDTO) {
        return bdcNtFczmService.saveLhgxrLscxPrintParam(bdcPrintParamDTO);
    }

    /**
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （南通）获取打印利害关系人、律师查询的XML数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印利害关系人、律师查询的XML数据")
    @ApiImplicitParam(name = "key", value = "参数缓存Redis键值", required = true, paramType = "path")
    public String getPrintXmlOfLhgxrLscx(@PathVariable("key") String key) {
        return bdcNtFczmService.getPrintXmlOfLhgxrLscx(key);
    }

    /**
     * 提供给受理，生成有房无房证明XML的数据服务
     * @param {bdcFczmDTO} qlrmc 权利人名称， qlrzjh 权利人证件号
     * @return xml 打印有房无房的XMl数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("提供给受理，生成有房无房证明XML的数据服务")
    @ApiImplicitParam(name = "BdcFczmDTO", value = "房产证明DTO", required = true, paramType = "body")
    public String generateYfwfzmXml(@RequestBody BdcFczmDTO bdcFczmDTO) {
        return bdcNtFczmService.generateYfwfzmXml(bdcFczmDTO);
    }

    /**
     * 南通获取权属证明及对应PDF文件信息（为了支持后续需求变动，例如字段新增直接返回Map集合，对照打印数据源）
     * @param bdcQszmQO 权利人证号、不动产权证号参数
     * @return {List} 权属证明信息
     * @throws Exception
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("南通获取权属证明及对应PDF文件信息")
    @ApiImplicitParam(name = "bdcQszmQO", value = "权利人证号、不动产权证号参数", required = true, paramType = "body")
    public List<Map<String, Object>> queryQszm(@RequestBody BdcQszmQO bdcQszmQO) throws Exception {
        return bdcNtFczmService.queryQszm(bdcQszmQO);
    }

}
