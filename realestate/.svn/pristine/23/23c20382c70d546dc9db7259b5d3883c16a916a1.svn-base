package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/2/23
 * @description 受理审核信息接口
 */
public interface BdcSlShxxRestService {

    /**
     * @param BdcSlShxxDO 审核信息实体类
     * @return int 返回操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新指定节点的审核信息
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx", method = RequestMethod.PUT)
    int updateBdcShxx(@RequestBody BdcSlShxxDO BdcSlShxxDO);

    /**
     * @param BdcSlShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx", method = RequestMethod.POST)
    int saveOrUpdateBdcShxx(@RequestBody BdcSlShxxDO BdcSlShxxDO);

    /**
     * @param shxxid
     * @return int 操作数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据shxxid查询shxx
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx/{shxxid}", method = RequestMethod.GET)
    BdcSlShxxDO queryBdcShxxById(@PathVariable(name = "shxxid") String shxxid);

    /**
     * @param  bdcSlShxxQO
     * @return List<BdcSlShxxDO>
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  获取审核信息接口
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx/list", method = RequestMethod.POST)
    List<BdcSlShxxDO> queryBdcShxx(@RequestBody BdcSlShxxQO bdcSlShxxQO);

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 依据主键更新多条审核信息数据
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxxList", method = RequestMethod.PATCH)
    int updateShxxList(@RequestBody List<BdcSlShxxDO> paramList);

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 签名信息
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/sign/{shxxid}", method = RequestMethod.DELETE)
    int deleteShxx(@PathVariable(name = "shxxid") String shxxid);

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量删除审核意见和签名信息
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/sign", method = RequestMethod.DELETE)
    int deleteShxxSign(@RequestBody List<String> shxxidList);

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/workflow/sign/{taskId}", method = RequestMethod.DELETE)
    int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId);

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shjssj/{taskId}", method = RequestMethod.PUT)
    int updateShjssj(@PathVariable(name = "taskId") String taskId);


    /**
     * @param bdcSlShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx/jdxx", method = RequestMethod.POST)
    List<BdcShxxVO> queryShJdxx(@RequestBody BdcSlShxxQO bdcSlShxxQO);

    /**
     * @param bdcSlShxxDO
     * @return signid
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取sign id
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/shxx/sign", method = RequestMethod.POST)
    BdcShxxVO getShxxSign(@RequestBody BdcSlShxxDO bdcSlShxxDO);


}
