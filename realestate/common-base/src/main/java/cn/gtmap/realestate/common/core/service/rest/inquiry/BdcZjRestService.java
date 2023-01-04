package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/4/10
 * @description 质检查询相关接口
 */
public interface BdcZjRestService {
    /**
     * 查询质检状态
     *
     * @param kssj
     * @param jssj
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zjcx/queryZjzt")
    Object queryZjzt(@RequestParam("kssj") String kssj, @RequestParam("jssj") String jssj);

    /**
     * 创建质检信息
     *
     * @param kssj
     * @param jssj
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zjcx/cjzjxx")
    Integer cjzjxx(@RequestParam("kssj") String kssj, @RequestParam("jssj") String jssj);

    /**
     * 更新质检状态
     *
     * @param bdcZjDOList
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zjcx/zjztgx")
    void zjztgx(@RequestBody List<BdcZjDO> bdcZjDOList);

    /**
     * 质检数据查询
     * @param pageable  分页对象
     * @param zjParamJson 查询条件
     * @return {Page} 质检数据查询分页结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zjcx/list")
    Page<BdcXmDO> listBdcZjxx(Pageable pageable,
                              @RequestParam(name = "zjParamJson", required = false) String zjParamJson);

    /**
     * 根据条件查询质检信息
     * @param bdcZjDO 质检信息DO
     * @return 质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/zjd")
    List<BdcZjDO> queryBdcZjxx(@RequestBody BdcZjDO bdcZjDO);

    /**
     * 随机筛选质检信息，创建质检单生成质检单编号
     * @param bdcZjQO 不动产质检查询DO
     * @return 质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/cj/random/zjd")
    String generateRandomZjd(@RequestBody BdcZjQO bdcZjQO);

    /**
     * 手动筛选质检信息，创建质检单生成质检单编号
     * @param bdcXmDOList 不动产项目信息
     * @return 质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/cj/manual/zjd")
    String generateManualZjd(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * 随机生成 100 条质检项目信息
     * @param bdcZjQO 不动产质检查询对象
     * @return 质检项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/random/zjxmxx")
    List<BdcZjXmxxDTO> randomBdcZjXmxx(@RequestBody BdcZjQO bdcZjQO);

    /**
     * 根据质检单ID获取质检信息
     * @param zjdid 质检单ID
     * @return 质检信息信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/zjdid/{zjdid}")
    List<BdcZjDO> getBdcZjxxByZjdId(@PathVariable(value = "zjdid") String zjdid);

    /**
     * 根据质检ID删除质检信息
     * @param zjid 质检ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/{zjid}")
    void deleteZjxx(@PathVariable(value = "zjid") String zjid);

    /**
     * 获取质检信息以及质检明细内容
     * @param zjid 质检ID
     * @return 质检信息、质检情况明细内容
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/mx/{zjid}")
    BdcZjxxDTO getBdcZjxxAndMx(@PathVariable(value = "zjid") String zjid);
    /**
     * 更新质检单质检状态
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zjd/zjzt/{zjdid}")
    void modifyZjdzt(@PathVariable(value = "zjdid") String zjdid);

    /**
     * 根据质检单ID获取质检单与关联的质检信息
     * @param zjdid 质检单ID
     * @return 质检单以及质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zjd/{zjdid}")
    BdcZjdDTO getBdcZjdAndZjxx(@PathVariable(value = "zjdid") String zjdid);

    /**
     * 保存质检信息和质检明细信息
     * @param bdcZjxxDTO 不动产质检信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/save")
    void saveZjxxAndZjmx(@RequestBody BdcZjxxDTO bdcZjxxDTO);

    /**
     * 根据质检单ID删除质检单信息
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value = "/realestate-inquiry/rest/v1.0/zjd/{zjdid}")
    void deleteZjd(@PathVariable(value = "zjdid") String zjdid);

    /**
     * 保存不动产质检DO信息
     * @param bdcZjDO 不动产质检DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/savezj")
    BdcZjDO saveBdcZjDO(@RequestBody BdcZjDO bdcZjDO);

    /**
     * 查询不动产质检汇总数据
     * @param bdcZjQO 不动产质检QO
     * @return 质检结果汇总数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/zjjg/hz")
    List<BdcZjXmxxDTO> queryBdcZjjgHzxx(@RequestBody BdcZjQO bdcZjQO);

    /**
     * 批量生成质检
     * @param bdcZjDO 不动产质检DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/plzj")
    BdcZjdDO plCreateBdcZj(@RequestBody BdcPlZjDTO bdcPlZjDTO);

    /**
     * 批量质检质检单详情
     * @param zjdbh 质检单编号
     * @return 质检信息
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zjxx/plzj/info")
    public BdcPlZjxxDTO plZjInfo(@RequestParam(value = "zjdbh") String zjdbh);

}
