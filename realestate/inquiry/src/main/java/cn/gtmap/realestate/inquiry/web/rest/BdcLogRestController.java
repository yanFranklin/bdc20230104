package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.config.logaop.PrintLogUtils;
import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcLogRestService;
import cn.gtmap.realestate.inquiry.service.BdcLogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 保存打印日志信息
 */
@RestController
public class BdcLogRestController implements BdcLogRestService {

    @Autowired
    private PrintLogUtils plUtils;

    @Autowired
    private BdcLogService bdcLogService;

    /**
     * 保存打印日志信息
     * @param bdcPrintLogDTO
     *
     * 修改为@RequestBody接收参数，避免请求头过大问题
     */
    @Override
    @ApiOperation("保存打印日志信息")
    @ApiImplicitParam(name = "bdcPrintLogDTO", value = "保存打印日志信息", required = true, paramType = "body")
    public void savaPrintInfo(@RequestBody BdcPrintLogDTO bdcPrintLogDTO) {
        plUtils.savePrintLog(bdcPrintLogDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 证书、证明、证明单打印信息
     * @description 保存打印的证书、证明、证明单数量信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存打印的证书、证明、证明单数量信息")
    @ApiImplicitParam(name = "bdcZszmPrintDTO", value = "证书、证明、证明单打印信息", required = true, paramType = "body")
    public boolean saveBdcZszmPrintInfo(@RequestBody BdcZszmPrintDTO bdcZszmPrintDTO) {
        return bdcLogService.saveBdcZszmPrintInfo(bdcZszmPrintDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsTjQO 统计参数
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计打印的证书、证明、证明单数量信息")
    @ApiImplicitParam(name = "bdcZsTjQO", value = "统计参数", required = true, paramType = "body")
    public Object countZszmPrint(@RequestBody BdcZsTjQO bdcZsTjQO) {
        return bdcLogService.countZszmPrint(bdcZsTjQO);
    }

    /**
     * 保存查询台账查询日志
     * @param bdcXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存查询台账查询日志")
    @ApiImplicitParam(name = "bdcXtCxLogDO", value = "日志信息", required = true, paramType = "body")
    public void saveBdcCxLog(@RequestBody BdcXtCxLogDO bdcXtCxLogDO) {
        bdcLogService.saveBdcCxLog(bdcXtCxLogDO);
    }


    /**
     * 查询本地保存的综合查询日志
     * @param pageable pageable
     * @param bdcXtCxLogCxtj 查询条件
     * @return return
     */
    @Override
    public Page<BdcXtCxLogDO> bdcCxZhcxLogByPage(Pageable pageable,String bdcXtCxLogCxtj){
        return bdcLogService.bdcCxZhcxLogByPage(pageable, bdcXtCxLogCxtj);
    }

    /**
     * 根据Id获取本地综合日志查询结果
     * @param id key
     * @return return
     */
    @Override
    public BdcXtCxLogDO bdcCxZhcxLogById(String id){
        return bdcLogService.bdcCxZhcxLogById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步Redis中的证书证明打印数据量信息至数据库中")
    public void syncZszmPrintCountToDB() {
        this.bdcLogService.syncZszmPrintCountToDB();
    }
}
