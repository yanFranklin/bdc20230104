package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcDjlxDjxlGxDO;
import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.domain.BdcDjxlQllxGxDO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCshXtPzRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjlxDjxlQllxVO;
import cn.gtmap.realestate.init.core.service.BdcCshFwkgService;
import cn.gtmap.realestate.init.core.service.BdcDjlxDjxlGxService;
import cn.gtmap.realestate.init.core.service.BdcDjxlDjyyGxService;
import cn.gtmap.realestate.init.core.service.BdcDjxlQllxGxService;
import cn.gtmap.realestate.init.service.other.InitFwKgService;
import cn.gtmap.realestate.init.web.BaseController;
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

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 不动产初始化配置服务接口
 */
@RestController
@Api(tags = "不动产初始化配置服务接口")
public class BdcCshXtPzRestController extends BaseController implements BdcCshXtPzRestService {
    /**
     * 初始化服务开关 服务
     */
    @Autowired
    BdcCshFwkgService bdcCshFwkgService;
    /**
     * 登记类型 登记小类 关系服务
     */
    @Autowired
    BdcDjlxDjxlGxService bdcDjlxDjxlGxService;
    /**
     * 登记小类登记原因关系服务
     */
    @Autowired
    BdcDjxlDjyyGxService bdcDjxlDjyyGxService;
    /**
     * 登记小类 权利类型关系服务
     */
    @Autowired
    BdcDjxlQllxGxService bdcDjxlQllxGxService;
    @Autowired
    InitFwKgService initFwKgService;

    /**
     * 根据业务类型获取该流程初始化服务的开关信息
     *
     * @param djxl 登记小类
     * @return {BdcCshFwkgDO} 初始化开关实体
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ApiOperation(value = "根据业务类型获取该流程初始化服务的开关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "djxl", value = "登记小类", required = true, type = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcCshFwkgDO queryBdcCshFwKgDO(@PathVariable("djxl") String djxl) {
        return initFwKgService.queryBdcCshFwKgDO(djxl);
    }

    /**
     * @param bdcCshFwkgDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="获取初始化服务开关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCshFwkgDO", value = "初始化服务开关配置", required = true, dataType = "BdcCshFwkgDO")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcCshFwkgDO> listBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO) {
        return bdcCshFwkgService.listBdcCshFwkg(bdcCshFwkgDO);
    }

    /**
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    @ApiOperation(value ="新增初始化服务开关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCshFwkgDO", value = "初始化服务开关配置", required = true, dataType = "BdcCshFwkgDO")})
    @ResponseStatus(HttpStatus.CREATED)
    public int insertBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO) {
        return bdcCshFwkgService.insertBdcCshFwkg(bdcCshFwkgDO);
    }

    /**
     * @param bdcCshFwkgDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    @ApiOperation(value ="修改初始化服务开关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCshFwkgDO", value = "初始化服务开关配置", required = true, dataType = "BdcCshFwkgDO")})
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO) {
        return bdcCshFwkgService.updateBdcCshFwkg(bdcCshFwkgDO);
    }

    /**
     * @param bdcCshFwkgDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    @ApiOperation(value ="删除初始化服务开关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCshFwkgDOList", value = "初始化服务开关配置", required = true, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcCshFwkg(@RequestBody List<BdcCshFwkgDO> bdcCshFwkgDOList) {
        return bdcCshFwkgService.deleteBdcCshFwkg(bdcCshFwkgDOList);
    }

    /**
     * @param bdcDjlxDjxlGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="获取登记类型登记小类信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjlxDjxlGxDO", value = "初始化服务开关配置", required = true, dataType = "BdcDjlxDjxlGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcDjlxDjxlGxDO> listBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {
        return bdcDjlxDjxlGxService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
    }

    /**
     * @param bdcDjlxDjxlGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    @ApiOperation(value ="新增登记类型登记小类信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjlxDjxlGxDO", value = "初始化服务开关配置", required = true, dataType = "BdcDjlxDjxlGxDO")})
    @ResponseStatus(HttpStatus.CREATED)
    public int insertBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {
        return bdcDjlxDjxlGxService.insertBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
    }

    /**
     * @param bdcDjlxDjxlGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    @ApiOperation(value ="修改登记类型登记小类信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjlxDjxlGxDO", value = "初始化服务开关配置", required = true, dataType = "BdcDjlxDjxlGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {
        return bdcDjlxDjxlGxService.updateBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
    }

    /**
     * @param bdcDjlxDjxlGxDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    @ApiOperation(value ="删除登记类型登记小类信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjlxDjxlGxDOList", value = "初始化服务开关配置", required = true, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcDjlxDjxlGx(@RequestBody List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOList) {
        return bdcDjlxDjxlGxService.deleteBdcDjlxDjxlGx(bdcDjlxDjxlGxDOList);
    }

    /**
     * @param bdcDjxlQllxGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="获取登记小类、权利类型关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlQllxGxDO", value = "登记小类、权利类型关系", required = true, dataType = "BdcDjxlQllxGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcDjxlQllxGxDO> listBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        return bdcDjxlQllxGxService.listBdcDjxlQllxGx(bdcDjxlQllxGxDO);
    }

    /**
     * @param bdcDjxlQllxGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    @ApiOperation(value ="新增登记小类、权利类型关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlQllxGxDO", value = "登记小类、权利类型关系", required = true, dataType = "BdcDjxlQllxGxDO")})
    @ResponseStatus(HttpStatus.CREATED)
    public int insertBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        return bdcDjxlQllxGxService.insertBdcDjxlQllxGx(bdcDjxlQllxGxDO);
    }

    /**
     * @param bdcDjxlQllxGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    @ApiOperation(value ="修改登记小类、权利类型关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlQllxGxDO", value = "登记小类、权利类型关系", required = true, dataType = "BdcDjxlQllxGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        return bdcDjxlQllxGxService.updateBdcDjxlQllxGx(bdcDjxlQllxGxDO);
    }

    /**
     * @param bdcDjxlQllxGxDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    @ApiOperation(value ="删除登记小类、权利类型关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlQllxGxDOList", value = "登记小类、权利类型关系", required = true, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcDjxlQllxGx(@RequestBody List<BdcDjxlQllxGxDO> bdcDjxlQllxGxDOList) {
        return bdcDjxlQllxGxService.deleteBdcDjxlQllxGx(bdcDjxlQllxGxDOList);
    }

    /**
     * @param pageable
     * @param bdcDjxlDjyyGxDOJson
     * @return Page<BdcDjxlDjyyGxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取 登记小类、登记原因关系
     */
    @Override
    @ApiOperation(value ="分页获取 登记小类、登记原因")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDOList", value = "登记小类、登记原因关系", required = true, dataType = "String")})
    @ResponseStatus(HttpStatus.OK)
    public Page<BdcDjxlDjyyGxDO> listBdcDjxlDjyyPage(Pageable pageable,@RequestParam(name ="bdcDjxlDjyyGxDOJson",required = false)  String bdcDjxlDjyyGxDOJson) {
        BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO=JSON.parseObject(bdcDjxlDjyyGxDOJson,BdcDjxlDjyyGxDO.class);
        return repo.selectPaging("listBdcDjxlDjyyByPage", bdcDjxlDjyyGxDO, pageable);
    }

