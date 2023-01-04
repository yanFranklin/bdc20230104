package cn.gtmap.realestate.register.service;

/**
 * 不动产公告信息推送service
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/07/22/20:03
 * @Description:
 */
public interface BdcGgtsService {
    /**
     * @param processInsId 工作流实例ID
     * @return 推送成功条数
     * @author <a href="mailto:sunyuhzuang@gtmap.cn">liaoxiang</a>
     * @description 推送公告信息至公告台账管理
     */
    int pushBdcGgtz(String processInsId);
}
