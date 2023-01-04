package cn.gtmap.realestate.register.service;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2020/11/25
 * @description 流程操作相关服务
 */
public interface BdcLcczxgService {
    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程撤销删除要撤案交易信息
     */
     void zlfca(String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封流程登簿办结后的需要发送回执单给法院
     */
     void cfhzPdf(String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 没有办结之前 挂起被修的流程并锁定bdcdyh和证书
     */
    void xzlcGqAndSd(String processInsId, String currentUserName);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 流程办结后 需要解挂和解锁被修正的流程
     */
    void xzlcJgAndJs(String processInsId,String currentUserName);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建档案交接流程
     * @date : 2021/6/9 9:30
     */
    void cjDajjLc(String gzlslid, String currentUserName) throws Exception;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发档案交接
     * @date : 2021/6/15 10:00
     */
    void zfDajj(String taskid, String gzlslid, String currentUserName);

    /**
     * @param gzlslid 工作流实例ID
     * @param opinion 退回意见
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 退回档案交接
     * @date : 2021/6/15 10:00
     */
    void thDajj(String gzlslid, String opinion);

    /**
      * @param gzlslid 工作流实例ID
      * @param currentUserName
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 档案接收节点办结时保存档案交接接收人
      */
    void saveDajjJsr(String gzlslid, String currentUserName);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 办结档案交接
     * @date : 2021/6/15 10:00
     */
    void bjDajj(String gzlslid);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变不动产单元的状态为锁定状态-1,项目表备注增加已裁决
     * @date : 2021/8/19 15:01
     */
    void updateSdztSdAndBz(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变裁决锁定的状态为0 正常
     * @date : 2021/8/20 10:06
     */
    void updateSdztJs(String gzlslid);

    /**
     * @param processInsId
     * @author <a href="mailto:sunyuzhuang">sunyuzhuang</a>
     * @description 修正流程删除，需要解锁和解挂流程
     * @date : 2021/8/30
     */
    void deleteXzlc(String processInsId, String currentUserName);

    /**
     * @param gzlslid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取行政代码
     * @date : 2021/9/27
     */
    Map<String, String> getXzdm(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变裁决锁定的状态为0 正常 流程删除时调用
     * @date : 2021/9/29 11:29
     */
    void updateSdztJsForLcsc(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 撤销登记转发或者办结时解锁单元锁定，更新时间和人
     * @date : 2022/9/1 11:42
     */
    void updateSdztForCxdj(String gzlslid, String userName);

    /**
     * @param processInsId    工作流实例ID
     * @param currentNodeName 当前自动转发节点名称，如A节点自动转发到B节点，即为B节点名称
     * @return 返回指定人员用户名code=0,username=
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取节点自动转发指定人员
     */
    Map<String, String> getZdzfSlr(String processInsId, String currentNodeName);

    /**
     * @param processInsId 工作流实例ID
     * @param currentNodeName 当前自动转发节点名称，如A节点自动转发到B节点，即为B节点名称
     * @return 返回指定人员用户名code=0,username=
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取节点自动转发指定人员
     */
    Map<String, String> getWwwsqYshry(String processInsId,String currentNodeName);
}
