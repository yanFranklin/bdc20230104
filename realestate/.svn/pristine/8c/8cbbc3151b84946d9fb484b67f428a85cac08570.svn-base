package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxmPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmSfbzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfxmPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2019/1/3
 * @description 不动产受理收费项目配置rest服务
 */
@RestController
@Api(tags = "不动产受理收费项目配置rest服务")
public class BdcSlSfxmPzRestController extends BaseController implements BdcSlSfxmPzRestService {
    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 不动产受理收费项目配置数据服务
     */
    @Autowired
    private BdcSlSfxmPzService bdcSlSfxmPzService;
    @Autowired
    private BdcSlSfxmSfbzService bdcSlSfxmSfbzDOService;


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据登记小类获取不动产受理收费项目配置", notes = "根据登记小类获取不动产受理收费项目配置服务")
    @ApiImplicitParam(name = "djxl", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxl(@PathVariable(value = "djxl") String djxl) {
        return bdcSlSfxmPzService.listBdcSlSfxmPzByDjxl(djxl);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费项目代码获取不动产收费项目收费标准", notes = "根据收费项目代码获取不动产收费项目收费标准服务")
    @ApiImplicitParam(name = "sfxmdm", value = "收费项目代码", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDO(@PathVariable(value = "sfxmdm") String sfxmdm) {
        return bdcSlSfxmSfbzDOService.listBdcSlSfxmSfbzDO(sfxmdm);
    }

    /**
     * @return 不动产收费项目收费标准
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取不动产收费项目收费标准
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取不动产收费项目收费标准", notes = "获取不动产收费项目收费标准服务")
    public List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbz() {
        return bdcSlSfxmSfbzDOService.listBdcSlSfxmSfbzDOAll();
    }

    /**
     * @param bdcSlSfxmPzDO 不动产受理收费项目配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理收费项目配置", notes = "保存不动产受理收费项目配置服务")
    @ApiImplicitParam(name = "bdcSlSfxmPzDO", value = "不动产受理收费项目配置", required = true, dataType = "BdcSlSfxmPzDO", paramType = "query")
    public int saveBdcSlSfxmPzDO(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        return bdcSlSfxmPzService.saveBdcSlSfxmPzDO(bdcSlSfxmPzDO);

    }

    /**
     * @param bdcSlSfxmPzDOList 收费项目配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收费项目配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除不动产受理收费项目配置", notes = "删除不动产受理收费项目配置服务")
    @ApiImplicitParam(name = "bdcSlSfxmPzDOList", value = "收费项目配置集合", required = true, dataType = "BdcSlSfxmPzDO", paramType = "query")
    public int deleteBdcSlSfxmPzDO(@RequestBody List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList) {
        return bdcSlSfxmPzService.deleteBdcSlSfxmPzDO(bdcSlSfxmPzDOList);

    }

    /**
     * @param pageable
     * @param bdcSlSfxmPzJson    收费项目配置
     * @return 收件材料配置分页
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据配置ID删除不动产受理收件材料配置信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费项目配置分页", notes = "收费项目配置分页服务")
    public Page<BdcSlSfxmPzDO> listBdcSlSfxmPzByPage(Pageable pageable, @RequestParam(name = "bdcSlSfxmPzJson", required = false) String bdcSlSfxmPzJson) {
        return bdcSlSfxmPzService.listBdcSlSfxmPzByPage(pageable, bdcSlSfxmPzJson);
    }

    /**
     * @param bdcSlSfxmPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收费项目配置最大序号
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取收费项目配置最大序号", notes = "获取收费项目配置最大序号")
    public Integer querySfxmPzMaxSxh(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        return bdcSlSfxmPzService.querySfxmPzMaxSxh(bdcSlSfxmPzDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取所有收费项目配置", notes = "获取所有收费项目配置")
    public List<BdcSlSfxmDTO> listAllBdcSlSfxmDTO(){
        return bdcSlSfxmPzService.listAllBdcSlSfxmDTO();

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据登记小类获取收费项目配置", notes = "根据登记小类获取收费项目配置")
    @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSfxmDTO> listBdcSfxmDTOByDjxl(@PathVariable(value="djxl") String djxl) {
        return bdcSlSfxmPzService.listBdcSlSfxmDTOByDjxl(djxl);
    }

}
