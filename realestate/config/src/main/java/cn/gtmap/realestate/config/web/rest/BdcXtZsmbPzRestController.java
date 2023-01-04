package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbSqlDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsmbPzQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtZsmbPzRestService;
import cn.gtmap.realestate.config.service.BdcXtZsmbPzService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description 业务配置系统：证书模板配置请求服务处理
 */
@RestController
@Api(tags = "不动产证书模板配置服务接口")
public class BdcXtZsmbPzRestController implements BdcXtZsmbPzRestService {
    @Autowired
    private BdcXtZsmbPzService bdcXtZsmbPzService;


    /**
     * @param pageable        分页对象
     * @param zsmbpzParamJson 查询条件
     * @return {Page} 证书模板配置分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询证书模板配置数据列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询证书模板数据列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zsmbpzParamJson", value = "证书模板查询参数JSON", required = false, paramType = "query")})
    public Page<BdcXtZsmbPzDO> listBdcXtZsmbPz(Pageable pageable,
                                               @RequestParam(name = "zsmbpzParamJson", required = false) String zsmbpzParamJson) {

        return bdcXtZsmbPzService.listBdcXtZsmbPz(pageable, JSON.parseObject(zsmbpzParamJson, BdcXtZsmbPzQO.class));
    }

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {Boolean} 正确：true，不正确：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 校验证书模板配置SQL是否正确
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("校验证书模板配置SQL是否正确")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcXtZsmbPzDO", value = "证书模板配置实体", required = true, paramType = "body")})
    public Boolean checkBdcXtZsmbPzSql(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO) {
        if (StringUtils.isBlank(bdcXtZsmbPzDO.getMbsql())) {
            return false;
        }

        return bdcXtZsmbPzService.checkBdcXtZsmbPzSql(bdcXtZsmbPzDO.getMbsql());
    }
    /**
     * @param bdcXtZsmbSqlDTO 证书模板配置sql实体
     * @return {List<String></>} 正确：true，不正确：false
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 验证证书模板配置SQL输入特定参数后执行结果
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("校验证书模板配置SQL,并按输入参数值注入sql,执行并返回执行结果")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcXtZsmbSqlDTO", value = "证书模板配置sql实体", required = true, paramType = "body")})
    public List<String> checkBdcXtZsmbPzSqlcs(@RequestBody BdcXtZsmbSqlDTO bdcXtZsmbSqlDTO) {
        if (StringUtils.isBlank(bdcXtZsmbSqlDTO.getSql())) {
            return new ArrayList<String>(Collections.singleton("模板sql为空！"));
        }
        return bdcXtZsmbPzService.checkBdcXtZsmbPzSqlcs(bdcXtZsmbSqlDTO.getSql(), bdcXtZsmbSqlDTO.getCsmc(), bdcXtZsmbSqlDTO.getCsz());
    }

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存证书模板配置配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存证书模板配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcXtZsmbPzDO", value = "证书模板配置", required = true, paramType = "body")})
    public int saveBdcXtZsmbPz(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO) {
        if (null == bdcXtZsmbPzDO || null == bdcXtZsmbPzDO.getQllx()) {
            throw new NullPointerException("配置系统-保存证书模板配置：请求参数‘证书模板配置或权利类型’为空！");
        }

        return bdcXtZsmbPzService.saveBdcXtZsmbPz(bdcXtZsmbPzDO);
    }

    /**
     * @param bdcXtZsmbPzDOList 证书模板配置实体集合
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 删除证书模板配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除证书模板")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtZsmbPzDOList", value = "证书模板配置集合", required = true, paramType = "body")})
    public int deleteBdcXtZsmbPz(@RequestBody List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList) {
        return bdcXtZsmbPzService.deleteBdcXtZsmbPz(bdcXtZsmbPzDOList);
    }

    /**
     * 根据权利类型查询,主要用于导入时判断是否存在
     *
     * @param qllx
     * @return BdcXtZsmbPzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:22 2020/8/7
     */
    @Override
    @ApiOperation(value = "查询证书模板ByQllx")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "qllx", value = "权利类型", required = true, dataType = "Integer", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcXtZsmbPzDO queryBdcXtZsmbPzByQllx(@PathVariable("qllx") Integer qllx) {
        return bdcXtZsmbPzService.queryBdcXtZsmbPzByQllx(qllx);
    }

    /**
     * 根据zsmbid查询，主要用于导出时，循环查询
     *
     * @param zsmbid
     * @return queryBdcXtZsmbPz
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:51 2020/8/7
     */
    @Override
    @ApiOperation(value = "查询证书模板ByZsmbid")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "zsmbid", value = "证书模板id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcXtZsmbPzDO queryBdcXtZsmbPzByZsmbid(@PathVariable("zsmbid") String zsmbid) {
        return bdcXtZsmbPzService.queryBdcXtZsmbPzByZsmbid(zsmbid);
    }

}
