package cn.gtmap.realestate.register.service.workflow;

import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjJkGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlSjJkDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjFzDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjPlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcWorkFlowDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 工作流事件统一接口
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-24 14:19
 **/
public interface BdcWorkFlowService {

    /**
     * @param gzlsjlx 工作流事件类型 bdc_zd_gzlsjlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/24 14:21
     */
    void executWorkFlowEvent(Integer gzlsjlx, BdcWorkFlowDTO bdcWorkFlowDTO);

    /**
     * @param bdcGzlSjDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件
     * @date : 2022/3/30 15:26
     */
    BdcGzlSjDO insertBdcGzlSj(BdcGzlSjDO bdcGzlSjDO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件信息
     * @date : 2022/3/30 15:48
     */
    List<BdcGzlSjDO> listBdcGzlsj(BdcGzlQO bdcGzlQO);

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件，同时需要删除工作流事件和接口的关系表数据
     * @date : 2022/3/30 16:01
     */
    void deleteBdcGzlsj(String sjid);

    /**
     * @param bdcGzlJkDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口
     * @date : 2022/3/30 17:13
     */
    int insertBdcGzlJk(BdcGzlJkDO bdcGzlJkDO);

    /**
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流接口
     * @date : 2022/3/30 17:13
     */
    void deleteBdcGzlJk(String jkid);

    /**
     * @param bdcGzlsjJkGxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口关系
     * @date : 2022/3/30 17:19
     */
    int insertBdcGzlJkGx(BdcGzlsjJkGxDO bdcGzlsjJkGxDO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流接口
     * @date : 2022/4/7 16:33
     */
    List<BdcGzlJkDO> listBdcGzljk(BdcGzlQO bdcGzlQO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和接口关联关系
     * @date : 2022/4/11 14:53
     */
    List<BdcGzlsjJkGxDO> listBdcGzlSjJkGx(BdcGzlQO bdcGzlQO);

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/4/12 17:07
     */
    void saveGzlsjGx(String sjid, String jkid);

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/4/12 17:07
     */
    void deleteGzlsjGx(String sjid, String jkid);

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件关系
     * @date : 2022/4/13 16:39
     */
    void deleteGzlsjGx(String sjid);

    /**
     * @param bdcGzlsjJkGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新关联关系
     * @date : 2022/4/14 15:19
     */
    void batchUpdateGzlsjGx(List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOList);

    /**
     * @param bdcGzlSjJkDTO
     * @param paramMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行同步方法
     * @date : 2022/4/18 14:22
     */
    void executeSyncMethord(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap);

    /**
     * @param bdcGzlSjJkDTO
     * @param paramMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行异步方法
     * @date : 2022/4/18 14:24
     */
    void executeAsyncMethord(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap);

    /**
     * @param bdcGzlsjPlDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存工作流事件（勾选多个流程或者多个节点）
     * @date : 2022/4/21 15:21
     */
    void batchSaveGzlsj(BdcGzlsjPlDTO bdcGzlsjPlDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制工作流事件
     * @date : 2023/1/4 11:12
     */
    void copyGzlsj(BdcGzlsjFzDTO bdcGzlsjFzDTO);
}
