package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0
 * @description 操作日志服务
 */
public interface BdcCzrzService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 生成操作日志
      */
    void initBdcCzrz(String gzlslid,String czyy,Integer czlx,String currentUserName);

    /**
      * @param spxtywh 审批系统业务号
     * @param orderBy 排序方式
      * @return 操作日志信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据审批系统业务号获取操作日志
      */
    List<BdcCzrzDO> listBdcCzrzBySpxtywh(String spxtywh,String orderBy);

    /**
     * 新增不动产操作日志内容
     * @param bdcCzrzDO  操作日志信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void addBdcCzrz(BdcCzrzDO bdcCzrzDO);

    /**
     * 更新不动产操作日志内容
     * @param bdcCzrzDO  操作日志信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void modifyBdcCzrzByRzid(BdcCzrzDO bdcCzrzDO);

    /**
     * 查询不动产操作日志内容
     * @param bdcCzrzDO 操作日志信息DO
     * @return {@code List<BdcCzrzDO>} 操作日志信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcCzrzDO> queryBdcCzrz(BdcCzrzDO bdcCzrzDO);

    /**
     * 添加删除操作日志
     * <p>原因获取流程的审批意见内容</p>
     * @param gzlslid 工作流实例ID
     * @param opinion 审核意见
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void addScCzrzWithOpinion(String gzlslid, String opinion);

    /**
     * 添加删除操作日志
     * <p>原因获取流程的审批意见内容</p>
     * @param gzlslid 工作流实例ID
     * @param opinion 审核意见
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    void addScCzrzWithOpinionWithXmxx(String gzlslid, String opinion,BdcXmDO bdcXmDO);

}
