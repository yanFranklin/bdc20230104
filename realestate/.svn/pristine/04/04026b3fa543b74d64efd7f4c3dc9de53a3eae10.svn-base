package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcRyzdRestService;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcRyzdService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/12
 * @description 不动产冗余字段处理服务接口
 */
@RestController
@Api(tags = "不动产冗余字段处理服务接口")
public class BdcRyzdRestController extends BaseController implements BdcRyzdRestService{
    /**
     * 接口请求占位变量为空导致的默认值
     */
    private static final String DEFAULT_XMID = "{xmid}";

    /**
     * 冗余字段处理Service
     */
    @Autowired
    private BdcRyzdService bdcRyzdService;
    @Autowired
    BdcQlxxService bdcQlxxService;
    /**
     * 项目服务
     */
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @description 根据项目ID关联处理不动产项目中存在的冗余字段
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据项目ID处理冗余字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public void updateRyzd(@PathVariable(value = "xmid", required = true) String xmid) {
        if(StringUtils.isBlank(xmid) || DEFAULT_XMID.equals(xmid)){
            throw new NullPointerException("空参数");
        }

        // 1、先获取所有冗余字段的值
        BdcRyzdDTO bdcRyzdDTO = bdcRyzdService.getRyzd(xmid);
        // 2、再依次更新存在冗余字段的表
        bdcRyzdService.updateRyzd(bdcRyzdDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 流程实例ID
     * @description 处理流程实例ID关联的不动产项目中的冗余字段
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("处理流程实例ID关联的不动产项目中的冗余字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query")})
    @Override
    public void updateRyzdWithProcessInstId(@RequestParam("processInsId") String processInsId) {
        if(StringUtils.isEmpty(processInsId)){
            throw new NullPointerException("空参数");
        }

        // 查询流程实例关联的项目
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.warn("未查询到工作流实例ID对应项目，处理冗余字段中止！");
            return;
        }

        // 依次生成项目对应证书证明号
        for(BdcXmDTO bdcXmDO : bdcXmDOList){
            this.updateRyzd(bdcXmDO.getXmid());
        }
    }

    /**
     * @param bdcqzhMap 各项目生成的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程对应项目、权利人证号冗余字段
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新项目证号冗余字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "Map<String,List<BdcBdcqzhDTO>>", paramType = "body")})
    @Override
    public void updateRyzdBdcqzh(@RequestBody Map<String, List<BdcBdcqzhDTO>> bdcqzhMap) {
        bdcRyzdService.updateRyzdBdcqzh(bdcqzhMap);
    }

    /**
     * @param processInsId@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新权利人冗余字段
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("处理流程实例ID关联的不动产项目中的权利人冗余字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query")})
    @Override
    public void updateRyzdQlrWithProcessInstId(@RequestParam("processInsId") String processInsId) {
        if(StringUtils.isEmpty(processInsId)){
            throw new NullPointerException("空参数");
        }

        // 查询流程实例关联的项目
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.warn("未查询到工作流实例ID对应项目，处理冗余字段中止！");
            return;
        }
        for(BdcXmDTO bdcXmDO : bdcXmDOList){
            BdcRyzdDTO bdcRyzdDTO = bdcRyzdService.getRyzdQlr(bdcXmDO.getXmid(), null, null);
            bdcRyzdService.updateRyzdQlrByXmid(bdcRyzdDTO);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新共有情况字段信息（权利人表和各个权利表）")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")})
    @Override
    public void updateGyqk(@RequestParam(name = "xmid") String xmid) {
        bdcRyzdService.updateGyqk(xmid);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新流程共有情况字段信息（权利人表和各个权利表）")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public void updateGyqkWithGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数gzlslid");
        }
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.warn("未查询到工作流实例ID对应项目，处理共有情况字段中止！");
            return;
        }
        for (BdcXmDTO bdcXmDO : bdcXmDOList) {
            bdcRyzdService.updateGyqk(bdcXmDO.getXmid());
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    可以依据更新的xmid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 首次登记，批量更新唯一权利人等值
     */
    @Override
    public void updateRyzdPl(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid) {
        bdcRyzdService.updateRyzdPl(gzlslid, xmid);
    }

    /**
     * @param bdcQlrXmDTOList 权利人项目DTO对象List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权人相关冗余字段
     */
    @Override
    public void updateRyzdQlrXm(@RequestBody List<BdcQlrXmDTO> bdcQlrXmDTOList) {
        bdcRyzdService.updateRyzdQlrXm(bdcQlrXmDTOList);
    }

    /**
     * @param bdcCfjgQO 查封机关QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新查封机关或解封机关
     */
    @Override
    public void updateCfjgOrJfjg(@RequestBody BdcCfjgQO bdcCfjgQO) {
        bdcQlxxService.updateCfjgOrJfjg(bdcCfjgQO);
    }

    /**
     * @param bdcFdcq3QO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息权利人字段
     */
    @Override
    public void updateBdcFdcq3Qlr(@RequestBody BdcFdcq3QO bdcFdcq3QO) {
        bdcQlxxService.updateFdcq3Qlr(bdcFdcq3QO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新使用期限字段信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")})
    @Override
    public void updateSyqx(@RequestParam(name = "xmid") String xmid){
        bdcRyzdService.updateSyqx(xmid);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新项目的使用期限(批量)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSyqxPlDTO", value = "使用期限更新实体", required = true, dataType = "BdcSyqxPlDTO", paramType = "query")})
    @Override
    public void updateSyqxPl(@RequestBody BdcSyqxPlDTO bdcSyqxPlDTO) throws Exception{
        bdcRyzdService.updateSyqxPl(bdcSyqxPlDTO);

    }
}
