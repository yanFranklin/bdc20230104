package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 房屋建设用地自然幢信息
 */
public interface FwJsydzrzxxMapper {

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息
     * @return 建设用地自然幢列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询建设用地自然幢列表
     */
    List<FwJsydzrzxxDO> listFwJsydzrzxx(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据建设用地自然幢主键集合更新状态
     */
    void updateFwJsydzrzxxZtPl(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * 根据建设用地自然幢主键集合查询建设用地自然幢和状态信息
     * @return 建设用地自然幢信息DTO集合
     */
    List<FwJsydzrzxxDTO> listFwJsydzrzxxByPage(FwJsydzrzxxQO fwJsydzrzxxQO);
}
