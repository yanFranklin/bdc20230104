package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsQsztDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmAndQlrDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.qo.certificate.*;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/14
 * @description 不动产证书业务服务接口定义
 */
public interface BdcZsRestService {

    /**
     * 从redis中获取保存的xmids
     *
     * @param key
     * @return
     */
    @RequestMapping("/realestate-certificate/rest/v1.0/zs/xmZsyl/getAllXmids")
    List<String> getXmidsByKey(@RequestParam("key") String key);

    /**
     * @param xmids 选中的记录数据
     * @return {String} 保存的Redis key
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书附表的xmids，保存至Redis中
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/xmZsyl/fbyl")
    String saveListXmidsToRedis(@RequestBody String xmids);


    /**
     * @param zsid 证书ID
     * @return {BdcZsDo} 不动产证书
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产权证书
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/{zsid}", method = RequestMethod.GET)
    BdcZsDO queryBdcZs(@PathVariable(value = "zsid") String zsid);

    /**
     * @param zsids 证书IDs
     * @return {BdcZsDo} 不动产证书
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取不动产权证书集合
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zss/{zsids}", method = RequestMethod.GET)
    List<BdcZsDO> queryZsByZsids(@PathVariable(value = "zsids") String zsids);

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zsXm/{zsid}", method = RequestMethod.GET)
    List<BdcXmDO> queryZsXmByZsid(@PathVariable(value = "zsid") String zsid);

    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs", method = RequestMethod.PATCH)
    int updateBdcZs(@RequestBody BdcZsDO bdcZsDO);

    /**
     * @param xmid 项目ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成单个项目不动产项目证书（明）号
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/{xmid}/bdcqzh", method = RequestMethod.GET)
    List<BdcBdcqzhDTO> generateBdcqzh(@PathVariable("xmid") String xmid);

    /**
     * @param processInsId 流程实例ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 流程关联项目生成证书（明）号
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/bdcqzh", method = RequestMethod.GET)
    void generateBdcqzhOfPro(@RequestParam("processInsId") String processInsId);


    /**
     * @param xmid 项目id
     * @return List<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/xm/{xmid}/zs", method = RequestMethod.GET)
    List<BdcZsDO> queryBdcZsByXmid(@PathVariable("xmid") String xmid);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/list", method = RequestMethod.POST)
    List<BdcZsDO> listBdcZs(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param bdcZsQO 证书查询对象
     * @return Integer 查询到的证书数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询证书，返回查询到的结果集
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/num", method = RequestMethod.POST)
    Integer countBdcZs(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param bdcZsQO 证书查询对象
     * @param page    分页查询页数
     * @param size    分页查询行数
     * @param sort    分页查询排序
     * @return Page<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书分页
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/page", method = RequestMethod.POST)
    Page<BdcZsVO> bdcZsByPageJson(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcZsQO bdcZsQO);

    /**
     * @param bdcZsQO 证书查询QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 依据条件查询zsid
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/print/zsidList", method = RequestMethod.POST)
    List<String> queryZsid(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param gzlslid 工作流实例
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 查询当前流程所有的zsid
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zsid/{gzlslid}/list", method = RequestMethod.GET)
    List<String> queryGzlZsid(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @return List<String>  zsid列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前工作流所有的证书类型
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zslx/list", method = RequestMethod.GET)
    List<String> queryGzlZslx(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid);

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的项目权属状态
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zsQszt", method = RequestMethod.POST)
    List<BdcZsQsztDTO> queryBdcZsQszt(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param zsidList 需要更新的证书IDList
     * @param dyzt     打印状态（对应字典表 是否字典表）
     * @return 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书的打印状态
     */
    @RequestMapping("/realestate-certificate/rest/v1.0/zs/dyzt/{dyzt}")
    int updateBdcZsDyzt(@RequestBody List<String> zsidList, @PathVariable(value = "dyzt") Integer dyzt);

