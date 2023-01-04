package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSdRestService;
import cn.gtmap.realestate.inquiry.service.BdcSdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-07
 * @description 不动产单元、产权证锁定
 */
@RestController
@Api(tags = "不动产单元、产权证锁定")
public class BdcSdRestController implements BdcSdRestService {
    @Autowired
    BdcSdService bdcSdService;

    /**
     * @param bdcDysdDOList
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产单元
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "锁定不动产单元")
    @ApiImplicitParam(name = "bdcDysdDO", value = "不动产单元锁定", required = true, paramType = "body")
    public int sdBdcdy(@RequestBody List<BdcDysdDO> bdcDysdDOList) {
        return bdcSdService.sdBdcdy(bdcDysdDOList, 1);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成不动产单元锁定信息")
    @ApiImplicitParam(name = "bdcDysdDO", value = "不动产单元锁定", required = true, paramType = "body")
    public void scSdBdcdyxx(@RequestBody List<BdcDysdDO> bdcDysdDOList) {
        this.bdcSdService.addBdcDySdxx(bdcDysdDOList, null);
    }

    /**
     * @param bdcZssdDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产权证
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "锁定不动产单元(无锁定原因)")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public void sdBdczsNoSdyy(@RequestBody List<BdcZssdDO> bdcZssdDOList) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new AppException("锁定不动产证书的参数不能为空");
        }
        bdcSdService.addBdcZsSdxx(bdcZssdDOList, null);
    }
    /**
     * @param bdcZssdDOList
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产权证
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "锁定不动产单元")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public int sdBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList, String sdyy) {
        if (CollectionUtils.isEmpty(bdcZssdDOList) || StringUtils.isBlank(sdyy)) {
            throw new AppException("锁定不动产证书的参数不能为空");
        }
        return bdcSdService.sdBdczs(bdcZssdDOList, 1,sdyy);
    }

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param bdcZssdDOList 证书锁定集合
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新证书锁定")
    @ApiImplicitParam(name = "zsids", value = "更新锁定的证书 id", required = true, paramType = "body")
    public int updateSdZs(@RequestBody List<BdcZssdDO> bdcZssdDOList) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new MissingArgumentException("zsid 集合");
        }
        return bdcSdService.updateSdZs(bdcZssdDOList);
    }

    /**
     * @param bdcDysdDOList
     * @param jsyy
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产单元
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "解锁不动产单元")
    @ApiImplicitParam(name = "bdcDysdDOList", value = "不动产单元锁定", required = true, paramType = "body")
    public int jsBdcdy(@RequestBody List<BdcDysdDO> bdcDysdDOList,
                       @RequestParam("jsyy") String jsyy) {
        return bdcSdService.jsBdcdy(bdcDysdDOList, jsyy);
    }

    /**
     * @param bdcZssdDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产权证
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "解锁不动产证书")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public int jsBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList,
                       @RequestParam("sdyy") String sdyy) {
        return bdcSdService.jsBdczs(bdcZssdDOList, sdyy);
    }

    /**
     * @param bdcDysdDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产单元锁定
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取不动产单元锁定")
    @ApiImplicitParam(name = "bdcDysdDO", value = "不动产单元锁定", required = true, paramType = "body")
    public List<BdcDysdDO> queryBdcdySd(@RequestBody BdcDysdDO bdcDysdDO) {
        return bdcSdService.queryBdcdySd(bdcDysdDO);
    }

    /**
     * @param bdcZssdDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产证书锁定
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取不动产证书锁定")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public List<BdcZssdDO> queryBdczsSd(@RequestBody BdcZssdDO bdcZssdDO) {
        return bdcSdService.queryBdczsSd(bdcZssdDO);
    }

    /**
     * @param bdcDysdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产单元锁定备注
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存不动产单元锁定备注")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public int saveBdcdysdBz(@RequestBody BdcDysdDO bdcDysdDO) {
        return bdcSdService.saveBdcdysdBz(bdcDysdDO);
    }

    /**
     * @param bdcZssdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产证书锁定备注
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存不动产证书锁定备注")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public int saveBdczssdBz(@RequestBody BdcZssdDO bdcZssdDO) {
        return bdcSdService.saveBdczssdBz(bdcZssdDO);
    }

    /**
     * @param bdcZssdDOList 证书锁定集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除证书锁定信息 <br>
     * 删除补录修改流程时需要同步删除证书锁定信息。
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除证书锁定")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "body")
    public int deleteBdczsSd(@RequestBody List<BdcZssdDO> bdcZssdDOList) {
        return bdcSdService.deleteBdczsSd(bdcZssdDOList);
    }

    /**
     * 根据gzlslid删除证书锁定数据
     * @param gzlslid
     * @description 业务初始化生成的证书锁定数据 删除业务时需要删除锁定数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除证书锁定")
    @ApiImplicitParam(name = "bdcZssdDO", value = "证书锁定", required = true, paramType = "query")
    public int deleteBdczsSdByGzlslid(@RequestParam(value = "processInsId") String gzlslid) {
        return bdcSdService.deleteBdczsSdByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询证书锁定信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, paramType = "path")
    public List<BdcZssdDO> queryBdczsSdByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSdService.queryBdczsSdByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询单元锁定信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, paramType = "path")
    public List<BdcDysdDO> queryBdcDySdByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSdService.queryBdcDySdByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过工作流实例ID修改证书锁定数据")
    @ApiImplicitParam(name = "bdcZssdDO", value = "不动产证书锁定DO", required = true, paramType = "body")
    public void updateZssdByGzlslid(@RequestBody BdcZssdDO bdcZssdDO) {
        this.bdcSdService.updateZssdByGzlslid(bdcZssdDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过工作流实例ID修改证书锁定数据")
    @ApiImplicitParam(name = "bdcDysdDO", value = "不动产证书锁定DO", required = true, paramType = "body")
    public void updateDysdByGzlslid(@RequestBody BdcDysdDO bdcDysdDO) {
        this.bdcSdService.updateDysdByGzlslid(bdcDysdDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "锁定/冻结 不动产证书或不动产单元数据")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, paramType = "query")
    public void bdczsSdDj(@RequestParam(name="processInsId")String processInsId) {
        this.bdcSdService.bdczsSdDj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "解锁/解冻 不动产证书或不动产单元数据")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, paramType = "query")
    public void bdczsSdJd(@RequestParam(name="processInsId")String processInsId) {
        this.bdcSdService.bdczsSdJd(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据bdcdyh解锁不动产单元锁定")
    @ApiImplicitParam(name = "bdcDysdDO", value = "不动产单元锁定", required = true, paramType = "body")
    public void jsBdcdyhByBdcdyh(@RequestBody BdcDysdDO bdcDysdDO) {
        this.bdcSdService.jsBdcdyhByBdcdyh(bdcDysdDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据cqzh解锁不动产证书锁定")
    @ApiImplicitParam(name = "bdcZssdDO", value = "不动产证书锁定", required = true, paramType = "body")
    public void jsBdczsByCqzh(@RequestBody BdcZssdDO bdcZssdDO) {
        this.bdcSdService.jsBdczsByCqzh(bdcZssdDO);
    }

    /**
     * 根据主键ID查询锁定信息
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键ID查询锁定信息")
    @ApiImplicitParam(name = "sdxxid", value = "主键ID", required = true, paramType = "param")
    public Object queryBdcSdxxById(@PathVariable("sdxxid") String sdxxid) {
        return bdcSdService.queryBdcSdxxById(sdxxid);
    }

    /**
     * 根据cqzh批量查询证书锁定信息
     * @param baseQO 主键ID
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据cqzh批量查询证书锁定信息")
    @ApiImplicitParam(name = "baseQO", value = "", required = true, paramType = "param")
    public List<BdcZssdDO> queryBdcZssdByCqzhs(@RequestBody BaseQO baseQO) {
        if(CollectionUtils.isEmpty(baseQO.getIds())){
            return Lists.newArrayList();
        }
        return bdcSdService.queryBdcZssdByCqzhs(baseQO.getIds());
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "解锁原项目的不动产证书")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, paramType = "query")
    public void jsBdcZsByYcqzh(@RequestParam(name="processInsId") String processInsId) {
        this.bdcSdService.jsBdczsByCqzh(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量删除证书锁定信息")
    @ApiImplicitParam(name = "zssdIdList", value = "证书锁定ID集合", required = true, paramType = "body")
    public int batchDeleteBdcZssd(@RequestBody List<String> zssdIdList) {
        return this.bdcSdService.batchDeleteBdcZssd(zssdIdList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过gzlslid获取历史关系，通过yxmid获取证书锁定/冻结数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sdzt", value = "锁定状态", required = false, paramType = "param")
    })
    public List<BdcZssdDO> queryYxmZssd(@PathVariable("gzlslid") String gzlslid, @RequestParam(value ="sdzt", required = false) Integer sdzt) {
        return this.bdcSdService.queryYxmZssd(gzlslid, sdzt);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过gzlslid获取历史关系，通过yxmid获取单元锁定/冻结数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sdzt", value = "锁定状态", required = false, paramType = "param")
    })
    public List<BdcDysdDO> queryYxmDysd(@PathVariable("gzlslid") String gzlslid, @RequestParam(value ="sdzt", required = false) Integer sdzt) {
        return this.bdcSdService.queryYxmDysd(gzlslid, sdzt);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据XMID查询证书锁定信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, paramType = "path")
    public List<BdcZssdDO> queryBdczsSdByXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcSdService.queryBdczsSdByXmid(xmid);
    }
}
