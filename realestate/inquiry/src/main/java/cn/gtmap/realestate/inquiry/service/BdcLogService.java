package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.register.BdcZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 日志处理
 */
public interface BdcLogService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 打印信息
     * @description  保存打印的证书、证明、证明单数量信息
     */
    Boolean saveBdcZszmPrintInfo(BdcZszmPrintDTO bdcZszmPrintDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsTjQO 统计参数
     * @description 统计打印的证书、证明、证明单数量信息
     */
    Object countZszmPrint(BdcZsTjQO bdcZsTjQO);

    /**
     * 保存查询台账查询日志
     * @param bdcXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void saveBdcCxLog(BdcXtCxLogDO bdcXtCxLogDO);

    /**
     * 查询本地保存的综合查询日志
     * @param pageable pageable
     * @param bdcXtCxLogCxtj 查询条件
     * @return return
     */
    Page<BdcXtCxLogDO> bdcCxZhcxLogByPage(Pageable pageable,String bdcXtCxLogCxtj);

    /**
     *  根据id查询综合查询日志
     * @param id id
     * @return return
     */
    BdcXtCxLogDO bdcCxZhcxLogById(String id);

    /**
     * 同步Redis中的证书证明打印数据量信息至数据库中
     * <p>临时接口，手动触发接口，后续可删除</p>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void syncZszmPrintCountToDB();
}
