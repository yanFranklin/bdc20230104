package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 15:01
 * @description 规则系统子规则服务接口
 */
public interface BdcGzZgzService {

    /**
     * 获取子规则数据列表
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param pageable pageable
     * @param bdcGzZgzQO bdcGzZgzDO
     * @return BdcGzZgzDO
     */
    Page<BdcGzZgzDO> listBdcGzZgzPage(Pageable pageable, BdcGzZgzQO bdcGzZgzQO);


    /**
     * @author: <a href="mailto:zhangxinyu2@gtmap.cn">zhangxinyu2</a>
     * @param: String gzid
     * @return: BdcGzZgzDO
     * @description 根据主键gzid查找子规则
     */
    BdcGzZgzDO getZgz(String gzid);

    /**
     * @author: <a href="mailto:zhangxinyu2@gtmap.cn">zhangxinyu2</a>
     * @param: BdcGzGxDO bdcGzGxDO
     * @return: boolean 关联为true,不关联为false
     * @description 判断组合规则和子规则是否关联
     */
    boolean queryBdcGzGx(BdcGzGxDO bdcGzGxDO);

    /**
     * 删除子规则
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcGzZgzDOList bdcGzZgzDOList
     */
    void deleteBdcGzZgz(List<BdcGzZgzDO> bdcGzZgzDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  删除单个子规则
     */
    void deleteBdcGzZgz(BdcGzZgzDO bdcGzZgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@return bdcGzZgzDTO
     *@description 根据gzid获取BdcGzZgzDTO
     */
    BdcGzZgzDTO queryBdcGzZgzDTOByGzid(String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@return bdcGzZgzDTOList
     *@description 根据gzid获取BdcGzZgzDTO列表
     */
    List<BdcGzZgzDTO> queryBdcGzZgzDTOListByZhid(String zhid);


    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@return bdcGzSjlDTO
     *@description 通过gzid获取规则数据流DTO列表
     */
    List<BdcGzSjlDTO> queryBdcGzSjlDTOByGzid(String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid  子规则ID
     * @return {List} 表达式、提示信息集合
     * @description  获取子规则关联的表达式、提示信息集合
     */
    List<BdcGzBdsTsxxDO> queryBdcGzBdsTsxxDOByGzid(String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则DTO
     * @return {String} 子规则主键ID
     * @description 保存子规则信息
     */
    String saveBdcGzZgz(BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则DTO
     * @return {String} 子规则主键ID
     * @description 直接新增子规则（实体中定义的ID都不改变，原样导入）
     */
    void insertBdcGzZgz(BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: {gzid} 子规则ID
     * @return: ｛String｝ 拷贝的子规则主键ID
     * @description 拷贝子规则信息，生成新的子规则
     */
    String copyBdcGzZgz(String gzid);
}
