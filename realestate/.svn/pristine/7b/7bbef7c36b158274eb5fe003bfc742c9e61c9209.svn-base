package cn.gtmap.interchange.service;

import cn.gtmap.interchange.core.domain.InfApply;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/9/25.
 * Time: 17:39
 * 共享库基本信息表服务
 */
public interface InfApplyService {
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

    /**
     * 根据流水号更新数据
     * @param infApply
     */
    void modifyInfApply(InfApply infApply);
}
