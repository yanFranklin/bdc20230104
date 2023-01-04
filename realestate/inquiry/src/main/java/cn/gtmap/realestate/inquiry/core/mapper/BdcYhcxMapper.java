package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询
 */
public interface BdcYhcxMapper {
    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取当前证书相关的项目信息
     */
    List<BdcXmDO> listZsXmByZsid(@Param("zsid") String zsid);

    /**
     * @param bdcdyh
     * @return List<BdcYhcxDyaDTO> 抵押信息
     * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取抵押信息
     */
    List<BdcYhcxDyaDTO> listYhcxDyaByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh
     * @return List<String> 锁定信息
     * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取锁定信息
     */
    List<String> listYhcxSdxxByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param map
     * @return List<BdcZsDO>
     * @description 根据map获取证书列表
     */
    List<BdcZsDO> listBdcZs(Map map);

}
