package cn.gtmap.realestate.init.core.service;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzWcqlsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzXmDTO;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 批量更新不动产单元服务
 */
public interface BdcDyhGzService {

    /**
     * @param dyhGzQO 单元号更正对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新不动产单元号
     */
    void updateBdcdyhPl(DyhGzQO dyhGzQO);

    /**
     * @param xmid 项目ID
     * @param xmtype 1：现势产权项目ID 2新增权利项目ID
     * @return 单元号更正项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取所关联的所有项目数据
     */
    DyhGzXmDTO queryDyhGzXmDTO(String xmid,String xmtype);

    /**
     * @param dyhGzQO 单元号更正对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证选择的数据是否存在疑似关联数据
     */
    List<BdcGzyzVO> yzXzxx(DyhGzQO dyhGzQO);

    /**
     * @param bdcXmDOList 项目集合
     * @return 单元号更正无产权历史关系数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据已选项目单元号获取无产权历史关系数据
     */
    DyhGzWcqlsgxDTO queryDyhGzWcqlsgxDTO(List<BdcXmDO> bdcXmDOList);

    /**
     * @param dyhGzQOList 单元号替换信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 替换外联产权对应的限制权利单元号
     */
    void updateWlcqXzql(List<DyhGzQO> dyhGzQOList);




}
