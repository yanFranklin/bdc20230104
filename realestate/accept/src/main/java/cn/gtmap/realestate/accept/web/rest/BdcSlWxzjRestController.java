package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlWxjjxxService;
import cn.gtmap.realestate.accept.service.BdcSlWxzjService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlWxzjRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/23
 * @description 不动产受理维修资金信息rest服务
 */
@RestController
@Api(tags = "不动产受理维修资金信息服务")
public class BdcSlWxzjRestController implements BdcSlWxzjRestService {

    /**
     * 不动产维修资金接口服务
     */
    @Autowired
    private BdcSlWxzjService bdcSlWxzjService;

    /**
     * 不动产受理维修基金数据服务
     */
    @Autowired
    private BdcSlWxjjxxService bdcSlWxjjxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "维修资金支付通知", notes = "维修资金支付通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmids", value = "项目ID集合", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "gzlslid", value = "工作实例ID", required = true, dataType = "String", paramType = "param")
    })
    public Object noticeWxzj(@RequestParam(name = " xmid") String xmid, @RequestParam(name = "gzlslid") String gzlslid) {
        return bdcSlWxzjService.tzwxzj(gzlslid, xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成维修资金收费信息", notes = "生成维修资金收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlWxjjxxDO", value = "不动产维修基金信息DO", required = true, dataType = "BdcSlWxjjxxDO", paramType = "body")
    })
    public void generateWxzjXmxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        this.bdcSlWxzjService.generateWxzjXmxx(bdcSlWxjjxxDO);
    }

    /**
     * 根据维修基金信息DO获取不动产受理维修基金信息
     *
     * @param bdcSlWxjjxxDO 维修基金信息DO
     * @return 受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成维修资金收费信息", notes = "生成维修资金收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlWxjjxxDO", value = "不动产维修基金信息DO", required = true, dataType = "BdcSlWxjjxxDO", paramType = "body")
    })
    public List<BdcSlWxjjxxDO> queryBdcSlWxjjxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        return this.bdcSlWxjjxxService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);
    }

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    public BdcSlWxjjxxDO insertBdcSlWxjjxxDO(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO){
        return this.bdcSlWxjjxxService.insertBdcSlWxjjxxDO(bdcSlWxjjxxDO);
    }

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDOList 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    public void insertBatchBdcSlWxjjxxDO(@RequestBody List<BdcSlWxjjxxDO>  bdcSlWxjjxxDOList) {
        this.bdcSlWxjjxxService.insertBatchBdcSlWxjjxxDO(bdcSlWxjjxxDOList);
    }

    /**
     * 根据维修基金信息ID删除不动产受理维修基金信息
     * @param wxjjxxid 维修基金信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    public Integer deleteWxjjxxByWxjjxxid(@RequestParam(name = "wxjjxxid") String wxjjxxid) {
        return this.bdcSlWxjjxxService.deleteWxjjxxByWxjjxxid(wxjjxxid);
    }

    /**
     * 根据工作流实例ID删除不动产受理维修基金信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    public Integer deleteWxjjxxByGzlslid(@RequestParam(name = "gzlslid") String gzlslid) {
        return this.bdcSlWxjjxxService.deleteWxjjxxByGzlslid(gzlslid);
    }

    /**
     * 更新不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 更新情况
     */
    public int updateBdcSlWxjjxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        return this.bdcSlWxjjxxService.updateBdcSlWxjjxx(bdcSlWxjjxxDO);
    }

}
