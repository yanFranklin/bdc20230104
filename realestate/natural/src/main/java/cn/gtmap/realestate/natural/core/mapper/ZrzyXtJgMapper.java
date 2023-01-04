package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 不动产系统银行 mapper
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/05/02
 */
@Component
public interface ZrzyXtJgMapper {
    /**
     * 查询出全部银行信息
     *
     * @param jglb
     * @return
     */
    List<BdcXtJgDO> listBdcXtJg(Integer jglb);

    /**
     * 批量修改系统机构是否可用
     *
     * @param yhmcList 银行名称
     * @param sfky     是否可用（1：可用 0：不可用）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void batchModifyXtJgSfky(@Param("yhmcList") List<String> yhmcList, @Param("sfky") int sfky);

    /**
     * @param jgmclist 机构名称集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据机构名称查询
     */
    List<BdcXtJgDO> queryBatchBdcXtJg(@Param("jgmcList") List<String> jgmclist);
}
