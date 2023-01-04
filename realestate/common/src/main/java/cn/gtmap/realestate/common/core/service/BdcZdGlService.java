package cn.gtmap.realestate.common.core.service;


import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/7
 * @description 字典数据操作
 */
public interface BdcZdGlService {

    /**
     * 通过字典class查询数据
     *
     * @param zdClass 字典Class
     * @return 字典数据
     */
    List<HashMap> getZdTableData(Class zdClass);

    /**
     * 新增字典数据
     *
     * @param zdClass 字典Class
     * @return 字典数据
     */
    void addZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 编辑字典数据
     *
     * @param zdClass 字典Class
     * @return 字典数据
     */
    void editZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 删除字典数据
     *
     * @param zdClass 字典Class
     * @return 字典数据
     */
    void delZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    int saveBdcZdTable(Object zd);
}
