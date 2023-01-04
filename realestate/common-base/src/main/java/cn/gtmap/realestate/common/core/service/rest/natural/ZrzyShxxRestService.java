package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtMryjDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyShxxQO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyShxxVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.3, 2018/11/3
 * @description 审核信息接口
 */
public interface ZrzyShxxRestService {

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx", method = RequestMethod.PUT)
    int updateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO);

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx", method = RequestMethod.POST)
    int saveOrUpdateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO);

    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/{shxxid}", method = RequestMethod.GET)
    ZrzyShxxDO queryZrzyShxxById(@PathVariable(name = "shxxid") String shxxid);

    /**
     * @param zrzyShxxQO
     * @return List<zrzyShxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取审核信息接口
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/list", method = RequestMethod.POST)
    List<ZrzyShxxDO> queryZrzyShxx(@RequestBody ZrzyShxxQO zrzyShxxQO);


    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxxList", method = RequestMethod.PATCH)
    int updateShxxList(@RequestBody List<ZrzyShxxDO> paramList);

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/sign/{shxxid}", method = RequestMethod.DELETE)
    int deleteShxxSign(@PathVariable(name = "shxxid") String shxxid);

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/sign", method = RequestMethod.DELETE)
    int deleteShxxSign(@RequestBody List<String> shxxidList);

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/workflow/sign/{taskId}", method = RequestMethod.DELETE)
    int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId);

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shjssj/{taskId}", method = RequestMethod.PUT)
    int updateShjssj(@PathVariable(name = "taskId") String taskId);

    @RequestMapping(value = "/realestate-natural/rest/v1.0/mryj/sql", method = RequestMethod.POST)
    String generateMryjBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestBody ZrzyXtMryjDO zrzyXtMryjDO);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的打印类型
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/dylx/{gzlslid}", method = RequestMethod.GET)
    Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param zrzyShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/jdxx", method = RequestMethod.POST)
    List<ZrzyShxxVO> queryShJdxx(@RequestBody ZrzyShxxQO zrzyShxxQO);

    /**
     * @param shxxid 任务Id
     * @return zrzyShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/mryj/{shxxid}", method = RequestMethod.GET)
    ZrzyShxxVO queryMryj(@PathVariable(value = "shxxid") String shxxid);

    /**
     * @param zrzyShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/shxx/sign", method = RequestMethod.POST)
    ZrzyShxxVO getShxxSign(@RequestBody ZrzyShxxDO zrzyShxxDO);

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @PostMapping("/realestate-natural/rest/v1.0/{gzlslid}/shxx")
    List<ZrzyShxxDO> generateShxxOfProInsId(@PathVariable("gzlslid") String gzlslid);


    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @DeleteMapping("/realestate-natural/rest/v1.0/shxx/deleteShxxPdf/{processInsId}")
    void deleteShxxPdf(@PathVariable(value = "processInsId") String processInsId);
}
