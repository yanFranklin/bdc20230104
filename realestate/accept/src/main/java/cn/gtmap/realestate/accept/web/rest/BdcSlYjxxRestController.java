package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlYjxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYjxxRestService;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description 不动产受理邮寄信息REST服务
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 *
 */
@RestController
@Api(tags = "不动产受理邮寄信息REST服务")
public class BdcSlYjxxRestController extends BaseController implements BdcSlYjxxRestService {

    @Autowired
    private BdcSlYjxxService bdcSlYjxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询邮寄信息", notes = "根据工作流实例ID查询邮寄信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlYjxxDTO> queryBdcSlYjxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        final List<BdcSlYjxxDO> bdcSlYjxxDOList = this.bdcSlYjxxService.queryYjxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlYjxxDOList)){
            return Collections.emptyList();
        }
        List<BdcSlYjxxDTO> bdcSlYjxxDTOList =new ArrayList<>(1);
        for(BdcSlYjxxDO bdcSlYjxxDO:bdcSlYjxxDOList){
            BdcSlYjxxDTO bdcSlYjxxDTO = new BdcSlYjxxDTO();
            BeanUtils.copyProperties(bdcSlYjxxDO, bdcSlYjxxDTO);
            bdcSlYjxxDTOList.add(bdcSlYjxxDTO);
        }
        return bdcSlYjxxDTOList;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产受理邮寄信息", notes = "cx不动产受理邮寄信息")
    @ApiImplicitParam(name = "bdcSlYjxxQO", value = "不动产受理邮寄信息QO", required = true, dataType = "BdcSlYjxxQO", paramType = "body")
    public List<BdcSlYjxxDO> listBdcSlYjxx(@RequestBody BdcSlYjxxQO bdcSlYjxxQO) {
        return bdcSlYjxxService.listBdcSlYjxx(bdcSlYjxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理邮寄信息", notes = "保存不动产受理邮寄信息")
    @ApiImplicitParam(name = "bdcSlYjxxDTO", value = "不动产受理邮寄信息DTO", required = true, dataType = "BdcSlYjxxDTO", paramType = "body")
    public String saveBdcSlYjxx(@RequestBody BdcSlYjxxDTO bdcSlYjxxDTO) {
        BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
        BeanUtils.copyProperties(bdcSlYjxxDTO, bdcSlYjxxDO);
        if(StringUtils.isBlank(bdcSlYjxxDO.getYjxxid())){
            bdcSlYjxxDO = this.bdcSlYjxxService.insertBdcSlYjxx(bdcSlYjxxDO);
        }else{
            this.bdcSlYjxxService.updateBdcSlYjxxByYjxxId(bdcSlYjxxDO);
        }
        return bdcSlYjxxDO.getYjxxid();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID删除邮寄信息", notes = "根据工作流实例ID删除邮寄信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void deleteBdcSlYjxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"未获取到工作流实例ID。");
        this.bdcSlYjxxService.removeYjxxByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化不动产邮寄信息", notes = "初始化不动产邮寄信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public BdcSlYjxxDTO initBdcSlYjxx(@PathVariable(value = "gzlslid") String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"未获取到工作流实例ID。");
        BdcSlYjxxDO bdcSlYjxxDO = this.bdcSlYjxxService.initBdcYjxx(gzlslid);
        BdcSlYjxxDTO bdcSlYjxxDTO = new BdcSlYjxxDTO();
        BeanUtils.copyProperties(bdcSlYjxxDO, bdcSlYjxxDTO);
        return bdcSlYjxxDTO;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新实体部分属性数据值", notes = "更新实体部分属性数据值")
    @ApiImplicitParam(name = "json", value = "邮寄信息json字符串", required = true, dataType = "String", paramType = "body")
    public int updateByJsonEntity(@RequestBody String json) {
        return this.bdcSlYjxxService.updateBdcYjxx(json);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送邮寄信息数据至EMS", notes = "推送邮寄信息数据至EMS")
    @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "param")
    public String pushYjxxToEms(@RequestParam(name="processInsId") String processInsId, @RequestParam(value = "currentUserName",required = false) String currentUserName) {
        return this.bdcSlYjxxService.pushYjxxToEms(processInsId,currentUserName);
    }


}