    /**
     * 查询原项目的证书信息
     * <p>通过工作流实例ID查询原项目证书信息（去重）</p>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzlslid 工作流实例ID
     * @param: qllx    权利类型
     * @return: 原项目证书信息集合
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/bdcqz/{gzlslid}", method = RequestMethod.GET)
    List<BdcZsDO> queryYxmBdcqzhByGzlslid(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) Integer qllx);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 原项目的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询原项目的证书信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/bdcqz/yxm", method = RequestMethod.POST)
    List<BdcZsDO> queryYxmZs(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return {List} 证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原项目证书信息集合，证书需要按照下一手项目ID排序
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/zs/bdcqz/{gzlslid}/sort")
    List<BdcZsDO> queryYxmZsSortByXmid(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "qllx", required = false) Integer qllx);

    /**
     * 查询未办结的缮证人名称为指定名称的项目 </br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人 id
     * @return {List} 证书项目相关信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/xm/wbj", method = RequestMethod.GET)
    List<BdcZsXmDTO> listWbjywxx(@RequestParam("szrid") String szrid);

    /**
     * 平台办结后执行更新事件 </br>
     * 针对需求 42397 提供的补偿接口服务 </br>
     * <p>
     * 1. 更新发证时间
     *
     * @param bdcZsXmDTOS 证书项目集合
     * @return {int} 更新条数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/xm/wbj", method = RequestMethod.POST)
    int updateWbjxm(@RequestBody List<BdcZsXmDTO> bdcZsXmDTOS);

    /**
     * @param bdcZsDO 证书信息
     * @param zsyl    是否证书预览
     * @param xmid    项目ID (zsyl为true时,xmid必填)
     * @return 共有情况
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * 是否分别持证为否，且产权人共有方式为共同共有--共同共有
     * 是否分别持证为否，且产权人共有方式为按份共有--按份共有 张三 50% 按份共有 李四 50% ....
     * 是否分别持证为是，且产权人共有方式为共同共有--共同共有
     * 是否分别持证为是，且产权人共有方式为按份共有--当前持证产权人名称 当前持证产权人的权利份额
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/gyqk")
    String dealZsGyqk(@RequestBody BdcZsDO bdcZsDO, @RequestParam("zsyl") boolean zsyl, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * 获取不动产单元号关联的锁定证书信息
     *
     * @param bdcZssdQO 查询实体
     * @return java.util.List<BdcZssdDO> 证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/zssd")
    List<BdcZssdDO> listBdcZssdxx(@RequestBody BdcZssdQO bdcZssdQO);

    /**
     * 获取证书信息，盐城自助打证机调用
     *
     * @param bdcZsQO
     * @return
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/query/list/for/zzdzj")
    CommonResponse<List<Map<String, Object>>> queryBdcZsListForZzdzj(@RequestBody BdcZsQO bdcZsQO);

    /**
     * 拆除登记回写证书附记
     *
     * @param processInsId
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zs/fj/update", method = RequestMethod.POST)
    void updateZsFj(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param bdcZsQO xmid:生成多本证时必传,xmid为当前证书对应的xmid集合
     *                 gzlslid:必传
     *                 zsid:生成多本证,且证书已经生成时必传
     * @return 校验证书信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
            * @description 组织校验证书信息
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/zsjy")
    BdcZsVO initJyZsxx(@RequestBody BdcZsQO bdcZsQO) throws Exception;


    /**
     * 添加项目和已有证书的关联关系
     *
     * @param zsid
     * @param xmid
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/addConnection")
    void generateXmZsConnection(@RequestParam("zsid") String zsid, @RequestParam("xmid") String xmid);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据身份证号查询不动产登记信息
     * @Date 2022/5/12 16:16
     **/
    @GetMapping(value = "/realestate-certificate/rest/v1.0/zs/listDjxx")
    List<BdcZsXmAndQlrDTO> listDjxx(@RequestBody BdcZsXmAndQlrQO qo);

    /**
     * 查询证明关联的产权证书信息
     * @param zsid 证书ID
     * @return {List} 证书信息
     * @Author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     **/
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/zmcq")
    List<BdcZsDO> listBdcZsByZmid(@RequestParam("zsid")String zsid);

    /**
     * 更新注销流程的证书信息
     * @param processInsId
     * @throws Exception
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/zx/gxzhzt")
    void gxzhzt(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param zsidList 证书IDList
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zsxx/exportZsqd")
    List<BdcZsqdVO> queryZsQdByZsid(@RequestBody List<String> zsidList, @RequestParam("gzlslid") String gzlslid);
}
