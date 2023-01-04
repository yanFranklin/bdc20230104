package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLcZhgzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzCheckRelatedQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/5
 * @description
 */
public interface BdcGzZhGzRestService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhmc,lclx,zhbs
     *@return List<BdcGzZhgz>
     *@description 分页查询组合规则信息
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/page")
    Page<BdcGzZhgzDO> listBdcGzZhgzPage(@RequestParam(name = "zhmc", required = false) String zhmc,
                                               @RequestParam(name = "zhbs", required = false) String zhbs,
                                               @RequestParam(name = "zhsm", required = false) String zhsm,
                                               @RequestParam(name = "lcmc", required = false) String lcmc,
                                              @RequestParam(name = "glgx", required = false) String glgx,
                                               @RequestParam(name = "gzid", required = false) String gzid,
                                        Pageable pageable);

    /**
     * @param  zhidList 组合id集合
     * @param  gzid 子规则id
     * @return int 新增关联关系记录数
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     * @description 子规则关联批量组合规则
     */
    @PostMapping("/realestate-engine/rest/v1.0/zhgz/glgx")
    int glgx(@RequestBody String[] zhidList, @RequestParam(value ="gzid", required = true) String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param zhgzParamJson  组合规则参数信息
     * @description 分页获取组合规则关联的子规则信息
     */
    @GetMapping("/realestate-engine/rest/v1.0/zhgz/zgz/page")
    Page<BdcGzZgzDO> listBdcZhgzZgzPage(Pageable pageable,
                                      @RequestParam(name = "zhgzParamJson",required = false) String zhgzParamJson);


    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhgzDO
     *@description 新增规则组合信息
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz")
    BdcGzZhgzDO insertBdcGzZhGz(@RequestBody BdcGzZhgzDO bdcGzZhgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@description 根据组合id删除组合规则记录
     */
    @DeleteMapping(value = "/realestate-engine/rest/v1.0/zhgz")
    void delBdcGzZhGz(@RequestParam(name = "zhid",required = true) String zhid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhGzQO
     *@return bdcGzZhgzDOList
     *@description 通过bdcGzZhGzQO查询组合规则list
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/list")
    List<BdcGzZhgzDO> queryBdcGzZhGzDOList(@RequestBody BdcGzZhGzQO bdcGzZhGzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhGzQO  验证查询参数
     * @return {List} 组合标识集合
     * @description 查询组合规则标识信息
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/zhbs")
    List<String> listBdcGzZhgzBs(@RequestBody BdcGzZhGzQO bdcGzZhGzQO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZhgzDO
     *@return num
     *@description 更新不动产组合规则信息
     */
    @PutMapping(value = "/realestate-engine/rest/v1.0/zhgz")
    int updateBdcGzZhGz(@RequestBody BdcGzZhgzDO bdcGzZhgzDO);

    /**
     * 查询组合标识是否唯一
     *
     * @param bdcGzZhgzDO bdcGzZhgzDO
     * @return int 条数
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/countZhbs")
    int countZhbs(@RequestBody BdcGzZhgzDO bdcGzZhgzDO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLcZhgzGxDO 流程规则关系
     * @return 操作数据记录数
     * @description  保存流程和组合规则对照关系
     */
    @PostMapping("/realestate-engine/rest/v1.0/zhgz/bdcLcZhgzGx")
    int saveBdcLcZhgzGx(@RequestBody BdcGzLcZhgzGxDO bdcGzLcZhgzGxDO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  bdcLcZhgzGxParamJson 查询条件
     * @return 流程和规则关系列表
     * @description  查询流程和组合规则对照关系信息
     */
    @GetMapping("/realestate-engine/rest/v1.0/zhgz/bdcLcZhgzGx")
    Page<BdcGzLcZhgzGxDO> listBdcLcZhgzGx(Pageable pageable,
                                          @RequestParam(name = "bdcLcZhgzGxParamJson", required = false)String bdcLcZhgzGxParamJson);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLcZhgzGxDOList 待删除记录
     * @return 删除记录数
     * @description  删除流程和组合规则对照关系信息
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/zhgz/bdcLcZhgzGx")
    int deleteBdcLcZhgzGx(List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  需要校验的规则查询参数
     * @return {BdcGzZhgzTsxxDTO} 提示信息
     * @description  单次规则验证，获取对应提示信息（参数bdcGzYzQO传值：zhbs、paramMap）
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/gzyz")
    BdcGzZhgzTsxxDTO getZhgzYzTsxx(@RequestBody BdcGzYzQO bdcGzYzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数Post
     * @return {List} 批量验证提示信息
     * @description  批量规则验证（参数设置为常用参数）
     *   说明：
     *   1、参数bdcGzYzQO传值：lcbs、bdcGzYzsjDTOList
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/plyz")
    List<BdcGzYzTsxxDTO> listBdcGzYzTsxx(@RequestBody BdcGzYzQO bdcGzYzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数
     * @return {List} 批量验证提示信息
     * @description   批量规则验证（传入任意参数）
     *   说明：
     *   1、参数bdcGzYzQO传值：zhbs、paramList
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/plyz/anyparam")
    List<BdcGzYzTsxxDTO> listBdcGzYzTsxxOfAnyParam(@RequestBody BdcGzYzQO bdcGzYzQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: 组合规则id
     * @return: ｛String｝复制后新的组合规则ID
     * @description 复制当前组合规则的信息，并创建一个新的组合规则
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/{zhid}/fzxgz")
    public String copyBdcGzZhGZ(@PathVariable("zhid") String zhid);

    /**
     * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param: String zhgzJSON
     * @return: BdcCommonResponse
     * @description 导入组合规则
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zhgz/import")
    public BdcCommonResponse importZhgz(@RequestParam(name = "zhgzJSON", required = true)String zhgzJSON);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证项
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @GetMapping("/realestate-engine/rest/v1.0/qzyz/list")
    List<BdcGzZhgzDO> listBdcGzZhgzQzyz();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证信息（包括关联的关联关系、子规则）
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @GetMapping("/realestate-engine/rest/v1.0/qzyz/xx")
    BdcGzQzyzDTO listBdcGzQzyzDTO();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcGzQzyzYzDTO} 验证结果
     * @description  验证强制验证配置内容
     *
     *  强制验证项组合规则标识：流程标识_QZYZ
     */
    @GetMapping("/realestate-engine/rest/v1.0/qzyz/check")
    BdcGzQzyzYzDTO checkQzyz();

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcGzZhGzCheckRelatedQO
     * @return
     * @description 校验哪些流程没有配置关联的 流程转发、新建等组合规则，并且配置了的话是否存在没有关联子规则
     */
    @PostMapping("/realestate-engine/rest/v1.0/qzyz/check/related")
    BdcGzZhGzCheckRelatedDTO checkRelated(@RequestBody BdcGzZhGzCheckRelatedQO bdcGzZhGzCheckRelatedQO);
}
