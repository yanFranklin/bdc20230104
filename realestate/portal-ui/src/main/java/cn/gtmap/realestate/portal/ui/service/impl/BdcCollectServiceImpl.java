package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.clients.CollectionManagerClient;
import cn.gtmap.realestate.portal.ui.service.BdcCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/12/20 16:58
 * @description 收藏功能
 */
@Service
public class BdcCollectServiceImpl implements BdcCollectService {
    @Autowired
    CollectionManagerClient collectionManagerClient;
    /**
     * 删除收藏
     *
     * @param username
     * @param code
     * @return
     */
    @Override
    @Async
    public void deleteRelation(String username, String code) {
        collectionManagerClient.deleteRelation(username, code);
    }
}
