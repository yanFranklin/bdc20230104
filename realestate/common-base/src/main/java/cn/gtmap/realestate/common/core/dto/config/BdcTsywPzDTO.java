package cn.gtmap.realestate.common.core.dto.config;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/31
 * @description 不动产特殊业务配置DTO实体定义
 */
public class BdcTsywPzDTO extends BdcTsywPzDO {

    /**
     * 配置项对应的自定义标识字典
     */
    List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList;

    public List<BdcTsywZdyzdxDO> getBdcTsywZdyzdxDOList() {
        return bdcTsywZdyzdxDOList;
    }

    public void setBdcTsywZdyzdxDOList(List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList) {
        this.bdcTsywZdyzdxDOList = bdcTsywZdyzdxDOList;
    }
}
