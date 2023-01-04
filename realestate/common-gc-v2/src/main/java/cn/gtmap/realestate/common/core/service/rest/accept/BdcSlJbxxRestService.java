package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJbxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/10
 * @description 不动产受理基本信息rest服务
 */
public interface BdcSlJbxxRestService {
    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理基本信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jbxx/{jbxxid}")
    BdcSlJbxxDO queryBdcSlJbxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jbxx/processinsid/{gzlslid}")
    BdcSlJbxxDO queryBdcSlJbxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/processinsids")
    List<BdcSlJbxxDO> queryBdcSlJbxxByGzlslids(@RequestBody List<String> gzlslids);

    /**
     * @param slbh 受理编号
     * @param type 1:一窗受理(无登记数据) 2 登记,其他值或空任取一条
     * @return 不动产受理基本信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据受理编号获取不动产受理基本信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jbxx/slbh/{slbh}")
    BdcSlJbxxDO queryBdcSlJbxxBySlbh(@PathVariable(value = "slbh") String slbh, @RequestParam(name = "type", required = false) String type);


    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/xzjbxx")
    BdcSlJbxxDO insertBdcSlJbxx(@RequestBody BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/")
    BdcSlJbxxDO insertBdcSlJbxx(@RequestBody BdcSlJbxxDO bdcSlJbxxDO);

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理基本信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/jbxx/")
    Integer updateBdcSlJbxx(@RequestBody BdcSlJbxxDO bdcSlJbxxDO);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID删除不动产受理基本信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jbxx/{jbxxid}")
    Integer deleteBdcSlJbxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param slbh 重复受理编号
     * @return 处理后受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理重复受理编号
     */
    @GetMapping("/realestate-accept/rest/v1.0/jbxx/dealSameSlbh/{slbh}")
    String dealSameSlbh(@PathVariable(value = "slbh") String slbh);

    /**
     * @return 配置的商品房工作流定义id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取配置的商品房工作流定义id
     */
    @GetMapping("/realestate-accept/rest/v1.0/jbxx/spfGzldyid")
    String spfGzldyid();

    /**
     * @param userDto 用户信息
     * @return 登记结构
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据用户信息获取用户登记机构(根据配置判断读取用户当前部门或部门父级)
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/djjg")
    String queryDjjgByUser(@RequestBody UserDto userDto);

    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 存在规则例外保存不动产受理基本信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/gzlw")
    BdcSlJbxxDO saveGzlwBdcSlJbxx(@RequestBody BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param bdcSlJbxxQO 不动产受理基本信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 获取不动产受理基本信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jbxx/listBdcSlJbxxByJbxxQO")
    List<BdcSlJbxxDO> listBdcSlJbxxByJbxxQO(@RequestBody BdcSlJbxxQO bdcSlJbxxQO);
}
