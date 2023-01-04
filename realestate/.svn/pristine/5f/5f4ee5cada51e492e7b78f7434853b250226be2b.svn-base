package cn.gtmap.realestate.exchange.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.InfApplyDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwTsjlDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/24
 * @description 一张网验证接口
 */
public interface YzwCheckMapper {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取超期并且没有结果信息
     */
    Map getCqWjgxx(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询未推送结果一张网编号集合
     */
    List<String> listWtsjgYzwbh();

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取推送记录
     */
    List<GxYzwTsjlDO> listTsjl(Map map);

    InfApplyDO getInfApply(@Param("zfxzspbh") String zfxzspbh);
}
