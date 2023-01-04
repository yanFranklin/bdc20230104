package cn.gtmap.realestate.natural.service;


import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 日志处理
 */
public interface ZrzyLogService {
    /**
     * @param zrzyZszmPrintDTO 打印信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存打印的证书、证明、证明单数量信息
     */
    Boolean saveZrzyZszmPrintInfo(ZrzyZszmPrintDTO zrzyZszmPrintDTO);

    /**
     * @param zrzyZsTjQO 统计参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 统计打印的证书、证明、证明单数量信息
     */
    Object countZszmPrint(ZrzyZsTjQO zrzyZsTjQO);

    /**
     * 保存查询台账查询日志
     *
     * @param zrzyXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void saveZrzyCxLog(ZrzyXtCxLogDO zrzyXtCxLogDO);

    /**
     * 查询本地保存的综合查询日志
     *
     * @param pageable        pageable
     * @param zrzyXtCxLogCxtj 查询条件
     * @return return
     */
    Page<ZrzyXtCxLogDO> zrzyCxZhcxLogByPage(Pageable pageable, String zrzyXtCxLogCxtj);

    /**
     * 根据id查询综合查询日志
     *
     * @param id id
     * @return return
     */
    ZrzyXtCxLogDO zrzyCxZhcxLogById(String id);
}
