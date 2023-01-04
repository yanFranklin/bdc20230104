package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLcZhgzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzQzyzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzQzyzYzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhGzCheckRelatedDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzLcZhgzGxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzCheckRelatedQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/5
 * @description 不动产规则组合规则DAO层服务接口
 */
public interface BdcGzZhGzService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param map,pageable
     *@return list<BdcGzZhGz>
     *@description 分页查询不动产规则组合规则信息
     */
    Page<BdcGzZhgzDO> listBdcXzLwByPage(Pageable pageable, Map map);

    /**
     * @param  zhidList 组合id集合
     * @param  gzid 子规则id
     * @return int 新增关联关系记录数
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     * @description 子规则关联批量组合规则
     */
    int glgx(String[] zhidList, String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhgzDO
     *@description 新增保存bdcGzZhGz信息
     */
    BdcGzZhgzDO insertBdcGzZhGz(BdcGzZhgzDO bdcGzZhgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhgzDO
     *@return num
     *@description 更新BdcGzZhGzDO信息
     */
    int updateBdcGzZhGz(BdcGzZhgzDO bdcGzZhgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhgzDO
     *@return list<BdcGzZhGzDO>
     *@description 查询不动产规则组合规则
     */
    List<BdcGzZhgzDO> queryBdcGzZhGzList(BdcGzZhgzDO bdcGzZhgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@description 通过组合id删除bdcGzZhGz记录
     */
    void delBdcGzZhGz(String zhid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs   组合标识
     * @return {BdcGzZhgzDO} 组合规则
     * @description 获取组合规则
     */
    BdcGzZhgzDO getBdcGzZhgzDO(String zhbs);

    /**
     * 查询组合标识是否唯一
     *
     * @param bdcGzZhgzDO bdcGzZhgzDO
     * @return int 条数
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    int countZhbs(BdcGzZhgzDO bdcGzZhgzDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhGzQO 验证查询参数
     * @return {List} 组合标识集合
     * @description 查询组合规则标识信息
     */
    List<String> listBdcGzZhgzBs(BdcGzZhGzQO bdcGzZhGzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable         分页对象
     * @param bdcGzLcZhgzGxQO 查询条件
     * @return 流程和规则关系列表
     * @description 查询流程和组合规则对照关系信息
     */
    Page<BdcGzLcZhgzGxDO> listBdcLcZhgzGx(Pageable pageable, BdcGzLcZhgzGxQO bdcGzLcZhgzGxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDO 流程规则关系
     * @return 操作数据记录数
     * @description 保存流程和组合规则对照关系
     */
    int saveBdcLcZhgzGx(BdcGzLcZhgzGxDO bdcGzLcZhgzGxDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDOList 待删除记录
     * @return 删除记录数
     * @description 删除流程和组合规则对照关系信息
     */
    int deleteBdcLcZhgzGx(List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param lcbs 流程标识
     * @return 组合规则标识
     * @description 根据流程标识查询匹配的组合规则标识
     */
    String getZhgzbsByLcbs(String lcbs);

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zhid 组合规则id
     * @return 组合规则id
     */
    String copyBdcGzZhgz(String zhid);

    /**
     * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param: String zhgzJSON
     * @return: BdcCommonResponse
     * @description 导入组合规则
     */
    BdcCommonResponse importZhgz(String zhgzJSON);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证项
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    List<BdcGzZhgzDO> listBdcGzZhgzQzyz();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证信息（包括关联的关联关系、子规则）
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    BdcGzQzyzDTO listBdcGzQzyzDTO();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcGzQzyzYzDTO} 验证结果
     * @description 验证强制验证配置内容
     *
     * 强制验证项组合规则标识：流程标识_QZYZ
     */
    BdcGzQzyzYzDTO checkQzyz();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcGzZhGzQO  组合规则参数信息
     * @description 分页获取组合规则关联的子规则信息
     */
    Page<BdcGzZgzDO> listBdcZhgzZgzPage(Pageable pageable, BdcGzZhGzQO bdcGzZhGzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 组合标识
     * @description 根据组合标识获取组合规则
     */
    BdcGzZhgzDTO listBdcZgzByZhbs(String zhbs);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcGzZhGzCheckRelatedQO
     * @return
     * @description 校验哪些流程没有配置关联的 流程转发、新建等组合规则，并且配置了的话是否存在没有关联子规则
     */
    BdcGzZhGzCheckRelatedDTO checkRelated(BdcGzZhGzCheckRelatedQO bdcGzZhGzCheckRelatedQO);
}
