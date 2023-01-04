package cn.gtmap.realestate.register.service;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0  2021-7-1
 * @description (盐城) 评价短信发送
 */
public interface BdcPjdxService{
    /**
     * 盐城评价短信下发
     *
     * @param processInsId 工作流实例ID
     */
    void queryPjdxMsg(String processInsId,String taskId);
}
