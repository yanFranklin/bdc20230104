package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-17
 * @description 为初始化系统提供 查询地籍信息服务
 */
public interface InitDjxxService extends InterfaceCode {

    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 地籍信息
     */
    DjxxResponseDTO getDjxxForInitByBdcdyh(String bdcdyh,String djqlrgxid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据不动产单元号查询 备份表的地籍信息
     */
    DjxxResponseDTO getHDjxxForInitByBdcdyh(String bdcdyh);


    /**
     * @param qlrDO
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description DO权利人实体  转换 成 DTO 后面改成使用dozer组件处理
     */
    DjxxQlrDTO revertQlrDOToDTO(Object qlrDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param withQlr
     * @return java.lang.String
     * @description 获取查询BDCDY的SQL
     */
    String getBdcdyQuerySql(boolean withQlr,String qlrmh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param withQlr
     * @return java.lang.String
     * @description 获取林权查询BDCDY的SQL
     */
    String getLqBdcdyQuerySql(boolean withQlr,String qlrmh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param withQlr
     * @return java.lang.String
     * @description 获取查询历史层BDCDY的SQL
     */
    default String getLsBdcdyQuerySql(boolean withQlr,String qlrmh){
        return "";
    };
}
