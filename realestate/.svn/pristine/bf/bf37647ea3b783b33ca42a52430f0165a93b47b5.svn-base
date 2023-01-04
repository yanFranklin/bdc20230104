package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.SyncDataMapper;
import cn.gtmap.realestate.exchange.service.inf.gx.SyncDataShareService;
import cn.gtmap.realestate.exchange.service.national.DjfDjYwxxService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/6/21
 * @description 升腾同步数据表处理
 */
@Service
public class SyncDataShareServiceImpl implements SyncDataShareService {

    @Autowired
    private SyncDataMapper syncDataMapper;
    @Autowired
    private DjfDjYwxxService djfDjYwxxService;

    /**
     * @param djfDjYwxx 登记业务信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/6/21
     * @description 根据登记业务信息同步升腾数据
     */
    @Override
    public void saveOrUpdateSyncData(DjfDjYwxxDO djfDjYwxx) {
        String ywh = djfDjYwxx.getYwh();
        Map keys = Maps.newHashMap();
        keys.put("ywh", djfDjYwxx.getYwh());
        String xmzt = djfDjYwxx.getXmzt();
        Map syncDatamap = syncDataMapper.getSyncDataByYwh(keys);
        Map param;
        if (syncDatamap == null) {
            insertSyncData(djfDjYwxx);
        } else {
            param = Maps.newHashMap();
            param.put("ywh", ywh);
            param.put("xmzt", xmzt);
            param.put("djbh", djfDjYwxx.getBz());
            param.put("proid", djfDjYwxx.getYwh());
            syncDataMapper.updateSyncDataXmzt(param);
        }
    }

    @Override
    public void insertSyncData(DjfDjYwxxDO djfDjYwxx) {
        String ywh = djfDjYwxx.getYwh();
        Map param = Maps.newHashMap();
        param.put("ywh", ywh);
        param.put("xmzt", djfDjYwxx.getXmzt());
        param.put("createtime", new Date());
        param.put("djlx", djfDjYwxx.getDjlx());
        param.put("djlxmc", djfDjYwxx.getDjlx());
        param.put("proid", djfDjYwxx.getYwh());
        syncDataMapper.insertSyncData(param);
    }
}
