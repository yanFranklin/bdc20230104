package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.qo.init.BdcQlridSyncQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.mapper.BdcLzrMapper;
import cn.gtmap.realestate.init.core.service.BdcQlridSyncService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/13
 * @description
 */
@Service
public class BdcQlridSyncServiceImpl implements BdcQlridSyncService {

    @Autowired
    private BdcLzrMapper bdcLzrMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQlridSyncServiceImpl.class);

    @Override
    public void deleteQlrSync(BdcQlridSyncQO bdcQlridSyncQO){
        //领证人
        if(Boolean.TRUE.equals(bdcQlridSyncQO.isSynclzr()) &&!StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcQlridSyncQO.getQlrlb())){
            if(StringUtils.isNotBlank(bdcQlridSyncQO.getXmid())){
                //删除权利人ID关联的领证人
                Map map = new HashMap<>();
                map.put("xmid", bdcQlridSyncQO.getXmid());
                int count =bdcLzrMapper.deleteQlrLzr(map);
                LOGGER.info("删除权利人默认同步删除领证人,权利人参数：{},删除数量：{}条",bdcQlridSyncQO,count);
            }
        }else{
            LOGGER.info("删除权利人默认同步删除领证人未开启或权利人类别为义务人,权利人参数：{}",bdcQlridSyncQO);
        }
    }
}
