package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcDldmSyqxGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdDsfzdgxDTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.TreeNodeVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 不动产字典服务
 */
public interface BdcZdRestService {
    /**
     * @param
     * @return  Map<String, List<Map>>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取所有字典
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/list", method = RequestMethod.GET)
    Map<String, List<Map>> listBdcZd();

    /**
     * @param zdmc 字段名称
     * @return List<Map>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据字段名称获取字典项
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/{zdmc}", method = RequestMethod.GET)
    List<Map> queryBdcZd(@PathVariable(name = "zdmc") String zdmc);

    /**
     * @param entity
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 字典项代码转名称
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/convertEntityToMc", method = RequestMethod.POST)
    Object convertEntityToMc(@RequestBody Object entity);
    /**
     * @param entity
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 字典项名称转代码
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/convertEntityToDm", method = RequestMethod.POST)
    Object convertEntityToDm(@RequestBody Object entity);

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 转换map中的字典项代码为名称
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/convertMapToMc", method = RequestMethod.POST)
    Map convertMapToMc(@RequestBody Map convertMap);

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 转换map中的字典项名称为代码
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/convertMapToDm", method = RequestMethod.POST)
    Map convertMapToDm(@RequestBody Map convertMap);

    @RequestMapping(value = "/realestate-register/rest/v1.0/dldmSyqxGx/{xmid}", method = RequestMethod.GET)
    BdcDldmSyqxGxDO queryDldmSyqxGx(@PathVariable(name = "xmid") String xmid);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存字典信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcZd/save", method = RequestMethod.POST)
    int saveBdcZdxx(@RequestBody String json,
                    @RequestParam(name = "className") String className, @RequestParam(name = "action") String action);

    /**
     * 查询第三方系统与登记系统字典项值对照关系
     *
     * @param bdcZdDsfzdgxDO bdcZdDsfzdgxDO
     * @return BdcZdDsfzdgxDO BdcZdDsfzdgxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcZd/dsfZdgx")
    BdcZdDsfzdgxDO dsfZdgx(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 查询数据字典的中国省市区县的树形结构数据
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [] 无参数
     * @return: List<TreeNodeVO> 树形结构的List<TreeNode>
     */
    @GetMapping("/realestate-register/rest/v1.0/bdcZd/zgssqx")
    List<TreeNodeVO> queryZdZgSsqx();

    /**
     * 查询第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return map 字典项数据
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcZd/queryDsfZdgx")
    Map<String, List<Map>> queryDsfZdgx();

    /**
     * 分页查询第三方字典项数据
     * 按字典标识与第三方系统标识进行分组查询
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param pageable 分页查询参数
     * @param json     第三方字典项查询参数的json字符串
     * @return 分页查询数据
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcDsfzdx/page")
    Page<BdcZdDsfzdgxDO> listBbcDsfZdxGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json);

    /**
     * 保存第三方字典关系数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDTO 字典项对照数据DTO
     * @return 字典项数据集合
     */
    @PostMapping("realestate-register/rest/v1.0/bdcDsfzdx/pl")
    List<BdcZdDsfzdgxDO> saveBdcZdDsfzdgx(@RequestBody BdcZdDsfzdgxDTO bdcZdDsfzdgxDTO);

    /**
     * 根据第三方配置项ID删除配置项信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param id 配置项ID
     */
    @DeleteMapping("/realestate-register/rest/v1.0/bdcDsfzdx")
    void deleteDsfZdxById(@RequestParam(name = "id", required = true) String id);

    /**
     * 根据字典标识与第三方字典标识删除第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO 第三方配置项DO
     */
    @DeleteMapping("/realestate-register/rest/v1.0/bdcDsfzdx/bs")
    void deleteDsfZdxBybs(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 据字典标识与第三方字典标识获取第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO
     * @return
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcDsfzdx/queryDsfZdxBybs")
    List<BdcZdDsfzdgxDO> queryDsfZdxBybs(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 根据字典项标识刷新第三方字典项对照关系
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zdbs    字典标识
     * @param dsfxtbs 第三方系统标识
     */
    @GetMapping("/realestate-register/rest/v1.0/bdcZdDsfzdgx/refresh")
    void refreshBdcZdDsfzdgx(@RequestParam(name = "zdbs") String zdbs, @RequestParam(name = "dsfxtbs") String dsfxtbs);

    @GetMapping("/realestate-register/rest/v1.0/bdcZd/refresh/{zdmc}")
    void refreshBdcZd(@PathVariable(name = "zdmc", required = false) String zdmc);

    @GetMapping("/realestate-register/rest/v1.0/bdcZdDsfzdgx/{zdbs}/{dsfxtbs}")
    List<Map> queryDsfZd(@PathVariable(name = "zdbs") String zdbs, @PathVariable(name = "dsfxtbs") String dsfxtbs);


    /**
     * 新增字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-register/rest/v1.0/bdcZd/addItem")
    void addZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 编辑字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-register/rest/v1.0/bdcZd/editItem")
    void editZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 删除字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-register/rest/v1.0/bdcZd/delItem")
    void delZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);


}
