package cn.gtmap.realestate.common.core.service.rest.init.hefei;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
public interface BdcBjbhRestService {

    /**
     * 分页查询办件编号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcBjbhQOStr
     * @return BdcBjbhDTO
     */
    @GetMapping("/init/rest/v1.0/bjbh/list/page/search")
    Page<BdcBjbhDTO> listBjbhByPage(Pageable pageable, @RequestParam(name = "bdcBjbhQOStr", required = false) String bdcBjbhQOStr);

    /**
     * 取号
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @GetMapping("/init/rest/v1.0/bjbh/qh")
    BdcZwQhResponseDTO takeNumber(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 4.3.1 申报登记
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/init/rest/v1.0/sbdj")
    BdcZwQhResponseDTO sbdj(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/init/rest/v1.0/sbdj/slxx")
    SbdjfkResponseDTO sbdjSlxx(@RequestParam(name = "processInsId") String processInsId,
                               @RequestParam(name = "blzt") String blzt);

    /**
     * 4.3.8	办结
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/init/rest/v1.0/sbdj/bjxx")
    SbdjfkResponseDTO sbdjBjxx(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 工作流事件 依次触发以上接口
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/init/rest/v1.0/sbdj/workflow")
    void sbdjWorkFlow(@RequestParam(name = "processInsId") String processInsId);
}
