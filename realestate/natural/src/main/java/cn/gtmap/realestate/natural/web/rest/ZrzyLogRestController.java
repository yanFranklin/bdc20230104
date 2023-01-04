package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.config.logaop.PrintLogUtils;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsTjQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyLogRestService;
import cn.gtmap.realestate.natural.service.ZrzyLogService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
public class ZrzyLogRestController implements ZrzyLogRestService {

    @Autowired
    private PrintLogUtils plUtils;

    @Autowired
    private ZrzyLogService zrzyLogService;


    @Override
    public void savaPrintInfo(String ZrzyPrintLogDTOJSON) {
        BdcPrintLogDTO bdcPrintLogDTO = new BdcPrintLogDTO();
        if (StringUtils.isNotBlank(ZrzyPrintLogDTOJSON)) {
            bdcPrintLogDTO = JSONObject.parseObject(ZrzyPrintLogDTOJSON, BdcPrintLogDTO.class);
        }
        plUtils.savePrintLog(bdcPrintLogDTO);
    }

    /**
     * @param zrzyZszmPrintDTO 证书、证明、证明单打印信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存打印的证书、证明、证明单数量信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存打印的证书、证明、证明单数量信息")
    @ApiImplicitParam(name = "bdcZszmPrintDTO", value = "证书、证明、证明单打印信息", required = true, paramType = "body")
    public boolean saveZrzyZszmPrintInfo(@RequestBody ZrzyZszmPrintDTO zrzyZszmPrintDTO) {
        return zrzyLogService.saveZrzyZszmPrintInfo(zrzyZszmPrintDTO);
    }

    /**
     * @param zrzyZsTjQO 统计参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计打印的证书、证明、证明单数量信息")
    @ApiImplicitParam(name = "bdcZsTjQO", value = "统计参数", required = true, paramType = "body")
    public Object countZszmPrint(@RequestBody ZrzyZsTjQO zrzyZsTjQO) {
        return zrzyLogService.countZszmPrint(zrzyZsTjQO);
    }

    /**
     * 保存查询台账查询日志
     *
     * @param zrzyXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存查询台账查询日志")
    @ApiImplicitParam(name = "bdcXtCxLogDO", value = "日志信息", required = true, paramType = "body")
    public void saveZrzyCxLog(@RequestBody ZrzyXtCxLogDO zrzyXtCxLogDO) {
        zrzyLogService.saveZrzyCxLog(zrzyXtCxLogDO);
    }

    /**
     * 查询本地保存的综合查询日志
     *
     * @param pageable        pageable
     * @param zrzyXtCxLogCxtj 查询条件
     * @return return
     */
    @Override
    public Page<ZrzyXtCxLogDO> zrzyCxZhcxLogByPage(Pageable pageable, String zrzyXtCxLogCxtj) {
        return zrzyLogService.zrzyCxZhcxLogByPage(pageable, zrzyXtCxLogCxtj);
    }

    /**
     * 根据Id获取本地综合日志查询结果
     *
     * @param id key
     * @return return
     */
    @Override
    public ZrzyXtCxLogDO zrzyCxZhcxLogById(String id) {
        return zrzyLogService.zrzyCxZhcxLogById(id);
    }
}
