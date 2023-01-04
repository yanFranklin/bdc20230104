package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description 宗地服务接口
 */
public interface ZdService {

    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询宗地信息List
     */
    List<ZdDjdcbDO> queryZdList();

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.ZdResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地信息
     */
    Page<Map> listZdByPage(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @description 根据 逻辑幢主键 初始化宗地树 (查FW_K表)
     */
    ZdTreeResponseDTO initZdTreeByFwDcbIndex(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @description 根据 逻辑幢主键 初始化宗地树 (查FW_LJZ表，获取所有ZRZH)
     */
    ZdTreeResponseDTO initZdTreeByFwDcbIndexAndAllZrzh(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @description 根据 地籍号 初始化宗地树 (查FW_LJZ表，获取所有ZRZH)
     */
    ZdTreeResponseDTO initZdTreeByDjhAndAllZrzh(String djh,String zrzh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description 根据DJH 查询 宗地 信息
     */
    ZdDjdcbDO querZdByDjh(String djh);


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗地
     */
    ZdDjdcbDO queryZdDjdcbByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyhList
     * @return lczt
     * @author
     * @description 根据不动产单元号查询宗地流程状态
     */
    List<String> queryZdLcztByBdcdyh(List<String> bdcdyhList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description 根据DJH 查询宗地 DCB
     */
    ZdDjdcbDO queryZdDjdcbByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description 根据DJH 查询备份宗地 DCB
     */
    HZdDjdcbDO queryHZdDjdcbByDjh(String djh);

    /**
     * @param pageable
     * @param map
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地界址信息信息
     */
    Page<DjdcbJzxxResponseDTO> listZdzjxxByPage(Pageable pageable, Map map);

    /**
     * @param djh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据地籍号查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByDjh(String djh);

    /**
     * 根据地籍号和自然幢号查询有效的房屋户室不动产单元号
     * @param djh  地籍号
     * @param zrzh 自然幢号
     * @return List<String> 房屋户室不动产单元号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<String> listValidBdcdyhByDjhZrzh(String djh, String zrzh);

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.ZdResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地信息
     */
    Page<Map> listZdxxByPageJson(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description 根据ZL 查询 宗地 信息
     */
    ZdDjdcbDO queryZdDjdcbByZlAndBdcdyh(String zl,String bdcdyh);

    List<ZdDjdcbDO> queryZdDjdcbByzl(String zl);
}
