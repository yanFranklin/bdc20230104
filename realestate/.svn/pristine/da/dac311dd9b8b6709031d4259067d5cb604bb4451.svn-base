package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZszmPrintDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsTjQO;
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
public interface ZrzyLogRestService {

    /**
     * 保存打印日志信息
     *
     * @param ZrzyPrintLogDTOJSON
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/bdc/Log/savePrintInfo")
    void savaPrintInfo(@RequestParam(name = "ZrzyPrintLogDTOJSON", required = false) String ZrzyPrintLogDTOJSON);

    /**
     * @param zrzyZszmPrintDTO 打印信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存打印的证书、证明、证明单数量信息
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/bdc/log/zszm/print")
    boolean saveZrzyZszmPrintInfo(@RequestBody ZrzyZszmPrintDTO zrzyZszmPrintDTO);

    /**
     * @param zrzyZsTjQO 统计参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/bdc/log/zszm/print/count")
    Object countZszmPrint(@RequestBody ZrzyZsTjQO zrzyZsTjQO);

    /**
     * 保存查询台账查询日志
     *
     * @param zrzyXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/bdc/log/xtcx")
    void saveZrzyCxLog(@RequestBody ZrzyXtCxLogDO zrzyXtCxLogDO);

    /**
     * 查询本地保存的综合查询日志
     *
     * @param pageable       pageable
     * @param zrzyXtCxLogCxtj 查询条件
     * @return return
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/bdc/log/xtcx/list")
    Page<ZrzyXtCxLogDO> zrzyCxZhcxLogByPage(Pageable pageable, @RequestParam(name = "zrzyXtCxLogCxtj") String zrzyXtCxLogCxtj);

    /**
     * 根据Id获取本地综合日志查询结果
     *
     * @param id key
     * @return return
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/bdc/log/xtcxdata")
    ZrzyXtCxLogDO zrzyCxZhcxLogById(@RequestParam(name = "id") String id);
}
