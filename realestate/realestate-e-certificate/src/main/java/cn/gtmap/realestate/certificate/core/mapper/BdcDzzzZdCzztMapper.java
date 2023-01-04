package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcZdDzzzCzztDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/10
 */
@Mapper
public interface BdcDzzzZdCzztMapper {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description
     */
    BdcZdDzzzCzztDO getDzzzZdCzztDOByDm(String czztdm);
}
