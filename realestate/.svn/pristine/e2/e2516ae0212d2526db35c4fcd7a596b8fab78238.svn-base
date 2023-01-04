package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 业务初始化系统对外初始化接口
 */
public interface BdcInitRestService {
    /**
     * 通过传入参数初始化相关系信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcSlxxDTO bdcSlxxDTO
     *@return List<BdcXmDO>
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/csh")
    List<BdcXmDO> csh(@RequestBody BdcSlxxDTO bdcSlxxDTO) throws Exception;

    /**
     * 通过传入项目id数据去删除对应业务信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmids 删除的项目id集合
     *@return
     *@description
     */
    @DeleteMapping(value = "/init/rest/v1.0/delete/ywxx")
    void deleteYwxx(@RequestParam(name="xmids") String[] xmids) throws Exception;

    /**
     * 通过传入工作流实例ID数据去删除对应业务信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid 工作流实例ID
     *@return
     *@description
     */
    @DeleteMapping(value = "/init/rest/v1.0/delete/ywxx/{gzlslid}")
    void deleteYwxx(@PathVariable(name="gzlslid")String gzlslid) throws Exception;

    /**
     * 通过传入项目id数据去查询对应业务信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid 查询的项目id
     *@return
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/query/ywxx/{xmid}")
    BdcYwxxDTO queryYwxx(@PathVariable(name="xmid")String xmid) throws Exception;

    /**
     * 通过传入项目id数据和对应数据结构去更新对应业务信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid 项目
     *@param bdcYwxxDTO 更新数据
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/update/ywxx/{xmid}")
    void updateYwxx(@PathVariable(name="xmid")String xmid,@RequestBody BdcYwxxDTO bdcYwxxDTO) throws Exception;

    /**
     * 插入业务数据
     * @param bdcYwxxDTO
     * @throws Exception
     */
    @PostMapping(value = "/init/rest/v1.0/insert/ywxx")
    String insertYwxx(@RequestBody BdcYwxxDTO bdcYwxxDTO) throws Exception;

    /**
     * 插入业务数据
     * @param bdcYwxxDTOList
     * @throws Exception
     */
    @PostMapping(value = "/init/rest/v1.0/insert/ywxx/list")
    List<BdcYwxxDTO> insertYwxxList(@RequestBody List<BdcYwxxDTO> bdcYwxxDTOList);

    /**
     * 通过传入原对象和目标对象，根据初始化dozer配置进行赋值转换
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param list 原对象和目标对象
     *@param sourceClass 原对象的className
     *@param targetClass 目标对象的className
     *@return  整合后的目标对象
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/ywxx/map")
    Object ywxxDozerMap(@RequestBody List<Object> list,@RequestParam(value = "sourceClass") String sourceClass,@RequestParam(value = "targetClass")String targetClass) throws Exception;


    /**
     * 通过传入参数初始化相关系信息(不入库,返回数据结构)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcSlxxDTO bdcSlxxDTO
     *@return List<BdcXmDO>
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/ycCsh")
    List<BdcYwxxDTO> ycCsh(@RequestBody BdcSlxxDTO bdcSlxxDTO) throws Exception;

    /**
     * 通过传入工作流实例ID数据去删除对应业务信息  将所有子系统的删除汇总
     *@author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param gzlslid 工作流实例ID
     * @param reason 原因
     * @param slzt 受理状态 不传赋值HlwSlztEnum.DELETE.getSlzt()
     *@return
     */
    @DeleteMapping(value = "/init/rest/v1.0/delete/ywxx/allsubsystem/{gzlslid}")
    void deleteYwxxAllSubsystem(@PathVariable(name="gzlslid")String gzlslid, @RequestParam(value = "reason", required = false) String reason,
                                @RequestParam(value = "slzt", required = false) String slzt) throws Exception;

    /**
     * 通过传入工作流实例ID数据去删除对应业务信息  将所有子系统的删除汇总
     * @param bdcDeleteYwxxDTO 删除业务信息DTO对象
     *      gzlslid 工作流实例ID
     *      reason 原因
     *      slzt 受理状态 不传赋值HlwSlztEnum.DELETE.getSlzt()
     * @throws Exception
     */
    @DeleteMapping(value = "/init/rest/v1.0/delete/ywxx/allsubsystem")
    void deleteYwxxAllSubsystem(@RequestBody BdcDeleteYwxxDTO bdcDeleteYwxxDTO) throws Exception;

    /**
     * @param bdcCopyReplaceYwxxDTOList 复制并且替换业务信息参数
     *
     * @return 处理后的业务信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 复制业务信息并替换相关字段
     */
    @PostMapping(value = "/init/rest/v1.0/ywxx/copy/replace")
    List<BdcYwxxDTO> copyAndReplaceYwxx(@RequestBody List<BdcCopyReplaceYwxxDTO> bdcCopyReplaceYwxxDTOList);
}
