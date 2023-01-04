package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * portal 审核信息相关接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/08/15
 */
@Controller
@RequestMapping("/rest/v1.0/shxx")
@Api(tags = "审核信息服务接口")
public class BdcShxxController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShxxController.class);

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image}")
    private String signImageUrl;
    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;


    /**
     * 依据主键更新多条审核信息数据
     *
     * @param paramList 审核信息
     * @return {int} 返回 -1 表示参数为空
     * @author <a href="mailto:lixin1@gtmap.cn">lixin1</a>
     */
    @PatchMapping(value = "/shxxList")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新多条审核信息数据")
    @ApiImplicitParams(@ApiImplicitParam(name = "bdcShxxDOList", value = "审核信息对象集合", dataType = "List", paramType = "body"))
    public int updateShxx(@RequestBody List<BdcShxxDO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            return bdcShxxFeignService.updateShxxList(paramList);
        }
        return -1;
    }

    /**
     * 删除签名信息
     *
     * @param shxxid 审核信息ID
     * @return 更新数据量
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping(value = "/sign/{shxxid}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除签名信息")
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "shxxid", value = "审核信息 ID", dataType = "string", paramType = "path"))
    public int deleteSign(@PathVariable(name = "shxxid") String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("审核信息ID缺失!");
        }
        return bdcShxxFeignService.deleteShxxSign(shxxid);
    }

    /**
     * 获取sign id
     *
     * @param bdcShxxDO
     * @return BdcShxxVO
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping(value = "/sign")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新多条审核信息数据")
    @ApiImplicitParams(@ApiImplicitParam(name = "bdcShxxDO", value = "审核信息 DO", dataType = "BdcShxxDO", paramType = "body"))
    public BdcShxxVO sign(@RequestBody BdcShxxDO bdcShxxDO) {
        BdcShxxVO bdcShxxVO = bdcShxxFeignService.getShxxSign(bdcShxxDO);
        bdcShxxVO.setQmtpdz(signImageUrl + bdcShxxVO.getQmid());
        return bdcShxxVO;
    }


    /**
     * @param gzlslid       工作流实例ID
     * @param currentJdmc   当前节点名称
     * @param taskId        大云任务ID，对应登记的shxxid
     * @param onlyCurrentJd 是否只返回当前一个节点的信息（true 则只返回当前一个节点的信息，其他签名节点不再获取）
     * @return List<BdcShxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @GetMapping(value = "/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取签名意见", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "大云任务ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentJdmc", value = "当前节点名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "onlyCurrentJd", value = "是否只返回当前一个节点的信息", dataType = "string", paramType = "query"),
    })
    @ResponseBody
    public Object listShxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "taskId") String taskId
            , @RequestParam(name = "currentJdmc") String currentJdmc, @RequestParam(name = "onlyCurrentJd") Boolean onlyCurrentJd) {
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setJdmc(currentJdmc);
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setShxxid(taskId);
        bdcShxxQO.setOnlyCurrentJd(onlyCurrentJd);
        bdcShxxQO.setSignImageUrl(signImageUrl);
        return bdcShxxFeignService.queryShJdxx(bdcShxxQO);
    }

    /**
     * @param taskId 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @GetMapping(value = "/mryj/{taskId}")
    @ResponseBody
    public BdcShxxVO queryMryj(@PathVariable(name = "taskId") String taskId) {
        return bdcShxxFeignService.queryMryj(taskId);
    }
}
