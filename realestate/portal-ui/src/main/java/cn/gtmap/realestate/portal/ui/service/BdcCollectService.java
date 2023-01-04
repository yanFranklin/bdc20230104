package cn.gtmap.realestate.portal.ui.service;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/12/20 16:56
 */
public interface BdcCollectService {
    /**
     * 删除收藏
     *
     * @param username
     * @param code
     * @return
     */
    void deleteRelation(String username, String code);
}
