package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.core.service.BdcSlZjjgService;
import cn.gtmap.realestate.accept.service.BdcGzyzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import cn.gtmap.realestate.common.core.dto.accept.YthZjjgDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgResponseDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcZjjgRestService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description 不动产资金监管服务
 */
@RestController
@Api(tags = "不动产资金监管服务")
public class BdcZjjgRestController implements BdcZjjgRestService {

    @Autowired
    BdcSlZjjgService bdcSlZjjgService;

    @Autowired
    BdcGzyzService bdcGzyzService;

    @Autowired
    StorageClientMatcher storageClient;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取资金监管信息")
    public List<BdcSlZjjgDO> listBdcSlZjjg(@PathVariable(value = "gzlslid")String gzlslid){
        return bdcSlZjjgService.listBdcSlZjjg(gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理项目ID获取资金监管信息")
    public List<BdcSlZjjgDO> listBdcSlZjjgByXmid(@PathVariable(value = "xmid")String xmid){
        return bdcSlZjjgService.listBdcSlZjjgByXmid(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取资金监管信息")
    public List<String> getFdjywlcDyidList(@RequestParam(value = "ywlx", required = false) String ywlx) {
        return bdcSlZjjgService.getFdjywlcDyidList(ywlx);
    }

    /**
     * @param bdcSlZjjgQO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询资金监管信息
     * @date : 2021/7/22 18:35
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取资金监管信息")
    public List<BdcSlZjjgDO> listBdcSlZjjg(@RequestBody BdcSlZjjgQO bdcSlZjjgQO) {
        return bdcSlZjjgService.listBdcSlZjjg(bdcSlZjjgQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新资金监管信息状态为已撤销")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query")
    public void updateZjjgZtYcx(@RequestParam(value = "processInsId")String processInsId) {
        this.bdcSlZjjgService.updateZjjgZtYcx(processInsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新项目附表是否资金监管为是")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query")
    public void updateZjjgXmfbSfzjjg(@RequestParam(value = "processInsId")String processInsId) {
        this.bdcSlZjjgService.updateZjjgXmfbSfzjjg(processInsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新项目附表是否资金监管为否")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query")
    public void updateZjjgCxXmfbSfzjjg(@RequestParam(value = "processInsId")String processInsId) {
        this.bdcSlZjjgService.updateZjjgCxXmfbSfzjjg(processInsId);
    }

    /**
     * @param yxmid
     * @param htbh
     * @author wangyinghao
     * @description 验证是否资金监管
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证一体化平台是否资金监管")
    public String sfzjjgYthpt(@PathVariable(name = "yxmid",required = false) String yxmid,
                              @PathVariable(name = "htbh") String htbh) {
        return bdcGzyzService.sfzjjgYthpt(yxmid,htbh);
    }

    /**
     * @param processInsId
     * @param htbh
     * @author wangyinghao
     * @description 查询一体化平台资金监管信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询一体化平台资金监管信息")
    public YthZjjgDto listYthSlZjjg(@RequestParam(value = "processInsId")String processInsId,
                                    @RequestParam(value = "htbh")String htbh) {
        return bdcSlZjjgService.listYthZjjg(htbh);
    }

    /**
     * 获取文件夹下的文件的storage地址
     * @param gzlslid 工作流实例ID
     * @param wjjmc 文件夹名称
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 文件信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取文件夹下的文件的storage地址")
    public Object getFileStorageUrl(@RequestParam("htbh") String htbh) {
        return bdcSlZjjgService.getFileStorageUrl(htbh);
    }

}
