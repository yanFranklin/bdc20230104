package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 农用地相关服务
 */
public interface NydRestService {
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询农用地权利人信息
     */
    @GetMapping("/building/rest/v1.0/nyd/qlr/{bdcdyh}")
    List<NydQlrDO> listNydQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydJtcyDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元查询农用地家庭成员
     */
    @GetMapping("/building/rest/v1.0/nyd/jtcy/{bdcdyh}")
    List<NydJtcyDO> listNydJtcyByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地地籍数据
     */
    @GetMapping("/building/rest/v1.0/nyd/{bdcdyh}")
    NydDjdcbDO queryNydByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询农用地信息
     */
    @PostMapping("/building/rest/v1.0/nyd/page")
    Page<Map> listNydByPageJson(Pageable pageable,
                                @RequestParam(name = "paramJson", required = false) String paramJson);
}
