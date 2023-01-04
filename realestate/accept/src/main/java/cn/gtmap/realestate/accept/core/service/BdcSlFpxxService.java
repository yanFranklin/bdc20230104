package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息数据服务
 */
public interface BdcSlFpxxService {
    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据收费信息ID获取不动产受理发票信息
     */
    List<BdcSlFpxxDO> queryBdcSlFpxxBySfxxid(String sfxxid);

    /**
     * 新增不动产受理收费信息
     * @param bdcSlFpxxDO 不动产受理发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcSlFpxxDO insertBdcSlFpxx(BdcSlFpxxDO bdcSlFpxxDO);

    /**
     * 根据收费信息ID删除不动产受理发票信息
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void deleteBdcSlFpxxBySfxxid(String sfxxid);

    /**
     * 保存或更新不动产受理收费信息
     * @param bdcSlFpxxDO 不动产受理发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Integer saveOrUpdateBdcSlFpxx(BdcSlFpxxDO bdcSlFpxxDO);

    /**
     * 获取发票信息，并上传发票信息
     * @param sfxxid 收费信息ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void getFpxxAndUploadFpxx(String sfxxid, String gzlslid);

    /**
     * 开具发票
     * @param sfxxid 收费信息
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void inovice(String sfxxid, String qlrlb, String gzlslid);

    /**
     * 获取电子发票号
     * @param sfxxid  收费信息ID
     * @param userCode 用户code
     * @return 电子发票
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String queryDzph(String sfxxid, String userCode);

    /**
     * 查询电子发票信息
     * @param bdcSlSfxxDO 收费信息
     * @param currentUserName 当前用户名称
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void queryDzfpxx(BdcSlSfxxDO bdcSlSfxxDO, String currentUserName);

    /**
     * 查询缴款情况成功，并自动执行获取发票号、开具发票信息功能
     * @param gzlslid 工作流实例ID
     * @param currentUserName 当前人员信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void queryJkqkAndAutoExec(String gzlslid, String currentUserName);

    /**
     * 根据收费信息ID批量删除不动产受理发票信息
     * @param sfxxidList 收费信息ID
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    void batchDeleteBdcSlFpxxBySfxxid(List<String> sfxxidList);

}
