package cn.gtmap.realestate.exchange.service.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzReponse;
import cn.gtmap.realestate.exchange.service.impl.national.dzzz.DzqzGmxaData;

/**
 * 国迈信安电子签章操作接口
 *
 * @author wangyinghao
 */
public interface DzqzGmxaService<T extends GmxaDzzzReponse> {
    /**
     * 初始化
     * @param dzqzGmxaData 配置
     */
    void init(DzqzGmxaData dzqzGmxaData);

    /**
     * 构建请求
     */
    void buildRequest();

    /**
     * 发送请求
     * @return
     */
    T send();

    /**
     * 是否成功
     * @return Boolean
     */
    Boolean isSuccess();
}
