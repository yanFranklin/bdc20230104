package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.exchange.core.domain.zd.BdcExchangeZddz;

import java.util.List;
import java.util.Map;

/**
 * @Auther 徐涛
 * @Date 2018-12-21
 * @Description
 */
public interface BdcExchangeZddzService {

    /**
     * @param dataModel 数据表model
     * @param xmid
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 对所有的表中的字典对象 处理成 国标
     */
    DataModel handleZddz(DataModel dataModel, String xmid);


    /**
     * @param headModel 数据头部model
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 对headModel中的字典对象 处理成 国标
     */
    HeadModel handleHeadZddz(HeadModel headModel);

    /**
     * @param obj     对象
     * @param zddzMap 对照表字典类型list
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 将对象里面的字典，存在对照表，就替换为国标字典
     */
    void doObj(Object obj, Map<String, List<BdcExchangeZddz>> zddzMap, String xmid);

    Map<String, List<BdcExchangeZddz>> getBdcExchangeZddzMap();

}
