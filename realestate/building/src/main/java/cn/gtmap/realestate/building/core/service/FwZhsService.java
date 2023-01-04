package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 子户室服务接口
 */
public interface FwZhsService {
    /**
     * @param fwZhsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键删除子户室
     */
    Integer deleteZhsByFwZhsIndex(String fwZhsIndex);

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室主键删除子户室
     */
    void deleteZhsByFwHsIndex(String fwHsIndex);

    /**
     * @param fwZhsDO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增子户室
     */
    FwZhsDO insertZhs(FwZhsDO fwZhsDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsDO
     * @return java.lang.Integer
     * @description 更新子户室
     */
    Integer updateZhs(FwZhsDO fwZhsDO);

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室主键查询子户室
     */
    List<FwZhsDO> listFwZhsByFwHsIndex(String fwHsIndex);

    /**
     * @param fwZhsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询子户室
     */
    FwZhsDO queryFwZhsByPk(String fwZhsIndex);


    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量增加子户室
     */
    List<FwZhsDO> batchInsert(List<FwZhsDO> fwZhsDOList);

    /**
     * @param fwZhsDOList
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 关联子户室
     */
    void relevanceZhs(List<FwZhsDO> fwZhsDOList, String fwHsIndex);

    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 取消关联子户室
     */
    void cancelRelevanceZhs(List<FwZhsDO> fwZhsDOList);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param fwHsIndex
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @description 分页查询子户室
     */
    Page<FwZhsDO> listByPage(Pageable pageable, String fwHsIndex);

    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室合并处理子户室
     */
    void hsBgDealZhs(FwHsDO fwHsDO, FwYchsDO fwYchsDO, List<FwZhsDO> fwZhsDOList);

    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋户室合并需要继承的子户室列表（根据配置）
     */
    List<FwZhsDO> listHsHbZhsByIndex(List<String> fwZhsList);

    /**
     * @param fwZhsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室信息实体查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwZhsDO(FwZhsDO fwZhsDO);

    /**
     * @param fwZhsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键list查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwZhsIndexList(List<String> fwZhsIndexList);
}
