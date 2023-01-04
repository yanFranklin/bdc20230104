package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzUseConditionDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/10
 */
@Mapper
public interface BdcDzzzUseConditionMapper {

    void insertDzzzUseCondition(BdcDzzzUseConditionDo dzzzUseConditionDo);

    List<BdcDzzzUseConditionDo> getUseConditionByZzbs(String zzbs);

    List<Map> getUseConditionByBdcqzh(String bdcqzh);

    List<Map> countUseConditionYybm(Map map);

    BdcDzzzUseConditionDo getUseConditionById(String id);
}
