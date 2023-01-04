package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxBgRequestDTO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxBgVO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxHbVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description
 */
public interface FwXmxxBgRestService {

    /**
     * @param xmxxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息变更
     */
    @PostMapping("/building/rest/v1.0/xmxxbg/bg")
    FwXmxxDO xmxxBg(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO);

    /**
     * @param xmxxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息灭失
     */
    @PostMapping("/building/rest/v1.0/xmxxbg/ms")
    void xmxxMs(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO);

    /**
     * @param xmxxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息灭失
     */
    @PostMapping("/building/rest/v1.0/xmxxbg/hb")
    FwXmxxDO xmxxHb(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO);

    /**
     * @param fwXmxxBgVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxBgVO查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/xmxxbg/validbdcdyh/xmxxbgvo")
    List<String> listValidBdcdyhByFwXmxxBgVO(@RequestBody FwXmxxBgVO fwXmxxBgVO);

    /**
     * @param fwXmxxHbVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxHbVO查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/xmxxbg/validbdcdyh/xmxxhbvo")
    List<String> listValidBdcdyhByFwXmxxHbVO(@RequestBody FwXmxxHbVO fwXmxxHbVO);
}
