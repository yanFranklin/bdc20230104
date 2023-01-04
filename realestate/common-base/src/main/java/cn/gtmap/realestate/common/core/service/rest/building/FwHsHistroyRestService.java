package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/4
 * @description
 */
public interface FwHsHistroyRestService {
    /**
     * @param bglx
     * @param bdcdyh
     * @param bgrq
     * @param pageable return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询户室变更历史记录
     */
    @PostMapping("/building/rest/v1.0/hs/histroy/page")
    Page<SSjHsbgljbDO> listHsbgHsitroyByPageJson(Pageable pageable,
                                                 @RequestParam(value = "paramJson", required = false) String paramJson);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description 查询户室变更记录
     */
    @GetMapping("/building/rest/v1.0/hs/histroy/{fwHsIndex}")
    List<List<FwHsBgHistoryDTO>> getHsBgHistoryByFwHsIndex(@PathVariable(value = "fwHsIndex")String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录
     */
    @GetMapping("/building/rest/v1.0/hs/histroy/bdcdyh")
    List<List<FwHsBgHistoryDTO>> getHsBgHistoryByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录(新插件)
     */
    @GetMapping("/building/rest/v1.0/hs/histroynew/bdcdyh")
    List<FwHsBgHistoryNewDTO> getHsBgHistoryNewByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description 查询变更的户室信息
     */
    @GetMapping("/building/rest/v1.0/hs/histroyhs")
    FwHsDO getHFwHsByFwHsIndex(@RequestParam(name = "fwHsIndex") String fwHsIndex, @RequestParam(name = "last", required = false) boolean last,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
