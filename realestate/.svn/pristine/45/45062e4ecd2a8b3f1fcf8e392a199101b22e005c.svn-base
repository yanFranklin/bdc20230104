package cn.gtmap.interchange.core.mapper.gx;

import cn.gtmap.interchange.core.domain.InfApply;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


@Repository
public interface InfApplyMapper {
    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param map
     * @rerutn
     * @description 获取办件基本信息
     */
    List<InfApply> getInfApply(Map map);

    /**
     * 获取未同步的数据
     * @param map 请求参数
     * @return 获取办件基本信息
     */
    List<InfApply> queryWtbInfApply(Map map);

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param
     * @rerutn
     * @description 获取序列，用于一张网编号
     */
    Integer getYzwBhSeq();
}
