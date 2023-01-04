package cn.gtmap.interchange.service;

import cn.gtmap.interchange.core.domain.GxTsqzkLog;
import cn.gtmap.interchange.core.domain.GxTsqzkYzsbxx;
import cn.gtmap.interchange.core.domain.InfApply;
import cn.gtmap.interchange.core.dto.QlygParamModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志服务
 */
public interface LogService {

    /**
     * 存储日志
     */
    void saveTsLog(List<InfApply> applyList);

    /**
     * 存储日志
     */
    void saveTsLog(String yzwbh);

    /**
     * 更新已存储日志
     */
    void updateTsLog(InfApply infApply, GxTsqzkLog newLog);

    /**
     * 更新已存储日志
     */
    void updateTsLog(InfApply infApply, GxTsqzkLog newLog, List<GxTsqzkYzsbxx> gxTsqzkYzsbxxList, boolean updateTsxx);

    /**
     * 通过日志id获取失败原因信息
     *
     * @param logid
     * @return
     */
    HashMap getSbyyByLogid(String logid);

    /**
     * 获取未推送业务号
     *
     * @return
     */
    List<QlygParamModel> getWtsYwhByLog();

    /**
     * 获取验证失败业务号
     *
     * @return
     */
    List<QlygParamModel> getYzsbYwhByLog();

    /**
     * 更新数据库日志记录
     */
    void updateTsLog(InfApply infapply, List<String> yzwbhs, HashMap<String, List<GxTsqzkYzsbxx>> gxTsqzkYzsbxxYzwbhRel, List<String> yzlx);

    /**
     * 获取业务号
     *
     * @return
     */
    List<QlygParamModel> queryYzwxx(Map<String, Object> queryMap);
}