    /**
     * @param bdcDjxlDjyyGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="获取登记小类、登记原因关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDO", value = "登记小类、登记原因关系", required = true, dataType = "BdcDjxlDjyyGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        return bdcDjxlDjyyGxService.listBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
    }

    @Override
    @ApiOperation(value ="查询登记原因配置顺序最大号")
    @ApiImplicitParams({@ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String")})
    @ResponseStatus(HttpStatus.OK)
    public int queryDjyyMaxCount(@RequestParam(name = "djxl") String djxl) {
        return bdcDjxlDjyyGxService.queryDjyyMaxCount(djxl);
    }

    /**
     * 根据登记小类查询登记原因（不传查所有）
     * @param djxls
     * @return
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询
     */
    @Override
    @ApiOperation(value ="根据登记小类查询登记原因（不传查所有）")
    @ApiImplicitParams({@ApiImplicitParam(name = "djxls", value = "登记小类集合", required = false, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public List<String> listDjyys(@RequestBody(required = false) List<String> djxls) {
        return bdcDjxlDjyyGxService.listDjyys(djxls);
    }

    /**
     * @param bdcDjxlDjyyGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    @ApiOperation(value ="新增登记小类、登记原因关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDO", value = "登记小类、登记原因关系", required = true, dataType = "BdcDjxlDjyyGxDO")})
    @ResponseStatus(HttpStatus.CREATED)
    public int insertBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        return bdcDjxlDjyyGxService.insertBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
    }

    /**
     * @param bdcDjxlDjyyGxDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    @ApiOperation(value ="修改登记小类、登记原因关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDO", value = "登记小类、登记原因关系", required = true, dataType = "BdcDjxlDjyyGxDO")})
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        return bdcDjxlDjyyGxService.updateBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
    }

    @Override
    @ApiOperation(value ="批量修改登记小类、登记原因关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "List<BdcDjxlDjyyGxDO>", value = "登记小类、登记原因关系", required = true, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public void batchUpdateBdcDjxlDjyyGx(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        this.bdcDjxlDjyyGxService.batchUpdateBdcDjxlDjyyGx(bdcDjxlDjyyGxDOList);
    }

    /**
     * @param bdcDjxlDjyyGxDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    @ApiOperation(value ="删除登记小类、登记原因关系 信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDOList", value = "登记小类、登记原因关系", required = true, dataType = "List")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteBdcDjxlDjyyGx(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        return bdcDjxlDjyyGxService.deleteBdcDjxlDjyyGx(bdcDjxlDjyyGxDOList);
    }

    /**
     * @param pageable
     * @param bdcDjlxDjxlQllxJson
     * @return Page<BdcDjlxDjxlQllxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取 登记类型、登记小类、登记原因、权利类型
     */
    @Override
    @ApiOperation(value ="分页获取 登记类型、登记小类、登记原因、权利类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlDjyyGxDOList", value = "登记小类、登记原因关系", required = true, dataType = "String")})
    @ResponseStatus(HttpStatus.OK)
    public Page<BdcDjlxDjxlQllxVO> listBdcDjlxDjxlQllx(Pageable pageable,@RequestParam(name ="bdcDjlxDjxlQllxJson",required = false) String bdcDjlxDjxlQllxJson) {
        BdcDjlxDjxlQllxVO bdcDjlxDjxlQllxVO=JSON.parseObject(bdcDjlxDjxlQllxJson,BdcDjlxDjxlQllxVO.class);
        return repo.selectPaging("listBdcDjlxDjxlQllxByPage", bdcDjlxDjxlQllxVO, pageable);
    }
}
