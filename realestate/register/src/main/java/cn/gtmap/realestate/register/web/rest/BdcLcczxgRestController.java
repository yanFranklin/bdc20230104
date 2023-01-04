package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.service.rest.register.BdcLcczxgRestService;
import cn.gtmap.realestate.register.service.BdcLcczxgService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">chenyucheng</a>
 * @version 1.3, 2018/12/29
 * @description 对于流程一些操作的工作流事件服务汇总
 */
@RestController
@Api(tags = "流程操作工作流事件服务")
public class BdcLcczxgRestController extends BaseController implements BdcLcczxgRestService {

    @Autowired
    BdcLcczxgService bdcLcczxgService;
    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程撤销删除要撤案交易信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "流程撤销删除要撤案交易信息")
    public void zlfca(String processInsId) {
        bdcLcczxgService.zlfca(processInsId);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封流程登簿办结后的需要发送回执单给法院
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查封流程登簿办结后的需要发送回执单给法院")
    public void cfhzPdf(String processInsId) {
        bdcLcczxgService.cfhzPdf(processInsId);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 没有办结之前 挂起被修的流程并锁定bdcdyh和证书
     */
    @Override
    public void xzlcGqAndSd(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName) {
        bdcLcczxgService.xzlcGqAndSd(processInsId, currentUserName);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 流程办结后 需要解挂和解锁被修正的流程
     */
    @Override
    public void xzlcJgAndJs(@RequestParam(name = "processInsId")String processInsId, @RequestParam(name = "currentUserName") String currentUserName) {
        bdcLcczxgService.xzlcJgAndJs(processInsId,currentUserName);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuhzuang</a>
     * @description 修正流程删除，需要解锁和解挂流程
     */
    @Override
    public void zxlcDelete(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "currentUserName") String currentUserName) {
        bdcLcczxgService.deleteXzlc(processInsId,currentUserName);
    }

}
