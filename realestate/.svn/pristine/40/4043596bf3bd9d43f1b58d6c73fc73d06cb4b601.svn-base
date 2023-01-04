package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.AccessBuildingService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.service.rest.building.AccessBuildingRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/13
 * @description
 */
@RestController
@RequestMapping("/building/rest/v1.0/access")
@Api(tags = "上报获取权籍数据接口")
public class AccessBuildingRestController extends BaseController implements AccessBuildingRestService {

    @Autowired
    private AccessBuildingService accessBuildingService;

    @Override
    @GetMapping("/query/fwc")
    @ApiOperation(value = "上报查询户室层信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttFwCDO> queryKttFwCList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttFwCList(bdcdyh);
    }

    @Override
    @GetMapping("/query/fwcDz")
    @ApiOperation(value = "上报查询户室层信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttFwCDO> queryKttFwCListDz(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttFwCListDz(bdcdyh);
    }

    @Override
    @GetMapping("/query/fwzrz")
    @ApiOperation(value = "上报查询自然幢信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<Map> queryKttFwZrzList(@RequestParam("bdcdyh") String
                                               bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttFwZrzList(bdcdyh);
    }

    @Override
    @GetMapping("/query/fwh")
    @ApiOperation(value = "上报查询户室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sfdz", value = "是否独幢", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttFwHDO> queryKttFwHList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("sfdz") boolean sfdz,
                                          @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        if (sfdz) {
            return accessBuildingService.queryKttFwHListIsDz(bdcdyh);
        } else {
            return accessBuildingService.queryKttFwHListNotDz(bdcdyh);
        }
    }

    @Override
    @GetMapping("/query/zd")
    @ApiOperation(value = "上报查询宗地宗海信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttGyJzdDO> queryKttGyJzdList(@RequestParam("bdcdyh") String
                                                      bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttGyJzdList(bdcdyh);
    }

    @Override
    @GetMapping("/query/jzx")
    @ApiOperation(value = "上报查询界址线信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttGyJzxDO> queryKttGyJzxList(@RequestParam("bdcdyh") String
                                                      bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttGyJzxList(bdcdyh);
    }

    @Override
    @GetMapping("/query/fwljz")
    @ApiOperation(value = "上报查询逻辑幢信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttFwLjzDO> queryKttFwLjzList(@RequestParam("bdcdyh") String
                                                      bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryKttFwLjzList(bdcdyh);
    }

    @Override
    @GetMapping("/query/zhk")
    @ApiOperation(value = "上报查询宗海空间信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<ZhKDO> queryZhkList(@RequestParam("bdcdyh") String
                                            bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryZhkList(bdcdyh);
    }

    /**
     * 根据宗地代码查询宗地变更记录表，按照更新时间倒序
     *
     * @param bh     宗地代码
     * @param qjgldm
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @GetMapping("/query/zdbgjlb")
    @ApiOperation(value = "上报查询宗地变更信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "宗地代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<Map> queryZdbgjlbList(@RequestParam("bh") String
                                              bh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bh)) {
            return null;
        }
        return accessBuildingService.queryZdbgjlbList(bh);
    }

    /**
     * 根据宗地代码查询宗地宗地空间属性
     *
     * @param bdcdyh
     * @param qjgldm
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @GetMapping("/query/zdk")
    @ApiOperation(value = "上报查询宗地空间信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<ZdKDO> queryZdList(@RequestParam("bdcdyh") String
                                           bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return accessBuildingService.queryZdkList(bdcdyh);
    }


}