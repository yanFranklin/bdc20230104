package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
public interface ZhService {
    /**
     * @param zhdm
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author chenchunxue
     * @description 根据宗海代码查询宗海
     */
    ZhDjdcbDO queryZhDjdcbByZhdm(String zhdm);
    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗海
     */
    ZhDjdcbDO queryZhDjdcbByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询备份宗海
     */
    HZhDjdcbDO queryHZhDjdcbByBdcdyh(String bdcdyh);
    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海及内部单元记录表属性结构描述
     */
    List<ZhZhjnbdyjlb> listZhZhjnbdyjlb(String dcdcbIndex);

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海及内部单元记录表属性结构描述 备份
     */
    List<HZhZhjnbdyjlb> listHZhZhjnbdyjlb(String dcdcbIndex);
    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海界址标示表
     */
    List<ZhJzbsb> listZhJzbsb(String dcdcbIndex);

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海界址标示表 备份
     */
    List<HZhJzbsbDO> listHZhJzbsb(String dcdcbIndex);
    
    /**
     * @param zhdm
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 宗海权属调查表
     */
    ZhQsdcDO queryZhQsdcDO(String zhdm);

}
