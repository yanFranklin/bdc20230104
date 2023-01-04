package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/19
 * @description 归档Mapper
 */
public interface BdcGdxxMapper {
    List<BdcXmGdxxDTO> listBdcGdxxHfByPage(HashMap map);

    List<BdcXmGdxxDTO> listBdcGdxxNtByPage(HashMap map);


    List<BdcXmGdxxDTO> listBdcSlGdxx(HashMap map);

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param map
    * @return List<BdcGdxxDO>
    * @description 根据map查询归档信息集合
    */
    List<BdcGdxxDO> listBdcGdxx(HashMap map);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcGdxxDO
     * @description 若项目已存在归档信息则更新归档信息,若项目不存在归档信息则插入归档信息
     */
    void updateBdcGdxx(BdcGdxxDO bdcGdxxDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param xmId
     * @description 根据xmId查询daId
     */
    String searchDaIdByXmId(String xmId);

    List<BdcGdxxDO> queryBdcGdxxByXmid(@Param("xmid") String xmid);
}
