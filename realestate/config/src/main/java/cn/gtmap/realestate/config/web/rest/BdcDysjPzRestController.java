package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcDysjPzDateQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcDysjPzRestService;
import cn.gtmap.realestate.config.service.BdcDysjPzServce;
import com.alibaba.fastjson.JSON;
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
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description 不动产系统打印数据源配置服务接口
 */

@RestController
@Api(tags = "不动产系统打印数据源配置服务接口")
public class BdcDysjPzRestController implements BdcDysjPzRestService {
    @Autowired
    BdcDysjPzServce bdcDysjPzServce;

    /**
     * @param pageable
     * @param dysjpzParamJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询打印数据源配置列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "dysjpzParamJson", value = "打印数据源查询参数JSON", required = false, paramType = "query")})
    public Page<BdcDysjPzDO> listBdcDysjPz(Pageable pageable, @RequestParam(name = "dysjZbPzParamJson", required = false) String dysjpzParamJson) {
        BdcDysjPzDateQO bdcDysjPzDateQO = JSON.parseObject(dysjpzParamJson, BdcDysjPzDateQO.class);
        Page<BdcDysjPzDO> ls = bdcDysjPzServce.listBdcDysjPzByPage(pageable, bdcDysjPzDateQO);
        return ls;
    }


    /**
     * @param bdcDysjPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存系统打印数据源配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysjPzDO", value = "不动产系统打印数据源配置", required = true, paramType = "body")})
    public int saveBdcDysjPzDO(@RequestBody BdcDysjPzDO bdcDysjPzDO) {
        return bdcDysjPzServce.saveBdcDysjPz(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改系统打印数据源配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysjPzDO", value = "不动产系统打印数据源配置", required = true, paramType = "body")})
    public int updateBdcDysjPzDO(@RequestBody BdcDysjPzDO bdcDysjPzDO) {
        return bdcDysjPzServce.updateBdcDysjPz(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除打印数据源配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysjPzDOList", value = "打印数据源配置集合", required = true, paramType = "body")})
    public int deleteBdcDysjPzDO(@RequestBody List<BdcDysjPzDO> bdcDysjPzDOList) {
        return bdcDysjPzServce.deleteBdcDysjPz(bdcDysjPzDOList);
    }


    /**
     * @param id@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据源配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印数据源配置")
    @ApiImplicitParam(name = "id", value = "打印数据源配置主键", required = true, paramType = "String")
    public BdcDysjPzDO queryBdcDysjPzDO(@RequestParam(name = "id") String id) {
        return bdcDysjPzServce.queryBdcDysjPz(id);
    }

    /**
     * @param dylxList 打印类型
     * @return BdcDysjPzDO 打印数据信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型获取打印主表配置信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据打印类型获取打印主表配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dylx", value = "打印类型", required = true, paramType = "String")})
    public Map queryBdcDysjPzByDylx(@RequestBody List<String> dylxList) {
        return bdcDysjPzServce.queryBdcDysjPzByDylx(dylxList);
    }

    /**
     * @param id@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取子表打印数据源配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取子表打印数据源配置")
    @ApiImplicitParam(name = "id", value = "打印数据源子表配置主键", required = true, paramType = "String")
    public BdcDysjZbPzDO queryBdcDysjZbPzDO(@RequestParam(name = "id") String id) {
        return bdcDysjPzServce.queryBdcDysjZbPz(id);
    }

    /**
     * @param pageable
     * @param dysjZbPzParamJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印子表数据
     */
    @Override
    public Page<BdcDysjZbPzDO> listBdcDysjZbPz(Pageable pageable, @RequestParam(name = "dysjZbPzParamJson", required = false) String dysjZbPzParamJson) {
        return bdcDysjPzServce.listBdcDysjZbPzByPage(pageable, JSON.parseObject(dysjZbPzParamJson, BdcDysjZbPzDO.class));
    }

    /**
     * @param bdcDysjZbPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印子表数据
     */
    @Override
    public int saveBdcDysjZbPzDO(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        return bdcDysjPzServce.saveBdcDysjZbPz(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印子表数据
     */
    @Override
    public int updateBdcDysjZbPzDO(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        return bdcDysjPzServce.updateBdcDysjZbPz(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印子表数据
     */
    @Override
    public int deleteBdcDysjZbPzDO(@RequestBody List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        return bdcDysjPzServce.deleteBdcDysjZbPz(bdcDysjZbPzDOList);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取子表配置数量
     */
    @Override
    public int countBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        return bdcDysjPzServce.countBdcDysjZbPz(bdcDysjZbPzDO);
    }

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDTO 打印配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型查询主表和子表的配置信息，如果打印类型在库里重复，则会给出报错信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据打印类型查询主表和子表的配置信息")
    @ApiImplicitParam(name = "dylx", value = "打印类型", dataType = "String", paramType = "path")
    @Override
    public BdcDysjPzDTO getPzxx(@PathVariable(value = "dylx") String dylx) {
        return bdcDysjPzServce.getPzxx(dylx);
    }

    /**
     * @param bdcDysjPzDTO 打印配置数据DTO
     * @return int 更新/保存的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新打印配置信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存或更新打印配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dylx", value = "打印类型", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcDysjPzDTO", value = "打印配置信息", dataType = "BdcDysjPzDTO", paramType = "body")})
    @Override
    public BdcDysjPzDTO saveOrUpdatePzxx(@RequestBody BdcDysjPzDTO bdcDysjPzDTO) {
        return bdcDysjPzServce.saveOrUpdatePzxx(bdcDysjPzDTO);
    }

    /**
     * @param xml xml信息
     * @return String  redisKey 保存到redis中的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将xml信息保存到redis中，设置有效期为60秒
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存或更新打印配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xml", value = "xml信息", dataType = "String", paramType = "body")})
    @Override
    public String sendXmlToRedis(@RequestBody String xml) {
        return bdcDysjPzServce.sendXmlToRedis(xml);
    }

    /**
     * @param redisKey redis健
     * @return String xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 从redis中获取保存的xml信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("从redis获取打印的xml信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "redisKey", value = "redisKey", dataType = "String", paramType = "path")})
    @Override
    public String getXmlFromRedis(@PathVariable(value = "redisKey") String redisKey) {
        return bdcDysjPzServce.getXmlFromRedis(redisKey);
    }

    @Override
    public int checkDylx(String dylx) {
        return bdcDysjPzServce.checkDylx(dylx);
    }
}
