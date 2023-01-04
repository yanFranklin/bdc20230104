package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPrintLogDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 日志模块保存打印信息
 */
public interface BdcLogRestService {

    /**
     * 保存打印日志信息
     * @param bdcPrintLogDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *
     * 修改为@RequestBody接收参数，避免请求头过大问题
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdc/Log/savePrintInfo")
    void savaPrintInfo(@RequestBody BdcPrintLogDTO bdcPrintLogDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 打印信息
     * @description  保存打印的证书、证明、证明单数量信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/zszm/print")
    boolean saveBdcZszmPrintInfo(@RequestBody BdcZszmPrintDTO bdcZszmPrintDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsTjQO 统计参数
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/zszm/print/count")
    Object countZszmPrint(@RequestBody BdcZsTjQO bdcZsTjQO);

    /**
     * 保存查询台账查询日志
     * @param bdcXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/xtcx")
    void saveBdcCxLog(@RequestBody BdcXtCxLogDO bdcXtCxLogDO);

    /**
     * 查询本地保存的综合查询日志
     * @param pageable pageable
     * @param bdcXtCxLogCxtj 查询条件
     * @return return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/xtcx/list")
    Page<BdcXtCxLogDO> bdcCxZhcxLogByPage(Pageable pageable,@RequestParam(name = "bdcXtCxLogCxtj")String bdcXtCxLogCxtj);

    /**
     * 根据Id获取本地综合日志查询结果
     * @param id key
     * @return return
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/xtcxdata")
    BdcXtCxLogDO bdcCxZhcxLogById(@RequestParam(name = "id")String id);

    /**
     * 同步Redis中的证书证明打印数据量信息至数据库中
     * <p>临时接口，手动触发接口，后续可删除</p>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdc/log/sync/zszm/print")
    void syncZszmPrintCountToDB();
}
